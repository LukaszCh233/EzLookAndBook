package EzLookAndBook.serviceProvider.entity;

import EzLookAndBook.user.VerificationStatus;
import EzLookAndBook.user.entity.Owner;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BusinessVerification {
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
