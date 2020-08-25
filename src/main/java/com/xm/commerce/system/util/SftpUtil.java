package com.xm.commerce.system.util;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SftpUtil {
    private static final Logger logger = LoggerFactory.getLogger(SftpUtil.class);
    static SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    JSch jsch = null;
    Session session = null;
    ChannelSftp channel = null;
    static ThreadLocal<SftpUtil> sftpLocal = new ThreadLocal<>();

    SftpUtil(String userName, String host, int port, String password) {
        connect(userName, host, port, password);
    }

    SftpUtil(String userName, String host, int port, String password, String keyFilePath, String passphrase) throws Exception {
        connect(userName, host, port, password, keyFilePath, passphrase);
    }

    boolean isConnected() {
        return null != channel && channel.isConnected();
    }

    static SftpUtil getSftpUtil(String userName, String host, int port, String password) throws Exception {
        SftpUtil sftpUtils = sftpLocal.get();
        if (sftpUtils == null || !sftpUtils.isConnected()) {
            sftpLocal.set(new SftpUtil(userName, host, port, password));
        }
        return sftpLocal.get();
    }

    static SftpUtil getSftpUtil(String userName, String host, int port, String password, String keyFilePath, String passphrase) throws Exception {
        SftpUtil sftpUtils = sftpLocal.get();
        if (sftpUtils == null || !sftpUtils.isConnected()) {
            sftpLocal.set(new SftpUtil(userName, host, port, password, keyFilePath, passphrase));
        }
        return sftpLocal.get();
    }

    static void release() {
        if (null != sftpLocal.get()) {
            sftpLocal.get().close();
            sftpLocal.set(null);
        }
    }

    /**
     * 连接到指定的IP
     */
    public void connect(String userName, String host, int port, String password) {
        try {
            // 创建JSch对象
            jsch = new JSch();
            // 根据用户名、主机ip、端口号获取一个Session对象
            session = jsch.getSession(userName, host, port);
            session.setConfig("PreferredAuthentications", "password");
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setTimeout(60000);
            session.setServerAliveInterval(2000);
            session.setServerAliveCountMax(8);
            session.connect(3000);
            logger.info("sftp session connected.");
            channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect(3000);
            logger.info("Connected successfully ${host} ${userName}");
        } catch (JSchException e) {
            logger.error("sftp connected failed...", e);
        }
    }

    /**
     * 密钥连接到指定的IP
     *
     * @param keyFilePath 密钥路径
     * @param passphrase  密钥的密码
     */
    public void connect(String userName, String host, int port, String password, String keyFilePath, String passphrase) {
        try {
            jsch = new JSch();
            if (keyFilePath != null) {
                if (passphrase != null) {
                    jsch.addIdentity(keyFilePath, passphrase);// 设置私钥
                } else {
                    jsch.addIdentity(keyFilePath);// 设置私钥
                }
//                logger.info("连接sftp，私钥文件路径：" + keyFilePath);
            }
//            logger.info("SFTP Host: " + host + "; UserName:" + userName);
            session = jsch.getSession(userName, host, port);
//            logger.debug("Session 已建立.");
            if (password != null) {
                session.setPassword(password);
            }
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            session.setConfig(sshConfig);
            session.setConfig("kex", "diffie-hellman-group1-sha1");
            session.connect();
//            logger.debug("Session 已连接.");
            channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect(3000);
//            logger.info("连接到SFTP成功.Host: " + host);
        } catch (Exception e) {
//            logger.error("连接SFTP失败：", e);
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接
     */
    void close() {
        if (channel != null) {
            try {
                channel.disconnect();
            } catch (Exception e) {
                logger.error("sftp close channel failed...", e);
            }
        }
        if (session != null) {
            try {
                session.disconnect();
            } catch (Exception e) {
                logger.error("sftp close session failed...", e);
            }
        }
    }
    /**
     * 执行相关的命令,
     * 但是部分情况不可用
     *
     * @throws JSchException
     */
    /*public String execCmd(String command){
        BufferedReader reader = null;
        String result = "";
        if (channel == null) {
            logger.info("SFTP服务器未连接");
            return result;
        }
        try {
            if (command != null) {
                ((ChannelExec) channel).setCommand(command);
                channel.connect();

                InputStream input = channel.getInputStream();
                reader = new BufferedReader(new InputStreamReader(input));
                String buf = null;
                while ((buf = reader.readLine()) != null) {
                    logger.info(buf);
                    result += buf;
                }
            }
        } catch (IOException e) {
            logger.error("远程服务器端IO流错误:" + e.getMessage());
            e.printStackTrace();
        } catch (JSchException e) {
            logger.error("Jsch传输异常:" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                logger.error("远程服务器端IO流关闭失败:" + e.getMessage());
                e.printStackTrace();
            }
        }
        return result;
    }*/

    /**
     * 上传文件
     *
     * @param fileName    上传的文件，含扩展名
     * @param localPath   文件本地目录
     * @param outFilePath 文件上传目录
     * @throws JSchException* @throws FileNotFoundException* 上传本地最新文件，并备份服务器端原文件
     */
    void upload(String fileName, String localPath, String outFilePath) {
        //连接sftp
        if (channel == null) {
            logger.info("SFTP服务器未连接");
            release();
            return;
        }
        String localFilepath = localPath + fileName;
        File localFile = new File(localFilepath);
        FileInputStream input = null;
        try {
            if (!localFile.exists()) {
                logger.warn("本地服务器:上传文件不存在");
                return;
            }
            if (existFile(outFilePath, fileName)) {
                return;
            }
            //上传的文件
            String uploadFile = outFilePath + fileName;
            logger.info("上传文件：" + fileName + "开始");
            input = new FileInputStream(localFile);
            channel.put(input, uploadFile, ChannelSftp.OVERWRITE);
            logger.info("上传到sftp成功");
            //上传完成，备份本地文件
            String localBak = bakFileName(fileName);
            String bakPath = bakPath(localPath, fileName);
            File newfile = new File(bakPath + localBak);
            if (localFile.renameTo(newfile)) {
                logger.info("本地文件备份成功：" + localBak);
            } else {
                logger.info("本地文件备份失败:" + localBak);
            }
        } catch (Exception e) {
            logger.info("上传文件：" + fileName + "失败");
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            release();
        }
    }

    /**
     * 上传文件某文件夹下所有文件
     *
     * @param fileName    上传的文件，含扩展名
     * @param localPath   文件本地目录
     * @param outFilePath 文件上传目录
     * @throws JSchException* @throws FileNotFoundException*
     */
    void uploadAllFile(String fileName, String localPath, String outFilePath, String bakPath) {
        //连接sftp
        if (channel == null) {
            logger.info("SFTP服务器未连接");
            release();
            return;
        }
        File[] files = new File[]{};
        File localFile = new File(localPath);
        FileInputStream input = null;
        List<FileInputStream> ins = new ArrayList<>();
        try {
            if (!localFile.exists()) {
                logger.warn("本地服务器:上传文件不存在");
                return;
            }
            if (existFile(outFilePath, fileName)) {
                return;
            }

            if (localFile.isDirectory()) {
                files = localFile.listFiles();
                if (files == null || files.length <= 0) {
                    return;
                }
                for (File f : files) {
                    //上传的文件
                    String uploadFile = outFilePath + f.getName();
                    logger.info("上传文件：" + f.getName() + "开始");
                    input = new FileInputStream(f);
                    ins.add(input);
                    channel.put(input, uploadFile, ChannelSftp.OVERWRITE);
                    logger.info("上传到sftp成功");
                }
            }
            logger.info(files.length + "---------------------------------------");
        } catch (Exception e) {
            logger.info("上传文件：" + fileName + "失败");
            e.printStackTrace();
        } finally {
            for (FileInputStream in : ins) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            release();
            //上传完成，备份本地文件
            if (files != null) {
                if (bakPath != null) {
                    for (File file : files) {
                        file.renameTo(new File(bakPath + bakFileName(file.getName())));
                    }
                } else {
                    for (File file : files) {
                        logger.info("删除文件：" + file.getName() + "--：" + file.delete());
                    }
                }
            }
        }
    }


    /**
     * 下载文件
     * 备份本地文件，再下载服务器端最新文件
     *
     * @param localPath  本地存放路径
     * @param remotePath 服务器端存放路径
     * @throws JSchException
     */
    void download(String fileName, String localPath, String remotePath) throws Exception {
        // src linux服务器文件地址，dst 本地存放地址
        if (channel == null) {
            logger.info("SFTP服务器未连接");
            return;
        }
        String remoteFile = remotePath + fileName;
        String localFile = localPath + fileName;
        FileOutputStream output = null;
        try {
            if (!existFile(remotePath, fileName)) {
                logger.warn("SFTP服务器:下载文件不存在" + remotePath + fileName);
                return;
            }
            File file = new File(localFile);
            if (file.exists()) {
                //创建新名字的抽象文件
                String bakname = bakFileName(fileName);
                String bakPath = bakPath(localPath, fileName);
                File newfile = new File(bakPath + bakname);
                if (file.renameTo(newfile)) {
                    logger.info("本地文件：" + fileName + "备份成功");
                } else {
                    logger.info("本地文件：" + fileName + "备份失败，将覆盖原文件！");
                }
            }
            output = new FileOutputStream(file);
            logger.info("下载文件：" + fileName + "开始");
            channel.get(remoteFile, output);
            logger.info("下载文件：" + fileName + "成功");
            //下载完成后备份服务器文件
            String bakFile = bakFileName(fileName);
            String bakRemotePath = bakPath(remotePath, fileName);
            //channel.rename(remoteFile, bakRemotePath + fileName)
            //delete(remotePath, fileName)
            logger.info("服务器端文件备份成功：" + bakFile);
        } catch (Exception e) {
            logger.info("下载文件：" + fileName + "失败");
            throw e;
        } finally {
            if (output != null) {
                output.close();
            }
            release();
        }
    }

    /**
     * 删除文件
     *
     * @param directory  要删除文件所在目录
     * @param deleteFile 要删除的文件
     * @throws JSchException
     */
    void delete(String directory, String deleteFile) throws Exception {
        if (channel == null) {
            logger.info("SFTP服务器未连接");
            return;
        }
        channel.cd(directory);
        channel.rm(deleteFile);
        logger.info("删除成功");
    }

    /**
     * 列出目录下的文件
     *
     * @param directory 要列出的目录
     * @return
     * @throws SftpException
     */
    @SuppressWarnings("rawtypes")
    Vector listFiles(String directory) throws SftpException {
        if (channel == null) {
            logger.info("SFTP服务器未连接");
            return null;
        }
        Vector vector = channel.ls(directory);
        return vector;
    }

    /**
     * 判断文件是否存在
     *
     * @param directory
     * @param filename
     * @return
     * @throws SftpException
     */
    Boolean existFile(String directory, String filename) throws SftpException {
        Vector files = listFiles(directory);
        boolean rst = false;
        for (Object file : files) {
            ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) file;
            if (entry.getFilename().equals(filename)) {
                rst = true;
            }
        }
        return rst;
    }

    String bakFileName(String sourceName) {
        return sourceName + "." + df.format(new Date()) + System.currentTimeMillis();
    }

    String bakPath(String outFilePath, String fileName) {
        List<String> names = Arrays.asList(fileName.split(".csv"));
        if (outFilePath.lastIndexOf("/") != -1) {
            return outFilePath.substring(0, outFilePath.lastIndexOf("/")) + "bak/";
        } else if (outFilePath.lastIndexOf("\\") != -1) {
            return outFilePath.substring(0, outFilePath.lastIndexOf("\\")) + "bak\\";
        }
        return outFilePath + "bak/";
    }
}
