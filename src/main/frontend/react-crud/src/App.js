import React, { Component } from "react";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

import AddForm from "./components/AddForm/AddForm";
import BookItem from "./components/BookItem/BookItem";
import BookTable from "./components/BookTable/BookTable";

import styles from './static/style/style.css'

class App extends Component {
  render() {
    return (
      <Router>
        <div>
            <Switch>
              <Route exact path={["/", "/index"]} component={BookTable} />
              <Route exact path="/add" component={AddForm} />
              <Route path="/view/:id" component={BookItem} />
            </Switch>
        </div>
      </Router>
    );
  }
}

export default App;