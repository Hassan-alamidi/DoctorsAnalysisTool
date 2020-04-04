import React from 'react';
import './App.scss';
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom'
import {ProtectedRoute} from "./components/protectedRoute"

import LoginPage from './pages/login'
import HomePage from './pages/home'
import NavBar from './components/navBar'

function App() {
  const withNavBar = () =>(
    <div>
      <NavBar />
      <ProtectedRoute component={HomePage} path="/home" />
    </div>
  );
  return(
    <Router>
      <Switch>
        <Route component={LoginPage} exact path="/" />
        <Route component={withNavBar} />
        <Route path="*" component={() => "404 Page Not Found"} />
      </Switch>
    </Router>
  );
}

export default App;
