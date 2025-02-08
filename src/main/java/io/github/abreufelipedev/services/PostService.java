package io.github.abreufelipedev.services;

import io.github.abreufelipedev.domain.Post;
import io.github.abreufelipedev.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post findById(String id) {
        return postRepository.findById(id).orElse(null);
    }
}
