package EzLookAndBook.serviceProvider.serviceProviderCateogry;

import EzLookAndBook.exception.ExistsException;
import EzLookAndBook.mapper.EntityMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceCategoryService {
    private final ServiceCategoryRepository serviceCategoryRepository;
    private final EntityMapper entityMapper;

    public ServiceCategoryService(ServiceCategoryRepository serviceCategoryRepository, EntityMapper entityMapper) {
        this.serviceCategoryRepository = serviceCategoryRepository;
        this.entityMapper = entityMapper;
    }

    public void createServiceCategory(ServiceCategoryRequest serviceCategoryRequest) {
        if (serviceCategoryRepository.findByNameIgnoreCase(serviceCategoryRequest.getName()).isPresent()) {
            throw new ExistsException("Category with this name already exists");
        }
        ServiceCategory serviceCategory = new ServiceCategory();
        serviceCategory.setName(serviceCategoryRequest.getName());

        serviceCategoryRepository.save(serviceCategory);
    }

    public ServiceCategoryDTO findServiceCategoryById(Long categoryId) {
        ServiceCategory serviceCategory = serviceCategoryRepository.findById(categoryId).orElseThrow(() ->
                new EntityNotFoundException("Category not found"));

        return entityMapper.mapServiceCategoryToServiceCategoryDTO(serviceCategory);
    }

    public List<ServiceCategoryDTO> findAllCategories() {
        List<ServiceCategory> serviceCategoryList = serviceCategoryRepository.findAll();
        if (serviceCategoryList.isEmpty()) {
            throw new EntityNotFoundException("List is empty");
        }

        return entityMapper.mapServiceCategoryListToServiceCategoryListDTO(serviceCategoryList);
    }
}
