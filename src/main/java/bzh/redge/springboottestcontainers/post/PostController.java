package bzh.redge.springboottestcontainers.post;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;

    @GetMapping("")
    List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    Optional<Post> getPostsByTitle(@PathVariable Integer id) {
        return Optional.ofNullable(postRepository.findById(id).orElseThrow(NotFoundPostException::new));
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    Post save(@RequestBody @Valid Post post) {
        return postRepository.save(post);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Integer id) {
        postRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    Post update(@PathVariable Integer id, @RequestBody @Valid Post post) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post updatedPost = new Post(optionalPost.get().id(),
                    optionalPost.get().userId(),
                    post.title(),
                    post.content(),
                    optionalPost.get().version());
            return postRepository.save(updatedPost);
        } else {
            throw new NotFoundPostException();
        }
    }

}
