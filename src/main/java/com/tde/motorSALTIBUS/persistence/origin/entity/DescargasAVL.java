package com.tde.motorSALTIBUS.persistence.origin.entity;

import javax.persistence.*;
import lombok.*;
import java.util.Date;

@Entity

@Table(name = "descargasavl")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DescargasAVL {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "intTipoAVL")
    private Integer intTipoAVL;

    @Column(name = "strModemID", length = 10)
    private String strModemID;

    @Column(name = "fLongitud_grad")
    private Double fLongitudGrad;

    @Column(name = "fLatitud_grad")
    private Double fLatitudGrad;

    @Column(name = "intVelocidad")
    private Integer intVelocidad;

   
    @Column(name = "intNum_Sat")
    private Integer intNumSat;

   // @Column(name = "intHeading")
   // private Integer intHeading;

    @Column(name = "dFecha_Hora_SAT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraSat;

    @Column(name = "intTipo_Evento")
    private Integer intTipoEvento;

    @Column(name = "intVariable1")
    private Integer intVariable1;


    @Column(name = "dfechaHoraComputadora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraComputadora;

    @Column(name = "intVarControl")
    private Integer intVarControl;

    @Column(name = "IdAsignacion")
    private Long idAsignacion;

    @Column(name = "intStatus")
    private Integer intStatus;

   // @Column(name = "IdLiquidacionH")
   // private Long idLiquidacionH;

   // @Column(name = "AplLiqH")
   // private Long aplLiqH;
}
