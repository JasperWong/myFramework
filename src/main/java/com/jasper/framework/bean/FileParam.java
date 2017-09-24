package com.jasper.framework.bean;

import java.io.InputStream;

public class FileParam {

    private String fieldName;
    private String fileName;
    private long fileSize;
    private String conetenType;
    private InputStream inputStream;

    public FileParam(String fieldName, String fileName, long fileSize, String conetenType, InputStream inputStream) {
        this.fieldName = fieldName;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.conetenType = conetenType;
        this.inputStream = inputStream;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getConetenType() {
        return conetenType;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
