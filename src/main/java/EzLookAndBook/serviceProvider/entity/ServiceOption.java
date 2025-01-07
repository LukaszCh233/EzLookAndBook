package EzLookAndBook.serviceProvider.entity;

import jakarta.persistence.*;
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
    private String name;
    private String description;
    private String price;
    @ManyToOne
    @JoinColumn(name = "serviceProvider_id")
    private ServiceProvider serviceProvider;
    @OneToMany(mappedBy = "serviceOption", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Availability> availabilities;
}
