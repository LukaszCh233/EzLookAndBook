package EzLookAndBook.serviceProvider.controller;

import EzLookAndBook.serviceProvider.availability.AvailabilityDTO;
import EzLookAndBook.serviceProvider.availability.AvailabilityService;
import EzLookAndBook.serviceProvider.serviceOpinion.ServiceOpinionDTO;
import EzLookAndBook.serviceProvider.serviceOpinion.ServiceOpinionService;
import EzLookAndBook.serviceProvider.serviceOption.ServiceOptionDTO;
import EzLookAndBook.serviceProvider.serviceOption.ServiceOptionService;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProviderDTO;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProviderService;
import EzLookAndBook.serviceProvider.serviceProviderCateogry.ServiceCategoryDTO;
import EzLookAndBook.serviceProvider.serviceProviderCateogry.ServiceCategoryService;
import EzLookAndBook.serviceProvider.support.ChatRequest;
import EzLookAndBook.serviceProvider.support.SupportChatDTO;
import EzLookAndBook.serviceProvider.support.SupportRequest;
import EzLookAndBook.serviceProvider.support.SupportService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/common")
public class CommonController {
    private final ServiceCategoryService serviceCategoryService;
    private final ServiceProviderService serviceProviderService;
    private final ServiceOptionService serviceOptionService;
    private final AvailabilityService availabilityService;
    private final SupportService supportService;
    private final ServiceOpinionService serviceOpinionService;

    public CommonController(ServiceCategoryService serviceCategoryService, ServiceProviderService serviceProviderService,
                            ServiceOptionService serviceOptionService, ServiceOpinionService serviceOpinionService,
                            AvailabilityService availabilityService, SupportService supportService) {
        this.serviceCategoryService = serviceCategoryService;
        this.serviceProviderService = serviceProviderService;
        this.serviceOptionService = serviceOptionService;
        this.availabilityService = availabilityService;
        this.supportService = supportService;
        this.serviceOpinionService = serviceOpinionService;
    }

    @GetMapping("/serviceCategory/{categoryId}")
    public ResponseEntity<ServiceCategoryDTO> displayCategory(@PathVariable Long categoryId) {

        ServiceCategoryDTO serviceCategory = serviceCategoryService.findServiceCategoryById(categoryId);

        return ResponseEntity.ok(serviceCategory);
    }

    @GetMapping("/serviceCategory")
    public ResponseEntity<List<ServiceCategoryDTO>> displayServiceCategoryList() {
        List<ServiceCategoryDTO> serviceCategoryList = serviceCategoryService.findAllCategories();

        return ResponseEntity.ok(serviceCategoryList);
    }

    @GetMapping("/serviceOpinions/{serviceProviderId}")
    public ResponseEntity<List<ServiceOpinionDTO>> displayServiceOpinions(@PathVariable Long serviceProviderId) {
        List<ServiceOpinionDTO> serviceOpinionDTOS = serviceOpinionService.
                findServiceProviderOpinionsById(serviceProviderId);

        return ResponseEntity.ok(serviceOpinionDTOS);
    }

    @GetMapping("/serviceOpinion/{serviceOpinionId}")
    public ResponseEntity<ServiceOpinionDTO> displayServiceOpinion(@PathVariable Long serviceOpinionId) {
        ServiceOpinionDTO serviceOpinionDTO = serviceOpinionService.findServiceOpinionById(serviceOpinionId);

        return ResponseEntity.ok(serviceOpinionDTO);
    }

    @GetMapping("supportChat/{supportChatId}")
    public ResponseEntity<SupportChatDTO> displaySupportChat(@PathVariable Long supportChatId) {
        SupportChatDTO supportChat = supportService.findSupportChatById(supportChatId);

        return ResponseEntity.ok(supportChat);
    }

    @PostMapping("/chat/{supportChatId}")
    public ResponseEntity<SupportChatDTO> sendAnswerChat(@PathVariable Long supportChatId,
                                                         @Valid @RequestBody ChatRequest chatRequest,
                                                         Principal principal) {
        SupportChatDTO supportChat = supportService.createAnswerToChat(supportChatId, chatRequest, principal);

        return ResponseEntity.ok(supportChat);
    }

    @PostMapping("/supportChat")
    public ResponseEntity<?> sendSupportChatRequestToAdmin(@Valid @RequestBody SupportRequest supportRequest,
                                                           Principal principal) {
        supportService.createSupportChatRequest(supportRequest, principal);

        return ResponseEntity.ok("Request has been sent");
    }

    //not work
    @GetMapping("/serviceProvider/option/{serviceOption}")
    public ResponseEntity<List<ServiceProviderDTO>> displayServiceProviderListByServiceOption(
            @PathVariable String serviceOption, Principal principal) {
        List<ServiceProviderDTO> serviceProviderDTOList = serviceProviderService.
                findServiceProviderByServiceOption(serviceOption, principal);

        return ResponseEntity.ok(serviceProviderDTOList);
    }

    @GetMapping("/serviceOptions/{serviceProviderId}")
    public ResponseEntity<List<ServiceOptionDTO>> displayServiceOptions(@PathVariable Long serviceProviderId) {
        List<ServiceOptionDTO> serviceOptionDTOList = serviceOptionService.findAllServiceOption(serviceProviderId);

        return ResponseEntity.ok(serviceOptionDTOList);
    }

    @GetMapping("/serviceProvider/{serviceProviderId}")
    public ResponseEntity<ServiceProviderDTO> displayServiceProviderById(@PathVariable Long serviceProviderId,
                                                                         Principal principal) {
        ServiceProviderDTO serviceProviderDTO = serviceProviderService.findServiceProviderById(serviceProviderId,
                principal);

        return ResponseEntity.ok(serviceProviderDTO);
    }

    @GetMapping("/serviceProvider/category/{categoryId}")
    public ResponseEntity<List<ServiceProviderDTO>> displayServiceProviderListByCategoryId(@PathVariable Long categoryId,
                                                                                           Principal principal) {
        List<ServiceProviderDTO> serviceProviderDTOList = serviceProviderService.
                findServiceProviderByCategoryId(categoryId, principal);

        return ResponseEntity.ok(serviceProviderDTOList);
    }

    @GetMapping("/serviceProvider/categoryName/{categoryName}")
    public ResponseEntity<List<ServiceProviderDTO>> displayServiceProviderListByCategoryName(
            @PathVariable String categoryName, Principal principal) {
        List<ServiceProviderDTO> serviceProviderDTOList = serviceProviderService.
                findServiceProviderByCategoryName(categoryName, principal);

        return ResponseEntity.ok(serviceProviderDTOList);
    }

    @GetMapping("/availability/option/{optionId}")
    public ResponseEntity<List<AvailabilityDTO>> displayAvailabilityDateOption(@PathVariable Long optionId) {
        List<AvailabilityDTO> availabilityDTO = availabilityService.findAvailabilityDateOption(optionId);

        return ResponseEntity.ok(availabilityDTO);
    }
}
