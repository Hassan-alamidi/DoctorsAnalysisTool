import React from 'react';
import './App.scss';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faNotesMedical, faAddressCard, faFileMedicalAlt, faArchive } from '@fortawesome/free-solid-svg-icons';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom'
import { ProtectedRoute } from "./components/protectedRoute"

import LoginPage from './pages/login'
import HomePage from './pages/home'
import PatientControlPanel from './pages/patientControlPanel'
import HistoryPage from './pages/history'
import NavBar from './components/navBar'

function App() {
    library.add(faNotesMedical, faAddressCard, faFileMedicalAlt, faArchive);
    const withNavBar = () => (
        <Router>
            <NavBar />
            <Switch>
                <ProtectedRoute component={HomePage} exact path="/hub/home" />
                <ProtectedRoute component={PatientControlPanel} exact path="/hub/patient" />
                <ProtectedRoute path="*" component={() => "404 Page Not Found"} />
            </Switch>
        </Router>
    );
    const dashboard = () => (
        <Router>
            <Switch>
                <ProtectedRoute component={HistoryPage} exact path="/dashboard/history" />
                <ProtectedRoute path="*" component={() => "404 Dashboard Not Found"} />
            </Switch>
        </Router>
    );
    return (
        <Router>
            <img id="background" src={require("./resources/images/loginPageBackground.jpg")} alt=""></img>
            <Switch>
                <Route component={LoginPage} exact path="/" />
                <Route component={dashboard} path="/dashboard" />
                <Route component={withNavBar} path="/hub" />
                <Route path="*" component={() => "404 Page Not Found"} />
            </Switch>
        </Router>
    );
}

export default App;
