package com.tde.motorSALTIBUS.persistence.origin.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tde.motorSALTIBUS.persistence.origin.entity.DescargasAVL;


@Repository
public interface DescargasAVLRepository extends CrudRepository<DescargasAVL, Long> {

 
	//nueva funcion, temporalmente agrega query nativo
	@Query(value ="SELECT TOP (200) * " +
			"FROM [SIGOWEB].[dbo].[tblDescargasAVL] " +
			"WHERE id > :lastId "
			+ "and strModemID IN ('4764366700', '4677018454', '7000267014', '7000267149', '7000267442', '7000267221', '7000267233', '7000267359', '7000266978', '7000267458', '7000267101', '7000267421', '7000267348', '7000267225') "
			+ "order by id", nativeQuery = true)
	List<DescargasAVL> findByIdGreaterThan(
			Long lastId);
	
	//esta debe quedar al final
	List<DescargasAVL> findByIdGreaterThan(
			Long lastId,
	        Pageable pageable);
	
	

}
