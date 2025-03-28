package com.codesupreme.mototaksiwebapi.dao.elan;

import com.codesupreme.mototaksiwebapi.model.elan.Elan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElanRepository  extends JpaRepository<Elan, Long> {
}
