package com.codesupreme.mototaksiwebapi.menyupro.api;

import com.codesupreme.mototaksiwebapi.menyupro.dao.MpBusinessRepository;
import com.codesupreme.mototaksiwebapi.menyupro.model.MpBusiness;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MpShareController {

    private final MpBusinessRepository businessRepo;

    // ✅ React build index.html-in real yolu
    @Value("${mp.share.indexPath:/var/www/mototaksi/index.html}")
    private String indexPath;

    @GetMapping(value = "/{slug}", produces = MediaType.TEXT_HTML_VALUE)
    public String sharePage(@PathVariable String slug) throws Exception {

        String index = readIndexHtml(); // ✅ filesystem-dən oxu

        try {
            Optional<MpBusiness> opt = businessRepo.findBySlug(slug);
            if (opt.isEmpty()) return index;

            MpBusiness b = opt.get();

            String title = safe(b.getBusinessName(), "MotoTaksi Menu");
            String desc  = safe(b.getBio(), "MenuPro restoran menyusu");
            String image = safe(b.getProfileImage(), "https://mototaksi.az/android-chrome-192x192.png");
            String url   = "https://mototaksi.az/" + slug;

            String og = """
                <title>%s</title>
                <meta name="description" content="%s"/>
                <meta property="og:title" content="%s"/>
                <meta property="og:description" content="%s"/>
                <meta property="og:image" content="%s"/>
                <meta property="og:type" content="website"/>
                <meta property="og:url" content="%s"/>
                """.formatted(esc(title), esc(desc), esc(title), esc(desc), esc(image), esc(url));

            return index.replace("</head>", og + "\n</head>");
        } catch (Exception e) {
            // ✅ heç olmasa 500 qaytarmayaq
            return index;
        }
    }

    private String readIndexHtml() throws Exception {
        return Files.readString(Path.of(indexPath), StandardCharsets.UTF_8);
    }

    private String safe(String v, String def) {
        if (v == null) return def;
        String s = v.trim();
        return s.isEmpty() ? def : s;
    }

    // sadə HTML escape (səhv meta qırılmasın deyə)
    private String esc(String s) {
        return s.replace("&", "&amp;")
                .replace("\"", "&quot;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}
