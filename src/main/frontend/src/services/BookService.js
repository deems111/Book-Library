import proxy from "../proxy/proxy-settings";

class BookService {
  getAllBooks() {
    return proxy.get("/books");
  }

  getBook(id) {
    return proxy.get(`/view/${id}`);
  }

  createBook(newBook) {
    return proxy.post("/create", newBook);
  }

  updateBook(id, book) {
    return proxy.put("/update/${id}", book);
  }

  deleteBook(id) {
    return proxy.delete(`/delete/${id}`);
  }

  getComments(id) {
    return proxy.get(`/comments/${id}`);
  }

}

export default new BookService();