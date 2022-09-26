package com.ssafy.rideus.common.api;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.rideus.common.exception.NotMatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static com.ssafy.rideus.common.exception.NotMatchException.CONTENT_TYPE_NOT_MATCH;

@Component
@RequiredArgsConstructor
public class S3Upload {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3Client amazonS3Client;

    public String uploadImageToS3(MultipartFile image) {
        String imageUrl = null;

        if(image != null) {
            if (!image.getContentType().startsWith("image")) {
                throw new NotMatchException(CONTENT_TYPE_NOT_MATCH);
            }

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(image.getSize());
            objectMetadata.setContentType(image.getContentType());
            String storeName = UUID.randomUUID().toString();

            try {
                amazonS3Client.putObject((new PutObjectRequest(bucket, storeName, image.getInputStream(), objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead)));

                // 이미지 url 가져오기
                imageUrl = amazonS3Client.getUrl(bucket, storeName).toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return imageUrl;
    }
}
