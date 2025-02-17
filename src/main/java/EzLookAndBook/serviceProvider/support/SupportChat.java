package EzLookAndBook.serviceProvider.support;

import EzLookAndBook.account.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SupportChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subject;
    private LocalDate date;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "support_chat_id")
    private List<Chat> chats;
    private Long userId;
    private Role role;
}
