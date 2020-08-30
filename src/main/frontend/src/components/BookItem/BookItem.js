import React, { Component } from "react";
import { Redirect } from "react-router-dom";
import BookService from "../../services/BookService";
import './BookItem.css'

class BookItem extends Component {

 constructor(props) {
    super(props);
    this.onChangeTitle = this.onChangeTitle.bind(this);
    this.onChangeGenre = this.onChangeGenre.bind(this);
    this.onChangeAuthors = this.onChangeAuthors.bind(this);
    this.getBook = this.getBook.bind(this);
    this.deleteBook = this.deleteBook.bind(this);
    this.updateBook = this.updateBook.bind(this);
    this.getComments = this.getComments.bind(this);

    this.state = {
      book: {
        id: null,
        title: "",
        genre: "",
        authors: ""
      },
      comments: [],
      redirect: false
    };
 }

 componentDidMount() {
   this.getBook(this.props.match.params.id);
   this.getComments(this.props.match.params.id);
 }

 onChangeTitle(e) {
   const title = e.target.value;

   this.setState(prevState => ({
       book: {
         ...prevState.book,
         title: title
       }
   }));
 }

 onChangeGenre(e) {
   const genre = e.target.value;
   this.setState(prevState => ({
     book: {
       ...prevState.book,
       genre: genre
     }
   }));
 }

 onChangeAuthors(e) {
   const authors = e.target.value;
   this.setState(prevState => ({
     book: {
       ...prevState.book,
       authors: authors
     }
   }));
 }

  getBook(id) {
    BookService.getBook(id)
      .then(response => {
        this.setState({
          book: response.data
        });
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  }

  getComments(id) {
    BookService.getComments(id)
      .then(response => {
        this.setState({
          comments: response.data
        });
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  }

  deleteBook() {
    BookService.deleteBook(this.state.book.id)
      .then(response => {
        this.setState({ redirect: true })
      })
      .catch(e => {
        console.log(e);
      });
  }

  updateBook() {
    BookService.updateBook(this.state.book.id, this.state.book)
      .then(response => {
        console.log(response.data);
            this.setState({
                redirect: true
            });
      })
      .catch(e => {
        console.log(e);
      });
  }

 render() {
    const { book, redirect, comments } = this.state;
    const existComments = this.state.comments.length;

     if (redirect) {
           return <Redirect to='/'/>;
     }

    return (
      <div>
          <div className="content">
            <h2>Book</h2>
            <form>
              <div>
                <label htmlFor="title">Title</label>
                <input
                  type="text"
                  id="title"
                  value={book.title}
                  onChange={this.onChangeTitle}
                />
              </div>
              <div>
                <label htmlFor="genre">Genre</label>
                <input
                  type="text"
                  id="Genre"
                  value={book.genre}
                  onChange={this.onChangeGenre}
                />
              </div>
              <div>
                <label htmlFor="authors">Authors</label>
                <input
                  type="text"
                  id="Authors"
                  value={book.authors}
                  onChange={this.onChangeAuthors}
                />
              </div>

            </form>

            <button
              className="button" type="delete"
              onClick={this.deleteBook} >
              Delete
            </button>
                        <button
                          className="button"
                          onClick={this.updateBook}
                        >
                          Update
                        </button>
      </div>
      <div class="content">
            <h2>Comments</h2>
                {!existComments ? (
                <h1>No Comments To Book</h1>
                ) :
                (

                    <table>
                         <thead>
                             <tr>
                                 <th>Authors</th>
                                 <th>Subject</th>
                             </tr>
                         </thead>
                          <tbody>
                              {comments &&
                              comments.map((comment, index) => (
                                  <tr key={comment.id}>
                                    <th>{comment.name}</th>
                                    <th>{comment.subject}</th>
                                  </tr>
                              ))}
                          </tbody>
                    </table>
                )}
      </div>
    </div>
    );
  }
}

export default BookItem;
