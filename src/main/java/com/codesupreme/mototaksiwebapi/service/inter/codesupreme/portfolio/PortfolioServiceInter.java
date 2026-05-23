package com.codesupreme.mototaksiwebapi.service.inter.codesupreme.portfolio;

import com.codesupreme.mototaksiwebapi.dto.codesupreme.portfolio.PortfolioDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PortfolioServiceInter {

    List<PortfolioDto> getAllPortfolios();

    List<PortfolioDto> getActivePortfolios();

    PortfolioDto getPortfolioById(Long id);

    PortfolioDto getPortfolioBySlug(String slug);

    ResponseEntity<PortfolioDto> createPortfolio(PortfolioDto portfolioDto);

    PortfolioDto updatePortfolio(Long id, PortfolioDto portfolioDto);

    List<PortfolioDto> getPortfoliosByCategory(String category);

    List<PortfolioDto> getPortfoliosByCategoryKey(String categoryKey);

    void deletePortfolio(Long id);
}