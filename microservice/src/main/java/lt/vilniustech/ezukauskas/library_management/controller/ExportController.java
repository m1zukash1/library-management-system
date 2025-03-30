package lt.vilniustech.ezukauskas.library_management.controller;

import lt.vilniustech.ezukauskas.library_management.model.Book;
import lt.vilniustech.ezukauskas.library_management.repository.BookRepository;
import lt.vilniustech.ezukauskas.library_management.service.XmlExportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsible for exporting entities to XML format.
 */
@RestController
@RequestMapping("/export")
public class ExportController {

    private final XmlExportService xmlExportService;
    private final BookRepository bookRepository;

    /**
     * Constructs the ExportController with required services.
     *
     * @param xmlExportService The service responsible for XML exports.
     * @param bookRepository   Repository for accessing books.
     */
    public ExportController(XmlExportService xmlExportService, BookRepository bookRepository) {
        this.xmlExportService = xmlExportService;
        this.bookRepository = bookRepository;
    }

    /**
     * Exports data to an XML file based on the provided entity type.
     *
     * @param entity The type of entity to export (books, authors, or members).
     * @param title  (Optional) Used when exporting books to filter by title.
     * @return ResponseEntity containing the absolute file path of the exported XML,
     * or an error message if the entity is unknown or an exception occurs.
     */
    @GetMapping
    public ResponseEntity<String> exportData(
            @RequestParam("entity") String entity,
            @RequestParam(name = "title", required = false) String title) {
        try {
            String xmlPath;
            if (entity.equalsIgnoreCase("books")) {
                List<Book> books = (title != null && !title.isEmpty())
                        ? bookRepository.findByTitleContainingIgnoreCase(title)
                        : bookRepository.findAll();
                xmlPath = xmlExportService.exportBooksToXml(books);
            } else if (entity.equalsIgnoreCase("authors")) {
                xmlPath = xmlExportService.exportAuthorsToXml();
            } else if (entity.equalsIgnoreCase("members")) {
                xmlPath = xmlExportService.exportMembersToXml();
            } else {
                return ResponseEntity.badRequest().body("Unknown entity: " + entity);
            }
            return ResponseEntity.ok(xmlPath);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error exporting XML: " + e.getMessage());
        }
    }
}
