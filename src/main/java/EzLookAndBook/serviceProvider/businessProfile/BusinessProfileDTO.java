package EzLookAndBook.serviceProvider.businessProfile;

public record BusinessProfileDTO(Long id, String businessName, String businessAddress, String city,
                                 String ownerName,
                                 String ownerLastName, String ownerEmail,
                                 String phoneNumber, String taxId, String category) {
}
