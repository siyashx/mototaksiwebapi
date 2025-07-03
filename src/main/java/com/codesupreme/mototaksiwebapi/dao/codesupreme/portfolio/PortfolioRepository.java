package com.codesupreme.mototaksiwebapi.dao.codesupreme.portfolio;

import com.codesupreme.mototaksiwebapi.model.codesupreme.portfolio.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    Optional<Portfolio> findBySlug(String slug);

    List<Portfolio> findByCategory(String category);

    boolean existsBySlug(String slug);
}

