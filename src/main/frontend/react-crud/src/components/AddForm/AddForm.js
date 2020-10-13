import React, { useState, useEffect }  from "react";
import { Link, Redirect } from "react-router-dom";

import BookService from "../../services/BookService";
import AuthorizationService from "../../services/AuthorizationService";
import Error from "../Error/Error";

const AddForm = () => {
  const init = {
    id: null,
    title: "Enter Title",
    genre: "Enter Genre",
    authors: "Enter Author(s)"
  };

    const [userRoleContent, setUserRoleContent] = useState(false);
    const [currentUser, setCurrentUser] = useState(undefined);

    useEffect(() => {
      const user = AuthorizationService.getCurrentUser();

      if (user) {
        setCurrentUser(user);
        setUserRoleContent(user.roles.includes("ROLE_USER"));
      }
    }, []);

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
       {shouldRedirect ? <Redirect to={`/`} noThrow /> : null},
       {userRoleContent ? (
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
       </div>) : (<Error />)
       }
      </>
   );
}

export default AddForm;