import React from "react";
import { Link } from "react-router-dom";

const Error404 = () => {
       return (
                <div class="content">
                <h1>Sorry, Page You Are Looking Not Found</h1>
                </div>)
                   <div>
                                           <Link to="/books">
                                               <button className="button">
                                                   Main Page
                                               </button>
                                           </Link>
                                           <Link to="/login">
                                               <button className="button">
                                                   Login
                                               </button>
                                           </Link>
                                           <Link to="/register">
                                               <button className="button">
                                                   Registration
                                               </button>
                                           </Link>
                                       </div>
       };

export default Error404;