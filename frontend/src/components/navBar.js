import React from 'react';
import "../resources/css/shared.scss"
import "bootstrap";

class NavigationBar extends React.Component {

  render(){
    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark" id="topNav" >
            <a className="navbar-brand" href="/">Medical Hub</a>
            <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span className="navbar-toggler-icon"></span>
            </button>
    
            <div className="collapse navbar-collapse" id="navbarSupportedContent">
                <ul className="navbar-nav mr-auto">
                    <li className="nav-item">
                        <a className="nav-link" href="#/action">
                            Administration Panel
                        </a>
                    </li>
                </ul>
                
                <ul className="nav navbar-nav navbar-right" id="user-dropdown">
                    <li className="nav-item dropdown">
                        <a className="nav-link dropdown-toggle" data-toggle="dropdown" href="#/action"> Doctors Name
                            <span className="caret"></span></a>
                        <ul className="dropdown-menu bg-dark">
                            <li className="nav-item">
                                <a className="nav-link" href="#/action">
                                Account
                                </a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" href="#/action">
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