package com.codesupreme.mototaksiwebapi.model.lotoreya;

import com.codesupreme.mototaksiwebapi.model.elan.Elan;
import com.codesupreme.mototaksiwebapi.model.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "lotoreya")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Lotoreya {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Lotoreyanı yaradan istifadəçi
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Lotoreya hansı elana bağlıdır
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "elan_id")
    private Elan elan;

    private Double biletPrice;

    @Temporal(TemporalType.DATE)
    private Date lotoreyaDate;

    private Boolean isAccept;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    // Qalib avtomobil
    @OneToOne(mappedBy = "lotoreya", cascade = CascadeType.ALL)
    private Carx carx;

    // Lotoreyaya aid bütün biletlər
    @OneToMany(mappedBy = "lotoreya", cascade = CascadeType.ALL)
    private List<Bilet> biletList;
}
