package back.image_test.controller;


import back.image_test.dto.ImageRequestDto;
import back.image_test.service.ImageTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ImageTestController {
    private final ImageTestService imageTestService;

    @PostMapping("/test")
    public String test(ImageRequestDto imageRequestDto) throws IOException {
        log.info("requestDto = {}", imageRequestDto);

        return imageTestService.imageCreate(imageRequestDto);
    }
}
