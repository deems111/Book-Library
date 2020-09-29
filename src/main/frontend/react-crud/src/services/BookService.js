import proxy from "../proxy/proxy-settings";

class BookService {
  getAllBooks() {
    return proxy.get("/books");
  }

  getBook(id) {
    return proxy.get(`/books/${id}`);
  }

  createBook(newBook) {
    return proxy.post("/books", newBook);
  }

  updateBook(id, book) {
    return proxy.put("/books/${id}", book);
  }

  deleteBook(id) {
    return proxy.delete(`/books/${id}`);
  }

  getComments(id) {
    return proxy.get(`/comments/${id}`);
  }

}

export default new BookService();