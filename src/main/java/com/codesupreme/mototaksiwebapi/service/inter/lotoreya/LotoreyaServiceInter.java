package com.codesupreme.mototaksiwebapi.service.inter.lotoreya;

import com.codesupreme.mototaksiwebapi.dto.lotoreya.LotoreyaDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LotoreyaServiceInter {

    List<LotoreyaDto> getAllLotoreyas();

    LotoreyaDto getLotoreyaById(Long id);

    ResponseEntity<LotoreyaDto> createLotoreya(LotoreyaDto lotoreyaDto);

    LotoreyaDto updateLotoreya(Long id, LotoreyaDto lotoreyaDto);

    void deleteLotoreyaById(Long id);
}
