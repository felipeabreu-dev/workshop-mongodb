package io.github.abreufelipedev.resources;

import io.github.abreufelipedev.domain.Post;
import io.github.abreufelipedev.resources.util.URL;
import io.github.abreufelipedev.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/posts")
@Tag(name = "Posts-API")
public class PostResource {

    @Autowired
    private PostService postService;

    @Operation(summary = "Find post by id", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Post found successfully"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Post> findById(@PathVariable String id) {
        Post post = postService.findById(id);
        return ResponseEntity.ok(post);
    }

    @Operation(summary = "Find posts by title", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Posts found successfully"),
            @ApiResponse(responseCode = "404", description = "No posts found")
    })
    @GetMapping("/titlesearch")
    public ResponseEntity<List<Post>> findByTitle(@RequestParam(value = "text", defaultValue = "") String text){
        text = URL.decodeParam(text);
        List<Post> posts = postService.findByTitle(text);

        return ResponseEntity.ok(posts);
    }

    @Operation(summary = "Find posts by word in title, body or comments with minimum and maximum date", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Posts found successfully"),
            @ApiResponse(responseCode = "404", description = "No posts found")
    })
    @GetMapping("/fullsearch")
    public ResponseEntity<List<Post>> fullSearch(
            @RequestParam(value = "text", defaultValue = "") String text,
            @RequestParam(value = "minDate", defaultValue = "") String minDate,
            @RequestParam(value = "maxDate", defaultValue = "") String maxDate){

        text = URL.decodeParam(text);
        Date min = URL.convertDate(minDate, new Date(0L));
        Date max = URL.convertDate(maxDate, new Date());

        List<Post> posts = postService.fullSearch(text, min, max);

        return ResponseEntity.ok(posts);
    }
}
