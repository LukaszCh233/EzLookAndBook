package EzLookAndBook.serviceProvider.controller;

import EzLookAndBook.serviceProvider.businessProfile.BusinessDTO;
import EzLookAndBook.serviceProvider.businessProfile.BusinessOwnerDTO;
import EzLookAndBook.serviceProvider.businessProfile.BusinessProfileDTO;
import EzLookAndBook.serviceProvider.businessProfile.BusinessProfileService;
import EzLookAndBook.serviceProvider.report.ReportedOpinionDTO;
import EzLookAndBook.serviceProvider.report.ReportedOpinionDetailsDTO;
import EzLookAndBook.serviceProvider.report.ReportedOpinionService;
import EzLookAndBook.serviceProvider.serviceOpinion.ServiceOpinionService;
import EzLookAndBook.serviceProvider.serviceProviderCateogry.ServiceCategoryRequest;
import EzLookAndBook.serviceProvider.serviceProviderCateogry.ServiceCategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminPanelController {
    private final BusinessProfileService businessProfileService;
    private final ServiceCategoryService serviceCategoryService;
    private final ServiceOpinionService serviceOpinionService;
    private final ReportedOpinionService reportedOpinionService;

    public AdminPanelController(BusinessProfileService businessProfileService,
                                ServiceCategoryService serviceCategoryService,
                                ServiceOpinionService serviceOpinionService,
                                ReportedOpinionService reportedOpinionService) {
        this.businessProfileService = businessProfileService;
        this.serviceCategoryService = serviceCategoryService;
        this.serviceOpinionService = serviceOpinionService;
        this.reportedOpinionService = reportedOpinionService;
    }

    @PostMapping("/serviceCategory")
    public ResponseEntity<String> addServiceCategoryToService(@RequestBody @Valid ServiceCategoryRequest serviceCategory) {
        serviceCategoryService.createServiceCategory(serviceCategory);
        return ResponseEntity.ok("Category has been added");
    }

    @GetMapping("/businessList/pending")
    public ResponseEntity<List<BusinessDTO>> displayBusinessListToVerification() {
        List<BusinessDTO> businessVerificationDTOList = businessProfileService.findAllBusinessForVerification();

        return ResponseEntity.ok(businessVerificationDTOList);
    }

    @GetMapping("/businessList/approved")
    public ResponseEntity<List<BusinessDTO>> displayApprovedBusinessList() {
        List<BusinessDTO> businessVerificationDTOList = businessProfileService.findAllVerifiedBusiness();

        return ResponseEntity.ok(businessVerificationDTOList);
    }

    @GetMapping("/businessList/rejected")
    public ResponseEntity<List<BusinessDTO>> displayRejectedBusinessList() {
        List<BusinessDTO> businessVerificationDTOList = businessProfileService.findAllRejectedBusiness();

        return ResponseEntity.ok(businessVerificationDTOList);
    }

    @PostMapping("/verification/approve/{businessVerificationId}")
    public ResponseEntity<String> approveBusinessVerification(@PathVariable Long businessVerificationId) {
        businessProfileService.approveVerification(businessVerificationId);

        return ResponseEntity.ok("Successful verification");
    }

    @PostMapping("/verification/reject/{businessVerificationId}")
    public ResponseEntity<String> rejectBusinessVerification(@PathVariable Long businessVerificationId) {
        businessProfileService.rejectVerification(businessVerificationId);

        return ResponseEntity.ok("Successful rejected");
    }

    @GetMapping("/businessProfile/{businessProfileId}")
    public ResponseEntity<BusinessProfileDTO> displayBusinessProfile(@PathVariable Long businessProfileId) {
        BusinessProfileDTO businessProfileDTO = businessProfileService.findBusinessProfileById(businessProfileId);

        return ResponseEntity.ok(businessProfileDTO);
    }

    @DeleteMapping("/serviceOpinion/{opinionId}")
    public ResponseEntity<?> deleteServiceOpinion(@PathVariable Long opinionId) {
        serviceOpinionService.deleteOpinionById(opinionId);

        return ResponseEntity.ok("Opinion has been deleted");
    }

    @GetMapping("/reportedOpinions")
    public ResponseEntity<List<ReportedOpinionDTO>> displayReportedOpinions() {
        List<ReportedOpinionDTO> reportedOpinions = reportedOpinionService.findReportedOpinions();

        return ResponseEntity.ok(reportedOpinions);
    }

    @GetMapping("/reportedOpinion/{reportedOpinionId}")
    public ResponseEntity<ReportedOpinionDetailsDTO> displayReportedOpinionDetails(@PathVariable Long reportedOpinionId) {
        ReportedOpinionDetailsDTO reportedOpinionDetails = reportedOpinionService
                .findReportedOpinionDetails(reportedOpinionId);

        return ResponseEntity.ok(reportedOpinionDetails);
    }

    @GetMapping("/businessOwner")
    public ResponseEntity<List<BusinessOwnerDTO>> displayBusinessOwnerList() {
        List<BusinessOwnerDTO> businessOwnerList = businessProfileService.findBusinessOwnerList();

        return ResponseEntity.ok(businessOwnerList);
    }

    @DeleteMapping("reportedOpinion/{reportedOpinionId}")
    public ResponseEntity<String> deleteReportAndReportedOpinion(@PathVariable Long reportedOpinionId) {
        reportedOpinionService.deleteReportAndReportedOpinion(reportedOpinionId);

        return ResponseEntity.ok("Opinion has been deleted");
    }

    @DeleteMapping("report/{reportedOpinionId}")
    public ResponseEntity<String> deleteReport(@PathVariable Long reportedOpinionId) {
        reportedOpinionService.deleteReport(reportedOpinionId);

        return ResponseEntity.ok("Report has been deleted");
    }
}
