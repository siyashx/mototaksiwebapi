package com.codesupreme.mototaksiwebapi.service.inter.lotoreya;

import com.codesupreme.mototaksiwebapi.dto.lotoreya.CarxDto;
import java.util.List;

public interface CarxServiceInter {

    List<CarxDto> getAllCarxs();

    CarxDto getCarxById(Long id);

    CarxDto createCarx(CarxDto carxDto);

    CarxDto updateCarx(Long id, CarxDto carxDto);

    void deleteCarxById(Long id);

    CarxDto assignWinner(Long lotoreyaId, List<String> codes);
}

