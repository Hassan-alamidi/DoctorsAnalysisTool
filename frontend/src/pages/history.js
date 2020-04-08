import React from 'react';
import "../resources/css/shared.scss"
import auth from "../components/auth"
import { Redirect } from "react-router-dom"
import NavBar from '../components/navBar'
import Sidebar from '../components/sidebar'
import ConditionsList from "../components/conditionsList"

const axios = require('axios').default;

class HistoryPage extends React.Component {
    constructor() {
        super();
        this.state = {
            patientPPSN: "",
            tokenExpired: false,
            hisoricalData:"",
            historicalDataType:"condition",
            patientNotFound:false,
            loading: true
        }
        this.apiRequest = this.apiRequest.bind(this);
        this.requestHistory = this.requestHistory.bind(this);
    }

    componentDidMount() {
        document.getElementById("background").src = require("../resources/images/DNA.jpg");
        document.getElementById("topNav").classList.add("fixed");
        this.state.patientPPSN = sessionStorage.getItem("patient");
        if (this.state.patientPPSN === undefined || this.state.patientPPSN === "") {
            this.setState({ patientNotFound: true, loading: false });
            return;
        }
       
        this.apiRequest("/conditions/on-going");
    }

    apiRequest(endpoint){
        axios('http://localhost:8080' + endpoint, { method: "get", withCredentials: true, headers: { "PPSN": this.state.patientPPSN } })
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

    requestHistory(history){
        let endpoint = ""
        const bkUp = this.state.historicalDataType;
        this.setState({ loading: true, historicalDataType:history });
        switch(history){
            case 'condition':
                endpoint = "/conditions/on-going"
                break;
            case 'observation':
                endpoint = "/observations/recent"
                break;
            case 'medication':
                endpoint = "/medication/current"
                break;
            case 'procedure':
                endpoint = "/procedure/recent"
                break;
            case 'encounter':
                endpoint = "/encounter/recent"
                break;
            default:
                this.setState({ loading: false, historicalDataType:bkUp });
                return;
        }
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
            }else {
                return (
                    <div>
                        <NavBar />
                        <Sidebar callback={this.requestHistory} />
                        <div className="fluid-container" id="dataListContainer" >
                            <ConditionsList conditions={this.state.hisoricalData} />
                        </div>
                    </div>
                );
            }
        } else {
            return (
                <div>
                    <NavBar />
                    <Sidebar callback={this.requestHistory} />
                    <div className="container" style={{textAlign:"center"}}>
                        <h1>"Loading Please Wait..."</h1>
                    </div>
                </div>
            )
        }
    }
}

export default HistoryPage;