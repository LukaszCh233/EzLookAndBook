package EzLookAndBook.serviceProvider.serviceOpinion;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OpinionRequest {
    @Min(1)
    @Max(5)
    private int rating;
    @NotBlank(message = "Opinion cannot be blank")
    private String opinion;
}
