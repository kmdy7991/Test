package back.image_test.service;

import back.image_test.dto.ImageRequestDto;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageTestService {
    private final AmazonS3 amazonS3;
    private final OpenAiImageModel openAiImageModel;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String imageCreate(ImageRequestDto imageRequestDto) throws IOException {
        ImageResponse call = openAiImageModel.call(
                new ImagePrompt(makeOrder(imageRequestDto.getLyrics(), imageRequestDto.getKeyword()),
                        OpenAiImageOptions.builder()
                                .withQuality("hd")
                                .withN(1)
                                .withHeight(1024)
                                .withWidth(1024)
                                .withResponseFormat("b64_json")
                                .build())
        );

        String b64 = call.getResult().getOutput().getB64Json();
        byte[] image = Base64.decodeBase64(b64);
        log.info("base 64 = {}", image);

        return makeFile(image);

    }

    private String makeOrder(String lyric, String keyword) {
        StringBuffer order = new StringBuffer();

        order.append("Create an album cover for a ")
                .append(keyword)
                .append(" song. Reflect the lyrics: ")
                .append(lyric)
                .append(".\n")
                .append("    The cover should visually represent the mood and theme of the song. Do not include any text.\n")
                .append("I want good quality.");
        return order.toString();
    }

    public String makeFile(byte[] image) throws IOException {
        int cnt = 1024;
        String url = "";

        try (ByteArrayOutputStream out = new ByteArrayOutputStream(cnt)) {

            int offset = 0;
            while (offset < image.length) {
                int chunkSize = Math.min(cnt, image.length - offset);

                byte[] byteArray = new byte[chunkSize];
                System.arraycopy(image, offset, byteArray, 0, chunkSize);

                out.write(byteArray);
                out.flush();

                offset += chunkSize;
            }

            // ByteArrayOutputStream -> ByteArrayInputStream
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(out.toByteArray());


            // MultipartFile 객체 생성
            MultipartFile multipartFile = new MockMultipartFile(UUID.randomUUID() + ".jpg", byteArrayInputStream.readAllBytes());

            url = upload(multipartFile);

//        s3Uploader.saveFile(multipartFile); //s3에 멀티파트 파일로 직접 업로드
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }

    public String upload(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getName();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket + "/album", multipartFile.getName(), multipartFile.getInputStream(), metadata);
        return amazonS3.getUrl(bucket + "/album", originalFilename).toString();
    }
}
