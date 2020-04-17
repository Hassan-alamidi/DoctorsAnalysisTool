import React from 'react';
import "../resources/css/shared.scss"
import CardButton from "../components/cardButton"
import CardAlt from "../components/card-alt"
import auth from "../components/auth"
import { Redirect, withRouter } from "react-router-dom"

const axios = require('axios').default;

class EncounterPage extends React.Component {
    constructor() {
        super();
        this.state = {
            patientPPSN: "",
            patient: "",
            nextPage:"",
            tokenExpired: false,
            patientNotFound: false,
            loading: true
        }
        
        this.pageChange = this.pageChange.bind(this);
        this.getTitle = this.getTitle.bind(this);
    }

    componentDidMount() {
        document.getElementById("background").src = require("../resources/images/woman-girl-silhouette-jogger-40751.jpg");
        this.state.patientPPSN = sessionStorage.getItem("patient");
        if (this.state.patientPPSN === undefined || this.state.patientPPSN === "") {
            this.setState({ patientNotFound: true, loading: false });
            return;
        }

        axios('http://localhost:8080/patient', { method: "get", withCredentials: true, headers: { "ppsn": this.state.patientPPSN } })
            .then(function (response) {
                console.log(response)
                response.data.detailed = false;
                this.setState({ patient: response.data, loading: false })
            }.bind(this)).catch(function (error) {
                console.log(error)
                if (error.response.status === 404) {
                    this.setState({ patientNotFound: true, loading: false });
                } else if (error.response.status === 403) {
                    auth.logout();
                    this.setState({ tokenExpired: true, loading: false });
                } else {
                    this.setState({ patientNotFound: true, loading: false });
                }
            }.bind(this));
    }

    getTitle() {
        return (this.state.patient.firstName + " " + this.state.patient.lastName);
    }

    pageChange(page){
        this.setState({nextPage:page})
    }

    render() {
        if (this.state.tokenExpired) {
            return (
                <Redirect to={
                    {
                        pathname: "/",
                        state: {
                            from: "/hub/patient"
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
                            from: "/hub/patient"
                        }
                    }
                } />
            )
        } else if (this.state.nextPage === "not implemented yet") {
            return(
            <Redirect to={
                {
                    pathname: "/dashboard/history",
                    state: {
                        from: "/hub/patient"
                    }
                }
            } />)
        } else { 
            return(
                <div className="container">
                    <CardAlt header={"Patient Encounter Documentation"}>
                        <div className="row">
                        <div className="col-xl-4">
                                <div className="row" style={{height:"100%"}}>
                                <CardButton header="Update Encounter" details="Update the details of this encounter"
                                                        icon="archive" callback={() => {this.pageChange("")}} />
                                </div>
                            </div>
                            <div className="col-xl-4">
                                <div className="row" style={{height:"100%"}}>
                                <CardButton header="Document Procedure" details="Document any procedures made today"
                                                        icon="archive" callback={() => {this.pageChange("")}} />
                                </div>
                            </div>
                            <div className="col-xl-4">
                                <div className="row" style={{height:"100%"}}>
                                <CardButton header="Document Condition" details="Document any conditions discovered today"
                                                        icon="archive" callback={() => {this.pageChange("")}} />
                                </div>
                            </div>
                        </div>
                        <div className="row">
                        <div className="col-xl-4">
                                <div className="row" style={{height:"100%"}}>
                                <CardButton header="Document Observation" details="Document any observations made today"
                                                        icon="archive" callback={() => {this.pageChange("")}} />
                                </div>
                            </div>
                            <div className="col-xl-4">
                                <div className="row" style={{height:"100%"}}>
                                <CardButton header="Document medications" details="Document any medications perscribed today"
                                                        icon="archive" callback={() => {this.pageChange("")}} />
                                </div>
                            </div>
                            <div className="col-xl-4">
                                <div className="row" style={{height:"100%"}}>
                                <CardButton header="Document treatment plan" details="Document any treatment plans made today"
                                                        icon="archive" callback={() => {this.pageChange("")}} />
                                </div>
                            </div>
                        </div>
                    </CardAlt>
                </div>
            )
        }
    }
}

export default withRouter(EncounterPage);