package com.codesupreme.mototaksiwebapi.service.impl.lotoreya;

import com.codesupreme.mototaksiwebapi.dao.lotoreya.BiletRepository;
import com.codesupreme.mototaksiwebapi.dao.lotoreya.CarxRepository;
import com.codesupreme.mototaksiwebapi.dao.lotoreya.LotoreyaRepository;
import com.codesupreme.mototaksiwebapi.dto.lotoreya.CarxDto;
import com.codesupreme.mototaksiwebapi.model.lotoreya.Bilet;
import com.codesupreme.mototaksiwebapi.model.lotoreya.Carx;
import com.codesupreme.mototaksiwebapi.model.lotoreya.Lotoreya;
import com.codesupreme.mototaksiwebapi.model.user.User;
import com.codesupreme.mototaksiwebapi.service.inter.lotoreya.CarxServiceInter;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CarxServiceImpl implements CarxServiceInter {

    private final CarxRepository carxRepository;
    private final LotoreyaRepository lotoreyaRepository;
    private final BiletRepository biletRepository;
    private final ModelMapper modelMapper;

    public CarxServiceImpl(CarxRepository carxRepository,
                           LotoreyaRepository lotoreyaRepository,
                           BiletRepository biletRepository,
                           ModelMapper modelMapper) {
        this.carxRepository = carxRepository;
        this.lotoreyaRepository = lotoreyaRepository;
        this.biletRepository = biletRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CarxDto> getAllCarxs() {
        return carxRepository.findAll().stream()
                .map(car -> modelMapper.map(car, CarxDto.class))
                .toList();
    }

    @Override
    public CarxDto getCarxById(Long id) {
        return carxRepository.findById(id)
                .map(car -> modelMapper.map(car, CarxDto.class))
                .orElse(null);
    }

    @Override
    public CarxDto createCarx(CarxDto carxDto) {
        Carx car = modelMapper.map(carxDto, Carx.class);
        car.setCreatedAt(new Date());
        Carx saved = carxRepository.save(car);
        return modelMapper.map(saved, CarxDto.class);
    }

    @Override
    public CarxDto updateCarx(Long id, CarxDto carxDto) {
        Optional<Carx> optionalCarx = carxRepository.findById(id);
        if (optionalCarx.isPresent()) {
            Carx carx = optionalCarx.get();

            if (carxDto.getWinnerUserId() != null) {
                carx.setWinnerUser(new User(carxDto.getWinnerUserId()));
            }

            if (carxDto.getWinnerBiletCode() != null) {
                carx.setWinnerBiletCode(carxDto.getWinnerBiletCode());
            }

            if (carxDto.getCreatedAt() != null) {
                carx.setCreatedAt(carxDto.getCreatedAt());
            }

            return modelMapper.map(carxRepository.save(carx), CarxDto.class);
        }
        return null;
    }

    @Override
    public void deleteCarxById(Long id) {
        carxRepository.deleteById(id);
    }

    @Transactional
    @Override
    public CarxDto assignWinner(Long lotoreyaId, List<String> finalistCodes) {
        if (lotoreyaId == null || finalistCodes == null || finalistCodes.size() < 2) {
            throw new RuntimeException("Zəhmət olmasa 2 finalist təqdim edin.");
        }

        // Lotoreyanı tapırıq
        Lotoreya lotoreya = lotoreyaRepository.findById(lotoreyaId).orElse(null);
        if (lotoreya == null) {
            throw new RuntimeException("Lotoreya tapılmadı.");
        }

        // Bütün biletlərdən finalistləri süzürük
        List<Bilet> finalists = biletRepository.findByLotoreyaId(lotoreyaId).stream()
                .filter(b -> finalistCodes.contains(b.getCode()))
                .toList();

        if (finalists.size() < 2) {
            throw new RuntimeException("2 finalist bilet tapılmadı.");
        }

        // Qalib bilet random seçilir
        Bilet winnerBilet = finalists.get(new Random().nextInt(finalists.size()));
        if (winnerBilet.getUser() == null) {
            throw new RuntimeException("Qalib istifadəçi məlumatı tapılmadı.");
        }

        // Qalib üçün Carx obyektini yaradırıq
        Carx carx = Carx.builder()
                .lotoreya(lotoreya)
                .winnerUser(winnerBilet.getUser())
                .winnerBiletCode(winnerBilet.getCode())
                .createdAt(new Date())
                .build();

        Carx savedCarx = carxRepository.save(carx);

        // Lotoreya ilə əlaqələndiririk
        lotoreya.setCarx(savedCarx);
        lotoreyaRepository.save(lotoreya);

        return modelMapper.map(savedCarx, CarxDto.class);
    }


}
