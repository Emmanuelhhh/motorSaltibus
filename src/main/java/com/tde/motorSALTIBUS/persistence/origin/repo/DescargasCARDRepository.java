package com.tde.motorSALTIBUS.persistence.origin.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tde.motorSALTIBUS.persistence.origin.entity.DescargasCARD;

@Repository
public interface DescargasCARDRepository extends JpaRepository<DescargasCARD, Long> {


	//nueva funcion, temporalmente agrega query nativo
	@Query(value ="SELECT TOP (200) * " +
			"FROM [SIGOWEB].[dbo].[tblDescargasCARD] " +
			"WHERE id_DGPRS > :lastId "
			+ "and strModem_ID IN ('4764366700', '4677018454', '7000267014', '7000267149', '7000267442', '7000267221', '7000267233', '7000267359', '7000266978', '7000267458', '7000267101', '7000267421', '7000267348', '7000267225') "
			+ "order by id_DGPRS", nativeQuery = true)
	List<DescargasCARD> findByIdDGprsGreaterThan(
			Long lastId);
	
	//esta debe quedar finalmente
	List<DescargasCARD> findByIdDGprsGreaterThan(
			Long lastId,
	        Pageable pageable);
	
}
