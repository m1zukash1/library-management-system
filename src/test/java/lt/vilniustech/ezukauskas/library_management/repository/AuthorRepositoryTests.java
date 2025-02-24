package lt.vilniustech.ezukauskas.library_management.repository;

import lt.vilniustech.ezukauskas.library_management.model.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AuthorRepositoryTests {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void testFindAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        assertThat(authors).isNotEmpty();
    }
}
