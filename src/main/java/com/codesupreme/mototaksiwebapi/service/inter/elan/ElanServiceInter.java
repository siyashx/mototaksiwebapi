package com.codesupreme.mototaksiwebapi.service.inter.elan;

import com.codesupreme.mototaksiwebapi.dto.elan.ElanDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ElanServiceInter {

    List<ElanDto> getAllElans();
    ElanDto getElanById(Long id);
    ResponseEntity<ElanDto> createElan(ElanDto ElanDto);
    ElanDto updateElan(Long id, ElanDto ElanDto);
    void deleteElanById(Long id);
}
