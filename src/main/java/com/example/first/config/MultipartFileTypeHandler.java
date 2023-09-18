package com.example.first.config;


import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MultipartFileTypeHandler extends BaseTypeHandler<MultipartFile> {

    @Override
    public void setNonNullParameter(
            java.sql.PreparedStatement ps, int i, MultipartFile parameter, JdbcType jdbcType) throws java.sql.SQLException {
        // 여기서 MultipartFile을 데이터베이스에 맞게 변환 및 설정합니다.
        // 예를 들어, 바이트 배열로 변환하여 데이터베이스 컬럼에 저장할 수 있습니다.
        byte[] fileBytes = new byte[0];
        try {
            fileBytes = parameter.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ps.setBytes(i, fileBytes);
    }

    @Override
    public MultipartFile getNullableResult(
            java.sql.ResultSet rs, String columnName) throws java.sql.SQLException {
        // 여기서 데이터베이스 컬럼에서 MultipartFile로 변환합니다.
        // 바이트 배열을 읽어와서 MultipartFile로 변환할 수 있습니다.
        byte[] fileBytes = rs.getBytes(columnName);
        return new MyMultipartFile(fileBytes); // MyMultipartFile은 사용자 정의 클래스로 바이트 배열을 MultipartFile로 변환하는 역할을 합니다.
    }

    @Override
    public MultipartFile getNullableResult(
            java.sql.ResultSet rs, int columnIndex) throws java.sql.SQLException {
        // 위와 동일한 내용을 처리합니다.
        return null;
    }

    @Override
    public MultipartFile getNullableResult(
            java.sql.CallableStatement cs, int columnIndex) throws java.sql.SQLException {
        // 위와 동일한 내용을 처리합니다.
        return null;
    }

    private class MyMultipartFile implements MultipartFile {
        public MyMultipartFile(byte[] fileBytes) {
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public String getOriginalFilename() {
            return null;
        }

        @Override
        public String getContentType() {
            return null;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public long getSize() {
            return 0;
        }

        @Override
        public byte[] getBytes() throws IOException {
            return new byte[0];
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return null;
        }

        @Override
        public void transferTo(File dest) throws IOException, IllegalStateException {

        }
    }
}
