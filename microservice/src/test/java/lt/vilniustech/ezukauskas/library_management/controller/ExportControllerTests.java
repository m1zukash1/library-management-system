package lt.vilniustech.ezukauskas.library_management.controller;

import lt.vilniustech.ezukauskas.library_management.service.XmlExportService;
import lt.vilniustech.ezukauskas.library_management.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExportController.class)
public class ExportControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private XmlExportService xmlExportService;

    @MockitoBean
    private BookRepository bookRepository;

    @Test
    public void testExportBooksEndpoint() throws Exception {
        when(xmlExportService.exportBooksToXml(anyList()))
                .thenReturn("dummy/path/books_export.xml");

        mockMvc.perform(get("/export")
                        .param("entity", "books")
                        .param("title", "1984"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("dummy/path/books_export.xml")));
    }
}
