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
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Override
    public CarxDto assignWinner(Long lotoreyaId) {
        Lotoreya lotoreya = lotoreyaRepository.findById(lotoreyaId).orElse(null);
        if (lotoreya == null) return null;

        List<Bilet> bilets = biletRepository.findByLotoreyaId(lotoreyaId);
        if (bilets.isEmpty()) throw new RuntimeException("Bu lotoreyada heç bir bilet yoxdur.");

        // Random qalib seç
        Bilet winnerBilet = bilets.get(new Random().nextInt(bilets.size()));

        Carx carx = Carx.builder()
                .lotoreya(lotoreya)
                .winnerUser(winnerBilet.getUser())
                .winnerBiletCode(winnerBilet.getCode())
                .createdAt(new Date())
                .build();

        Carx saved = carxRepository.save(carx);

        // Əlaqələndir lotoreya ilə (optional)
        lotoreya.setCarx(saved);
        lotoreyaRepository.save(lotoreya);

        return modelMapper.map(saved, CarxDto.class);
    }
}
