package com.xm.commerce.system.util;

import com.google.common.collect.ImmutableMap;
import com.xm.commerce.common.exception.FileUploadException;
import com.xm.commerce.common.exception.ResourceNotFoundException;
import com.xm.commerce.system.constant.EcommerceConstant;
import com.xm.commerce.system.model.dto.FileUploadDto;
import com.xm.commerce.system.model.dto.PictureDto;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.xm.commerce.system.constant.EcommerceConstant.SUPPORTED_PIC_SUFFIX;

@Component
public class FileUtil {

	@Resource
	FtpConfigReader ftpConfigReader;

	public FileUploadDto fileUpload(MultipartFile multipartFiles, String uploadDir) throws Exception {
		if (multipartFiles.isEmpty()) {
			throw new FileUploadException();
		}
		//文件上传
		String oldFilename = multipartFiles.getOriginalFilename();
		String suffixName = FilenameUtils.getExtension(oldFilename);
		if (suffixName == null) {
			throw new ResourceNotFoundException();
		}
		String extension = FilenameUtils.getExtension(oldFilename);
		if (!Arrays.asList(SUPPORTED_PIC_SUFFIX).contains(extension)) {
			throw new FileUploadException(ImmutableMap.of("格式不正确", extension));
		}
		byte[] bytes = multipartFiles.getBytes();
		return FtpUtils.uploadFile(ftpConfigReader.getMap(), Collections.singletonList(new PictureDto(bytes, FilenameUtils.getExtension(oldFilename))), uploadDir).get(0);
	}

//	public FileUploadDto markUpload(MultipartFile multipartFile, String uploadDir) throws Exception {
//		//文件上传
//		String oldFilename = multipartFile.getOriginalFilename();
//		String suffix = Objects.requireNonNull(oldFilename).substring(oldFilename.lastIndexOf(".") + 1);
//		String newFilename = UUID.randomUUID().toString() + "." + suffix;
//		byte[] bytes = multipartFile.getBytes();
//		int size = Integer.parseInt(String.valueOf(multipartFile.getSize()));
//		FtpUtils.uploadFile(ftpConfigReader.getMap(), Collections.singletonList(bytes), uploadDir);
//		String url = ftpConfigReader.getFilePath() + uploadDir + "/" + newFilename;
//		return new FileUploadDto(url, size);
//	}

	public List<FileUploadDto> fileUploadByBytes(List<PictureDto> pictureDtoList, String uploadDir) throws Exception {
		//文件上传
		return FtpUtils.uploadFile(ftpConfigReader.getMap(), pictureDtoList, uploadDir);
	}

	public List<FileUploadDto> fileUploadByByte(PictureDto pictureDto, String uploadDir) throws Exception {
		//文件上传
		return FtpUtils.uploadFile(ftpConfigReader.getMap(), Collections.singletonList(pictureDto), uploadDir);
	}

	public static boolean checkFileSuffix(MultipartFile file, String[] suffix) {
		String filename = file.getOriginalFilename();
		for (String s : suffix) {
			if (filename != null && filename.endsWith(s)) {
				return true;
			}
		}
		return false;
	}
}
