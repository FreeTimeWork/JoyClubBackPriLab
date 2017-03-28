package com.joycity.joyclub.apiback.modal.upload;

/**
 * Created by CallMeXYZ on 2017/3/28.
 */
public class FileUploadResult {
    private String url;
    private String fileSize;
    private String fileType;

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
