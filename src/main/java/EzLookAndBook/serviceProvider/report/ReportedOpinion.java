package EzLookAndBook.serviceProvider.report;

import EzLookAndBook.serviceProvider.serviceOpinion.ServiceOpinion;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProvider;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ReportedOpinion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "serviceProvider_id", nullable = false)
    private ServiceProvider serviceProvider;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "service_opinion_id", nullable = false)
    private ServiceOpinion serviceOpinion;
    @Enumerated(EnumType.STRING)
    private ReportStatus status;
    private String reason;
}
