package com.shayarify.controller;

import com.shayarify.model.Hashtag;
import com.shayarify.repository.HashtagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller for managing hashtags.
 */
@RestController
@RequestMapping("/api/hashtags")
public class HashtagController {

    @Autowired
    private HashtagRepository hashtagRepository;

    /**
     * Get all hashtags.
     */
    @GetMapping
    public ResponseEntity<List<Hashtag>> getAllHashtags() {
        List<Hashtag> hashtags = hashtagRepository.findAll();
        return ResponseEntity.ok(hashtags);
    }

    /**
     * Get hashtag by tag (e.g., "#poetry").
     */
    @GetMapping("/{tag}")
    public ResponseEntity<Hashtag> getHashtag(@PathVariable String tag) {
        Optional<Hashtag> hashtag = hashtagRepository.findByTag(tag.toLowerCase());
        return hashtag.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Create a new hashtag.
     */
    @PostMapping
    public ResponseEntity<Hashtag> createHashtag(@RequestBody Hashtag hashtag) {
        // Ensure tag is always lowercase and unique
        hashtag.setTag(hashtag.getTag().toLowerCase());
        Optional<Hashtag> existing = hashtagRepository.findByTag(hashtag.getTag());
        if (existing.isPresent()) {
            return ResponseEntity.badRequest().body(existing.get());
        }
        Hashtag saved = hashtagRepository.save(hashtag);
        return ResponseEntity.ok(saved);
    }
}