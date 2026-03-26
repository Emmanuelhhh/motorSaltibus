package com.tde.motorSALTIBUS.persistence.destination.entity;


import javax.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "tblDescargasAVLSaltibus")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DescargasAVLSaltibus {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "intTipoAVL")
    private Integer intTipoAVL;

    @Column(name = "strModemID", length = 50)
    private String strModemID;

    @Column(name = "fLongitud_grad")
    private Double fLongitudGrad;

    @Column(name = "fLatitud_grad")
    private Double fLatitudGrad;

    @Column(name = "intVelocidad")
    private Integer intVelocidad;

    @Column(name = "intNum_Sat")
    private Integer intNumSat;

    @Column(name = "dFecha_Hora_SAT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraSat;

    @Column(name = "intTipo_Evento")
    private Integer intTipoEvento;

    @Column(name = "intVariable1")
    private Integer intVariable1;
/*
    @Column(name = "dFechaHoraComputadora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraComputadora;
*/
    @Column(name = "intVarControl")
    private Integer intVarControl;

    @Column(name = "intStatus")
    private Integer intStatus;
/*
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
  */  
    //ESTE DATO LO INSERTA UN TRIGGER, NO SE NECESITA MAPEAR
   //    @Column(name = "id_uni")
  //  private Long idUni;
}
