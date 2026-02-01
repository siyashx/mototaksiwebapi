package com.codesupreme.mototaksiwebapi.menyupro.whatsapp.evolution;

import com.codesupreme.mototaksiwebapi.menyupro.util.PhoneUtil;
import org.springframework.stereotype.Service;

@Service
public class EvolutionService {

    private final EvolutionClient client;

    public EvolutionService(EvolutionClient client) {
        this.client = client;
    }

    public void sendOtp(String phone, String otp) {

        String normalized = PhoneUtil.normalize(phone);

        String msg = """
                MenuPro Təsdiqləmə Kodu: %s
                
                Bu kodu heç kimlə paylaşmayın.
                """.formatted(otp);

        // Səndə artıq bu 994... format işləyir
        client.sendText(normalized, msg);

        // Əgər gələcəkdə problem olsa:
        // client.sendText(PhoneUtil.toWaJid(normalized), msg);
    }
}
