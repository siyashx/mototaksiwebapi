package com.codesupreme.mototaksiwebapi.service.impl.codesupreme.tag;

import com.codesupreme.mototaksiwebapi.dao.codesupreme.tag.TagRepository;
import com.codesupreme.mototaksiwebapi.dto.codesupreme.tag.TagDto;
import com.codesupreme.mototaksiwebapi.model.codesupreme.tag.Tag;
import com.codesupreme.mototaksiwebapi.service.inter.codesupreme.tag.TagServiceInter;
import com.codesupreme.mototaksiwebapi.util.SlugUtil;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagServiceInter {

    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    public TagServiceImpl(TagRepository tagRepository, ModelMapper modelMapper) {
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TagDto> getAllTags() {
        List<Tag> tags = tagRepository.findAll();
        List<TagDto> tagDtos = new ArrayList<>();

        for (Tag tag : tags) {
            tagDtos.add(modelMapper.map(tag, TagDto.class));
        }

        return tagDtos;
    }

    @Override
    public TagDto getTagById(Long id) {
        Optional<Tag> tagOpt = tagRepository.findById(id);
        return tagOpt.map(tag -> modelMapper.map(tag, TagDto.class)).orElse(null);
    }

    @Override
    public ResponseEntity<TagDto> createTag(TagDto tagDto) {
        Tag tag = modelMapper.map(tagDto, Tag.class);

        if (tagDto.getSlug() == null || tagDto.getSlug().trim().isEmpty()) {
            tag.setSlug(SlugUtil.toSlug(tagDto.getLabel()));
        } else {
            tag.setSlug(SlugUtil.toSlug(tagDto.getSlug()));
        }

        Tag savedTag = tagRepository.save(tag);
        return ResponseEntity.ok(modelMapper.map(savedTag, TagDto.class));
    }

    @Override
    public TagDto updateTag(Long id, TagDto tagDto) {
        Optional<Tag> tagOpt = tagRepository.findById(id);

        if (tagOpt.isPresent()) {
            Tag tag = tagOpt.get();

            if (tagDto.getLabel() != null) {
                tag.setLabel(tagDto.getLabel());
                tag.setSlug(SlugUtil.toSlug(tagDto.getLabel()));
            }

            if (tagDto.getSlug() != null && !tagDto.getSlug().trim().isEmpty()) {
                tag.setSlug(SlugUtil.toSlug(tagDto.getSlug()));
            }

            Tag updatedTag = tagRepository.save(tag);
            return modelMapper.map(updatedTag, TagDto.class);
        }

        return null;
    }

    @Override
    public List<TagDto> searchTagsByLabel(String keyword) {
        List<Tag> tags = tagRepository.findByLabelContainingIgnoreCase(keyword);
        List<TagDto> tagDtos = new ArrayList<>();

        for (Tag tag : tags) {
            tagDtos.add(modelMapper.map(tag, TagDto.class));
        }

        return tagDtos;
    }

    public Tag getTagBySlug(String slug) {
        return tagRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Tag tapılmadı!"));
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}