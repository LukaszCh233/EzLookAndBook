package EzLookAndBook.mapper;

import EzLookAndBook.serviceProvider.booking.BookingDTO;
import EzLookAndBook.serviceProvider.booking.Booking;
import EzLookAndBook.serviceProvider.availability.Availability;
import EzLookAndBook.serviceProvider.availability.AvailabilityDTO;
import EzLookAndBook.serviceProvider.businessProfile.BusinessDTO;
import EzLookAndBook.serviceProvider.businessProfile.BusinessProfile;
import EzLookAndBook.serviceProvider.businessProfile.BusinessProfileDTO;
import EzLookAndBook.serviceProvider.report.ReportedOpinion;
import EzLookAndBook.serviceProvider.report.ReportedOpinionDTO;
import EzLookAndBook.serviceProvider.report.ReportedOpinionDetailsDTO;
import EzLookAndBook.serviceProvider.serviceOpinion.ServiceOpinion;
import EzLookAndBook.serviceProvider.serviceOpinion.ServiceOpinionDTO;
import EzLookAndBook.serviceProvider.serviceOption.ServiceOption;
import EzLookAndBook.serviceProvider.serviceOption.ServiceOptionDTO;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProvider;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProviderDTO;
import EzLookAndBook.serviceProvider.serviceProviderCateogry.ServiceCategory;
import EzLookAndBook.serviceProvider.serviceProviderCateogry.ServiceCategoryDTO;
import EzLookAndBook.account.admin.AdminDTO;
import EzLookAndBook.account.client.ClientDTO;
import EzLookAndBook.account.owner.OwnerDTO;
import EzLookAndBook.account.admin.Admin;
import EzLookAndBook.account.client.Client;
import EzLookAndBook.account.owner.Owner;
import EzLookAndBook.serviceProvider.support.Chat;
import EzLookAndBook.serviceProvider.support.ChatDTO;
import EzLookAndBook.serviceProvider.support.SupportChat;
import EzLookAndBook.serviceProvider.support.SupportChatDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityMapper {
    public ServiceProviderDTO mapServiceProviderToServiceProviderDTO(ServiceProvider serviceProvider) {
        return new ServiceProviderDTO(serviceProvider.getName(), serviceProvider.getAddress(), serviceProvider.getCity()
                , serviceProvider.getNumberPhone());
    }

    public List<ServiceProviderDTO> mapServiceProviderListToServiceProviderListDTO(List<ServiceProvider> serviceProviderList) {
        return serviceProviderList.stream()
                .map(this::mapServiceProviderToServiceProviderDTO)
                .collect(Collectors.toList());
    }

    public ServiceOpinionDTO mapServiceOpinionToServiceOpinionDTO(ServiceOpinion serviceOpinion) {
        return new ServiceOpinionDTO(serviceOpinion.getUserName(), serviceOpinion.getLocalDateTime(),
                serviceOpinion.getRating(), serviceOpinion.getOpinion());
    }

    public List<ServiceOpinionDTO> mapServiceOpinionListToServiceOpinionListDTO(List<ServiceOpinion> serviceOpinionList) {
        return serviceOpinionList.stream()
                .map(this::mapServiceOpinionToServiceOpinionDTO)
                .collect(Collectors.toList());
    }

    public BusinessProfileDTO mapBusinessProfileToBusinessProfileDTO(BusinessProfile businessProfile) {
        return new BusinessProfileDTO(businessProfile.getId(), businessProfile.getBusinessName(),
                businessProfile.getBusinessAddress(), businessProfile.getCity(),
                businessProfile.getOwner().getName(), businessProfile.getOwner().getLastName()
                ,businessProfile.getOwner().getEmail(), businessProfile.getPhoneNumber(),
                businessProfile.getTaxId(), businessProfile.getServiceCategory().getName());
    }

    public BusinessDTO mapBusinessVerificationToBusinessDTO(BusinessProfile businessProfile) {
        return new BusinessDTO(businessProfile.getId(), businessProfile.getBusinessName());
    }

    public List<BusinessProfileDTO> mapBusinessProfileListToBusinessProfileListDTO(List<BusinessProfile> businessProfileList) {
        return businessProfileList.stream()
                .map(this::mapBusinessProfileToBusinessProfileDTO)
                .collect(Collectors.toList());
    }

    public List<BusinessDTO> mapBusinessVerificationListToBusinessListDTO(List<BusinessProfile> businessProfileList) {
        return businessProfileList.stream()
                .map(this::mapBusinessVerificationToBusinessDTO)
                .collect(Collectors.toList());
    }

    public OwnerDTO mapOwnerToOwnerDTO(Owner owner) {
        return new OwnerDTO(owner.getName(), owner.getLastName(), owner.getEmail());
    }

    public ClientDTO mapClientToClientDTO(Client client) {
        return new ClientDTO(client.getName(), client.getEmail());
    }

    public AdminDTO mapAdminToAdminDTO(Admin admin) {
        return new AdminDTO(admin.getName(), admin.getEmail());
    }

    public ServiceCategoryDTO mapServiceCategoryToServiceCategoryDTO(ServiceCategory serviceCategory) {
        return new ServiceCategoryDTO(serviceCategory.getName());
    }

    public List<ServiceCategoryDTO> mapServiceCategoryListToServiceCategoryListDTO(List<ServiceCategory> serviceCategoryList) {
        return serviceCategoryList.stream()
                .map(this::mapServiceCategoryToServiceCategoryDTO)
                .collect(Collectors.toList());
    }

    public ServiceOptionDTO mapServiceOptionToServiceOptionDTO(ServiceOption serviceOption) {
        return new ServiceOptionDTO(serviceOption.getName(), serviceOption.getDescription(), serviceOption.getPrice());
    }

    public List<ServiceOptionDTO> mapServiceOptionListToServiceOptionListDTO(List<ServiceOption> serviceOptionList) {
        return serviceOptionList.stream()
                .map(this::mapServiceOptionToServiceOptionDTO)
                .collect(Collectors.toList());
    }

    public AvailabilityDTO mapAvailabilityToAvailabilityDTO(Availability availability) {
        return new AvailabilityDTO(availability.getAvailableDate(), availability.getAvailableHours());
    }
    public BookingDTO mapBookingToBookingDTO(Booking booking) {
        return new BookingDTO(booking.getClient().getName(),booking.getNumber(),booking.getClient().getEmail(),
                booking.getServiceOption().getName(),booking.getAppointmentDate(),booking.getAppointmentHour());
    }
    public List<BookingDTO> mapBookingListToBookingListDTO(List<Booking> bookingList) {
        return bookingList.stream()
                .map(this::mapBookingToBookingDTO)
                .collect(Collectors.toList());
    }
    public List<ReportedOpinionDTO> mapReportedOpinionListToReportedOpinionListDTO(List<ReportedOpinion> reportedOpinionList) {
        return reportedOpinionList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public ReportedOpinionDetailsDTO mapReportedOpinionDetailsToReportedOpinionDetailsDTO(ReportedOpinion reportedOpinion) {
        return new ReportedOpinionDetailsDTO(reportedOpinion.getBusinessProfile().getId(),
                reportedOpinion.getBusinessProfile().getBusinessName(),
                reportedOpinion.getBusinessProfile().getOwner().getEmail(), reportedOpinion.getReason(),
                reportedOpinion.getServiceOpinion().getId(),reportedOpinion.getServiceOpinion().getUserId(),
                reportedOpinion.getServiceOpinion().getUserName(),reportedOpinion.getServiceOpinion().getOpinion()
                );
    }
    public SupportChatDTO mapSupportChatToSupportChatDTO(SupportChat supportChat) {
        List<ChatDTO> chatDTOS = mapChatListToChatListDTO(supportChat.getChats());

        return new SupportChatDTO(supportChat.getSubject(),supportChat.getDate(),supportChat.getUserId(),
                supportChat.getRole(),chatDTOS);
    }
    private ChatDTO mapChatToChatDTO(Chat chat) {
        return new ChatDTO(chat.getPersonName(), chat.getText());
    }
    private List<ChatDTO> mapChatListToChatListDTO(List<Chat> chat) {
        return chat.stream()
                .map(this::mapChatToChatDTO)
                .collect(Collectors.toList());
    }
    private ReportedOpinionDTO convertToDTO(ReportedOpinion reportedOpinion) {
        return new ReportedOpinionDTO(reportedOpinion.getId(),reportedOpinion.getServiceOpinion().getId(),
                reportedOpinion.getBusinessProfile().getOwner().getEmail());
    }

}
