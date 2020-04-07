import React from 'react';
import "../resources/css/shared.scss"
import bootstrap from "bootstrap";

class SideBar extends React.Component {

  render(){
    return (
        <nav className="navbar navbar-dark bg-dark">
            <a className="navbar-brand" href="/">Medical Hub</a>
            <div className="navbar-collapse" id="navbarSupportedContent">
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

export default SideBar;