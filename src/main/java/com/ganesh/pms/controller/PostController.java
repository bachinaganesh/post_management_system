package com.ganesh.pms.controller;

import com.ganesh.pms.dtos.requests.PostRequestDTO;
import com.ganesh.pms.dtos.responses.PostResponseDTO;
import com.ganesh.pms.service.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final IPostService postService;

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<PostResponseDTO> createPost(@RequestBody PostRequestDTO postRequestDTO) {
        PostResponseDTO postResponseDTO = postService.createPost(postRequestDTO);
        return ResponseEntity.ok(postResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> getAllPosts() {
        List<PostResponseDTO> postResponseDTOS = postService.getAllPosts();
        return new ResponseEntity<>(postResponseDTOS, HttpStatus.OK);
    }
}
