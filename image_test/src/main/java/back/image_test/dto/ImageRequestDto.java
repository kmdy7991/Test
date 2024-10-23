package back.image_test.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class ImageRequestDto {
    private String userId;
    private String musicId;
    private String lyrics;
    private String keyword;
}
