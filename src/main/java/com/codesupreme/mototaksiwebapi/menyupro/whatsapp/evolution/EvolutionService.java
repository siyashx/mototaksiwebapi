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

    public void notifyAdminNewBusiness(String businessName, String phone) {

        String normalized = PhoneUtil.normalize(phone);

        String msg = """
            🆕 Yeni MenuPro Biznes Qeydiyyatı
            
            Biznes: %s
            Telefon: %s
            
            Admin paneldən təsdiqləyin.
            """.formatted(businessName, normalized);

        // 🔴 Admin nömrəsi
        String adminPhone = "994709559984";

        client.sendText(adminPhone, msg);
    }

}
