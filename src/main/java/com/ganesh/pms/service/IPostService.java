package com.ganesh.pms.service;

import com.ganesh.pms.dtos.requests.PostRequestDTO;
import com.ganesh.pms.dtos.responses.PostResponseDTO;

import java.util.List;

public interface IPostService {

    PostResponseDTO createPost(PostRequestDTO postRequestDTO);
    List<PostResponseDTO> getAllPosts();
}
