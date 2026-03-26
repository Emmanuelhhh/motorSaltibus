package com.tde.motorSALTIBUS.persistence.destination.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.tde.motorSALTIBUS.persistence.destination.entity.DescargasCARDSaltibus;

public interface DescargasCARDSaltibusRepository extends CrudRepository<DescargasCARDSaltibus, Long> {

	
    Optional<DescargasCARDSaltibus> findTopByOrderByIdDGprsDesc();

}
