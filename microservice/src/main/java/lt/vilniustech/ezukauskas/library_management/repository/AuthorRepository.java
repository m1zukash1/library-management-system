package lt.vilniustech.ezukauskas.library_management.repository;

import lt.vilniustech.ezukauskas.library_management.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
