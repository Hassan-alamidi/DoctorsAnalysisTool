import React from 'react';
import "../resources/css/shared.scss"
import { useLocation } from 'react-router-dom'
import "bootstrap";
import auth from "./auth"

class NavigationBar extends React.Component {

    render(){
        //alert(auth.isAdmin());
        return (
            <nav className="navbar navbar-expand-lg navbar-dark bg-dark" id="topNav" >
                <a className="navbar-brand" href="/hub/home">Medical Hub</a>
                <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
        
                <div className="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul className="navbar-nav mr-auto">
                        {(auth.isAdmin()) &&
                            <li className="nav-item">
                                <a className="nav-link" href="#/action">
                                    Register Staff
                                </a>
                            </li>
                        }
                        {(window.location.pathname !== "/hub/home") && 
                            <li className="nav-item">
                                <a className="nav-link" href="/hub/patient">
                                    Patient Control Panel
                                </a>
                            </li>
                        }
                    </ul>
                    
                    <ul className="nav navbar-nav navbar-right" id="user-dropdown">
                        <li className="nav-item dropdown">
                            <a className="nav-link dropdown-toggle" data-toggle="dropdown" href="#/action"> Doctors Name
                                <span className="caret"></span></a>
                            <ul className="dropdown-menu bg-dark">
                                <li className="nav-item">
                                    <a className="nav-link" href="/info/account">
                                    Account
                                    </a>
                                </li>
                                <li className="nav-item">
                                    <a className="nav-link" onClick={() => {auth.logout()}} href="/">
                                        Sign Out
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </nav>
        );
    }
}

export default NavigationBar;