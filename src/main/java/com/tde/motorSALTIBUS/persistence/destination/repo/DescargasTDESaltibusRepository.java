package com.tde.motorSALTIBUS.persistence.destination.repo;


import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import com.tde.motorSALTIBUS.persistence.destination.entity.DescargasTDESaltibus;
public interface DescargasTDESaltibusRepository extends CrudRepository<DescargasTDESaltibus , Long> {
	
    Optional<DescargasTDESaltibus> findTopByOrderByIdDGprsDesc();
}
