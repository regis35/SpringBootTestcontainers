package bzh.redge.springboottestcontainers.post;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

@Testcontainers
@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgre = new PostgreSQLContainer<>(new DockerImageName("postgres:16.0"));

    @Autowired
    PostRepository postRepository;

    @Test
    void connectionEstablished() {
        assertThat(postgre.isCreated(), is(true));
        assertThat(postgre.isRunning(), is(true));
    }

    @BeforeEach
    public void setUp() throws Exception {
        List<Post> posts = List.of(new Post(1,1,"Bonjour", "Super",null));
        postRepository.saveAll(posts);

    }

    @Test
    public void should_get(){

        Post result = postRepository.findByTitle("Bonjour");
        assertThat(result, is(notNullValue()));

    }

//    @DynamicPropertySource
//    static void properties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
//    }
}