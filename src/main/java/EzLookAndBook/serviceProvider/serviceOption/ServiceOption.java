package EzLookAndBook.serviceProvider.serviceOption;

import EzLookAndBook.serviceProvider.availability.Availability;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProvider;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ServiceOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
    @NotNull(message = "Price cannot be null")
    private String price;
    @ManyToOne
    @JoinColumn(name = "serviceProvider_id")
    private ServiceProvider serviceProvider;
    @OneToMany(mappedBy = "serviceOption", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Availability> availabilities;
}
