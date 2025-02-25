package EzLookAndBook.serviceProvider.report;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ReportedOpinionRequest {
    @NotNull(message = "Id cannot be null")
    private Long serviceOpinionId;
    @NotBlank(message = "Reason cannot be blank")
    @Size(max = 500, message = "Reason cannot exceed 500 characters")
    private String reason;
}
