package com.codesupreme.mototaksiwebapi.dao.lotoreya;

import com.codesupreme.mototaksiwebapi.model.lotoreya.Bilet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BiletRepository extends JpaRepository<Bilet, Long> {

    List<Bilet> findByUserId(Long userId);

    List<Bilet> findByLotoreyaId(Long lotoreyaId);
}
