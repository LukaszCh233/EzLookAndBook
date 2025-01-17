package EzLookAndBook.serviceProvider.support;

import EzLookAndBook.account.admin.Admin;
import EzLookAndBook.account.admin.AdminRepository;
import EzLookAndBook.account.client.Client;
import EzLookAndBook.account.client.ClientRepository;
import EzLookAndBook.account.owner.Owner;
import EzLookAndBook.account.owner.OwnerRepository;
import EzLookAndBook.mapper.EntityMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SupportService {
    private final SupportRepository supportRepository;
    private final OwnerRepository ownerRepository;
    private final ClientRepository clientRepository;
    private final AdminRepository adminRepository;
    private final EntityMapper entityMapper;

    public SupportService(SupportRepository supportRepository, OwnerRepository ownerRepository,
                          ClientRepository clientRepository, AdminRepository adminRepository, EntityMapper entityMapper) {
        this.supportRepository = supportRepository;
        this.ownerRepository = ownerRepository;
        this.clientRepository = clientRepository;
        this.adminRepository = adminRepository;
        this.entityMapper = entityMapper;
    }

    public void createSupportChatRequest(SupportRequest supportRequest, Principal principal) {
        Object user = getUserDetails(principal);

        Chat chat = new Chat();
        chat.setText(supportRequest.getText());

        SupportChat supportChat = new SupportChat();
        supportChat.setSubject(supportRequest.getSubject());
        supportChat.setDate(LocalDate.now());
        supportChat.setChats(new ArrayList<>(List.of(chat)));

        if (user instanceof Client client) {
            supportChat.setUserId(client.getId());
            supportChat.setRole(client.getRole());
            chat.setPersonName(client.getName());
        } else if (user instanceof Owner owner) {
            supportChat.setUserId(owner.getId());
            supportChat.setRole(owner.getRole());
            chat.setPersonName(owner.getName());
        }

        supportRepository.save(supportChat);
    }

    public SupportChatDTO findSupportChatById(Long supportChatId) {
        SupportChat supportChat = supportRepository.findById(supportChatId).orElseThrow(() ->
                new EntityNotFoundException("chat not found"));

        return entityMapper.mapSupportChatToSupportChatDTO(supportChat);
    }

    public SupportChatDTO createAnswerToChat(Long supportChatId, ChatRequest chatRequest, Principal principal) {
        Object user = getUserDetails(principal);

        Chat chat = new Chat();
        chat.setText(chatRequest.getText());

        if (user instanceof Client client) {
            chat.setPersonName(client.getName());
        } else if (user instanceof Owner owner) {
            chat.setPersonName(owner.getName());
        } else if (user instanceof Admin admin) {
            chat.setPersonName(admin.getName());
        }

        SupportChat supportChat = supportRepository.findById(supportChatId).orElseThrow(() ->
                new EntityNotFoundException("chat not found"));
        supportChat.getChats().add(chat);

        supportRepository.save(supportChat);

        return entityMapper.mapSupportChatToSupportChatDTO(supportChat);
    }

    private Object getUserDetails(Principal principal) {
        String email = principal.getName();

        Optional<Client> client = clientRepository.findByEmail(email);
        if (client.isPresent()) {
            return client.get();
        }
        Optional<Owner> owner = ownerRepository.findByEmail(email);
        if (owner.isPresent()) {
            return owner.get();
        }
        Optional<Admin> admin = adminRepository.findByEmail(email);
        if (admin.isPresent()) {
            return admin.get();
        }
        throw new IllegalStateException("No Client or Owner or Admin found with the provided email");
    }
}
