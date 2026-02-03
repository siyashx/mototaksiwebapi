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
        if (opt.isEmpty()) {
            return readIndexHtml();
        }

        MpBusiness b = opt.get();

        String title = b.getBusinessName();
        String desc = b.getBio();
        String image = b.getProfileImage();
        String url = "https://mototaksi.az/" + slug;

        String index = readIndexHtml();

        String og = """
            <title>%s</title>
            <meta name="description" content="%s"/>
            <meta property="og:title" content="%s"/>
            <meta property="og:description" content="%s"/>
            <meta property="og:image" content="%s"/>
            <meta property="og:type" content="website"/>
            <meta property="og:url" content="%s"/>
            """.formatted(title, desc, title, desc, image, url);

        return index.replace("</head>", og + "\n</head>");
    }

    private String readIndexHtml() throws Exception {
        ClassPathResource res = new ClassPathResource("static/index.html");
        return StreamUtils.copyToString(res.getInputStream(), StandardCharsets.UTF_8);
    }
}
