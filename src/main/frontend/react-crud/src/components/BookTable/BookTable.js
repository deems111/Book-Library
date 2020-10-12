import React, { useState, useEffect } from "react";
import BookService from "../../services/BookService";
import { Link } from "react-router-dom";

const BookTable = () => {
    const [books, setBooks] = useState([]);

  useEffect(() => {
    getAllBooks();
  }, []);

    const getAllBooks = () => {
        BookService.getAllBooks()
          .then(response => {
            setBooks(response.data);
            console.log(response.data);
          })
          .catch(e => {
            console.log(e);
          });
    }

    const refreshList = () => {
        getAllBooks();
    }

       return  books.length === 0 ? (
          <div className="content">
             <h1>No Books In Library</h1>
              <Link to="/add">
                  <button type="button">
                      Add
                  </button>
              </Link>
          </div>
          ) : (
                <div class="content">
                <h1>Book Library</h1>
                <h3>List of Books</h3>
                <table>
                    <thead>
                        <tr>
                            <th>Book ID</th>
                            <th>Title</th>
                            <th>Genre</th>
                            <th>Authors</th>
                            <th>View</th>
                        </tr>
                    </thead>
                    <tbody>
                        {books &&
                          books.map((book, index) => (
                              <tr key={book.id}>
                                <th>{book.id}</th>
                                <th>{book.title}</th>
                                <th>{book.genre}</th>
                                <th>{book.authors}</th>
                                <th>
                                    <Link to={"/view/" + book.id}>
                                                            <button type="button">
                                                                 View
                                                            </button>
                                    </Link>
                                </th>
                              </tr>
                        ))}
                    </tbody>
                </table>
                <div>
                    <Link to="/add">
                        <button className="button">
                            Add Book
                        </button>
                    </Link>

                </div>
                </div>
    )};

export default BookTable;