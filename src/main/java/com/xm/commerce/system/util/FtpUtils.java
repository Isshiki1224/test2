package com.xm.commerce.system.util;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import com.xm.commerce.system.model.dto.FileUploadDto;
import com.xm.commerce.system.model.dto.PictureDto;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FtpUtils {

	/**
	 * 利用JSch包实现SFTP上传文件
	 */
	public static List<FileUploadDto> uploadFile(Map<String, String> conf, List<PictureDto> pictureDtos, String path) throws Exception {

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
		if (StringUtils.isBlank(prvKeyFilePath) && StringUtils.isBlank(pubKeyFilePath)) {
			jsch.addIdentity(prvKeyFilePath, pubKeyFilePath, "".getBytes());
		}
		if (port < 1 || port > ((1 << 16) - 1)) {
			// 连接服务器，采用默认端口
			session = jsch.getSession(user, ip);
		} else {
			// 采用指定的端口连接服务器
			session = jsch.getSession(user, ip, port);
		}
		// 设置登陆主机的密码
		if (password != null) {
			//设置密码
			session.setPassword(password);
		}
		// 设置第一次登陆的时候提示，可选值：(ask | yes | no)
		session.setConfig("StrictHostKeyChecking", "no");
		//设置登陆超时时间
		session.connect(3000);
		try {
			// 创建sftp通信通道
			channel = session.openChannel("sftp");
			channel.connect(1000);
			ChannelSftp sftp = (ChannelSftp) channel;
			// 进入服务器指定的文件夹
			// 创建路径
			path = parentFilePath + filePath + path;
			createDir(path, sftp);
			// 以下代码实现从本地上传一个文件到服务器，如果要实现下载，对换以下流就可以了
			List<FileUploadDto> fileUploadDtoList = new ArrayList<>();
			for (PictureDto pictureDto : pictureDtos) {
				UUID uuid = UUID.nameUUIDFromBytes(pictureDto.getBytes());
				try (OutputStream outputStream = sftp.put(uuid.toString() + pictureDto.getSuffix())) {
					outputStream.write(pictureDto.getBytes());
				}
				fileUploadDtoList.add(new FileUploadDto(ip + path + uuid.toString(), pictureDto.getBytes().length));
			}
			return fileUploadDtoList;
		} finally {
			session.disconnect();
			if (channel != null) {
				channel.disconnect();
			}
		}
	}

	/**
	 * 创建一个文件目录
	 */
	private static void createDir(String createPath, ChannelSftp sftp) throws SftpException {
		if (isDirNotExist(createPath, sftp)) {
			String[] pathArray = createPath.split("/");
			for (String path : pathArray) {
				if ("".equals(path)) {
					continue;
				}
				if (isDirNotExist(path, sftp)) {
					// 建立目录
					sftp.mkdir(path);
					// 进入并设置为当前目录
				}
				sftp.cd(path);
			}
		} else {
			sftp.cd(createPath);
		}
	}

	/**
	 * 判断目录是否不存在
	 */
	private static boolean isDirNotExist(String directory, ChannelSftp sftp) {
		boolean isDirExistFlag = false;
		try {
			SftpATTRS sftpATTRS = sftp.lstat(directory);
			isDirExistFlag = true;
			return !sftpATTRS.isDir();
		} catch (Exception e) {
			if ("no such file".equals(e.getMessage().toLowerCase())) {
				isDirExistFlag = false;
			}
		}
		return !isDirExistFlag;
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
