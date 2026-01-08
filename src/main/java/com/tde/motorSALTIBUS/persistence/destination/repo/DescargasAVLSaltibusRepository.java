package com.tde.motorSALTIBUS.persistence.destination.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.tde.motorSALTIBUS.persistence.destination.entity.DescargasAVLSaltibus;

public interface DescargasAVLSaltibusRepository extends CrudRepository<DescargasAVLSaltibus, Long > {

	Optional<DescargasAVLSaltibus> findTopByOrderByIdDesc();
	
}
