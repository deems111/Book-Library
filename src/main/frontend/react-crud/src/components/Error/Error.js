import React from "react";
import { Link } from "react-router-dom";

const Error = () => {
       return (
                <div class="content">
                <h1>Sorry, You Are Not Allowed to Access This Page</h1>
                <h3>Please Login or Register</h3>
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
                </div>)
       };

export default Error;