package com.tde.motorSALTIBUS.persistence.origin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tde.motorSALTIBUS.persistence.origin.entity.DescargasCARD;

@Repository
public interface DescargasCARDRepository extends JpaRepository<DescargasCARD, Long> {

    // Ejemplos de consultas personalizadas:
    // List<TblDescargasCARD> findByIdDGprs(Long idDGprs);
    // List<TblDescargasCARD> findByStrValidador(String validador);
}
