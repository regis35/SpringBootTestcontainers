package bzh.redge.springboottestcontainers.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
@Slf4j
@RequiredArgsConstructor
public class PostDataLoader implements CommandLineRunner {
    private final PostRepository postRepository;
    private final ObjectMapper objectMapper;

    @Value("classpath:data/posts.json")
    private Resource resourceFile;

    @Override
    public void run(String... args) throws Exception {
        if(postRepository.count() == 0) {
            log.info("Loading posts from to database");
            try(InputStream in = resourceFile.getInputStream()) {
                Posts response = objectMapper.readValue(in, Posts.class);
                postRepository.saveAll(response.posts());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read json " + e.getMessage(),e);
            }
            log.info("{} posts loaded", postRepository.count());
        }
    }
}
