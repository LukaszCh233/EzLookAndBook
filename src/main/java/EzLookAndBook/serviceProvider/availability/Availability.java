package EzLookAndBook.serviceProvider.availability;

import EzLookAndBook.serviceProvider.serviceOption.ServiceOption;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime availableDate;
    @ElementCollection
    private List<LocalTime> availableHours;
    @ManyToOne
    @JoinColumn(name = "service_option_id", nullable = false)
    private ServiceOption serviceOption;

}