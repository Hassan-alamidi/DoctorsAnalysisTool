import React from 'react';
import "../../resources/css/shared.scss"
import "bootstrap";
import {MedicationListNoBody} from '../reportLists/medicationList'
import { Redirect} from "react-router-dom"
import $ from "jquery";

const axios = require('axios').default;

class MedicationModal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            error:"",
            medication:{
                //Medications were originally meant to be searchable via autosuggestion, which would then add a Medications assigned code.
                //but that is low priority on my list of things todo and it is unlikly I will have time to get this completed so setting code to ignore
                code:"ignored",
                type:"",
                reasonDescription:"",
                reasonCode:"ignore",
                prescribedAmount:0
            },
            close:false,
            create:false
        }

        this.submitMedication = this.submitMedication.bind(this);
        this.returnToPatientPage = this.returnToPatientPage.bind(this);
        this.typeChangeHandler = this.typeChangeHandler.bind(this);
        this.descriptionChangeHandler = this.descriptionChangeHandler.bind(this);
        this.treatmentStartChangeHandler = this.treatmentStartChangeHandler.bind(this);
        this.treatmentEndChangeHandler = this.treatmentEndChangeHandler.bind(this);
        this.reasonDescriptionChangeHandler = this.reasonDescriptionChangeHandler.bind(this);
        this.prescribedAmountChangeHandler = this.prescribedAmountChangeHandler.bind(this);
        this.updateFooter = this.updateFooter.bind(this);
        this.creationFooter = this.creationFooter.bind(this);
        this.valueEntered = this.valueEntered.bind(this);
        this.resetObjectState = this.resetObjectState.bind(this);
        this.listFooter = this.listFooter.bind(this);
        this.deleteMedication = this.deleteMedication.bind(this);
    }

    valueEntered(val){
        console.log(val);
        return (val !== undefined && val.trim() !== "")
    }

    resetObjectState(){
        this.setState({
            create:false,
            medication:{
                code:"ignored",
                type:"",
                reasonDescription:"",
                reasonCode:"ignore",
                prescribedAmount:0
            }
        });
    }

    submitMedication(){
        if(this.state.medication !== undefined && this.valueEntered(this.state.medication.description) 
        && this.valueEntered(this.state.medication.treatmentStart)){
            let requestData = this.state.medication;
            requestData.patient = this.props.patient;
            requestData.encounter = this.props.currentEncounter;
            axios('/medication', { 
                    data:requestData,
                        method: this.props.requestType, 
                        withCredentials: true})
                .then(function (response) {
                    this.resetObjectState();
                    const encounterId = response.data.encounter.id;
                    
                    axios('/encounter/' + encounterId, { 
                            method: "get", 
                            withCredentials: true})
                        .then(function (response) {
                            console.log(response.data)
                            this.props.callback(response.data)
                        }.bind(this))

                }.bind(this));
        }else{
            this.setState({error:"You must enter all details marked with *"})
        }
    }

    deleteMedication(id){
        axios('/medication/'+id, { 
                method: 'delete', 
                withCredentials: true})
        .then(function (response) {
            this.resetObjectState();
            const encounterId = this.props.currentEncounter.id;
            
            axios('/encounter/' + encounterId, { 
                    method: "get", 
                    withCredentials: true})
                .then(function (response) {
                    console.log(response.data)
                    this.props.callback(response.data)
                }.bind(this))

        }.bind(this));
    }

    returnToPatientPage(){
        this.setState({close:true});
    }

    descriptionChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.medication;
        backup.description = value
        this.setState({ medication: backup });
    }

    typeChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.medication;
        backup.type = value
        this.setState({ medication: backup });
    }

    treatmentStartChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.medication;
        backup.treatmentStart = value
        this.setState({ medication: backup });
    }

    treatmentEndChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.medication;
        backup.treatmentEnd = value
        this.setState({ medication: backup });
    }


    reasonDescriptionChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.medication;
        backup.reasonDescription = value
        this.setState({ medication: backup });
    }

    prescribedAmountChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.medication;
        backup.prescribedAmount = value.replace(/\D/g, "")
        this.setState({ medication: backup });
    }

    render(){
        if(this.state.close){
            $('#createMedicationModal').modal('hide');
            return (
                <Redirect to={
                    {
                        pathname: "/hub/patient",
                        state: {
                            from: "/hub/patient/encounter"
                        }
                    }
                } />
            );
        }else if(this.props.currentEncounter && this.props.currentEncounter.medications.length > 0 && !this.state.create){
            return(
                <div className="modal fade" id="createMedicationModal" tabIndex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                        <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                            <div className="modal-content">
                                <div className="modal-header">
                                    <h5 className="modal-title" id="exampleModalLongTitle">Current Documented Medication</h5>
                                </div>
                                <div className="modal-body">
                                    <MedicationListNoBody callback={this.deleteMedication} medications={this.props.currentEncounter.medications} />
                                </div>
                                <this.listFooter />
                        </div>
                    </div>
                </div>
            )
        }else{
            return(
                <div className="modal fade" id="createMedicationModal" tabIndex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                    <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="exampleModalLongTitle">Please Enter Medication Details</h5>
                            </div>
                            <div className="modal-body">
                                <p className="text-danger">{this.state.error}</p>
                                <p>* Medication Details </p>
                                <input className="form-control form-control-lg" id="descriptionInput" type="text" placeholder="Details, i.e. Penicillin V Potassium 250 MG Oral Tablet" name="type" onChange={this.descriptionChangeHandler} />
                                <p>* Type Of Medication i.e. immunization.......CHANGE THIS TO CHECKBOX</p>
                                <input className="form-control form-control-lg" id="typeInput" type="text" placeholder="Name" name="type" onChange={this.typeChangeHandler} />
                                <p>* Date This Treatment Started</p>
                                <input className="form-control form-control-lg" id="treatmentStartInput" type="date" placeholder="yyyy-mm-dd" name="treatmentStart" onChange={this.treatmentStartChangeHandler} />
                                <p>Date This Treatment Is Scheduled To End, (if immunization leave blank)</p>
                                <input className="form-control form-control-lg" id="treatmentEndInput" type="date" placeholder="yyyy-mm-dd" name="treatmentEnd" onChange={this.treatmentEndChangeHandler} />
                                <p>* Number Of Dispenses For Prescriptions The Patient May Need During This Period, i.e. Maximum Dispensed Allowed Without Revist</p>
                                <input className="form-control form-control-lg" id="prescribedAmountInput" type="text" value={this.state.medication.prescribedAmount} placeholder="Number Of Dispenses For Prescriptions" name="prescribedAmount" onChange={this.prescribedAmountChangeHandler} />
                                <p>* Reason For Medication</p>
                                <input className="form-control form-control-lg" id="reasonDescriptionInput" type="text" placeholder="Reason, i.e. Chest Infection" name="reasonDescription" onChange={this.reasonDescriptionChangeHandler} />
                            </div>
                            {this.props.requestType === "post" ? <this.creationFooter/> : <this.updateFooter/>}
                        </div>
                    </div>
                </div>
            );
        }
    }

    updateFooter(){
        return(
            <div className="modal-footer">
                <button type="button" className="btn btn-secondary" data-dismiss="modal" onClick={() => {this.setState({create:false})}}>Cancel</button>
                <button type="button" onClick={this.submitMedication} className="btn btn-primary">Update Medication</button>
            </div>
        )
    }

    creationFooter(){
        return(
            <div className="modal-footer">
                <button type="button" className="btn btn-secondary" data-dismiss="modal" onClick={() => {this.setState({create:false})}}>Cancel</button>
                <button type="button" onClick={this.submitMedication} className="btn btn-primary">Create New</button>
            </div>
        );
    }

    listFooter(){
        return(
            <div className="modal-footer">
                <button type="button" className="btn btn-secondary" data-dismiss="modal" onClick={() => {this.setState({create:false})}}>Cancel</button>
                <button type="button" onClick={() => {this.setState({create:true})}} className="btn btn-primary">Create New</button>
            </div>
        );
    }
}

export default MedicationModal;