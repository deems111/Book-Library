import React, { useState, useEffect } from "react";
import { Link, Redirect } from "react-router-dom";

import BookService from "../../services/BookService";
import AuthorizationService from "../../services/AuthorizationService";
import Error from "../Error/Error";

const BookItem = props => {
  const initState = {
    id: "",
    title: "",
    genre: "",
    authors: "",
    comments: [],
  };

  const [book, setBook] = useState(initState);
  const [comments, setComments] = useState(initState);
  const [shouldRedirect, setShouldRedirect] = useState(false);

  const [userRoleContent, setUserRoleContent] = useState(false);
  const [adminRoleContent, setAdminRoleContent] = useState(false);
  const [currentUser, setCurrentUser] = useState(undefined);

    useEffect(() => {
        const user = AuthorizationService.getCurrentUser();
        if (user) {
            setCurrentUser(user);
            setUserRoleContent(user.roles.includes("ROLE_USER"));
            setAdminRoleContent(user.roles.includes("ROLE_ADMIN"));
            getBook(props.match.params.id);
            getComments(props.match.params.id);
        }

    }, [props.match.params.id]);

    const handleInputChange = event => {
        const { name, value } = event.target;
        setBook({ ...book, [name]: value });
    };

  const getBook = id =>  {
    BookService.getBook(id)
      .then(response => {
        setBook(response.data);
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  }

  const getComments = id => {
    BookService.getComments(id)
      .then(response => {
        setComments(response.data);
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  }

  const deleteBook = () => {
    BookService.deleteBook(book.id)
      .then(response => {
         console.log(response.data);
         setShouldRedirect(true);
      })
      .catch(e => {
        console.log(e);
         setShouldRedirect(false);
      });
  }

  const updateBook = () => {
   var updatedBook = {
         id: book.id,
         title: book.title,
         genre: book.genre,
         authors: book.authors
       };

    BookService.updateBook(updatedBook.id, updatedBook)
      .then(response => {
        console.log(response.data);
        setShouldRedirect(true);
      })
      .catch(e => {
        console.log(e);
        setShouldRedirect(false);
      });
  }

    return (
    <>
    {shouldRedirect ? <Redirect to={`/`} noThrow /> : null}
    {userRoleContent || adminRoleContent ? (
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
                  onChange={handleInputChange}
                  name="title"
                />
              </div>
              <div>
                <label htmlFor="genre">Genre</label>
                <input
                  type="text"
                  id="Genre"
                  value={book.genre}
                  onChange={handleInputChange}
                  name="genre"
                />
              </div>
              <div>
                <label htmlFor="authors">Authors</label>
                <input
                  type="text"
                  id="Authors"
                  value={book.authors}
                  onChange={handleInputChange}
                  name="authors"
                />
              </div>

            </form>
            {adminRoleContent ? (
            <button
              className="button" type="delete"
              onClick={deleteBook} >
              Delete
            </button>) : null }
                        <button
                          className="button"
                          onClick={updateBook}
                        >
                          Update
                        </button>
      </div>
      <div class="content">
            <h2>Comments</h2>
                {!false ? (
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
                              {book.comments &&
                              book.comments.map((comment, index) => (
                                  <tr key={comment.id}>
                                    <th>{comment.name}</th>
                                    <th>{comment.subject}</th>
                                  </tr>
                              ))}
                          </tbody>
                    </table>
                )}
      </div>
    </div> ) : (<Error />)
    }
    </>
    );
}

export default BookItem;