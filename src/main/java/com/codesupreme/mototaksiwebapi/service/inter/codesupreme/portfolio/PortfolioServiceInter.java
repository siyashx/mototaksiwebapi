package com.codesupreme.mototaksiwebapi.service.inter.codesupreme.portfolio;

import com.codesupreme.mototaksiwebapi.dto.codesupreme.portfolio.PortfolioDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PortfolioServiceInter {

    List<PortfolioDto> getAllPortfolios();

    PortfolioDto getPortfolioById(Long id);

    ResponseEntity<PortfolioDto> createPortfolio(PortfolioDto portfolioDto);

    PortfolioDto updatePortfolio(Long id, PortfolioDto portfolioDto);

    void deletePortfolio(Long id);

    List<PortfolioDto> getPortfoliosByCategory(String category);

    PortfolioDto getPortfolioBySlug(String slug);
}

