package EzLookAndBook.serviceProvider.booking;

import EzLookAndBook.account.client.Client;
import EzLookAndBook.serviceProvider.serviceOption.ServiceOption;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProvider;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceProvider serviceProvider;
    @ManyToOne
    @JoinColumn(name = "serviceOption_id", nullable = false)
    private ServiceOption serviceOption;
    private String number;
    private LocalDate appointmentDate;
    private LocalTime appointmentHour;
    private Status status;
}
