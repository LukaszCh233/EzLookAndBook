package EzLookAndBook.serviceProvider.serviceProviderCateogry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Long> {
    @Query("SELECT c FROM ServiceCategory c WHERE LOWER(c.name) = LOWER(:name)")
    Optional<ServiceCategory> findByNameIgnoreCase(@Param("name") String name);

}
