package com.xm.commerce.system.util;

import com.jcraft.jsch.*;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Map;

public class FtpUtils {

	/**
	 * 利用JSch包实现SFTP上传文件
	 *
	 * @param bytes    文件字节流
	 * @param fileName 文件名
	 */
	public static void uploadFile(Map<String, String> conf, byte[] bytes, String fileName, String path) throws Exception {

		String ip = conf.get("ip");
		String user = conf.get("user");
		String password = conf.get("password");
		int port = Integer.parseInt(conf.get("port"));
		String filePath = conf.get("filePath");
		String parentFilePath = conf.get("parentFilePath");
		String prvKeyFilePath = conf.get("prvKeyFilePath");
		String pubKeyFilePath = conf.get("pubKeyFilePath");

		Session session;
		Channel channel = null;
		JSch jsch = new JSch();
		if (prvKeyFilePath != null){
			jsch.addIdentity(prvKeyFilePath, pubKeyFilePath, "".getBytes());
		}
		if (port <= 0) {
			//连接服务器，采用默认端口
			session = jsch.getSession(user, ip);
		} else {
			//采用指定的端口连接服务器
			session = jsch.getSession(user, ip, port);
		}
		//如果服务器连接不上，则抛出异常
		if (session == null) {
			throw new Exception("session is null");
		}
		//设置登陆主机的密码
		if (password != null){
			//设置密码
			session.setPassword(password);
		}
		//设置第一次登陆的时候提示，可选值：(ask | yes | no)
		session.setConfig("StrictHostKeyChecking", "no");
		//设置登陆超时时间
		session.connect(3000);
		OutputStream outstream = null;
		try {
			//创建sftp通信通道
			channel = session.openChannel("sftp");
			channel.connect(1000);
			ChannelSftp sftp = (ChannelSftp) channel;
			//进入服务器指定的文件夹
			//创建路径
			path = parentFilePath + filePath + path;
			createDir(parentFilePath, path, sftp);

//			sftp.cd(path);
			//以下代码实现从本地上传一个文件到服务器，如果要实现下载，对换以下流就可以了
			outstream = sftp.put(fileName);
			outstream.write(bytes);
		} finally {
			//关流操作
			if (outstream != null) {
				outstream.flush();
				outstream.close();
			}
			session.disconnect();
			if (channel != null) {
				channel.disconnect();
			}
		}
	}

	/**
	 * 创建一个文件目录
	 */
	private static void createDir(String parentFilePath, String createPath, ChannelSftp sftp) throws SftpException {
		if (!isDirExist(createPath, sftp)) {
			String[] pathArray = createPath.split("/");
			for (String path : pathArray) {
				if ("".equals(path)) {
					continue;
				}
				if (!isDirExist(path, sftp)) {
					// 建立目录
					sftp.mkdir(path);
					// 进入并设置为当前目录
				}
				sftp.cd(path);
			}
		}else {
			sftp.cd(createPath);
		}
	}

	/**
	 * 判断目录是否存在
	 */
	private static boolean isDirExist(String directory, ChannelSftp sftp) {
		boolean isDirExistFlag = false;
		try {
			SftpATTRS sftpATTRS = sftp.lstat(directory);
			isDirExistFlag = true;
			return sftpATTRS.isDir();
		} catch (Exception e) {
			if ("no such file".equals(e.getMessage().toLowerCase())) {
				isDirExistFlag = false;
			}
		}
		return isDirExistFlag;
	}

	public static byte[] download(String parentFilePath, String fromPath, String user, String password, String ip, int port) throws Exception {
		JSch jsch = new JSch();
		Session session = jsch.getSession(user, ip, port);
		if (session == null) {
			throw new Exception("session is null");
		}
		session.setPassword(password);
		session.setConfig("StrictHostKeyChecking", "no");
		session.connect(30000);

		ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");

		channel.connect();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		BufferedOutputStream buff = new BufferedOutputStream(outputStream);
		String absolutePath = parentFilePath + fromPath;
		channel.get(absolutePath, buff);
		channel.disconnect();

		return outputStream.toByteArray();
	}
}
