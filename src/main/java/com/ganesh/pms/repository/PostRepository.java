package com.ganesh.pms.repository;

import com.ganesh.pms.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT P FROM Post P WHERE P.user.email=:email")
    List<Post> findPostsByUserEmail(String email);
}
