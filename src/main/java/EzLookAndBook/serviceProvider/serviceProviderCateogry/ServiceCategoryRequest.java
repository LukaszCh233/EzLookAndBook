package EzLookAndBook.serviceProvider.serviceProviderCateogry;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCategoryRequest {
    @NotBlank(message = "Name must not be blank")
    private String name;
}
