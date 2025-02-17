package EzLookAndBook.serviceProvider.businessProfile;

import EzLookAndBook.account.owner.VerificationStatus;

public record BusinessProfileDTO(Long id, String businessName, String businessAddress, String city,
                                 String ownerName,
                                 String ownerLastName, String ownerEmail,
                                 String phoneNumber, String taxId, String category, VerificationStatus status) {
}
