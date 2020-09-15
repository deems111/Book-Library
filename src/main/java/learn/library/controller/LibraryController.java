package learn.library.controller;

import learn.library.entity.Book;
import learn.library.entity.Comment;
import learn.library.entity.Genre;
import learn.library.entity.dto.BookDto;
import learn.library.service.LibraryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class LibraryController {

    private final LibraryImpl libraryService;

    @Autowired
    public LibraryController(LibraryImpl libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping({"/", "/index"})
    public String getBooks(Model model) {
        model.addAttribute("books", libraryService.getBooks().stream().map(BookDto::new).collect(Collectors.toList()));
        return "index";
    }

    @GetMapping("/add")
    public String addNewBookToModel(Model model) {
        model.addAttribute("book", new BookDto(new Book(null, new Genre(), new HashSet<>())));
        return "add";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute BookDto bookDto) {
        libraryService.addBook(bookDto);
        return "redirect:/";
    }

    @PostMapping("/delete/{book}")
    public String deleteBook(@PathVariable Book book) {
        libraryService.deleteBookById(book.getId());
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updateBook(@ModelAttribute BookDto bookDto) {
        libraryService.updateBook(bookDto);
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
        model.addAttribute("book", book == null ? new BookDto() : new BookDto(book));
        return "view";
    }

}
