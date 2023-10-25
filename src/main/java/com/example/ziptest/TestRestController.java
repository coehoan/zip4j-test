package com.example.ziptest;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Data
@AllArgsConstructor
@RestController
public class TestRestController {
    private final String PASSWORD = "test123";
    private final String FILE = "src\\main\\resources\\static\\result.json"; // 압축 할 파일 경로
    private final String FOLDER = "src\\main\\resources\\static\\10100"; // 압축 할 폴더 경로
    private final String CURRENT_PATH = System.getProperty("user.dir");

    @PostMapping("/zip")
    public void zip() throws IOException {
        String zipPath = CURRENT_PATH + File.separator + "files.zip";

        try {
            ZipFile zipFile = new ZipFile(zipPath); // zipPath에 새로운 zip 파일 생성
            ZipParameters param = new ZipParameters();

            param.setCompressionMethod(CompressionMethod.DEFLATE); // zip 파일 압축 알고리즘
            param.setCompressionLevel(CompressionLevel.HIGHER); // zip 파일 압축 레벨

            zipFile.addFile(FILE, param); // zip 파일에 파일 추가
//            zipFile.addFolder(new File(FOLDER), param); // zip 파일에 폴더 추가
            zipFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/zip-password")
    public void zipWithPassword() throws IOException {
        String zipPath = CURRENT_PATH + File.separator + "protected_files.zip";

        try {
            ZipFile zipFile = new ZipFile(zipPath, PASSWORD.toCharArray()); // zipPath에 새로운 zip 파일 생성 + 패스워드 설정
            ZipParameters param = new ZipParameters();

            param.setCompressionMethod(CompressionMethod.DEFLATE); // zip 파일 압축 알고리즘
            param.setCompressionLevel(CompressionLevel.HIGHER); // zip 파일 압축 레벨

            param.setEncryptFiles(true);
            param.setEncryptionMethod(EncryptionMethod.AES); // 패스워드 AES256 암호화

            zipFile.addFile(FILE, param); // zip 파일에 파일 추가
//            zipFile.addFolder(new File(FOLDER), param); // zip 파일에 폴더 추가
            zipFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/unzip")
    public void unzip(MultipartFile file) throws IOException {
        try {
            if (!file.isEmpty()) {
                ZipFile zipFile = new ZipFile(file.getOriginalFilename()).isEncrypted()
                        ? new ZipFile(file.getOriginalFilename(), PASSWORD.toCharArray())
                        : new ZipFile(file.getOriginalFilename());;
                String zipPath = CURRENT_PATH + File.separator + "files";

                zipFile.extractAll(zipPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
