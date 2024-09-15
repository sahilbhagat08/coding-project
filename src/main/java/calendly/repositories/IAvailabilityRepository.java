package calendly.repositories;

import calendly.model.AvailabilityModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAvailabilityRepository extends MongoRepository<AvailabilityModel, Long> {

    List<AvailabilityModel> findByUserId(Long userId);
}

