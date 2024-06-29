package bzh.redge.springboottestcontainers.post;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

public interface PostRepository extends ListCrudRepository<Post, Integer> {

    Post findByTitle(String title);
}
