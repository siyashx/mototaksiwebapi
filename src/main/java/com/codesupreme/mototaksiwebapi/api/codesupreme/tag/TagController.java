package com.codesupreme.mototaksiwebapi.api.codesupreme.tag;

import com.codesupreme.mototaksiwebapi.dto.codesupreme.tag.TagDto;
import com.codesupreme.mototaksiwebapi.model.codesupreme.tag.Tag;
import com.codesupreme.mototaksiwebapi.service.impl.codesupreme.tag.TagServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v6/tag")
public class TagController {

    private final TagServiceImpl tagService;

    public TagController(TagServiceImpl tagService) {
        this.tagService = tagService;
    }

    // List all tags
    @GetMapping
    public List<TagDto> getAllTags() {
        return tagService.getAllTags();
    }

    // Get tag by ID
    @GetMapping("/{tagId}")
    public ResponseEntity<?> getTagById(@PathVariable("tagId") Long tagId) {
        TagDto tagDto = tagService.getTagById(tagId);
        if (tagDto != null) {
            return ResponseEntity.ok(tagDto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Tag doesn't exist with given ID.");
    }

    // Create tag
    @PostMapping
    public ResponseEntity<TagDto> createTag(@RequestBody TagDto tagDto) {
        return tagService.createTag(tagDto);
    }

    // Update tag
    @PutMapping("/{tagId}")
    public ResponseEntity<?> updateTag(
            @PathVariable("tagId") Long tagId,
            @RequestBody TagDto tagDto
    ) {
        TagDto updatedTag = tagService.updateTag(tagId, tagDto);
        if (updatedTag != null) {
            return ResponseEntity.ok(updatedTag);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Tag not found to update.");
    }

    // Search by label keyword
    @GetMapping("/search")
    public ResponseEntity<List<TagDto>> searchTags(@RequestParam("keyword") String keyword) {
        List<TagDto> result = tagService.searchTagsByLabel(keyword);
        return ResponseEntity.ok(result);
    }

    // Get tag by slug
    @GetMapping("/slug/{slug}")
    public ResponseEntity<?> getTagBySlug(@PathVariable String slug) {
        Tag tag = tagService.getTagBySlug(slug);
        if (tag != null) {
            return ResponseEntity.ok(tag);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Tag not found with given slug.");
    }

    // Delete tag
    @DeleteMapping("/{tagId}")
    public ResponseEntity<?> deleteTag(@PathVariable("tagId") Long tagId) {
        tagService.deleteTag(tagId);
        return ResponseEntity.ok().build();
    }
}