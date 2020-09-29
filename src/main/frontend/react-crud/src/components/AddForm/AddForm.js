import React, { useState }  from "react";
import {  Redirect } from "react-router-dom";
import BookService from "../../services/BookService";

const AddForm = () => {
  const init = {
    id: null,
    title: "Enter Title",
    genre: "Enter Genre",
    authors: "Enter Author(s)"
  };

  const [shouldRedirect, setShouldRedirect] = useState(false);
  const [book, setBook] = useState(init);

  const newBook = () => {
    setBook(init);
    setShouldRedirect(false);
    };

  const handleInputChange = event => {
    const { name, value } = event.target;
    setBook({ ...book, [name]: value });
  };

  const saveBook = () => {
     var newBook = {
       title: book.title,
       genre: book.genre,
       authors: book.authors
     };

    BookService.createBook(newBook)
        .then(response => {
            setBook({
              id: response.newBook.id,
              title: response.newBook.title,
              genre: response.newBook.genre,
              authors: response.newBook.authors
            });
        console.log(response.newBook);
        setShouldRedirect(true);
        })
        .catch(e => {
            console.log(e);
            setShouldRedirect(false);
        });
  };

  return  (
  <>
       {shouldRedirect ? <Redirect to={`/`} noThrow /> : null}
       <div>
           <div>
             <h4>Add Book</h4>
             <form>
               <div>
                 <label htmlFor="title">Title</label>
                 <input
                   type="text"
                   id="title"
                   value={book.title}
                   onChange={handleInputChange}
                   name="title"
                 />
               </div>
               <div>
                 <label htmlFor="genre">Genre</label>
                 <input
                   type="text"
                   id="genre"
                   value={book.genre}
                   onChange={handleInputChange}
                   name="genre"
                 />
               </div>
               <div>
                  <label htmlFor="authors">Authors</label>
                  <input
                    type="text"
                    id="authors"
                    value={book.authors}
                    onChange={handleInputChange}
                    name="authors"
                  />
               </div>
             </form>

             <button
               className="button"
               onClick={saveBook}
             >
               Add Book
             </button>
           </div>
       </div>
      </>
   );
}

export default AddForm;