package EzLookAndBook.serviceProvider.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
    private String opinion;
}
