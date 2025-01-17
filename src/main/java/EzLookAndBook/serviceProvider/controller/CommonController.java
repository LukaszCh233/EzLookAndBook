package EzLookAndBook.serviceProvider.controller;

import EzLookAndBook.serviceProvider.serviceOpinion.ServiceOpinionDTO;
import EzLookAndBook.serviceProvider.serviceOpinion.ServiceOpinionService;
import EzLookAndBook.serviceProvider.support.ChatRequest;
import EzLookAndBook.serviceProvider.support.SupportChatDTO;
import EzLookAndBook.serviceProvider.support.SupportRequest;
import EzLookAndBook.serviceProvider.support.SupportService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/common")
public class CommonController {
    private final ServiceOpinionService serviceOpinionService;
    private final SupportService supportService;

    public CommonController(ServiceOpinionService serviceOpinionService, SupportService supportService) {
        this.serviceOpinionService = serviceOpinionService;
        this.supportService = supportService;
    }

    @GetMapping("/serviceOpinions/{serviceProviderId}")
    public ResponseEntity<List<ServiceOpinionDTO>> displayServiceOpinions(@PathVariable Long serviceProviderId) {
        List<ServiceOpinionDTO> serviceOpinionDTOS = serviceOpinionService.findServiceProviderOpinionsById(serviceProviderId);

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
                                                         @RequestBody ChatRequest chatRequest, Principal principal) {
        SupportChatDTO supportChat = supportService.createAnswerToChat(supportChatId, chatRequest, principal);

        return ResponseEntity.ok(supportChat);
    }
    @PostMapping("/supportChat")
    public ResponseEntity<?> sendSupportChatRequestToAdmin(@RequestBody SupportRequest supportRequest, Principal principal) {
        supportService.createSupportChatRequest(supportRequest,principal);

        return ResponseEntity.ok("request has been sent");
    }
}
