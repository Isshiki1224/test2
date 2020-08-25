package com.xm.commerce.system.util;


import com.google.common.collect.ImmutableMap;
import com.xm.commerce.system.constant.EcommerceConstant;
import com.xm.commerce.common.exception.FileUploadException;
import com.xm.commerce.common.exception.ResourceNotFoundException;
import com.xm.commerce.system.model.dto.FileUploadDto;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.FilenameUtils;


import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
public class FileUtil {

    @Resource
    FtpConfigReader ftpConfigReader;
    @Resource
    FileConfigReader fileConfigReader;

    private static final int SHOW_WIDTH = 160;
    private static final int SHOW_HEIGHT = 160;
    private static final String SHOW_SUFFIX = "jpg";

    public List<FileUploadDto> filesUpload(MultipartFile[] multipartFiles, String uploadDir) throws Exception {
        List<FileUploadDto> fileUploadDtos = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            FileUploadDto fileUploadDto = fileUpload(multipartFile, uploadDir);
            fileUploadDtos.add(fileUploadDto);
            String uri = fileUploadDto.getUri();
            String fileName = uri.substring(uri.lastIndexOf("/")+1);
            thumbnailUpload(ImageIO.read(multipartFile.getInputStream()),fileName,uploadDir);
        }
        return fileUploadDtos;
    }

    public FileUploadDto fileUpload(MultipartFile multipartFile, String uploadDir) throws Exception {
        if (multipartFile.isEmpty()) {
            throw new FileUploadException();
        }
        //文件上传
        String oldFilename = multipartFile.getOriginalFilename();
        int size = (int) multipartFile.getSize();
        String suffixName = FilenameUtils.getExtension(oldFilename);
        if (suffixName == null) {
            throw new ResourceNotFoundException();
        }
        if (!EcommerceConstant.IMAGE_SUFFIX1.equals(suffixName) && !EcommerceConstant.IMAGE_SUFFIX2.equals(suffixName) && !EcommerceConstant.IMAGE_SUFFIX3.equals(suffixName)) {
            throw new FileUploadException(ImmutableMap.of("格式不正确", suffixName));
        }
        String newFilename = UUID.randomUUID().toString() + oldFilename.substring(oldFilename.lastIndexOf("."));
        byte[] bytes = multipartFile.getBytes();
        FtpUtils.uploadFile(ftpConfigReader.getMap(), bytes, newFilename, uploadDir);
        String url = uploadDir + newFilename;
        return new FileUploadDto(url, size);
    }

    public FileUploadDto markUpload(MultipartFile multipartFile, String uploadDir) throws Exception {
        //文件上传
        String oldFilename = multipartFile.getOriginalFilename();
        String suffix = Objects.requireNonNull(oldFilename).substring(oldFilename.lastIndexOf(".") + 1);
        String newFilename = UUID.randomUUID().toString() + "." + suffix;
        byte[] bytes = multipartFile.getBytes();
        int size = Integer.parseInt(String.valueOf(multipartFile.getSize()));
        FtpUtils.uploadFile(ftpConfigReader.getMap(), bytes, newFilename, uploadDir);
        String url = ftpConfigReader.getFilePath() + uploadDir + "/" + newFilename;
        FileUploadDto fileUploadDto = new FileUploadDto(url, size);
        return fileUploadDto;
    }

    public void thumbnailUpload(Image image, String saveFileName, String uploadDir) throws Exception {
        Image scaledInstance = image.getScaledInstance(SHOW_WIDTH, SHOW_HEIGHT, Image.SCALE_SMOOTH);
        BufferedImage bufferedImage = new BufferedImage(SHOW_WIDTH, SHOW_HEIGHT, BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(scaledInstance, 0, 0, null);
        bufferedImage.getGraphics().dispose();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, SHOW_SUFFIX, byteArrayOutputStream);
        fileUploadByBytes(byteArrayOutputStream.toByteArray(),
                saveFileName + "." + SHOW_SUFFIX,
                byteArrayOutputStream.size(),
                uploadDir);
    }

    public FileUploadDto fileUploadByBytes(byte[] bytes, String newFilename, int size, String uploadDir) throws Exception {
        //文件上传
        FtpUtils.uploadFile(ftpConfigReader.getMap(), bytes, newFilename, uploadDir);
        String url = ftpConfigReader.getFilePath() + uploadDir + "/" + newFilename;
        FileUploadDto fileUploadDto = new FileUploadDto(url, size);
        return fileUploadDto;
    }

//    public FileUploadDto updateFile(MultipartFile multipartFile, String uploadDir) throws Exception {
//        //文件上传
//        String oldFilename = multipartFile.getOriginalFilename();
//        int size = (int) multipartFile.getSize();
//        byte[] bytes = multipartFile.getBytes();
//        FtpUtils.uploadFile(ftpConfigReader.getMap(), bytes, oldFilename, uploadDir);
//        String url = ftpConfigReader.getFilePath() + uploadDir + "/" + oldFilename;
//        FileUploadDto fileUploadDto = new FileUploadDto(url, ftpConfigReader.getIp(), size);
//        return fileUploadDto;
//    }

    public boolean checkFileSuffix(MultipartFile file){
        String filename = file.getOriginalFilename();
        String[] suffix = fileConfigReader.getSuffix();
        for (String s : suffix) {
            if (filename != null && filename.endsWith(s)) {
                return true;
            }
        }
        return false;
    }



}
