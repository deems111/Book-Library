import React, { Component } from "react";
import BookService from "../../services/BookService";
import { Link } from "react-router-dom";

class BookTable extends Component {
    constructor(props) {
        super(props);
        this.getBooks = this.getBooks.bind(this);
        this.refreshList = this.refreshList.bind(this);

        this.state = {
          books: []
        };
      }

    componentDidMount() {
      this.getBooks();
    }

    getBooks() {
      BookService.getAllBooks()
        .then(response => {
          this.setState({
            books: response.data
          });
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    }

    refreshList() {
      this.getBooks();
    }

    render() {
       const { books } = this.state;
       if (books.length === 0) {
       return (
       <div className="content">
          <h1>No Books In Library</h1>
           <Link to="/add">
               <button type="button">
                   Add
               </button>
           </Link>
       </div>
       )
       }

       return (
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
    )}
};

export default BookTable;