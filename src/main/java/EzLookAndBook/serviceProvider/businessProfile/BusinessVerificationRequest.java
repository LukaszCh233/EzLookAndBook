package EzLookAndBook.serviceProvider.businessProfile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BusinessVerificationRequest {
    @NotBlank(message = "Name cannot be blank")
    private String businessName;
    @NotBlank(message = "Address cannot be blank")
    private String businessAddress;
    @NotBlank(message = "City cannot be blank")
    private String city;
    @NotBlank(message = "Phone number cannot be blank")
    private String phoneNumber;
    @NotBlank(message = "Tax ID cannot be blank")
    @Size(max = 20, message = "Tax ID cannot exceed 20 characters")
    private String taxId;
    @NotNull(message = "Service category id cannot be null")
    private Long serviceCategoryId;
}
