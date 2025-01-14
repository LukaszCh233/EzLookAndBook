package EzLookAndBook.serviceProvider.businessProfile;

import EzLookAndBook.serviceProvider.serviceProviderCateogry.ServiceCategory;
import lombok.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BusinessVerificationRequest {
    private String businessName;
    private String businessAddress;
    private String city;
    private String phoneNumber;
    private String taxId;
    private ServiceCategory serviceCategory;
}
