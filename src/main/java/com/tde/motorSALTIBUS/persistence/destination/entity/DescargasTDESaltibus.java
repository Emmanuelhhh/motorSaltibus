package com.tde.motorSALTIBUS.persistence.destination.entity;


import javax.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "tblDescargasTDESaltibus")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DescargasTDESaltibus {

    @Id
    @Column(name = "id_DGPRS", nullable = false)
    private Long idDGprs;

    @Column(name = "intTipoFrame")
    private Integer intTipoFrame;

    @Column(name = "intSubidas_Pta1")
    private Integer intSubidasPta1;

    @Column(name = "intBajadas_Pta1")
    private Integer intBajadasPta1;

    @Column(name = "intBloqueos_Pta1")
    private Integer intBloqueosPta1;

    @Column(name = "intPablos_Pta1")
    private Integer intPablosPta1;

    @Column(name = "intSubidas_Pta2")
    private Integer intSubidasPta2;

    @Column(name = "intBajadas_Pta2")
    private Integer intBajadasPta2;

    @Column(name = "intBloqueos_Pta2")
    private Integer intBloqueosPta2;

    @Column(name = "intPablos_Pta2")
    private Integer intPablosPta2;

    @Column(name = "intNumOperador")
    private Integer intNumOperador;

    @Column(name = "intBanderaLiquidacion")
    private Integer intBanderaLiquidacion;

    @Column(name = "intVarControl")
    private Integer intVarControl;

    @Column(name = "intNumApagados_Pta1")
    private Integer intNumApagadosPta1;

    @Column(name = "intNumApagados_Pta2")
    private Integer intNumApagadosPta2;

    @Column(name = "intId_Asignacion")
    private Long intIdAsignacion;

    @Column(name = "intStatus")
    private Integer intStatus;

    @Column(name = "dFehaHoraInsert")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraInsert;
}
