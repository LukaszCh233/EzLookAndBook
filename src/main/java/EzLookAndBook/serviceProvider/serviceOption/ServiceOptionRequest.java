package EzLookAndBook.serviceProvider.serviceOption;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceOptionRequest {
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    @NotBlank(message = "Description cannot be blank")
    private String description;
    @NotNull(message = "Price cannot be null")
    private String price;
}
