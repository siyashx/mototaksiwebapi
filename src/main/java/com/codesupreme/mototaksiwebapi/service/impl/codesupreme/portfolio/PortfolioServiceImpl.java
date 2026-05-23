package com.codesupreme.mototaksiwebapi.service.impl.codesupreme.portfolio;

import com.codesupreme.mototaksiwebapi.dao.codesupreme.portfolio.PortfolioRepository;
import com.codesupreme.mototaksiwebapi.dto.codesupreme.portfolio.PortfolioDto;
import com.codesupreme.mototaksiwebapi.model.codesupreme.portfolio.Portfolio;
import com.codesupreme.mototaksiwebapi.service.inter.codesupreme.portfolio.PortfolioServiceInter;
import com.codesupreme.mototaksiwebapi.util.SlugUtil;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return portfolioRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public List<PortfolioDto> getActivePortfolios() {
        return portfolioRepository.findByActiveTrueOrderByCreatedAtDesc()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public PortfolioDto getPortfolioById(Long id) {
        return portfolioRepository.findById(id)
                .map(this::mapToDto)
                .orElse(null);
    }

    @Override
    public PortfolioDto getPortfolioBySlug(String slug) {
        return portfolioRepository.findBySlug(slug)
                .map(this::mapToDto)
                .orElse(null);
    }

    @Override
    public ResponseEntity<PortfolioDto> createPortfolio(PortfolioDto portfolioDto) {
        Portfolio portfolio = modelMapper.map(portfolioDto, Portfolio.class);

        String title = portfolioDto.getTitle();
        String category = portfolioDto.getCategory();

        portfolio.setSlug(
                portfolioDto.getSlug() != null && !portfolioDto.getSlug().isBlank()
                        ? generateUniqueSlug(portfolioDto.getSlug())
                        : generateUniqueSlug(title)
        );

        portfolio.setCategoryKey(
                portfolioDto.getCategoryKey() != null && !portfolioDto.getCategoryKey().isBlank()
                        ? portfolioDto.getCategoryKey()
                        : generateCategoryKey(category)
        );

        portfolio.setImageAlt(
                portfolioDto.getImageAlt() != null && !portfolioDto.getImageAlt().isBlank()
                        ? portfolioDto.getImageAlt()
                        : title
        );

        portfolio.setActive(
                portfolioDto.getActive() != null
                        ? portfolioDto.getActive()
                        : true
        );

        Portfolio saved = portfolioRepository.save(portfolio);

        return ResponseEntity.ok(mapToDto(saved));
    }

    @Override
    public PortfolioDto updatePortfolio(Long id, PortfolioDto portfolioDto) {
        return portfolioRepository.findById(id)
                .map(portfolio -> {

                    if (portfolioDto.getTitle() != null) {
                        portfolio.setTitle(portfolioDto.getTitle());
                    }

                    if (portfolioDto.getDescription() != null) {
                        portfolio.setDescription(portfolioDto.getDescription());
                    }

                    if (portfolioDto.getImageUrl() != null) {
                        portfolio.setImageUrl(portfolioDto.getImageUrl());
                    }

                    if (portfolioDto.getImageAlt() != null) {
                        portfolio.setImageAlt(portfolioDto.getImageAlt());
                    }

                    if (portfolioDto.getCategory() != null) {
                        portfolio.setCategory(portfolioDto.getCategory());
                    }

                    if (portfolioDto.getCategoryKey() != null && !portfolioDto.getCategoryKey().isBlank()) {
                        portfolio.setCategoryKey(portfolioDto.getCategoryKey());
                    } else if (portfolioDto.getCategory() != null && !portfolioDto.getCategory().isBlank()) {
                        portfolio.setCategoryKey(generateCategoryKey(portfolioDto.getCategory()));
                    }

                    if (portfolioDto.getClient() != null) {
                        portfolio.setClient(portfolioDto.getClient());
                    }

                    if (portfolioDto.getProjectUrl() != null) {
                        portfolio.setProjectUrl(portfolioDto.getProjectUrl());
                    }

                    if (portfolioDto.getActive() != null) {
                        portfolio.setActive(portfolioDto.getActive());
                    }

                    if (portfolioDto.getSlug() != null && !portfolioDto.getSlug().isBlank()) {
                        String newSlug = SlugUtil.toSlug(portfolioDto.getSlug());

                        if (!newSlug.equals(portfolio.getSlug())) {
                            portfolio.setSlug(generateUniqueSlugForUpdate(newSlug, portfolio.getId()));
                        }
                    } else if (portfolioDto.getTitle() != null && !portfolioDto.getTitle().isBlank()) {
                        String newSlug = SlugUtil.toSlug(portfolioDto.getTitle());

                        if (!newSlug.equals(portfolio.getSlug())) {
                            portfolio.setSlug(generateUniqueSlugForUpdate(newSlug, portfolio.getId()));
                        }
                    }

                    if ((portfolio.getImageAlt() == null || portfolio.getImageAlt().isBlank())
                            && portfolio.getTitle() != null) {
                        portfolio.setImageAlt(portfolio.getTitle());
                    }

                    if ((portfolio.getCategoryKey() == null || portfolio.getCategoryKey().isBlank())
                            && portfolio.getCategory() != null) {
                        portfolio.setCategoryKey(generateCategoryKey(portfolio.getCategory()));
                    }

                    Portfolio updated = portfolioRepository.save(portfolio);

                    return mapToDto(updated);
                })
                .orElse(null);
    }

    @Override
    public List<PortfolioDto> getPortfoliosByCategory(String category) {
        return portfolioRepository.findByCategoryIgnoreCase(category)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public List<PortfolioDto> getPortfoliosByCategoryKey(String categoryKey) {
        return portfolioRepository.findByCategoryKeyIgnoreCase(categoryKey)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public void deletePortfolio(Long id) {
        portfolioRepository.deleteById(id);
    }

    private PortfolioDto mapToDto(Portfolio portfolio) {
        return modelMapper.map(portfolio, PortfolioDto.class);
    }

    private String generateUniqueSlug(String value) {
        String baseSlug = SlugUtil.toSlug(value);

        if (baseSlug == null || baseSlug.isBlank()) {
            baseSlug = "portfolio";
        }

        String slug = baseSlug;
        int counter = 1;

        while (portfolioRepository.existsBySlug(slug)) {
            slug = baseSlug + "-" + counter;
            counter++;
        }

        return slug;
    }

    private String generateUniqueSlugForUpdate(String value, Long id) {
        String baseSlug = SlugUtil.toSlug(value);

        if (baseSlug == null || baseSlug.isBlank()) {
            baseSlug = "portfolio";
        }

        String slug = baseSlug;
        int counter = 1;

        while (portfolioRepository.existsBySlugAndIdNot(slug, id)) {
            slug = baseSlug + "-" + counter;
            counter++;
        }

        return slug;
    }

    private String generateCategoryKey(String category) {
        if (category == null || category.isBlank()) {
            return "layiheler";
        }

        String normalized = category.trim().toLowerCase();

        return switch (normalized) {
            case "veb sayt", "web sayt", "vebsayt", "websayt" -> "veb-sayt";
            case "mobil tətbiq", "mobil tetbiq", "mobil app", "mobil" -> "mobil-tetbiq";
            case "dizayn", "design" -> "dizayn";
            case "startap", "startup" -> "startap";
            case "layihələr", "layiheler", "layihələrimiz", "layihelerimiz", "layihə", "layihe" -> "layiheler";
            default -> SlugUtil.toSlug(category);
        };
    }
}