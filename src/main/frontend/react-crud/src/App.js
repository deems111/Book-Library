import React, { Component, useState, useEffect } from "react";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

import AddForm from "./components/AddForm/AddForm";
import BookItem from "./components/BookItem/BookItem";
import BookTable from "./components/BookTable/BookTable";
import LoginForm from "./components/LoginForm/LoginForm";
import RegForm from "./components/RegForm/RegForm";
import Error from "./components/Error/Error";

import AuthorizationService from "./services/AuthorizationService";

import "bootstrap/dist/css/bootstrap.min.css";
import styles from './static/style/style.css'

class App extends Component {
    render() {
    return (
      <Router>
        <div>
            <Switch>
              <Route exact path={["/", "/index", "/books"]} component={BookTable} />
              <Route exact path="/add" component={AddForm} />
              <Route path="/view/:id" component={BookItem} />
              <Route path="/login" component={LoginForm} />
              <Route path="/register" component={RegForm} />
              <Route path="/error" component={Error} />
              <Route component={Error} />
            </Switch>
        </div>
      </Router>
    );
  }
}

export default App;