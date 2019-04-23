package com.foolday.admin.base;

import com.foolday.common.exception.PlatformException;
import com.foolday.common.util.PlatformAssert;
import com.foolday.serviceweb.dto.image.FileDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MultipartFileUtils {
    /**
     * 2 filedto
     * 敢不敢多写点注释？？？
     *
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public static FileDto toFileDto(MultipartFile multipartFile) {
        PlatformAssert.notNull(multipartFile);
        try {
            return FileDto.builder()
                    .originalFilename(multipartFile.getOriginalFilename())
                    .name(multipartFile.getName())
                    .inputStream(multipartFile.getInputStream())
                    .contentType(multipartFile.getContentType())
                    .bytes(multipartFile.getBytes())
                    .size(multipartFile.getSize())
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            throw new PlatformException("文件解析异常,e{}", e);
        }
    }

    /**
     * back list fileDto
     *
     * @param multipartFiles
     * @return
     */
    public static List<FileDto> toFileDtos(MultipartFile... multipartFiles) {
        return Stream.of(multipartFiles)
                .map(MultipartFileUtils::toFileDto)
                .collect(Collectors.toList());
    }

}
