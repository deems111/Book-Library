import proxy from "../proxy/proxy-settings";
import authHeader from "./AuthHeader";

class BookService {
  getAllBooks() {
    return proxy.get("/books", { headers: authHeader() });
  }

  getBook(id) {
    return proxy.get(`/books/${id}`, { headers: authHeader() });
  }

  createBook(newBook) {
    return proxy.post("/books", newBook, { headers: authHeader() });
  }

  updateBook(id, book) {
    return proxy.put("/books/${id}", book, { headers: authHeader() });
  }

  deleteBook(id) {
    return proxy.delete(`/books/${id}`, { headers: authHeader() });
  }

  getComments(id) {
    return proxy.get(`/comments/${id}`, { headers: authHeader() });
  }

}

export default new BookService();