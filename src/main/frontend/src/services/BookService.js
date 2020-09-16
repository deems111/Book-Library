import proxy from "../proxy/proxy-settings";

class BookService {
  getAllBooks() {
    return proxy.get("/books");
  }

  getBook(id) {
    return proxy.get(`/book/${id}`);
  }

  createBook(newBook) {
    return proxy.post("/books", newBook);
  }

  updateBook(id, book) {
    return proxy.put("/book/${id}", book);
  }

  deleteBook(id) {
    return proxy.delete(`/book/${id}`);
  }

  getComments(id) {
    return proxy.get(`/comments/${id}`);
  }

}

export default new BookService();