package lt.vilniustech.ezukauskas.library_management.service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import lt.vilniustech.ezukauskas.library_management.model.BooksWrapper;
import lt.vilniustech.ezukauskas.library_management.model.AuthorsWrapper;
import lt.vilniustech.ezukauskas.library_management.model.MembersWrapper;
import lt.vilniustech.ezukauskas.library_management.model.Book;
import lt.vilniustech.ezukauskas.library_management.model.Author;
import lt.vilniustech.ezukauskas.library_management.model.Member;
import lt.vilniustech.ezukauskas.library_management.repository.BookRepository;
import lt.vilniustech.ezukauskas.library_management.repository.AuthorRepository;
import lt.vilniustech.ezukauskas.library_management.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * Service responsible for exporting entities to XML format.
 */
@Service
public class XmlExportService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final MemberRepository memberRepository;

    /**
     * Constructs the XmlExportService with required repositories.
     *
     * @param bookRepository   Repository for accessing books.
     * @param authorRepository Repository for accessing authors.
     * @param memberRepository Repository for accessing members.
     */
    public XmlExportService(BookRepository bookRepository,
                            AuthorRepository authorRepository,
                            MemberRepository memberRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * Exports a given list of books to an XML file.
     *
     * @param books List of books to be exported.
     * @return The absolute path of the generated XML file.
     * @throws Exception If an error occurs during XML generation.
     */
    public String exportBooksToXml(List<Book> books) throws Exception {
        BooksWrapper wrapper = new BooksWrapper();
        wrapper.setBooks(books);
        JAXBContext context = JAXBContext.newInstance(BooksWrapper.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        File xmlFile = new File("books_export.xml");
        marshaller.marshal(wrapper, xmlFile);
        return xmlFile.getAbsolutePath();
    }

    /**
     * Exports all books stored in the database to an XML file.
     *
     * @return The absolute path of the generated XML file.
     * @throws Exception If an error occurs during XML generation.
     */
    public String exportBooksToXml() throws Exception {
        return exportBooksToXml(bookRepository.findAll());
    }

    /**
     * Exports all authors stored in the database to an XML file.
     *
     * @return The absolute path of the generated XML file.
     * @throws Exception If an error occurs during XML generation.
     */
    public String exportAuthorsToXml() throws Exception {
        List<Author> authors = authorRepository.findAll();
        AuthorsWrapper wrapper = new AuthorsWrapper();
        wrapper.setAuthors(authors);
        JAXBContext context = JAXBContext.newInstance(AuthorsWrapper.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        File xmlFile = new File("authors_export.xml");
        marshaller.marshal(wrapper, xmlFile);
        return xmlFile.getAbsolutePath();
    }

    /**
     * Exports all members stored in the database to an XML file.
     *
     * @return The absolute path of the generated XML file.
     * @throws Exception If an error occurs during XML generation.
     */
    public String exportMembersToXml() throws Exception {
        List<Member> members = memberRepository.findAll();
        MembersWrapper wrapper = new MembersWrapper();
        wrapper.setMembers(members);
        JAXBContext context = JAXBContext.newInstance(MembersWrapper.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        File xmlFile = new File("members_export.xml");
        marshaller.marshal(wrapper, xmlFile);
        return xmlFile.getAbsolutePath();
    }
}
