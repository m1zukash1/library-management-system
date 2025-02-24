package lt.vilniustech.ezukauskas.library_management.service;

import lt.vilniustech.ezukauskas.library_management.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.File;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class XmlExportServiceTests {

    @Autowired
    private XmlExportService xmlExportService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testExportBooksToXml() throws Exception {
        assertThat(bookRepository.findAll()).isNotEmpty();
        String xmlPath = xmlExportService.exportBooksToXml();
        File file = new File(xmlPath);
        assertThat(file.exists()).isTrue();
        assertThat(file.length()).isGreaterThan(0);
    }

    @Test
    public void testExportAuthorsToXml() throws Exception {
        assertThat(authorRepository.findAll()).isNotEmpty();
        String xmlPath = xmlExportService.exportAuthorsToXml();
        File file = new File(xmlPath);
        assertThat(file.exists()).isTrue();
        assertThat(file.length()).isGreaterThan(0);
    }

    @Test
    public void testExportMembersToXml() throws Exception {
        assertThat(memberRepository.findAll()).isNotEmpty();

        String xmlPath = xmlExportService.exportMembersToXml();
        File file = new File(xmlPath);

        assertThat(file.exists()).isTrue();
        assertThat(file.length()).isGreaterThan(0);
    }
}
