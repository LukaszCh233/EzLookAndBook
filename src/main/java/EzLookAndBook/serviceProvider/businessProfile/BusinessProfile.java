package EzLookAndBook.serviceProvider.businessProfile;

import EzLookAndBook.account.owner.Owner;
import EzLookAndBook.account.owner.VerificationStatus;
import EzLookAndBook.serviceProvider.serviceProviderCateogry.ServiceCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BusinessProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String businessName;
    private String businessAddress;
    private String city;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Owner owner;
    private String phoneNumber;
    private String taxId;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private ServiceCategory serviceCategory;
    @Enumerated(EnumType.STRING)
    private VerificationStatus status = VerificationStatus.PENDING;
}
