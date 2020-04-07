import React from 'react';
import './App.scss';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faNotesMedical, faAddressCard, faFileMedicalAlt, faArchive } from '@fortawesome/free-solid-svg-icons';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom'
import { ProtectedRoute } from "./components/protectedRoute"

import LoginPage from './pages/login'
import HomePage from './pages/home'
import PatientControlPanel from './pages/patientControlPanel'
import NavBar from './components/navBar'

function App() {
    library.add(faNotesMedical, faAddressCard, faFileMedicalAlt, faArchive);
    const withNavBar = () => (
        <div>
            <NavBar />
            <ProtectedRoute component={HomePage} path="/home" />
            <ProtectedRoute component={PatientControlPanel} path="/patient" />
        </div>
    );
    return (
        <Router>
            <img id="background" src={require("./resources/images/loginPageBackground.jpg")} alt=""></img>
            <Switch>
                <Route component={LoginPage} exact path="/" />
                <Route component={withNavBar} />
                <Route path="*" component={() => "404 Page Not Found"} />
            </Switch>
        </Router>
    );
}

export default App;
