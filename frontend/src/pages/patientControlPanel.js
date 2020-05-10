import React from 'react';
import "../resources/css/shared.scss"
import PatientInfoCard from "../components/PatientInfoCard"
import CardButton from "../components/cardButton"
import CardAlt from "../components/card-alt"
import auth from "../components/auth"
import { Redirect, withRouter } from "react-router-dom"
import $ from "jquery";

const axios = require('axios').default;

class PatientControlPanelPage extends React.Component {
    constructor() {
        super();
        this.state = {
            patient: "",
            nextPage:"current",
            tokenExpired: false,
            patientNotFound: false,
            loading: true
        }
        
        this.pageChange = this.pageChange.bind(this);
        this.getTitle = this.getTitle.bind(this);
    }

    componentDidMount() {
        document.getElementById("background").src = require("../resources/images/woman-girl-silhouette-jogger-40751.jpg");
        const patientPPSN = sessionStorage.getItem("patient");
        
        if (patientPPSN === undefined || patientPPSN === "") {
            this.setState({ patientNotFound: true, loading: false });
            return;
        }

        axios('/patient', { method: "get", withCredentials: true, headers: { "ppsn": patientPPSN } })
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
        $('.modal').modal('hide');
        if (!this.state.loading) {
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
            } else if (this.state.nextPage !== "current") {
                return(
                <Redirect push to={
                    {
                        pathname: this.state.nextPage,
                        state: {
                            from: "/hub/patient"
                        }
                    }
                } />)
            } else {
                const header = this.getTitle()
                return (
                    <div className="container">
                        <PatientInfoCard header={header} patient={this.state.patient} callback={this.managingPatient} />
                        <CardAlt header={"Patient Management"}>
                            <div className="row">
                                <div className="col-xl-6">
                                    <div className="row" style={{height:"100%"}}>
                                        <CardButton header="Personal Information" details="Update patients personal information"
                                                    icon="address-card" callback={() => {this.pageChange("/hub/patient/information")}} />
                                        <CardButton header="Document Encounter" details="Document encounter with patient"
                                                    icon="notes-medical" callback={() => {this.pageChange("/hub/patient/encounter")}} />
                                    </div>
                                </div>
                                <div className="col-xl-6">
                                    <div className="row" style={{height:"100%"}}>
                                        <CardButton header="Patient Medical History" details="View patients medical history"
                                                    icon="archive" callback={() => {this.pageChange("/dashboard/history")}} />
                                        <CardButton header="Build Prediction Report" details="Generated report with predictions on patients health based on most recent encounters"
                                                    icon="file-medical-alt" callback={() => {this.pageChange("/hub/patient/prediction")}} />
                                    </div>
                                </div>
                            </div>
                        </CardAlt>
                    </div>
                );
            }
        } else {
            return (
                <div className="container">
                    <h1>"Loading Please Wait..."</h1>
                </div>
            )
        }
    }
}

export default withRouter(PatientControlPanelPage);