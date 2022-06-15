package site.metacoding.restdoc.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import site.metacoding.restdoc.domain.Post;

@RequestMapping("/api")
@RestController
public class PostApiController {

    @PostMapping("/post")
    public ResponseEntity<?> save(@RequestBody Post post) {
        Post postEntity = Post.builder()
                .id(1)
                .title("스프링부트")
                .content("재밌어요")
                .build();

        return new ResponseEntity<>(postEntity, HttpStatus.CREATED);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        Post postEntity = Post.builder()
                .id(1)
                .title("스프링부트")
                .content("재밌어요")
                .build();

        return new ResponseEntity<>(postEntity, HttpStatus.OK);
    }

    @GetMapping("/post")
    public ResponseEntity<?> findAll() {
        Post postEntity1 = Post.builder()
                .id(1)
                .title("스프링부트")
                .content("재밌어요")
                .build();

        Post postEntity2 = Post.builder()
                .id(2)
                .title("파이썬")
                .content("재미없어요")
                .build();

        List<Post> posts = Arrays.asList(postEntity1, postEntity2);

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}
