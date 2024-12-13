package EzLookAndBook.serviceProvider.dto;

import java.time.LocalDateTime;

public record ServiceOpinionDTO(String userName, LocalDateTime localDateTime, int rating, String opinion) {
}
