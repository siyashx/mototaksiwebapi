package com.codesupreme.mototaksiwebapi.api.codesupreme.portfolio;

import com.codesupreme.mototaksiwebapi.dto.codesupreme.portfolio.PortfolioDto;
import com.codesupreme.mototaksiwebapi.service.impl.codesupreme.portfolio.PortfolioServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v6/portfolio")
public class PortfolioController {

    private final PortfolioServiceImpl portfolioService;

    public PortfolioController(PortfolioServiceImpl portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping
    public List<PortfolioDto> getAllPortfolios() {
        return portfolioService.getAllPortfolios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PortfolioDto> getPortfolioById(@PathVariable Long id) {
        PortfolioDto dto = portfolioService.getPortfolioById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<PortfolioDto> getPortfolioBySlug(@PathVariable String slug) {
        PortfolioDto dto = portfolioService.getPortfolioBySlug(slug);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/category")
    public ResponseEntity<List<PortfolioDto>> getPortfoliosByCategory(@RequestParam String category) {
        return ResponseEntity.ok(portfolioService.getPortfoliosByCategory(category));
    }

    @PostMapping
    public ResponseEntity<PortfolioDto> createPortfolio(@RequestBody PortfolioDto portfolioDto) {
        return portfolioService.createPortfolio(portfolioDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PortfolioDto> updatePortfolio(@PathVariable Long id, @RequestBody PortfolioDto portfolioDto) {
        PortfolioDto updated = portfolioService.updatePortfolio(id, portfolioDto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePortfolio(@PathVariable Long id) {
        portfolioService.deletePortfolio(id);
        return ResponseEntity.ok().build();
    }
}
