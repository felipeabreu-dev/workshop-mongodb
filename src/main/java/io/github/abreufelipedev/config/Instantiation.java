package io.github.abreufelipedev.config;

import io.github.abreufelipedev.domain.Post;
import io.github.abreufelipedev.domain.User;
import io.github.abreufelipedev.dto.AuthorDTO;
import io.github.abreufelipedev.dto.CommentDTO;
import io.github.abreufelipedev.repositories.PostRepository;
import io.github.abreufelipedev.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.TimeZone;

@Configuration
public class Instantiation implements CommandLineRunner {



    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-3:00"));

        userRepository.deleteAll();
        postRepository.deleteAll();

        User maria = new User(null, "Maria Brown", "maria@gmail.com");
        User alex = new User(null, "Alex Green", "alex@gmail.com");
        User bob = new User(null, "Bob Grey", "bob@gmail.com");

        userRepository.saveAll(Arrays.asList(maria, alex, bob));

        Post post1 = new Post(null, sdf.parse("21/03/2018"), "Partiu viagem", "Vou viajar para São Paulo. Abraços!", new AuthorDTO(maria.getId(), maria.getName()));
        Post post2 = new Post(null, sdf.parse("23/03/2018"), "Bom dia", "Acordei feliz hoje!", new AuthorDTO(maria.getId(), maria.getName()));

        CommentDTO comment1 = new CommentDTO("Boa viagem mano!", sdf.parse("2018/03/21"), new AuthorDTO(alex.getId(), alex.getName()));
        CommentDTO comment2 = new CommentDTO("Aproveite!", sdf.parse("2018/03/22"), new AuthorDTO(bob.getId(), bob.getName()));
        CommentDTO comment3 = new CommentDTO(" Tenha um ótimo dia!", sdf.parse( "2018/03/23"), new AuthorDTO(alex.getId(), alex.getName()));

        post1.getComments().addAll(Arrays.asList(comment1, comment2));
        post2.getComments().add(comment3);

        postRepository.saveAll(Arrays.asList(post1, post2));

        maria.getPosts().addAll(Arrays.asList(post1, post2));
        userRepository.save(maria);
    }
}
