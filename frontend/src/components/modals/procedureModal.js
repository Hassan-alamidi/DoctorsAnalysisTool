import React from 'react';
import "../../resources/css/shared.scss"
import "bootstrap";
import {ProceduresListNoBody} from '../reportLists/procedureList'
import { Redirect} from "react-router-dom"
import $ from "jquery";

const axios = require('axios').default;

class ProcedureModal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            error:"",
            create:false,
            procedure:{
                //procedures were originally meant to be searchable via autosuggestion, which would then add a procedures assigned code.
                //but that is low priority on my list of things todo and it is unlikly I will have time to get this completed so setting code to ignore
                code:"ignored",
                reasonCode:"ignored"
            },
            close:false
        }

        this.submitProcedure = this.submitProcedure.bind(this);
        this.returnToPatientPage = this.returnToPatientPage.bind(this);
        this.detailsChangeHandler = this.detailsChangeHandler.bind(this);
        this.descriptionChangeHandler = this.descriptionChangeHandler.bind(this);
        this.reasonChangeHandler = this.reasonChangeHandler.bind(this);
        this.dateChangeHandler = this.dateChangeHandler.bind(this);
        this.updateFooter = this.updateFooter.bind(this);
        this.creationFooter = this.creationFooter.bind(this);
        this.resetObjectState = this.resetObjectState.bind(this);
        this.deleteProcedure = this.deleteProcedure.bind(this);
        this.listFooter = this.listFooter.bind(this);
    }

    resetObjectState(){
        this.setState({
            procedure:{
                code:"ignored",
                reasonCode:"ignored"
            }  
        });
    }

    submitProcedure(){
        if(this.state.procedure !== undefined && this.state.procedure.description !== undefined && this.state.procedure.description.trim() !== "" 
            && this.state.procedure.carriedOutOn !== undefined && this.state.procedure.carriedOutOn.trim() !== ""){
            let requestData = this.state.procedure;
            requestData.patient = this.props.patient;
            requestData.encounter = this.props.currentEncounter;
            axios('http://localhost:8080/procedure', { 
                    data:requestData,
                        method: this.props.requestType, 
                        withCredentials: true})
                .then(function (response) {
                    this.resetObjectState();
                    const encounterId = response.data.encounter.id;
                    
                    axios('http://localhost:8080/encounter/' + encounterId, { 
                            method: "get", 
                            withCredentials: true})
                        .then(function (response) {
                            console.log(response.data)
                            this.props.callback(response.data);
                        }.bind(this))

                }.bind(this));
        }else{
            this.setState({error:"You must enter the name and date of procedure"})
        }
    }

    deleteProcedure(id){
        axios('http://localhost:8080/procedure/'+id, { 
                method: 'delete', 
                withCredentials: true})
        .then(function (response) {
            this.resetObjectState();
            const encounterId = this.props.currentEncounter.id;
            
            axios('http://localhost:8080/encounter/' + encounterId, { 
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

    detailsChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.procedure;
        backup.details = value
        this.setState({ procedure: backup });
    }

    descriptionChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.procedure;
        backup.description = value
        this.setState({ procedure: backup });
    }

    reasonChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.procedure;
        backup.reasonDescription = value
        this.setState({ procedure: backup });
    }

    dateChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.procedure;
        backup.carriedOutOn = value
        this.setState({ procedure: backup });
    }

    render(){
        if(this.state.close){
            $('#createProcedureModal').modal('hide');
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
        }else if(this.props.currentEncounter && this.props.currentEncounter.procedures.length > 0 && !this.state.create){
            return(
                <div className="modal fade" id="createProcedureModal" tabIndex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                        <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                            <div className="modal-content">
                                <div className="modal-header">
                                    <h5 className="modal-title" id="exampleModalLongTitle">Current Documented Procedures</h5>
                                </div>
                                <div className="modal-body">
                                    <ProceduresListNoBody callback={this.deleteProcedure} procedures={this.props.currentEncounter.procedures} />
                                </div>
                                <this.listFooter />
                        </div>
                    </div>
                </div>
            )
        }else{
            return(
                <div className="modal fade" id="createProcedureModal" tabIndex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                    <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="exampleModalLongTitle">Please Enter Procedure Details</h5>
                            </div>
                            <div className="modal-body">
                                <p className="text-danger">{this.state.error}</p>
                                <p>* Name Of Procedure </p>
                                <input className="form-control form-control-lg" id="descriptionInput" type="text" placeholder="Name" name="description" onChange={this.descriptionChangeHandler} />
                                <p>* Date Carried Out On</p>
                                <input className="form-control form-control-lg" id="dateInput" type="date" placeholder="yyyy-mm-dd" name="carriedOutOn" onChange={this.dateChangeHandler} />
                                <p>Reason For Procedure, i.e. name of condition</p>
                                <input className="form-control form-control-lg" id="reasonInput" type="text" placeholder="Reason" name="reasonDescription" onChange={this.reasonChangeHandler} />
                                <p>Extra Details</p>
                                <input className="form-control form-control-lg" id="detailsInput" type="text" placeholder="Details" name="details" onChange={this.detailsChangeHandler} />
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
                <button type="button" onClick={this.submitProcedure} className="btn btn-primary">Update Procedure</button>
            </div>
        )
    }

    creationFooter(){
        return(
            <div className="modal-footer">
                <button type="button" className="btn btn-secondary" data-dismiss="modal" onClick={() => {this.setState({create:false})}}>Cancel</button>
                <button type="button" onClick={this.submitProcedure} className="btn btn-primary">Create New</button>
            </div>
        );
    }

    listFooter(){
        return(
            <div className="modal-footer">
                <button type="button" className="btn btn-secondary" data-dismiss="modal">Cancel</button>
                <button type="button" onClick={() => {this.setState({create:true})}} className="btn btn-primary">Create New</button>
            </div>
        );
    }
}

export default ProcedureModal;