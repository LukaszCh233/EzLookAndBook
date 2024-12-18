package EzLookAndBook.mapper;

import EzLookAndBook.serviceProvider.dto.BusinessVerificationDTO;
import EzLookAndBook.serviceProvider.dto.ServiceOpinionDTO;
import EzLookAndBook.serviceProvider.dto.ServiceProviderDTO;
import EzLookAndBook.serviceProvider.entity.BusinessVerification;
import EzLookAndBook.serviceProvider.entity.ServiceOpinion;
import EzLookAndBook.serviceProvider.entity.ServiceProvider;
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

    public BusinessVerificationDTO mapBusinessVerificationToBusinessVerificationDTO(BusinessVerification businessVerification) {
        return new BusinessVerificationDTO(businessVerification.getId(), businessVerification.getBusinessName(),
                businessVerification.getBusinessAddress(), businessVerification.getCity(),
                businessVerification.getPhoneNumber(), businessVerification.getOwner().getName(),
                businessVerification.getOwner().getLastName(), businessVerification.getOwner().getEmail(),
                businessVerification.getTaxId(), businessVerification.getServiceCategory().getName());
    }

    public List<BusinessVerificationDTO> mapBusinessVerificationListToBusinessVerificationListDTO(List<BusinessVerification> businessVerificationList) {
        return businessVerificationList.stream()
                .map(this::mapBusinessVerificationToBusinessVerificationDTO)
                .collect(Collectors.toList());
    }
    public OwnerDTO mapOwnerToOwnerDTO(Owner owner) {
        return new OwnerDTO(owner.getName(), owner.getLastName(),owner.getEmail());
    }
    public ClientDTO mapClientToClientDTO(Client client) {
        return new ClientDTO(client.getName(), client.getEmail());
    }
    public AdminDTO mapAdminToAdminDTO(Admin admin) {
        return new AdminDTO(admin.getName(), admin.getEmail());
    }
}
