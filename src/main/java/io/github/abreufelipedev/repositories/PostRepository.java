package io.github.abreufelipedev.repositories;

import io.github.abreufelipedev.domain.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {
}
