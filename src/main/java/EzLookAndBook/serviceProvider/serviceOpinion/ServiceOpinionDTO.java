package EzLookAndBook.serviceProvider.serviceOpinion;

import java.time.LocalDateTime;

public record ServiceOpinionDTO(String userName, LocalDateTime localDateTime, int rating, String opinion) {
}
