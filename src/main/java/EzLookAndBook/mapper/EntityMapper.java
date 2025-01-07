package EzLookAndBook.mapper;

import EzLookAndBook.serviceProvider.dto.*;
import EzLookAndBook.serviceProvider.entity.*;
import EzLookAndBook.user.dto.AdminDTO;
import EzLookAndBook.user.dto.ClientDTO;
import EzLookAndBook.user.dto.OwnerDTO;
import EzLookAndBook.user.entity.Admin;
import EzLookAndBook.user.entity.Client;
import EzLookAndBook.user.entity.Owner;
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
                businessProfile.getPhoneNumber(), businessProfile.getOwner().getName(),
                businessProfile.getOwner().getLastName(), businessProfile.getOwner().getEmail(),
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
}
