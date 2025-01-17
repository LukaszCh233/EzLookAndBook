package EzLookAndBook.serviceProvider.support;

import EzLookAndBook.account.Role;

import java.time.LocalDate;
import java.util.List;

public record SupportChatDTO(String subject, LocalDate date, Long userId, Role role, List<ChatDTO> chats) {

}

