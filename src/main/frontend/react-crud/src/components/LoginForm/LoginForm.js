import React, { useState, useRef } from "react";
import { Link } from "react-router-dom";

import AuthorizationService from "../../services/AuthorizationService";

const required = (value) => {
  if (!value) {
    return (
      <div className="alert" role="alert">
        This field is required!
      </div>
    );
  }
};

const LoginForm = (props) => {
  const form = useRef();
  const checkBtn = useRef();

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");

  const onChangeUsername = (e) => {
    const username = e.target.value;
    setUsername(username);
  };

  const onChangePassword = (e) => {
    const password = e.target.value;
    setPassword(password);
  };

  const handleLogin = (e) => {
    e.preventDefault();

    setMessage("");

      AuthorizationService.login(username, password).then(
        () => {
          props.history.push("/books");
          window.location.reload();
        },
        (error) => {
          const resMessage =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();

          setMessage(resMessage);
        }
      );

  };

  return (
    <div>
      <div>
        <form onSubmit={handleLogin} ref={form}>
          <div className="form">
            <label htmlFor="username">Username</label>
            <input
              type="text"
              name="username"
              value={username}
              onChange={onChangeUsername}
            />
          </div>

          <div className="form">
            <label htmlFor="password">Password</label>
            <input
              type="password"
              className="form-control"
              name="password"
              value={password}
              onChange={onChangePassword}
            />
          </div>

          <div className="form-group">
            <button className="button">
              {(
                <span className="spinner-border spinner-border-sm"></span>
              )}
              <span>Login</span>
            </button>
          </div>

          {message && (
            <div className="form-group">
              <div className="alert alert-danger" role="alert">
                {message}
              </div>
            </div>
          )}
        </form>
          <Link to="/register">
                                <button className="button">
                                    Registration
                                </button>
                            </Link>
      </div>
    </div>
  );
};

export default LoginForm;