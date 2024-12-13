package EzLookAndBook.serviceProvider.entity;

import EzLookAndBook.user.entity.Owner;
import jakarta.persistence.*;
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
    private String name;
    private String address;
    private String city;
    private String numberPhone;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Owner owner;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private ServiceCategory serviceCategory;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceOption> serviceOptions;
}
