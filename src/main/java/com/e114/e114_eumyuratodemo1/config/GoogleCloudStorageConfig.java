package com.e114.e114_eumyuratodemo1.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

//사진 파일 저장용 구글클라우드 스토리지 설정
@Configuration
public class GoogleCloudStorageConfig {
    @Bean
    public Storage storage() throws IOException {

        ClassPathResource resource = new ClassPathResource("composite-helix-386116-10c2d6745c9a.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream());
        String projectId = "composite-helix-386116";
        return StorageOptions.newBuilder()
                .setProjectId(projectId)
                .setCredentials(credentials)
                .build()
                .getService();
    }
}
