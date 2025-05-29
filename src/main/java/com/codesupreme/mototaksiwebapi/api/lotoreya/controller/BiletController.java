package com.codesupreme.mototaksiwebapi.api.lotoreya.controller;

import com.codesupreme.mototaksiwebapi.dto.lotoreya.BiletDto;
import com.codesupreme.mototaksiwebapi.service.impl.lotoreya.BiletServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v6/bilet")
public class BiletController {

    private final BiletServiceImpl biletService;

    public BiletController(BiletServiceImpl biletService) {
        this.biletService = biletService;
    }

    // Bütün biletlər
    @GetMapping
    public List<BiletDto> getAllBilets() {
        return biletService.getAllBilets();
    }

    // Bilet ID ilə
    @GetMapping("/{id}")
    public ResponseEntity<?> getBiletById(@PathVariable Long id) {
        BiletDto dto = biletService.getBiletById(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    // İstifadəçiyə aid biletlər
    @GetMapping("/user/{userId}")
    public List<BiletDto> getBiletsByUserId(@PathVariable Long userId) {
        return biletService.getBiletsByUserId(userId);
    }

    // Lotoreyaya aid biletlər
    @GetMapping("/lotoreya/{lotoreyaId}")
    public List<BiletDto> getBiletsByLotoreyaId(@PathVariable Long lotoreyaId) {
        return biletService.getBiletsByLotoreyaId(lotoreyaId);
    }

    // Bilet al
    @PostMapping("/buy")
    public ResponseEntity<?> buyBilet(@RequestParam Long lotoreyaId, @RequestParam Long userId) {
        try {
            BiletDto dto = biletService.buyBilet(lotoreyaId, userId);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Bilet sil
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBilet(@PathVariable Long id) {
        biletService.deleteBilet(id);
        return ResponseEntity.ok().build();
    }
}
