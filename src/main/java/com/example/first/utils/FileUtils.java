package com.example.first.utils;

import com.example.first.dto.BoardMultiFile;
import org.apache.tika.Tika;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Component
public class FileUtils {
    /**
     * 다운로드할 첨부파일(리소스) 조회 (as Resource)
     * @param file - 첨부파일 상세정보
     * @return 첨부파일(리소스)
     */
    public Resource readFileAsResource(final BoardMultiFile file) {
        String savePath = file.getSavePath();
        Path filePath = Paths.get(savePath);

        System.out.println("savePath = " + savePath);
        System.out.println("filePath = " + filePath);

        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists() || !resource.isFile()) {
                throw new RuntimeException("file not found : " + filePath.toString());
            }
            return resource;
        } catch (MalformedURLException e) {
            throw new RuntimeException("file not found : " + filePath.toString());
        }
    }







    private static final Tika tika = new Tika();

    // 파일 업로드 시, 확장자 검사
    public static boolean validImgFile(InputStream inputStream) {
        try {

            // 아래와 일치하는 확장자만 허용
            List<String> ValidTypeList = Arrays.asList(
                    "image/jpeg", "image/pjpeg", "image/png", "image/gif", "image/bmp", "image/x-windows-bmp",
                    "application/vnd.ms-excel", // 엑셀 파일
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // XLSX 파일
                    "application/pdf" // PDF 파일
            );

            String mimeType = tika.detect(inputStream);
            System.out.println("MimeType : " + mimeType);

            boolean isValid = ValidTypeList.stream().anyMatch(notValidType -> notValidType.equalsIgnoreCase(mimeType));
            return isValid;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
