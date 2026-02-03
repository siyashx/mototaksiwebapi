package com.codesupreme.mototaksiwebapi.menyupro.api;

import com.codesupreme.mototaksiwebapi.menyupro.dao.MpBusinessRepository;
import com.codesupreme.mototaksiwebapi.menyupro.model.MpBusiness;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MpShareController {

    private final MpBusinessRepository businessRepo;

    @GetMapping(value = "/{slug}", produces = MediaType.TEXT_HTML_VALUE)
    public String sharePage(@PathVariable String slug) {

        var opt = businessRepo.findBySlug(slug);

        String url = "https://mototaksi.az/" + slug;

        if (opt.isEmpty()) {
            return ogPage(
                    "MotoTaksi",
                    "Menyu tapılmadı",
                    "https://mototaksi.az/favicon.ico",
                    url
            );
        }

        MpBusiness b = opt.get();

        String title = safe(b.getBusinessName(), "MenuPro");
        String desc  = safe(b.getBio(), "Onlayn menyu");
        String image = safe(b.getProfileImage(), "https://mototaksi.az/favicon.ico");

        // ✅ OG meta + user browser üçün redirect
        return ogPage(title, desc, image, url);
    }

    private String ogPage(String title, String desc, String image, String url) {
        title = esc(title);
        desc  = esc(desc);
        image = esc(image);
        url   = esc(url);

        return """
            <!doctype html>
            <html lang="az">
              <head>
                <meta charset="utf-8"/>
                <title>%s</title>
                <meta name="description" content="%s"/>
                <meta property="og:title" content="%s"/>
                <meta property="og:description" content="%s"/>
                <meta property="og:image" content="%s"/>
                <meta property="og:type" content="website"/>
                <meta property="og:url" content="%s"/>
                <meta name="twitter:card" content="summary_large_image"/>
                <meta name="twitter:title" content="%s"/>
                <meta name="twitter:description" content="%s"/>
                <meta name="twitter:image" content="%s"/>
                <script>window.location.replace("%s");</script>
              </head>
              <body>
                <noscript><a href="%s">Davam et</a></noscript>
              </body>
            </html>
            """.formatted(title, desc, title, desc, image, url, title, desc, image, url, url);
    }

    private String safe(String v, String def) {
        if (v == null) return def;
        String s = v.trim();
        return s.isEmpty() ? def : s;
    }

    private String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
}
