import React from 'react';
import "../resources/css/shared.scss"
import "bootstrap";

class SideBar extends React.Component {
    constructor() {
        super();
        this.state = {
            currentSelection:"conditions",
            currentOption:"On Going Conditions",
            currentFilter:"/conditions/current",
            alternateFilter:"/conditions",
            filterOption:"Full Condition History"
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
        if(this.state.currentOption === "Immunization"){
            return(
                <div className="panel-body">
                    <p>Filter Options</p>
                    <button className="btn btn-outline-light"
                        onClick={() => {}}>
                        No Filter Options Available
                    </button>
                </div>
            )
        }else{
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
                                onClick={() => {this.props.callback("/conditions/current","On Going Conditions")
                                                this.setState({
                                                currentOption:"On Going Conditions",
                                                currentFilter:"/conditions/current",
                                                alternateFilter:"/conditions",
                                                filterOption:"Full Condition History"})}}>
                                Condition History
                            </button>
                        </li>
                        <li className="nav-item li-button">
                            <button className="nav-link btn btn-link"
                                onClick={() => {this.props.callback("/medication/current","Current Medication")
                                this.setState({currentOption:"Current Medication",
                                                currentFilter:"/medication/current",
                                                alternateFilter:"/medication",
                                                filterOption:"Full Medication History"})}}>
                                Medication History
                            </button>
                        </li>
                        <li className="nav-item li-button">
                            <button className="nav-link btn btn-link"
                                onClick={() => {this.props.callback("/medication/immunization","Immunization")
                                this.setState({currentOption:"Immunization",
                                                currentFilter:"/medication/Immunization",
                                                alternateFilter:"/medication/Immunization",
                                                filterOption:"Immunization"})}}>
                                Immunizations
                            </button>
                        </li>
                        <li className="nav-item li-button">
                            <button className="nav-link btn btn-link"
                                onClick={() => {this.props.callback("/procedure/recent","Recent Procedures")
                                this.setState({currentOption:"Recent Procedures",
                                                currentFilter:"/procedure/recent",
                                                alternateFilter:"/procedure",
                                                filterOption:"Full Procedure History"})}}> 
                                Procedure History
                            </button>
                        </li>
                        <li className="nav-item li-button">
                        <button className="nav-link btn btn-link"
                            onClick={() => {this.props.callback("/observations/recent","Recent Observations")
                            this.setState({currentOption:"Recent Observations",
                                                currentFilter:"/observations/recent",
                                                alternateFilter:"/observations",
                                                filterOption:"Full Observation History"})}}>
                                Observation History
                            </button>
                        </li>
                        <li className="nav-item li-button">
                            <button className="nav-link btn btn-link"
                                onClick={() => {this.props.callback("/encounter/recent", "Recent Encounters")
                                this.setState({currentOption:"Recent Encounters",
                                                currentFilter:"/encounter/recent",
                                                alternateFilter:"/encounter",
                                                filterOption:"Full Encounter History"})}}>
                                Encounter History
                            </button>
                        </li>
                        <li className="nav-item li-button">
                            <button className="nav-link btn btn-link"
                                onClick={() => {this.props.callback("/treatment/current", "On Going Treatments")
                                this.setState({currentOption:"On Going Treatments",
                                                currentFilter:"/treatment/current",
                                                alternateFilter:"/treatment",
                                                filterOption:"Full Treatment History"})}}>
                                Treatment History
                            </button>
                        </li>
                        <li className="nav-item li-button">
                            <button className="nav-link btn btn-link"
                                onClick={() => {this.props.callback("/treatment/current", "On Going Treatments")
                                this.setState({currentOption:"On Going Treatments",
                                                currentFilter:"/treatment/current",
                                                alternateFilter:"/treatment",
                                                filterOption:"Full Treatment History"})}}>
                                Recently Generated Reports
                            </button>
                        </li>
                    </ul>
                </div>
            </nav>
        );
    }
}

export default SideBar;