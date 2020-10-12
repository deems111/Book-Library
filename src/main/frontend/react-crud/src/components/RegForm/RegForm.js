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

const RegForm = (props) => {
  const form = useRef();
  const checkBtn = useRef();

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("user");
  const [message, setMessage] = useState("");

  const onChangeUsername = (e) => {
    const username = e.target.value;
    setUsername(username);
  };

  const onChangePassword = (e) => {
    const password = e.target.value;
    setPassword(password);
  };

    const onChangeRole = (e) => {
      const role = e.target.value;
      setRole(role);
    };

  const handleRegister = (e) => {
    e.preventDefault();

    setMessage("");


      AuthorizationService.reg(username, password, role).then(
        (response) => {
          setMessage(response.data.message);
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
         <form onSubmit={handleRegister} ref={form}>
          <div className="form-group">
            <label htmlFor="username">Username</label>
            <input
              type="text"
              className="form-control"
              name="username"
              value={username}
              onChange={onChangeUsername}
            />
          </div>

          <div className="form-group">
            <label htmlFor="password">Password</label>
            <input
              type="password"
              className="form-control"
              name="password"
              value={password}
              onChange={onChangePassword}
            />
          </div>
          <div>
           <label>
                    Select Role:
                    <select value={role}
                      onChange={onChangeRole}>
                      <option value="user">User</option>
                      <option value="admin">Admin</option>
                      <option value="all">All Roles</option>
                    </select>
                  </label>
                  </div>
          <div className="form-group">
            <button className="btn btn-primary btn-block">
              {(
                <span className="spinner-border spinner-border-sm"></span>
              )}
              <span>Register</span>
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
          <Link to="/login">
                                <button className="button">
                                    Login
                                </button>
                            </Link>
      </div>
    </div>
  );
};

export default RegForm;