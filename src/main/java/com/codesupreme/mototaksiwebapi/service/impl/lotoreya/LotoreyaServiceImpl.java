package com.codesupreme.mototaksiwebapi.service.impl.lotoreya;

import com.codesupreme.mototaksiwebapi.dao.lotoreya.LotoreyaRepository;
import com.codesupreme.mototaksiwebapi.dto.lotoreya.LotoreyaDto;
import com.codesupreme.mototaksiwebapi.model.elan.Elan;
import com.codesupreme.mototaksiwebapi.model.lotoreya.Lotoreya;
import com.codesupreme.mototaksiwebapi.model.user.User;
import com.codesupreme.mototaksiwebapi.service.inter.lotoreya.LotoreyaServiceInter;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LotoreyaServiceImpl implements LotoreyaServiceInter {

    private final LotoreyaRepository lotoreyaRepository;
    private final ModelMapper modelMapper;

    public LotoreyaServiceImpl(LotoreyaRepository lotoreyaRepository, ModelMapper modelMapper) {
        this.lotoreyaRepository = lotoreyaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<LotoreyaDto> getAllLotoreyas() {
        List<LotoreyaDto> dtoList = new ArrayList<>();
        List<Lotoreya> entityList = lotoreyaRepository.findAll();

        for (Lotoreya entity : entityList) {
            LotoreyaDto dto = modelMapper.map(entity, LotoreyaDto.class);
            dtoList.add(dto);
        }

        return dtoList;
    }

    @Override
    public LotoreyaDto getLotoreyaById(Long id) {
        Lotoreya entity = lotoreyaRepository.findById(id).orElse(null);
        if (entity != null) {
            return modelMapper.map(entity, LotoreyaDto.class);
        }
        return null;
    }

    @Override
    public ResponseEntity<LotoreyaDto> createLotoreya(LotoreyaDto lotoreyaDto) {
        Lotoreya entity = modelMapper.map(lotoreyaDto, Lotoreya.class);
        entity.setIsAccept(false); // default olaraq qeyri-aktiv
        entity.setCreatedAt(new java.util.Date());

        Lotoreya saved = lotoreyaRepository.save(entity);
        return ResponseEntity.ok(modelMapper.map(saved, LotoreyaDto.class));
    }

    @Override
    public LotoreyaDto updateLotoreya(Long id, LotoreyaDto dto) {
        Optional<Lotoreya> lotoreyaOptional = lotoreyaRepository.findById(id);

        if (lotoreyaOptional.isPresent()) {
            Lotoreya lotoreya = lotoreyaOptional.get();

            if (dto.getUserId() != null) {
                lotoreya.setUser(new User(dto.getUserId(), null, null, null, null, null, null, null, null));
            }

            if (dto.getElanId() != null) {
                lotoreya.setElan(new Elan(dto.getElanId()));
            }


            if (dto.getBiletPrice() != null) {
                lotoreya.setBiletPrice(dto.getBiletPrice());
            }

            if (dto.getLotoreyaDate() != null) {
                lotoreya.setLotoreyaDate(dto.getLotoreyaDate());
            }

            if (dto.getIsAccept() != null) {
                lotoreya.setIsAccept(dto.getIsAccept());
            }

            if (dto.getCreatedAt() != null) {
                lotoreya.setCreatedAt(dto.getCreatedAt());
            }

            Lotoreya updated = lotoreyaRepository.save(lotoreya);
            return modelMapper.map(updated, LotoreyaDto.class);
        }

        return null;
    }

    @Override
    public void deleteLotoreyaById(Long id) {
        lotoreyaRepository.deleteById(id);
    }
}
