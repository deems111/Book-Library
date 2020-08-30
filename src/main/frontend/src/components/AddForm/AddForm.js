import React, { Component }  from "react";
import {  Redirect } from "react-router-dom";
import BookService from "../../services/BookService";

class AddForm extends Component {
 constructor(props) {
    super(props);
    this.onChangeTitle = this.onChangeTitle.bind(this);
    this.onChangeGenre = this.onChangeGenre.bind(this);
    this.onChangeAuthors = this.onChangeAuthors.bind(this);
    this.saveBook = this.saveBook.bind(this);

    this.state = {
        id: null,
        title: "Enter Title",
        genre: "Enter Genre",
        authors: "Enter Author(s)",
        redirect: false
    };
  }

 onChangeTitle(e) {
    this.setState({
     title: e.target.value
    });
 }

 onChangeGenre(e) {
   this.setState({
     genre: e.target.value
   });
 }

  onChangeAuthors(e) {
    this.setState({
        authors: e.target.value
    });
  }

  saveBook() {
    var newBook = {
        title: this.state.title,
        genre: this.state.genre,
        authors: this.state.authors
    };

    BookService.createBook(newBook)
        .then(response => {
            this.setState({
              id: response.newBook.id,
              title: response.newBook.title,
              genre: response.newBook.genre,
              authors: response.newBook.authors,
              redirect: true
            });
        console.log(response.newBook);
        })
        .catch(e => {
            console.log(e);
        });
  }

 render() {
    const { redirect } = this.state;
    if (redirect) {
            return <Redirect to='/books'/>;
    }

    return (
       <div>
           <div>
             <h4>Add Book</h4>
             <form>
               <div>
                 <label htmlFor="title">Title</label>
                 <input
                   type="text"
                   id="title"
                   value={this.state.title}
                   onChange={this.onChangeTitle}
                 />
               </div>
               <div>
                 <label htmlFor="genre">Genre</label>
                 <input
                   type="text"
                   id="genre"
                   value={this.state.genre}
                   onChange={this.onChangeGenre}
                 />
               </div>

               <div>
                                <label htmlFor="authors">Authors</label>
                                <input
                                  type="text"
                                  id="authors"
                                  value={this.state.authors}
                                  onChange={this.onChangeAuthors}
                                />
                              </div>

             </form>


             <button
               className="button"
               onClick={this.saveBook}
             >
               Add Book
             </button>
           </div>
       </div>

    );
 }
}

export default AddForm;