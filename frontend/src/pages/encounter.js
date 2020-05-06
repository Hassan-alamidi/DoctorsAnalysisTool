import React from 'react';
import "../resources/css/shared.scss"
import {CardButtonModal} from "../components/cardButton"
import CardAlt from "../components/card-alt"
import auth from "../components/auth"
import EncounterConfirmModal from "../components/modals/encounterConfirmModal"
import EncounterCreationModal from "../components/modals/encounterCreationModal"
import ProcdureModal from "../components/modals/procedureModal"
import ConditionModal from "../components/modals/conditionModal"
import ObservationModal from "../components/modals/observationModal"
import MedicationModal from "../components/modals/medicationModal"
import ProcedureModal from "../components/modals/procedureModal"
import TreatmentPlanModal from "../components/modals/treatmentPlanModal"
import GenericModal from "../components/modals/confirmationModal"
import { Redirect, withRouter } from "react-router-dom"
import $ from "jquery";

const axios = require('axios').default;

class EncounterPage extends React.Component {
    constructor() {
        super();
        this.state = {
            patient: "",
            currentEncounter:undefined,
            tokenExpired: false,
            patientNotFound: false,
            loading: true,
            updating:false,
            finalized:false
        }
        
        this.setCurrentEncounter = this.setCurrentEncounter.bind(this);
        this.finalizeEncounter = this.finalizeEncounter.bind(this);
        this.removeModal = this.removeModal.bind(this);
    }

    componentDidMount() {
        document.getElementById("background").src = require("../resources/images/woman-girl-silhouette-jogger-40751.jpg");
        const patientPPSN = sessionStorage.getItem("patient");
        if (patientPPSN === undefined || patientPPSN.trim() === "") {
            this.setState({ patientNotFound: true});
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
                    console.log("patient Not Found")
                    this.setState({ patientNotFound: true});
                } else if (error.response.status === 403) {
                    console.log("Token expired");
                    auth.logout();
                    this.setState({ tokenExpired: true});
                } else {
                    console.log("Something went wrong")
                    this.setState({ patientNotFound: true});
                }
            }.bind(this));
    }

    finalizeEncounter(){
        //need to componitize axios requests to reduce redundant code, in a rush now to get work done though so that will come later if time presents itself
        axios('http://localhost:8080/encounter/finalize/' + this.state.currentEncounter.id, { 
                method: "put", 
                withCredentials: true})
            .then(function (response) {
                console.log(response.data)
                this.setState({finalized:true});
            }.bind(this))
    }

    setCurrentEncounter(encounter){
        console.log("called")
        this.removeModal()
        //$('#LoadingModal').modal('hide');
        //$('#createEncounterModal').modal('hide');
        this.setState({updating:true,currentEncounter:encounter});
    }

    removeModal(){
        $('.modal').modal('hide');
        $('body').removeClass('modal-open');
        $('.modal-backdrop').remove();
    }

    render() {
        if (this.state.tokenExpired) {
            this.removeModal();
            return (
                <Redirect to={
                    {
                        pathname: "/",
                        state: {
                            from: "/hub/patient/encounter"
                        }
                    }
                } />
            )
        } else if (this.state.patientNotFound) {
            this.removeModal();
            return (
                <Redirect to={
                    {
                        pathname: "/hub/home",
                        state: {
                            from: "/hub/patient/encounter"
                        }
                    }
                } />
            )
        } else if (this.state.finalized === true) {
            this.removeModal();
            return(
            <Redirect to={
                {
                    pathname: "/hub/patient",
                    state: {
                        from: "/hub/patient/encounter"
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
                                                            icon="archive" modalId="#createEncounterModal" />
                                </div>
                            </div>
                            <div className="col-xl-4">
                                <div className="row" style={{height:"100%"}}>
                                    <CardButtonModal header="Document Procedure" details="Document any procedures made today"
                                                            icon="archive" modalId="#createProcedureModal" />
                                </div>
                            </div>
                            <div className="col-xl-4">
                                <div className="row" style={{height:"100%"}}>
                                    <CardButtonModal header="Document Condition" details="Document any conditions discovered today"
                                                            icon="archive" modalId="#createConditionModal" />
                                </div>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-xl-4">
                                <div className="row" style={{height:"100%"}}>
                                    <CardButtonModal header="Document Observation" details="Document any observations made today"
                                                            icon="archive" modalId="#createObservationModal" />
                                </div>
                            </div>
                            <div className="col-xl-4">
                                <div className="row" style={{height:"100%"}}>
                                    <CardButtonModal header="Document medications" details="Document any medications prescribed today"
                                                            icon="archive" modalId="#createMedicationModal" />
                                </div>
                            </div>
                            <div className="col-xl-4">
                                <div className="row" style={{height:"100%"}}>
                                    <CardButtonModal header="Document treatment plan" details="Document any treatment plans made today"
                                                            icon="archive" modalId="#createTreatmentPlanModal" />
                                </div>
                            </div>
                        </div>
                        <div className="row">
                            <button type="button" className="btn btn-secondary space-please btn-lg btn-block" data-toggle="modal" data-target="#genericModal">Finalize Encounter</button>
                        </div>
                    </CardAlt>
                    {(this.state.updating) && <EncounterCreationModal patient={this.state.patient} callback={this.setCurrentEncounter} requestType="put" currentEncounter={this.state.currentEncounter} />}
                    <ProcdureModal patient={this.state.patient} callback={this.setCurrentEncounter} requestType="post" currentEncounter={this.state.currentEncounter} />
                    <ConditionModal patient={this.state.patient} callback={this.setCurrentEncounter} requestType="post" currentEncounter={this.state.currentEncounter} />
                    <ObservationModal patient={this.state.patient} callback={this.setCurrentEncounter} requestType="post" currentEncounter={this.state.currentEncounter} />
                    <MedicationModal patient={this.state.patient} callback={this.setCurrentEncounter} requestType="post" currentEncounter={this.state.currentEncounter} />
                    <ProcedureModal patient={this.state.patient} callback={this.setCurrentEncounter} requestType="post" currentEncounter={this.state.currentEncounter} />
                    <TreatmentPlanModal patient={this.state.patient} callback={this.setCurrentEncounter} requestType="post" currentEncounter={this.state.currentEncounter} />   
                    <GenericModal header="Finalize Encounter" callback={this.finalizeEncounter} >
                        <h6>Are you sure you wish to finalize this encounter? <br/><br/> Finalization means that the patient has left your care and all documentation related to this encounter is complete. <br/><br/> Once finalized no further updates to this encounter will be permitted.</h6>
                    </GenericModal>     
                </div>
            )
        }
    }
}

export default withRouter(EncounterPage);