package io.github.abreufelipedev.services;

import io.github.abreufelipedev.domain.Post;
import io.github.abreufelipedev.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post findById(String id) {
        return postRepository.findById(id).orElse(null);
    }

    public List<Post> findByTitle(String title){
        return postRepository.findByTitle(title);
    }
}
