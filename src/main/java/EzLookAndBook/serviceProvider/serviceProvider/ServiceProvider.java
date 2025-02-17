package EzLookAndBook.serviceProvider.serviceProvider;

import EzLookAndBook.account.owner.Owner;
import EzLookAndBook.serviceProvider.serviceOption.ServiceOption;
import EzLookAndBook.serviceProvider.serviceProviderCateogry.ServiceCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ServiceProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "Address cannot be blank")
    private String address;
    @NotBlank(message = "City cannot be blank")
    private String city;
    @NotBlank(message = "Phone number cannot be blank")
    private String numberPhone;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Owner owner;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private ServiceCategory serviceCategory;

    @OneToMany(mappedBy = "serviceProvider", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceOption> serviceOptions;
}
