package com.codesupreme.mototaksiwebapi.api.elan.controller;


import com.codesupreme.mototaksiwebapi.dto.elan.ElanDto;
import com.codesupreme.mototaksiwebapi.service.impl.elan.ElanServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v6/elan")
public class ElanController {

    private final ElanServiceImpl ElanServiceImpl;

    public ElanController(ElanServiceImpl ElanServiceImpl) {
        this.ElanServiceImpl = ElanServiceImpl;
    }

    // List
    @GetMapping
    public List<ElanDto> getAllElans() {
        return ElanServiceImpl.getAllElans();
    }

    // Id
    @GetMapping("/{elanId}")
    public ResponseEntity<?> getElanById(@PathVariable("elanId") Long elanId) {
        ElanDto elanDto = ElanServiceImpl.getElanById(elanId);
        if (elanDto != null) {
            return ResponseEntity.ok(elanDto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Elan doesn't exist with given id..");
    }

    // Update
    @PutMapping("/{elanId}")
    public ResponseEntity<?> updateElan(
            @PathVariable("elanId") Long id,
            @RequestBody ElanDto elanDto
    ) {
        ElanDto updatedElan = ElanServiceImpl.updateElan(id, elanDto);
        if (updatedElan != null) {
            return ResponseEntity.ok(updatedElan);
        }
        return ResponseEntity.notFound().build();
    }

    // Delete
    @DeleteMapping("/{elanId}")
    public ResponseEntity<?> deleteElanById(@PathVariable("elanId") Long elanId) {
        ElanServiceImpl.deleteElanById(elanId);
        return ResponseEntity.ok().build();
    }

    // Create
    @PostMapping
    public ResponseEntity<ElanDto> createElan(@RequestBody ElanDto elanDto) {
        return ElanServiceImpl.createElan(elanDto);
    }
}
