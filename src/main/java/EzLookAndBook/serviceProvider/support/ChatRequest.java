package EzLookAndBook.serviceProvider.support;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ChatRequest {
    @NotBlank(message = "Text cannot be blank")
    String text;
}
