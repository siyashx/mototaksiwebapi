package com.codesupreme.mototaksiwebapi.menyupro.dao;

import com.codesupreme.mototaksiwebapi.menyupro.model.MpBusiness;
import com.codesupreme.mototaksiwebapi.menyupro.model.MpCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MpCategoryRepository extends JpaRepository<MpCategory, Long> {

    List<MpCategory> findAllByBusiness(MpBusiness business);
}
