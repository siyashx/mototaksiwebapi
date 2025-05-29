package com.codesupreme.mototaksiwebapi.service.impl.lotoreya;

import com.codesupreme.mototaksiwebapi.dao.lotoreya.BiletRepository;
import com.codesupreme.mototaksiwebapi.dao.lotoreya.LotoreyaRepository;
import com.codesupreme.mototaksiwebapi.dao.user.UserRepository;
import com.codesupreme.mototaksiwebapi.dto.lotoreya.BiletDto;
import com.codesupreme.mototaksiwebapi.model.lotoreya.Bilet;
import com.codesupreme.mototaksiwebapi.model.lotoreya.Lotoreya;
import com.codesupreme.mototaksiwebapi.model.user.User;
import com.codesupreme.mototaksiwebapi.service.inter.lotoreya.BiletServiceInter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BiletServiceImpl implements BiletServiceInter {

    private final BiletRepository biletRepository;
    private final LotoreyaRepository lotoreyaRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public BiletServiceImpl(
            BiletRepository biletRepository,
            LotoreyaRepository lotoreyaRepository,
            UserRepository userRepository,
            ModelMapper modelMapper) {
        this.biletRepository = biletRepository;
        this.lotoreyaRepository = lotoreyaRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BiletDto> getAllBilets() {
        return biletRepository.findAll().stream()
                .map(bilet -> modelMapper.map(bilet, BiletDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public BiletDto getBiletById(Long id) {
        return biletRepository.findById(id)
                .map(bilet -> modelMapper.map(bilet, BiletDto.class))
                .orElse(null);
    }

    @Override
    public List<BiletDto> getBiletsByUserId(Long userId) {
        return biletRepository.findByUserId(userId).stream()
                .map(bilet -> modelMapper.map(bilet, BiletDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BiletDto> getBiletsByLotoreyaId(Long lotoreyaId) {
        return biletRepository.findByLotoreyaId(lotoreyaId).stream()
                .map(bilet -> modelMapper.map(bilet, BiletDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public BiletDto buyBilet(Long lotoreyaId, Long userId) {
        Lotoreya lotoreya = lotoreyaRepository.findById(lotoreyaId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (lotoreya == null || user == null) return null;

        Double biletPrice = lotoreya.getBiletPrice();
        if (user.getBalance() < biletPrice) {
            throw new RuntimeException("Balance kifayət etmir!");
        }

        // İstifadəçi balansdan çıxılır
        user.setBalance(user.getBalance() - biletPrice);
        userRepository.save(user);

        // Yeni bilet yaradılır
        Bilet bilet = Bilet.builder()
                .code(generateBiletCode())
                .lotoreya(lotoreya)
                .user(user)
                .createdAt(new Date())
                .build();

        Bilet saved = biletRepository.save(bilet);
        return modelMapper.map(saved, BiletDto.class);
    }

    @Override
    public void deleteBilet(Long id) {
        biletRepository.deleteById(id);
    }

    private String generateBiletCode() {
        return "B-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
