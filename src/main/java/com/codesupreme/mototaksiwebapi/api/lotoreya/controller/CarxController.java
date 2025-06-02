package com.codesupreme.mototaksiwebapi.api.lotoreya.controller;

import com.codesupreme.mototaksiwebapi.dto.lotoreya.AssignWinnerRequest;
import com.codesupreme.mototaksiwebapi.dto.lotoreya.CarxDto;
import com.codesupreme.mototaksiwebapi.service.impl.lotoreya.CarxServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v6/carx")
public class CarxController {

    private final CarxServiceImpl carxService;

    public CarxController(CarxServiceImpl carxService) {
        this.carxService = carxService;
    }

    // Bütün qalibləri gətir
    @GetMapping
    public List<CarxDto> getAllCarxes() {
        return carxService.getAllCarxs();
    }

    // Qalibi ID ilə gətir
    @GetMapping("/{id}")
    public ResponseEntity<?> getCarxById(@PathVariable Long id) {
        CarxDto dto = carxService.getCarxById(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    // Yeni qalib manual olaraq yarat (əgər lazım olarsa)
    @PostMapping
    public ResponseEntity<CarxDto> createCarx(@RequestBody CarxDto carxDto) {
        CarxDto created = carxService.createCarx(carxDto);
        return ResponseEntity.ok(created);
    }

    // Qalibi yenilə
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCarx(@PathVariable Long id, @RequestBody CarxDto carxDto) {
        CarxDto updated = carxService.updateCarx(id, carxDto);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    // Qalibi sil
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCarx(@PathVariable Long id) {
        carxService.deleteCarxById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/assign")
    public ResponseEntity<?> assignWinner(@RequestBody AssignWinnerRequest request) {
        try {
            CarxDto winner = carxService.assignWinner(request.getLotoreyaId(), request.getCodes());
            return ResponseEntity.ok(winner);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
