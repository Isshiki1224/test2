package com.xm.commerce.system.model.dto;

public class FileUploadDto {

    private String uri;
    private Integer size;

    public FileUploadDto() {
    }

    public FileUploadDto(String uri, Integer size) {
        this.uri = uri;
        this.size = size;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
