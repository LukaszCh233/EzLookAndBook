package EzLookAndBook.serviceProvider.service;

import EzLookAndBook.exception.ExistsException;
import EzLookAndBook.mapper.EntityMapper;
import EzLookAndBook.serviceProvider.dto.ServiceCategoryDTO;
import EzLookAndBook.serviceProvider.entity.ServiceCategory;
import EzLookAndBook.serviceProvider.repository.ServiceCategoryRepository;
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

    public void createServiceCategory(ServiceCategory serviceCategory) {
        if (serviceCategoryRepository.findByNameIgnoreCase(serviceCategory.getName()).isPresent()) {
            throw new ExistsException("Category with this name already exists");
        }
        serviceCategoryRepository.save(serviceCategory);
    }

    public ServiceCategoryDTO findServiceCategoryById(Long categoryId) {
        ServiceCategory serviceCategory = serviceCategoryRepository.findById(categoryId).orElseThrow(() ->
                new EntityNotFoundException("not found category"));

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
