package com.codesupreme.mototaksiwebapi.service.impl.elan;


import com.codesupreme.mototaksiwebapi.dao.elan.ElanRepository;
import com.codesupreme.mototaksiwebapi.dto.elan.ElanDto;
import com.codesupreme.mototaksiwebapi.model.elan.Elan;
import com.codesupreme.mototaksiwebapi.service.inter.elan.ElanServiceInter;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ElanServiceImpl implements ElanServiceInter {

    private final ElanRepository elanRepository;
    private final ModelMapper modelMapper;

    public ElanServiceImpl(ElanRepository elanRepository, ModelMapper modelMapper) {
        this.elanRepository = elanRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ElanDto> getAllElans() {
        List<ElanDto> listDto = new ArrayList<>();

        List<Elan> listEntity = elanRepository.findAll();

        for (Elan entity : listEntity) {
            ElanDto dto = modelMapper.map(entity, ElanDto.class);
            listDto.add(dto);
        }
        return listDto;
    }

    @Override
    public ElanDto getElanById(Long id) {
        Elan entity = elanRepository.findById(id).orElse(null);
        if (entity != null) {
            return modelMapper.map(entity, ElanDto.class);
        }
        return null;
    }

    @Override
    public ResponseEntity<ElanDto> createElan(ElanDto elanDto) {
        Elan elanEntity = modelMapper.map(elanDto, Elan.class);
        elanEntity.setIsDeleted(false);
        Elan savedElan = elanRepository.save(elanEntity);
        return ResponseEntity.ok(modelMapper.map(savedElan, ElanDto.class));

    }

    @Override
    public ElanDto updateElan(Long id, ElanDto elanDto) {
        Optional<Elan> elanOptional = elanRepository.findById(id);
        if (elanOptional.isPresent()) {
            Elan elan = elanOptional.get();

            if (elanDto.getUserId() != null) {
                elan.setUserId(elanDto.getUserId());
            }

            if (elanDto.getImage() != null) {
                elan.setImage(elanDto.getImage());
            }

            if (elanDto.getPrice() != null) {
                elan.setPrice(elanDto.getPrice());
            }

            if (elanDto.getTitle() != null) {
                elan.setTitle(elanDto.getTitle());
            }

            if (elanDto.getDescription() != null) {
                elan.setDescription(elanDto.getDescription());
            }

            if (elanDto.getCategory() != null) {
                elan.setCategory(elanDto.getCategory());
            }

            if (elanDto.getPremium() != null) {
                elan.setPremium(elanDto.getPremium());
            }

            if (elanDto.getExpireDate() != null) {
                elan.setExpireDate(elanDto.getExpireDate());
            }

            if (elanDto.getIsDeleted() != null) {
                elan.setIsDeleted(elanDto.getIsDeleted());
            }

            if (elanDto.getCreatedAt() != null) {
                elan.setCreatedAt(elanDto.getCreatedAt());
            }

            if (elanDto.getUpdatedAt() != null) {
                elan.setUpdatedAt(elanDto.getUpdatedAt());
            }


            elan = elanRepository.save(elan);
            return modelMapper.map(elan, ElanDto.class);


        }
        return null;
    }

    @Override
    public void deleteElanById(Long id) {
        elanRepository.deleteById(id);
    }


}
