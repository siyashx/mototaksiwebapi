package com.codesupreme.mototaksiwebapi.api.lotoreya.controller;

import com.codesupreme.mototaksiwebapi.dto.lotoreya.LotoreyaDto;
import com.codesupreme.mototaksiwebapi.service.impl.lotoreya.LotoreyaServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v6/lotoreya")
public class LotoreyaController {

    private final LotoreyaServiceImpl lotoreyaService;

    public LotoreyaController(LotoreyaServiceImpl lotoreyaService) {
        this.lotoreyaService = lotoreyaService;
    }

    // Get all
    @GetMapping
    public List<LotoreyaDto> getAllLotoreyas() {
        return lotoreyaService.getAllLotoreyas();
    }

    // Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getLotoreyaById(@PathVariable Long id) {
        LotoreyaDto dto = lotoreyaService.getLotoreyaById(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Lotoreya not found with given id.");
    }

    // Create
    @PostMapping
    public ResponseEntity<LotoreyaDto> createLotoreya(@RequestBody LotoreyaDto dto) {
        return lotoreyaService.createLotoreya(dto);
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLotoreya(@PathVariable Long id, @RequestBody LotoreyaDto dto) {
        LotoreyaDto updated = lotoreyaService.updateLotoreya(id, dto);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Lotoreya not found or update failed.");
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLotoreya(@PathVariable Long id) {
        lotoreyaService.deleteLotoreyaById(id);
        return ResponseEntity.ok().build();
    }
}
