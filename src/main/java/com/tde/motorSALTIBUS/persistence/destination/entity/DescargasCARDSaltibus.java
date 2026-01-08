package com.tde.motorSALTIBUS.persistence.destination.entity;


import javax.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "tblDescargasCARDSaltibus")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DescargasCARDSaltibus {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "id_DGPRS")
    private Long idDGprs;

    @Column(name = "strValidador", length = 10, nullable = false)
    private String strValidador;

    @Column(name = "strIDTarjeta", length = 10, nullable = false)
    private String strIDTarjeta;

    @Column(name = "intTipoTarjeta")
    private Integer intTipoTarjeta;

    @Column(name = "intTipoEventoTarjeta")
    private Integer intTipoEventoTarjeta;

    @Column(name = "intSaldoInicialTarjeta")
    private Integer intSaldoInicialTarjeta;

    @Column(name = "intSaldoFinalTarjeta")
    private Integer intSaldoFinalTarjeta;

    @Column(name = "dFechaEventoTarjeta", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEventoTarjeta;

    @Column(name = "intVarControl")
    private Integer intVarControl;

    @Column(name = "intStatus")
    private Integer intStatus;

    @Column(name = "dFechaAVL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAvl;

    @Column(name = "intNumOperador")
    private Integer intNumOperador;

    @Column(name = "intIDPuntoVenta")
    private Integer intIDPuntoVenta;

    @Column(name = "intFolioVenta")
    private Integer intFolioVenta;

    @Column(name = "intFolioTarjeta")
    private Integer intFolioTarjeta;

    @Column(name = "CobroSIR")
    private BigDecimal cobroSir;

    @Column(name = "strModem_ID", length = 10)
    private String strModemId;
}
