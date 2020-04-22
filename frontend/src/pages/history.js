import React from 'react';
import "../resources/css/shared.scss"
import auth from "../components/auth"
import { Redirect, withRouter } from "react-router-dom"
import NavBar from '../components/navBar'
import Sidebar from '../components/sidebar'
import {ConditionsListTransparent} from "../components/reportLists/conditionsList"
import {MedicationListTransparent} from "../components/reportLists/medicationList"
import {ObservationListTransparent} from "../components/reportLists/observationList"
import ProcedureListTransparent from "../components/reportLists/procedureList"
import EncounterList from "../components/reportLists/encounterList"

const axios = require('axios').default;

class HistoryPage extends React.Component {
    constructor() {
        super();
        this.state = {
            patientPPSN: "",
            tokenExpired: false,
            hisoricalData:"",
            historicalDataType:"Full Condition History",
            patientNotFound:false,
            loading: true
        }
        this.apiRequest = this.apiRequest.bind(this);
        this.requestHistory = this.requestHistory.bind(this);
    }

    componentDidMount() {
        document.getElementById("background").src = require("../resources/images/DNA.jpg");
        document.getElementById("topNav").classList.add("fixed");
        //set state not used as it triggers page reload and redirects back to home
        this.state.patientPPSN = sessionStorage.getItem("patient");
        if (this.state.patientPPSN === undefined || this.state.patientPPSN === "") {
            this.setState({ patientNotFound: true, loading: false });
            return;
        }
       
        this.apiRequest("/conditions");
    }

    apiRequest(endpoint){
        axios('http://localhost:8080' + endpoint, { method: "get", withCredentials: true, headers: { "ppsn": this.state.patientPPSN } })
            .then(function (response) {
                console.log(response)
                this.setState({ loading: false, hisoricalData:response.data })
            }.bind(this)).catch(function (error) {
                console.log(error)
                if (error.response.status === 404) {
                    this.setState({loading: false });
                } else if (error.response.status === 403) {
                    auth.logout();
                    this.setState({ tokenExpired: true, loading: false });
                } else {
                    this.setState({ loading: false });
                }
            }.bind(this));
    }

    requestHistory(endpoint, history){
        this.setState({ loading: true, historicalDataType:history });
        this.apiRequest(endpoint)
    }

    render() {
        if (!this.state.loading) {
            if (this.state.tokenExpired) {
                return (
                    <Redirect to={
                        {
                            pathname: "/",
                            state: {
                                from: "/dashboard/history"
                            }
                        }
                    } />
                )
            } else if (this.state.patientNotFound) {
                return (
                    <Redirect to={
                        {
                            pathname: "/hub/home",
                            state: {
                                from: "/dashboard/history"
                            }
                        }
                    } />
                )
            } else if(this.state.historicalDataType.includes("Procedure")) {
                return (
                    <div>
                        <NavBar />
                        <Sidebar callback={this.requestHistory} />
                        <div className="fluid-container" id="dataListContainer" >
                            <ProcedureListTransparent procedures={this.state.hisoricalData} header={this.state.historicalDataType} />
                        </div>
                    </div>
                );
            }else if(this.state.historicalDataType.includes("Medication")) {
                return (
                    <div>
                        <NavBar />
                        <Sidebar callback={this.requestHistory} />
                        <div className="fluid-container" id="dataListContainer" >
                            <MedicationListTransparent medications={this.state.hisoricalData} header={this.state.historicalDataType} />
                        </div>
                    </div>
                );
            } else if(this.state.historicalDataType.includes("Observation")){
                return (
                    <div>
                        <NavBar />
                        <Sidebar callback={this.requestHistory} />
                        <div className="fluid-container" id="dataListContainer" >
                            <ObservationListTransparent observations={this.state.hisoricalData} header={this.state.historicalDataType}/>
                        </div>
                    </div>
                );
            } else if(this.state.historicalDataType.includes("Condition")){
                return (
                    <div>
                        <NavBar />
                        <Sidebar callback={this.requestHistory} />
                        <div className="fluid-container" id="dataListContainer" >
                            <ConditionsListTransparent conditions={this.state.hisoricalData} header={this.state.historicalDataType}/>
                        </div>
                    </div>
                );
            } else if(this.state.historicalDataType.includes("Encounter")){
                return (
                    <div>
                        <NavBar />
                        <Sidebar callback={this.requestHistory} />
                        <div className="fluid-container" id="dataListContainer" >
                            <EncounterList encounters={this.state.hisoricalData} header={this.state.historicalDataType}/>
                        </div>
                    </div>
                );
            } else{
                return (
                    <div>
                        <NavBar />
                        <Sidebar callback={this.requestHistory} />
                        <div className="fluid-container" style={{textAlign:"center", top: "80px", position: "relative"}}>
                            <h1>Something Went Wrong Please Try Again</h1>
                        </div>
                    </div>
                )
            }
        } else {
            return (
                <div>
                    <NavBar />
                    <Sidebar callback={this.requestHistory} />
                    <div className="fluid-container" style={{textAlign:"center", top: "80px", position: "relative"}}>
                        <h1>"Loading Please Wait..."</h1>
                    </div>
                </div>
            )
        }
    }
}

export default withRouter(HistoryPage);