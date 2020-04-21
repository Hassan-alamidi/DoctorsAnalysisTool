import React from 'react';
import "../resources/css/shared.scss"
import {CardButton, CardButtonModal} from "../components/cardButton"
import CardAlt from "../components/card-alt"
import auth from "../components/auth"
import EncounterConfirmModal from "../components/encounterConfirmModal"
import EncounterCreationModal from "../components/encounterCreationModal"
import { Redirect, withRouter } from "react-router-dom"
import $ from "jquery";

const axios = require('axios').default;

class EncounterPage extends React.Component {
    constructor() {
        super();
        this.state = {
            patient: "",
            nextPage:"",
            currentEncounter:undefined,
            tokenExpired: false,
            patientNotFound: false,
            loading: true,
            updating:false
        }
        
        this.pageChange = this.pageChange.bind(this);
        this.getTitle = this.getTitle.bind(this);
        this.setCurrentEncounter = this.setCurrentEncounter.bind(this);
    }

    componentDidMount() {
        document.getElementById("background").src = require("../resources/images/woman-girl-silhouette-jogger-40751.jpg");
        const patientPPSN = sessionStorage.getItem("patient");
        if (patientPPSN === undefined || patientPPSN.trim() === "") {
            this.setState({ patientNotFound: true, loading: false });
            return;
        }

        axios('http://localhost:8080/patient', { method: "get", withCredentials: true, headers: { "ppsn": patientPPSN } })
            .then(function (response) {
                response.data.detailed = false;
                console.log(response)
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
        this.setState({nextPage:page});
    }

    setCurrentEncounter(encounter){
        $('#encounterModal').modal('hide');
        $('#LoadingModal').modal('hide');
        $('#createEncounterModal').modal('hide');
        this.setState({currentEncounter:encounter});
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
                    {(!this.state.loading && this.state.currentEncounter === undefined) && 
                    <EncounterConfirmModal patient={this.state.patient} callback={this.setCurrentEncounter} />}
                    <CardAlt header={"Patient Encounter Documentation"}>
                        <div className="row">
                            <div className="col-xl-4">
                                <div className="row" style={{height:"100%"}}>
                                    <CardButtonModal header="Update Encounter" details="Update the details of this encounter"
                                                            icon="archive" callback={() => {this.setState({updating:true})}} modalId="#createEncounterModal" />
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
                        <div className="row">
                            <button type="button" className="btn btn-secondary space-please btn-lg btn-block" onClick="">Finalize</button>
                        </div>
                    </CardAlt>
                    {(this.state.updating) && <EncounterCreationModal patient={this.state.patient} callback={this.setCurrentEncounter} requestType="put" currentEncounter={this.state.currentEncounter} />}
                </div>
            )
        }
    }
}

export default withRouter(EncounterPage);