package com.foolday.serviceweb.dto.image;

import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.io.File;
import java.io.InputStream;

@Data
@Builder
public class FileDto {
    private String name;

    @Nullable
    private String originalFilename;

    @Nullable
    private String contentType;

    /**
     * 文件描述
     */
    @Nullable
    private String description;

    private boolean empty;

    private long size;

    private byte[] bytes;

    private InputStream inputStream;

    private File file;

    /**
     * 文件上传后每个都分配一个uuid32
     */
    private String imageId;
}
