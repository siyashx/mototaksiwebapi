package com.codesupreme.mototaksiwebapi.menyupro.dao;

import com.codesupreme.mototaksiwebapi.menyupro.model.MpBusiness;
import com.codesupreme.mototaksiwebapi.menyupro.model.MpProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MpProductRepository extends JpaRepository<MpProduct, Long> {

    List<MpProduct> findAllByBusiness(MpBusiness business);

    List<MpProduct> findAllByCategoryId(Long categoryId);
}
