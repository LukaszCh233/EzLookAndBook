package EzLookAndBook.serviceProvider.serviceOpinion;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OpinionRequest {
    @Min(1)
    @Max(5)
    @NotNull(message = "rating cannot be null")
    private int rating;
    @NotBlank(message = "Opinion cannot be blank")
    private String opinion;
}
