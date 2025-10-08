package com.codesupreme.mototaksiwebapi.service.impl.qrcode;

import com.codesupreme.mototaksiwebapi.dao.qrcode.QrCodeRepository;
import com.codesupreme.mototaksiwebapi.dto.qrcode.QrCodeDto;
import com.codesupreme.mototaksiwebapi.model.qrcode.QrCode;
import com.codesupreme.mototaksiwebapi.service.inter.qrcode.QrCodeServiceInter;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QrCodeServiceImpl implements QrCodeServiceInter {

    private final QrCodeRepository qrCodeRepository;
    private final ModelMapper modelMapper;

    public QrCodeServiceImpl(QrCodeRepository qrCodeRepository, ModelMapper modelMapper) {
        this.qrCodeRepository = qrCodeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<QrCodeDto> getAllQrCodes() {
        List<QrCodeDto> listDto = new ArrayList<>();

        List<QrCode> listEntity = qrCodeRepository.findAll();

        for (QrCode entity : listEntity) {
            QrCodeDto dto = modelMapper.map(entity, QrCodeDto.class);
            listDto.add(dto);
        }
        return listDto;
    }

    @Override
    public QrCodeDto getQrCodeById(Long id) {
        QrCode entity = qrCodeRepository.findById(id).orElse(null);
        if (entity != null) {
            return modelMapper.map(entity, QrCodeDto.class);
        }
        return null;
    }

    @Override
    public ResponseEntity<QrCodeDto> createQrCode(QrCodeDto qrCodeDto) {
        QrCode qrCodeEntity = modelMapper.map(qrCodeDto, QrCode.class);
        QrCode savedQrCode = qrCodeRepository.save(qrCodeEntity);
        return ResponseEntity.ok(modelMapper.map(savedQrCode, QrCodeDto.class));

    }

    @Override
    public QrCodeDto updateQrCode(Long id, QrCodeDto qrCodeDto) {
        Optional<QrCode> qrCodeOptional = qrCodeRepository.findById(id);
        if (qrCodeOptional.isPresent()) {
            QrCode qrCode = qrCodeOptional.get();

            if (qrCodeDto.getMainLink() != null) {
                qrCode.setMainLink(qrCodeDto.getMainLink());
            }

            if (qrCodeDto.getIsAppLink() != null) {
                qrCode.setIsAppLink(qrCodeDto.getIsAppLink());
            }

            if (qrCodeDto.getAndroidLink() != null) {
                qrCode.setAndroidLink(qrCodeDto.getAndroidLink());
            }

            if (qrCodeDto.getIosLink() != null) {
                qrCode.setIosLink(qrCodeDto.getIosLink());
            }

            if (qrCodeDto.getCreatedAt() != null) {
                qrCode.setCreatedAt(qrCodeDto.getCreatedAt());
            }

            if (qrCodeDto.getUpdatedAt() != null) {
                qrCode.setUpdatedAt(qrCodeDto.getUpdatedAt());
            }


            qrCode = qrCodeRepository.save(qrCode);
            return modelMapper.map(qrCode, QrCodeDto.class);


        }
        return null;
    }

    @Override
    public void deleteQrCodeById(Long id) {
        qrCodeRepository.deleteById(id);
    }

}
