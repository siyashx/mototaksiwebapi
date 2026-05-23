package com.codesupreme.mototaksiwebapi.api.codesupreme.portfolio;

import com.codesupreme.mototaksiwebapi.dto.codesupreme.portfolio.PortfolioDto;
import com.codesupreme.mototaksiwebapi.service.inter.codesupreme.portfolio.PortfolioServiceInter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v6/portfolio")
@CrossOrigin(origins = "*")
public class PortfolioController {

    private final PortfolioServiceInter portfolioService;

    public PortfolioController(PortfolioServiceInter portfolioService) {
        this.portfolioService = portfolioService;
    }

    // Saytda görünəcək aktiv portfolio işləri
    @GetMapping
    public ResponseEntity<List<PortfolioDto>> getActivePortfolios() {
        return ResponseEntity.ok(portfolioService.getActivePortfolios());
    }

    // Admin panel üçün bütün portfolio işləri
    @GetMapping("/all")
    public ResponseEntity<List<PortfolioDto>> getAllPortfolios() {
        return ResponseEntity.ok(portfolioService.getAllPortfolios());
    }

    // ID ilə portfolio tapmaq
    @GetMapping("/{id}")
    public ResponseEntity<?> getPortfolioById(@PathVariable Long id) {
        PortfolioDto dto = portfolioService.getPortfolioById(id);

        if (dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Portfolio bu ID ilə tapılmadı.");
        }

        return ResponseEntity.ok(dto);
    }

    // Slug ilə portfolio tapmaq
    @GetMapping("/slug/{slug}")
    public ResponseEntity<?> getPortfolioBySlug(@PathVariable String slug) {
        PortfolioDto dto = portfolioService.getPortfolioBySlug(slug);

        if (dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Portfolio bu slug ilə tapılmadı.");
        }

        return ResponseEntity.ok(dto);
    }

    // Kateqoriya adına görə filter
    // /api/v6/portfolio/category?category=Veb sayt
    @GetMapping("/category")
    public ResponseEntity<List<PortfolioDto>> getPortfoliosByCategory(
            @RequestParam String category
    ) {
        return ResponseEntity.ok(portfolioService.getPortfoliosByCategory(category));
    }

    // Kateqoriya key-ə görə filter
    // /api/v6/portfolio/category-key?categoryKey=veb-sayt
    @GetMapping("/category-key")
    public ResponseEntity<List<PortfolioDto>> getPortfoliosByCategoryKey(
            @RequestParam String categoryKey
    ) {
        return ResponseEntity.ok(portfolioService.getPortfoliosByCategoryKey(categoryKey));
    }

    // Yeni portfolio işi yaratmaq
    @PostMapping
    public ResponseEntity<PortfolioDto> createPortfolio(@RequestBody PortfolioDto portfolioDto) {
        return portfolioService.createPortfolio(portfolioDto);
    }

    // Portfolio işi yeniləmək
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePortfolio(
            @PathVariable Long id,
            @RequestBody PortfolioDto portfolioDto
    ) {
        PortfolioDto updated = portfolioService.updatePortfolio(id, portfolioDto);

        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Yenilənəcək portfolio işi tapılmadı.");
        }

        return ResponseEntity.ok(updated);
    }

    // Portfolio işi silmək
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePortfolio(@PathVariable Long id) {
        PortfolioDto dto = portfolioService.getPortfolioById(id);

        if (dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Silinəcək portfolio işi tapılmadı.");
        }

        portfolioService.deletePortfolio(id);

        return ResponseEntity.ok("Portfolio işi uğurla silindi.");
    }
}