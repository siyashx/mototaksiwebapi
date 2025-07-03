package com.codesupreme.mototaksiwebapi.service.impl.codesupreme.portfolio;

import com.codesupreme.mototaksiwebapi.dao.codesupreme.portfolio.PortfolioRepository;
import com.codesupreme.mototaksiwebapi.dto.codesupreme.portfolio.PortfolioDto;
import com.codesupreme.mototaksiwebapi.model.codesupreme.portfolio.Portfolio;
import com.codesupreme.mototaksiwebapi.service.inter.codesupreme.portfolio.PortfolioServiceInter;
import com.codesupreme.mototaksiwebapi.util.SlugUtil;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PortfolioServiceImpl implements PortfolioServiceInter {

    private final PortfolioRepository portfolioRepository;
    private final ModelMapper modelMapper;

    public PortfolioServiceImpl(PortfolioRepository portfolioRepository, ModelMapper modelMapper) {
        this.portfolioRepository = portfolioRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PortfolioDto> getAllPortfolios() {
        List<Portfolio> portfolios = portfolioRepository.findAll();
        List<PortfolioDto> dtoList = new ArrayList<>();
        for (Portfolio portfolio : portfolios) {
            dtoList.add(modelMapper.map(portfolio, PortfolioDto.class));
        }
        return dtoList;
    }

    @Override
    public PortfolioDto getPortfolioById(Long id) {
        return portfolioRepository.findById(id)
                .map(portfolio -> modelMapper.map(portfolio, PortfolioDto.class))
                .orElse(null);
    }

    @Override
    public ResponseEntity<PortfolioDto> createPortfolio(PortfolioDto portfolioDto) {
        Portfolio portfolio = modelMapper.map(portfolioDto, Portfolio.class);
        portfolio.setSlug(SlugUtil.toSlug(portfolio.getTitle()));
        portfolio.setCreatedAt(LocalDateTime.now());
        portfolio.setUpdatedAt(LocalDateTime.now());

        Portfolio saved = portfolioRepository.save(portfolio);
        return ResponseEntity.ok(modelMapper.map(saved, PortfolioDto.class));
    }

    @Override
    public PortfolioDto updatePortfolio(Long id, PortfolioDto portfolioDto) {
        Optional<Portfolio> opt = portfolioRepository.findById(id);
        if (opt.isPresent()) {
            Portfolio portfolio = opt.get();

            if (portfolioDto.getTitle() != null) {
                portfolio.setTitle(portfolioDto.getTitle());
                portfolio.setSlug(SlugUtil.toSlug(portfolioDto.getTitle()));
            }
            if (portfolioDto.getDescription() != null) portfolio.setDescription(portfolioDto.getDescription());
            if (portfolioDto.getImageUrl() != null) portfolio.setImageUrl(portfolioDto.getImageUrl());
            if (portfolioDto.getCategory() != null) portfolio.setCategory(portfolioDto.getCategory());
            if (portfolioDto.getClient() != null) portfolio.setClient(portfolioDto.getClient());
            if (portfolioDto.getProjectUrl() != null) portfolio.setProjectUrl(portfolioDto.getProjectUrl());

            portfolio.setUpdatedAt(LocalDateTime.now());

            Portfolio updated = portfolioRepository.save(portfolio);
            return modelMapper.map(updated, PortfolioDto.class);
        }

        return null;
    }

    @Override
    public void deletePortfolio(Long id) {
        portfolioRepository.deleteById(id);
    }

    @Override
    public List<PortfolioDto> getPortfoliosByCategory(String category) {
        List<Portfolio> list = portfolioRepository.findByCategory(category);
        List<PortfolioDto> result = new ArrayList<>();
        for (Portfolio p : list) {
            result.add(modelMapper.map(p, PortfolioDto.class));
        }
        return result;
    }

    @Override
    public PortfolioDto getPortfolioBySlug(String slug) {
        return portfolioRepository.findBySlug(slug)
                .map(portfolio -> modelMapper.map(portfolio, PortfolioDto.class))
                .orElse(null);
    }
}

