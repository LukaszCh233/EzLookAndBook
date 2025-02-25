package EzLookAndBook.serviceTest;

import EzLookAndBook.TestPrincipal;
import EzLookAndBook.serviceProvider.report.ReportedOpinionDTO;
import EzLookAndBook.serviceProvider.report.ReportedOpinionRequest;
import EzLookAndBook.serviceProvider.report.ReportedOpinionService;
import EzLookAndBook.serviceProvider.serviceOpinion.OpinionRequest;
import EzLookAndBook.serviceProvider.serviceOpinion.ServiceOpinion;
import EzLookAndBook.serviceProvider.serviceOpinion.ServiceOpinionDTO;
import EzLookAndBook.serviceProvider.serviceOpinion.ServiceOpinionService;
import EzLookAndBook.serviceProvider.serviceProvider.ServiceProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class ServiceOpinionServiceTest extends DeleteBeforeEach {

    @Autowired
    private ServiceOpinionService serviceOpinionService;
    @Autowired
    private ReportedOpinionService reportedOpinionService;
    @Autowired
    private TestHelper testHelper;

    @Test
    public void whenAddOpinionToServiceOpinionShouldBeFindInServiceOpinionList() {
        testHelper.newClient("city", "client@example.com");
        ServiceProvider serviceProvider = testHelper.newServiceProvider();

        OpinionRequest request = new OpinionRequest();
        request.setOpinion("opinion");
        request.setRating(3);

        serviceOpinionService.addOpinion(serviceProvider.getId(), request, new TestPrincipal("client@example.com"));

        List<ServiceOpinionDTO> serviceOpinionList = serviceOpinionService.findServiceProviderOpinionsById
                (serviceProvider.getId());

        assertEquals(serviceOpinionList.size(), 1);
        assertEquals(serviceOpinionList.get(0).opinion(), "opinion");
        assertEquals(serviceOpinionList.get(0).rating(), 3);
    }

    @Test
    public void whenReportServiceOpinionOpinionShouldBeFindInReportedOpinionList() {
        ServiceOpinion serviceOpinion = testHelper.newServiceOpinion();

        ReportedOpinionRequest request = new ReportedOpinionRequest();
        request.setServiceOpinionId(serviceOpinion.getId());
        request.setReason("reasons");

        reportedOpinionService.reportOpinionToAdmin(request, new TestPrincipal("owner@example.com"));

        List<ReportedOpinionDTO> reportedOpinionList = reportedOpinionService.findReportedOpinions();

        assertEquals(reportedOpinionList.size(), 1);
        assertEquals(reportedOpinionList.get(0).email(), "owner@example.com");
    }
}
