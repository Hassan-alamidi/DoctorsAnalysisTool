import React from 'react';
import "../../resources/css/shared.scss"
import "bootstrap";
import { Redirect} from "react-router-dom"
import $ from "jquery";

const axios = require('axios').default;

class ConditionModal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            error:"",
            condition:{
                //condition were originally meant to be searchable via autosuggestion, which would then add a condition assigned code.
                //but that is low priority on my list of things todo and it is unlikly I will have time to get this completed so setting code to ignore
                code:"ignored"
            },
            close:false
        }

        this.submitCondition = this.submitCondition.bind(this);
        this.returnToPatientPage = this.returnToPatientPage.bind(this);
        this.detailsChangeHandler = this.detailsChangeHandler.bind(this);
        this.nameChangeHandler = this.nameChangeHandler.bind(this);
        this.symptomsChangeHandler = this.symptomsChangeHandler.bind(this);
        this.discoveredChangeHandler = this.discoveredChangeHandler.bind(this);
        this.updateFooter = this.updateFooter.bind(this);
        this.creationFooter = this.creationFooter.bind(this);
    }

    submitCondition(){
        if(this.state.condition !== undefined && this.state.condition.name !== undefined && this.state.condition.name.trim() !== "" 
            && this.state.condition.discovered !== undefined && this.state.condition.discovered.trim() !== ""){
            let requestData = this.state.condition;
            requestData.patient = this.props.patient;
            requestData.encounter = this.props.currentEncounter;
            axios('http://localhost:8080/conditions', { 
                    data:requestData,
                        method: this.props.requestType, 
                        withCredentials: true})
                .then(function (response) {
                    const encounterId = response.data.encounter.id;
                    
                    axios('http://localhost:8080/encounter/' + encounterId, { 
                            method: "get", 
                            withCredentials: true})
                        .then(function (response) {
                            console.log(response.data)
                            this.props.callback(response.data)
                        }.bind(this))

                }.bind(this));
        }else{
            this.setState({error:"You must enter the name and date of the condition"})
        }
    }

    returnToPatientPage(){
        this.setState({close:true});
    }

    detailsChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.condition;
        backup.details = value
        this.setState({ condition: backup });
    }

    nameChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.condition;
        backup.name = value
        this.setState({ condition: backup });
    }

    symptomsChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.condition;
        backup.symptoms = value
        this.setState({ condition: backup });
    }

    discoveredChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.condition;
        backup.discovered = value
        this.setState({ condition: backup });
    }

    render(){
        if(this.state.close){
            $('#createconditionModal').modal('hide');
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
        }else{
            return(
                <div className="modal fade" id="createconditionModal" tabIndex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                    <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="exampleModalLongTitle">Please Enter Condition Details</h5>
                            </div>
                            <div className="modal-body">
                                <p className="text-danger">{this.state.error}</p>
                                <p>* Name Of Condition </p>
                                <input className="form-control form-control-lg" id="conditionInput" type="text" placeholder="Name" name="name" onChange={this.nameChangeHandler} />
                                <p>* Date This Condition was discovered</p>
                                <input className="form-control form-control-lg" id="discoveredInput" type="date" placeholder="yyyy-mm-dd" name="discovered" onChange={this.discoveredChangeHandler} />
                                <p>Symptoms Alongside This Condition</p>
                                <input className="form-control form-control-lg" id="symptomsInput" type="text" placeholder="Symptoms" name="symptoms" onChange={this.symptomsChangeHandler} />
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
                <button type="button" className="btn btn-secondary" data-dismiss="modal" >Cancel</button>
                <button type="button" onClick={this.submitCondition} className="btn btn-primary">Update condition</button>
            </div>
        )
    }

    creationFooter(){
        return(
            <div className="modal-footer">
                <button type="button" className="btn btn-secondary" data-dismiss="modal">Cancel</button>
                <button type="button" onClick={this.submitCondition} className="btn btn-primary">Create New</button>
            </div>
        );
    }
}

export default ConditionModal;