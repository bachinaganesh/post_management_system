package com.ganesh.pms.service.impl;

import com.ganesh.pms.dtos.requests.PostRequestDTO;
import com.ganesh.pms.dtos.responses.PostResponseDTO;
import com.ganesh.pms.models.Post;
import com.ganesh.pms.models.User;
import com.ganesh.pms.repository.PostRepository;
import com.ganesh.pms.service.IPostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements IPostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public PostResponseDTO createPost(PostRequestDTO postRequestDTO) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = this.modelMapper.map(postRequestDTO, Post.class);
        post.setUser(user);
        Post createdPost = this.postRepository.save(post);
        return this.modelMapper.map(createdPost, PostResponseDTO.class);
    }

    @Override
    public List<PostResponseDTO> getAllPosts() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        assert user != null;
        return this.postRepository.findPostsByUserEmail(user.getEmail()).stream()
                .map(post -> this.modelMapper.map(post, PostResponseDTO.class))
                .collect(Collectors.toList());
    }
}
