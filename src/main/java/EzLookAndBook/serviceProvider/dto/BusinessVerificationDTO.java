package EzLookAndBook.serviceProvider.dto;

public record BusinessVerificationDTO(Long id, String businessName, String businessAddress, String city,
                                      String ownerName,
                                      String ownerLastName, String ownerEmail,
                                      String phoneNumber, String taxId, String category) {
}
