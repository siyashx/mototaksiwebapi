package com.codesupreme.mototaksiwebapi.service.inter.codesupreme.tag;

import com.codesupreme.mototaksiwebapi.dto.codesupreme.tag.TagDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TagServiceInter {

    TagDto getTagById(Long id);

    List<TagDto> getAllTags();

    ResponseEntity<TagDto> createTag(TagDto tagDto);

    List<TagDto> searchTagsByLabel(String keyword);

    TagDto updateTag(Long id, TagDto tagDto);

    void deleteTag(Long id);
}