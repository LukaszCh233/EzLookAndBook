package EzLookAndBook.serviceProvider.report;

public record ReportedOpinionDetailsDTO(Long businessId, String businessName, String email, String reason,
                                        Long serviceOpinionId, Long userId, String userName, String opinion) {
}
