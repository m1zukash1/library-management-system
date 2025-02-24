package lt.vilniustech.ezukauskas.library_management.repository;

import lt.vilniustech.ezukauskas.library_management.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
}
