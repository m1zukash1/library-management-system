package lt.vilniustech.ezukauskas.library_management.repository;

import lt.vilniustech.ezukauskas.library_management.model.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testFindAllMembers() {
        List<Member> members = memberRepository.findAll();
        assertThat(members).isNotEmpty();
    }
}
