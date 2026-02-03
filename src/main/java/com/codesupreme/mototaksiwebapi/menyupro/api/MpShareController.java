package com.codesupreme.mototaksiwebapi.menyupro.api;

import com.codesupreme.mototaksiwebapi.menyupro.dao.MpBusinessRepository;
import com.codesupreme.mototaksiwebapi.menyupro.model.MpBusiness;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MpShareController {

    private final MpBusinessRepository businessRepo;

    @GetMapping(value = "/{slug}", produces = MediaType.TEXT_HTML_VALUE)
    public String sharePage(@PathVariable String slug) throws Exception {

        Optional<MpBusiness> opt = businessRepo.findBySlug(slug);
        String index = readIndexHtml();

        if (opt.isEmpty()) {
            return index; // default index.html
        }

        MpBusiness b = opt.get();

        String title = safe(b.getBusinessName(), "MenuPro");
        String desc  = safe(b.getBio(), "Menyu");
        String image = safe(b.getProfileImage(), "https://img.icons8.com/fluent/1200/shop.jpg");
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
    }

    private String readIndexHtml() throws Exception {
        // ✅ classpath-dan oxu (səndə əvvəldə /var/www/... səhvi burdan çıxmışdı)
        ClassPathResource res = new ClassPathResource("static/index.html");
        return StreamUtils.copyToString(res.getInputStream(), StandardCharsets.UTF_8);
    }

    private String safe(String s, String def) {
        if (s == null) return def;
        String t = s.trim();
        return t.isEmpty() ? def : t;
    }

    private String esc(String s) {
        return s.replace("&","&amp;").replace("\"","&quot;").replace("<","&lt;").replace(">","&gt;");
    }
}
