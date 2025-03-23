package com.jude.charge.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "t_charge")
public class TCharge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 32)
    @Column(name = "c_id", length = 32)
    private String cId;

    @Size(max = 4)
    @Column(name = "c_type", length = 4)
    private String cType;

    @Column(name = "c_charge_pre")
    private Integer cChargePre;

    @Column(name = "c_charge")
    private Integer cCharge;

    @Size(max = 2)
    @Column(name = "supplier", length = 2)
    private String supplier;

    @Column(name = "tem_id")
    private Integer temId;

    @Column(name = "create_time")
    private Instant createTime;

    @Column(name = "update_time")
    private Instant updateTime;

}