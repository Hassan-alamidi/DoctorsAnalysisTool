import React from 'react';
import "../resources/css/shared.scss"
import PatientInfoCard from "../components/PatientInfoCard"
import CardAlt from "../components/card-alt"
import auth from "../components/auth"
import { Redirect, withRouter } from "react-router-dom"
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

const axios = require('axios').default;

class PatientControlPanelPage extends React.Component {
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
            } else if (this.state.nextPage === "history") {
                return(
                <Redirect to={
                    {
                        pathname: "/dashboard/history",
                        state: {
                            from: "/hub/patient"
                        }
                    }
                } />)
                    //this.props.history.push("/dashboard/history");
            } else {
                const header = this.getTitle()
                return (
                    <div className="container">
                        <PatientInfoCard header={header} patient={this.state.patient} callback={this.managingPatient} />
                        <CardAlt header={"Patient Management"}>
                            <div className="row">
                                <div className="col-xl-6">
                                    <div className="row" style={{height:"100%"}}>
                                        <div className="cardButton col">
                                            <h5>Personal Information</h5>
                                            <hr />
                                            <div>
                                                <p>Update patients personal information</p>
                                                <FontAwesomeIcon icon="address-card" />
                                            </div>
                                        </div>
                                        <div className="cardButton col">
                                            <h5>Document Encounter</h5>
                                            <hr />
                                            <div>
                                                <p>Document recent encounter with patient</p>
                                                <FontAwesomeIcon icon="notes-medical" />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div className="col-xl-6">
                                    <div className="row" style={{height:"100%"}}>
                                        <div className="cardButton col" onClick={() => {this.pageChange("history")}}>
                                            <h5>Patient Medical History</h5>
                                            <hr />
                                            <div>
                                                <p>View patients medical history</p>
                                                <FontAwesomeIcon icon="archive" />
                                            </div>
                                        </div>
                                        <div className="cardButton col">
                                            <h5>Build Prediction Report</h5>
                                            <hr />
                                            <div>
                                                <p>Generated report with predictions on patients health based on most recent encounters</p>
                                                <FontAwesomeIcon icon="file-medical-alt" />
                                            </div>
                                        </div>
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