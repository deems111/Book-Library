package learn.library.controller;

import learn.library.entity.Book;
import learn.library.entity.Comment;
import learn.library.entity.Genre;
import learn.library.entity.dto.BookDto;
import learn.library.service.LibraryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Controller
public class LibraryController {

    private final LibraryImpl libraryService;

    @Autowired
    public LibraryController(LibraryImpl libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping({"/", "/index"})
    public String getBooks(Model model) {
        List<Book> books = libraryService.getBooks();
        List<BookDto> bookDtos = new ArrayList<>();
        for (Book book : books) {
            bookDtos.add(new BookDto(book));
        }
        model.addAttribute("books", bookDtos);
        return "index";
    }

    @GetMapping("/add")
    public String addBook(Model model) {
        model.addAttribute("book", new BookDto(new Book(null, new Genre(), new HashSet<>())));
        return "add";
    }

    @PostMapping("/add")
    public String addBook(@RequestParam(name = "genre") String genre,
                          @RequestParam(name = "title") String title,
                          @RequestParam(name = "authors") String authors,
                          Model model) {
        libraryService.addBook(authors,title, genre);
        return "redirect:/";
    }

    @GetMapping("/update")
    public String updateBook(@RequestParam(name = "genre") String genre,
                             @RequestParam(name = "title") String title,
                             @RequestParam(name = "authors") String authors,
                             @RequestParam(name = "id") Long id,
                             Model model) {
        Book book = libraryService.updateBook(id, authors, title, genre);
        model.addAttribute("book", new BookDto(book));
        return "edit";
    }

    @GetMapping("/delete")
    public String deleteBook(@RequestParam(name = "id") Long id) {
        if (libraryService.getBooksById(id) != null) {
            libraryService.deleteBookById(id);
        }
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String showBookForEdit(@RequestParam(name = "id") Long id, Model model) {
        Book book = libraryService.getBooksById(id);
        model.addAttribute("book", new BookDto(book));
        return "edit";
    }

    @GetMapping("/view")
    public String showBookComments(@RequestParam(name = "id") Long id, Model model) {
        List<Comment> comments = libraryService.getCommentsByBookId(id);
        Book book = libraryService.getBooksById(id);
        if (comments == null || comments.isEmpty()) {
            comments = new ArrayList<>();
            comments.add(new Comment("Нет комментариев к книге", null, book));
        }
        model.addAttribute("comments", comments);
        model.addAttribute("book", new BookDto(book));
        return "view";
    }

}
