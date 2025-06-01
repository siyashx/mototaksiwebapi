package com.codesupreme.mototaksiwebapi.service.impl.lotoreya;

import com.codesupreme.mototaksiwebapi.dao.elan.ElanRepository;
import com.codesupreme.mototaksiwebapi.dao.lotoreya.LotoreyaRepository;
import com.codesupreme.mototaksiwebapi.dao.user.UserRepository;
import com.codesupreme.mototaksiwebapi.dto.lotoreya.LotoreyaDto;
import com.codesupreme.mototaksiwebapi.model.elan.Elan;
import com.codesupreme.mototaksiwebapi.model.lotoreya.Bilet;
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
    private final UserRepository userRepository;
    private final ElanRepository elanRepository;
    private final ModelMapper modelMapper;

    public LotoreyaServiceImpl(
            LotoreyaRepository lotoreyaRepository,
            ModelMapper modelMapper,
            UserRepository userRepository,
            ElanRepository elanRepository
    ) {
        this.lotoreyaRepository = lotoreyaRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.elanRepository = elanRepository;
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
        Lotoreya entity = new Lotoreya();

        // User və Elan obyekti manual set edilir
        User user = userRepository.findById(lotoreyaDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User tapılmadı"));

        Elan elan = elanRepository.findById(lotoreyaDto.getElanId())
                .orElseThrow(() -> new RuntimeException("Elan tapılmadı"));

        entity.setUser(user);
        entity.setElan(elan);

        entity.setIsAccept(false); // default olaraq qeyri-aktiv
        entity.setCreatedAt(new java.util.Date());

        // digər field-lar əgər lazım olsa
        entity.setBiletPrice(lotoreyaDto.getBiletPrice());
        entity.setTotalBiletCount(lotoreyaDto.getTotalBiletCount());
        entity.setLotoreyaDate(lotoreyaDto.getLotoreyaDate());

        Lotoreya saved = lotoreyaRepository.save(entity);

        // Geri dönərkən userId və elanId yenidən doldurulsun
        LotoreyaDto responseDto = new LotoreyaDto();
        responseDto.setId(saved.getId());
        responseDto.setUserId(saved.getUser().getId());
        responseDto.setElanId(saved.getElan().getId());
        responseDto.setIsAccept(saved.getIsAccept());
        responseDto.setCreatedAt(saved.getCreatedAt());
        responseDto.setBiletPrice(saved.getBiletPrice());
        responseDto.setTotalBiletCount(saved.getTotalBiletCount());
        responseDto.setLotoreyaDate(saved.getLotoreyaDate());

        // carx və biletList boş qala bilər (əgər indi əlavə olunmursa)

        return ResponseEntity.ok(responseDto);
    }


    @Override
    public LotoreyaDto updateLotoreya(Long id, LotoreyaDto dto) {
        Optional<Lotoreya> lotoreyaOptional = lotoreyaRepository.findById(id);

        if (lotoreyaOptional.isPresent()) {
            Lotoreya lotoreya = lotoreyaOptional.get();

            if (dto.getUserId() != null) {
                lotoreya.setUser(new User(dto.getUserId(), null, null, null, null, null, null, null));
            }

            if (dto.getElanId() != null) {
                lotoreya.setElan(new Elan(dto.getElanId()));
            }

            if (dto.getBiletPrice() != null) {
                lotoreya.setBiletPrice(dto.getBiletPrice());
            }

            if (dto.getBilets() != null) {
                List<Bilet> biletEntities = dto.getBilets().stream()
                        .map(biletDto -> modelMapper.map(biletDto, Bilet.class))
                        .toList();

                lotoreya.setBilets(biletEntities);
            }

            if (dto.getTotalBiletCount() != null) {
                lotoreya.setTotalBiletCount(dto.getTotalBiletCount());
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
