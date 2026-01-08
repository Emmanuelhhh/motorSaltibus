package com.tde.motorSALTIBUS.persistence.origin.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.tde.motorSALTIBUS.persistence.origin.entity.DescargasAVL;


@Repository
public interface DescargasAVLRepository extends CrudRepository<DescargasAVL, Long> {

    // Puedes agregar consultas adicionales si las necesitas:
    // List<TblDescargasAVL> findByIntTipoAVL(Integer tipo);
	
	@Query(value ="SELECT TOP (1) * " +
	"FROM [INTELIBUS].[dbo].[tblDescargasAVL] " +
	"WHERE id = :id order by id", nativeQuery = true)
	Optional<DescargasAVL>  findById(@Param("id") Long id); 
	
	
	Iterable<DescargasAVL> findTop1ByIdGreaterThanOrderByIdAsc(Long id);
}
