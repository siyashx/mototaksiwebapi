package com.codesupreme.mototaksiwebapi.service.inter.lotoreya;

import com.codesupreme.mototaksiwebapi.dto.lotoreya.BiletDto;
import java.util.List;

public interface BiletServiceInter {

    List<BiletDto> getAllBilets();

    BiletDto getBiletById(Long id);

    List<BiletDto> getBiletsByLotoreyaId(Long lotoreyaId);

    List<BiletDto> getBiletsByUserId(Long userId);

    List<BiletDto> getBiletsByUserIdAndLotoreyaId(Long userId, Long lotoreyaId);

    // Əsas funksiya: istifadəçi bilet alır
    BiletDto buyBilet(Long lotoreyaId, Long userId);

    void deleteBilet(Long id);
}
