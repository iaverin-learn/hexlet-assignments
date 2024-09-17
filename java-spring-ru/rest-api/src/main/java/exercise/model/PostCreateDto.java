package exercise.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostCreateDto {
    private String slug;
    private String title;
    private String body;
}
