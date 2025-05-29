package com.codesupreme.mototaksiwebapi.model.lotoreya;

import com.codesupreme.mototaksiwebapi.model.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "carx")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Carx {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Qalib istifadəçi
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_user_id")
    private User winnerUser;

    private String winnerBiletCode;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    // Hər bir carx bir lotoreyaya bağlı ola bilər (optional)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lotoreya_id")
    private Lotoreya lotoreya;
}
