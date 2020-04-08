import React from 'react';
import "../resources/css/shared.scss"
import bootstrap from "bootstrap";

class SideBar extends React.Component {

  render(){
    //TODO make more customizable
    return (
        <nav className="navbar navbar-dark bg-dark" id="sidebar" >
            <div id="navbarSupportedContent">
                <ul className="navbar-nav">
                    <li className="nav-item">
                        <button className="nav-link btn btn-link"
                            onClick={() => this.props.callback("condition")}>
                            Condition History
                        </button>
                    </li>
                    <li className="nav-item">
                        <button className="nav-link btn btn-link"
                            onClick={() => this.props.callback("medication")}>
                            Medication History
                        </button>
                    </li>
                    <li className="nav-item">
                        <button className="nav-link btn btn-link"
                            onClick={() => this.props.callback("procedure")}> 
                            Procedure History
                        </button>
                    </li>
                    <li className="nav-item">
                    <button className="nav-link btn btn-link"
                        onClick={() => this.props.callback("observation")}>
                            Observation History
                        </button>
                    </li>
                    <li className="nav-item">
                        <button className="nav-link btn btn-link"
                            onClick={() => this.props.callback("encounter")}>
                            Encounter History
                        </button>
                    </li>
                </ul>
            </div>
        </nav>
    );
  }
}

export default SideBar;