import React from 'react';
import "../resources/css/shared.scss"
import "bootstrap";

class SideBar extends React.Component {
    constructor() {
        super();
        this.state = {
            currentSelection:"conditions",
            currentOption:"Full Condition History",
            currentFilter:"/conditions",
            alternateFilter:"/conditions/current",
            filterOption:"Recent Conditions"
        }
        this.filterOption = this.filterOption.bind(this);
        this.switchState = this.switchState.bind(this);
    }

    switchState(){
        this.setState({
            currentOption:this.state.filterOption, 
            filterOption:this.state.currentOption,
            currentFilter:this.state.alternateFilter, 
            alternateFilter:this.state.currentFilter})
    }

    filterOption(){
        return(
            <div className="panel-body">
                <p>Filter Options</p>
                <button className="btn btn-outline-light"
                    onClick={() => {this.props.callback(this.state.alternateFilter, this.state.filterOption)
                                    this.switchState()}}>
                    Filter {this.state.filterOption}
                </button>
            </div>
        )
    }

    render(){
        //TODO make more customizable, at the moment this is more of a rush job
        return (
            <nav className="navbar navbar-dark bg-dark" id="sidebar" >
                <div id="navbarSupportedContent">
                    <this.filterOption/>
                    <ul className="navbar-nav">
                        <li className="nav-item li-button">
                            <button className="nav-link btn btn-link"
                                onClick={() => {this.props.callback("/conditions","Full Condition History")
                                                this.setState({
                                                currentOption:"Full Condition History",
                                                currentFilter:"/conditions",
                                                alternateFilter:"/conditions/current",
                                                filterOption:"Recent Conditions"})}}>
                                Condition History
                            </button>
                        </li>
                        <li className="nav-item li-button">
                            <button className="nav-link btn btn-link"
                                onClick={() => {this.props.callback("/medication","Full Medication History")
                                this.setState({currentOption:"Full Medication History",
                                                currentFilter:"/medication",
                                                alternateFilter:"/medication/current",
                                                filterOption:"Current Medication"})}}>
                                Medication History
                            </button>
                        </li>
                        <li className="nav-item li-button">
                            <button className="nav-link btn btn-link"
                                onClick={() => {this.props.callback("/procedure","Full Procedure History")
                                this.setState({currentOption:"Full Procedure History",
                                                currentFilter:"/procedure",
                                                alternateFilter:"/procedure/recent",
                                                filterOption:"Recent Procedures"})}}> 
                                Procedure History
                            </button>
                        </li>
                        <li className="nav-item li-button">
                        <button className="nav-link btn btn-link"
                            onClick={() => {this.props.callback("/observations","Full Observation History")
                            this.setState({currentOption:"Full Observation History",
                                                currentFilter:"/observations",
                                                alternateFilter:"/observations/recent",
                                                filterOption:"Recent Observations"})}}>
                                Observation History
                            </button>
                        </li>
                        <li className="nav-item li-button">
                            <button className="nav-link btn btn-link"
                                onClick={() => {this.props.callback("/encounter", "Full Encounter History")
                                this.setState({currentOption:"Full Encounter History",
                                                currentFilter:"/encounter",
                                                alternateFilter:"/encounter/recent",
                                                filterOption:"Recent Encounters"})}}>
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