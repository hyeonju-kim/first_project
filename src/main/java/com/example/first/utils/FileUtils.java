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
    private static final Tika tika = new Tika();
    /* 다운로드할 첨부파일(리소스) 조회 (as Resource)
     * @param file - 첨부파일 상세정보
     * @return 첨부파일(리소스) */
    public Resource readFileAsResource(final BoardMultiFile file) {
        // 첨부파일의 저장 경로를 가져옵니다.
        String savePath = file.getSavePath();
        Path filePath = Paths.get(savePath);

        System.out.println("savePath = " + savePath);
        System.out.println("filePath = " + filePath);

        try {
            // 파일 경로를 이용하여 리소스 객체를 생성합니다.
            Resource resource = new UrlResource(filePath.toUri());

            // 리소스가 존재하지 않거나 파일이 아닌 경우 예외를 발생시킵니다.
            if (!resource.exists() || !resource.isFile()) {
                throw new RuntimeException("file not found : " + filePath.toString());
            }

            return resource;
        } catch (MalformedURLException e) {
            // 파일 경로가 잘못된 경우 예외를 발생시킵니다.
            throw new RuntimeException("file not found : " + filePath.toString());
        }
    }


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
