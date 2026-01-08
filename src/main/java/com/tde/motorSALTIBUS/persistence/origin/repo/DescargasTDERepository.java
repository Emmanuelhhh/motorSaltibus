package com.tde.motorSALTIBUS.persistence.origin.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tde.motorSALTIBUS.persistence.origin.entity.DescargasTDE;

@Repository
public interface DescargasTDERepository extends JpaRepository<DescargasTDE, Long> {

    // Ejemplo de consulta personalizada:
    // List<TblDescargasTDE> findByIntNumOperador(Integer operador);
}
