package bzh.redge.springboottestcontainers.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class PostControllerTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgre = new PostgreSQLContainer<>(new DockerImageName("postgres:16.0"));

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldFindAllPosts() {

        Post[] posts = restTemplate.getForObject("/posts", Post[].class);
        assertNotNull(posts);
        assertThat(posts.length, is(3));
    }

    @Test
    void shouldFindPostById() {

        ResponseEntity<Post> response = restTemplate.exchange("/posts/1", HttpMethod.GET,null,Post.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));

    }
}