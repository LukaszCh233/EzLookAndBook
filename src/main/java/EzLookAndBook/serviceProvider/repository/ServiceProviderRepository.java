package EzLookAndBook.serviceProvider.repository;

import EzLookAndBook.serviceProvider.entity.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {

    List<ServiceProvider> findByServiceCategoryNameAndCityIgnoreCase(String categoryName, String city);

    @Query("SELECT sp FROM ServiceProvider sp WHERE sp.serviceCategory.id = :id AND LOWER(sp.city) = LOWER(:city)")
    List<ServiceProvider> findByServiceCategoryIdAndCityIgnoreCase(@Param("id") Long id, @Param("city") String city);

    @Query("SELECT sp FROM ServiceProvider sp JOIN sp.serviceOptions so WHERE LOWER(so.name) = LOWER(:name) AND LOWER(sp.city) = LOWER(:city)")
    List<ServiceProvider> findByServiceOptionNameAndCityIgnoreCase(@Param("name") String name, @Param("city") String city);

    Optional<ServiceProvider> findByOwnerId(Long id);
}
