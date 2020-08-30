import React, { Component } from "react";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";

import AddForm from "./components/AddForm/AddForm";
import BookItem from "./components/BookItem/BookItem";
import BookTable from "./components/BookTable/BookTable";

class App extends Component {
  render() {
    return (
      <Router>
        <div>

          <div className="container mt-3">
            <Switch>
              <Route exact path={["/", "/list"]} component={BookTable} />
              <Route exact path="/add" component={AddForm} />
              <Route path="/book/:id" component={BookItem} />
            </Switch>
          </div>
        </div>
      </Router>
    );
  }
}

export default App;