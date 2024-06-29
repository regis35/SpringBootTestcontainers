package bzh.redge.springboottestcontainers.post;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

public record Post(@Id Integer id,
                   Integer userId,

                   @NotEmpty String title,
                   @NotEmpty String content,
                   @Version
                   Integer version) {
}
