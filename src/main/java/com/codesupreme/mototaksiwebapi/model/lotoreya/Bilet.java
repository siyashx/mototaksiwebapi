package com.codesupreme.mototaksiwebapi.model.lotoreya;

import com.codesupreme.mototaksiwebapi.model.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "bilet")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Bilet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lotoreya_id")
    private Lotoreya lotoreya;

    private Date createdAt;
}
