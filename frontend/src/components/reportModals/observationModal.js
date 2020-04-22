import React from 'react';
import "../../resources/css/shared.scss"
import "bootstrap";
import { Redirect} from "react-router-dom"
import $ from "jquery";

const axios = require('axios').default;

class ObservationModal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            error:"",
            observation:{
                //Observations were originally meant to be searchable via autosuggestion, which would then add a Observations assigned code.
                //but that is low priority on my list of things todo and it is unlikly I will have time to get this completed so setting code to ignore
                code:"ignored"
            },
            close:false
        }

        this.submitObservation = this.submitObservation.bind(this);
        this.returnToPatientPage = this.returnToPatientPage.bind(this);
        this.typeChangeHandler = this.typeChangeHandler.bind(this);
        this.dateTakenChangeHandler = this.dateTakenChangeHandler.bind(this);
        this.resultValueChangeHandler = this.resultValueChangeHandler.bind(this);
        this.unitChangeHandler = this.unitChangeHandler.bind(this);
        this.updateFooter = this.updateFooter.bind(this);
        this.creationFooter = this.creationFooter.bind(this);
        this.valueEntered = this.valueEntered.bind(this);
    }

    valueEntered(val){
        return (val !== undefined && val.trim() !== "")
    }

    submitObservation(){
        if(this.state.observation !== undefined && this.valueEntered(this.state.observation.type) && this.valueEntered(this.state.observation.dateTaken)
            && this.valueEntered(this.state.observation.resultValue) && this.valueEntered(this.state.observation.unit)){
            let requestData = this.state.observation;
            requestData.patient = this.props.patient;
            requestData.encounter = this.props.currentEncounter;
            axios('http://localhost:8080/observation', { 
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
            this.setState({error:"You must enter all details of observation"})
        }
    }

    returnToPatientPage(){
        this.setState({close:true});
    }

    typeChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.observation;
        backup.type = value
        this.setState({ observation: backup });
    }

    dateTakenChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.observation;
        backup.dateTaken = value
        this.setState({ observation: backup });
    }

    resultValueChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.observation;
        backup.resultValue = value
        this.setState({ observation: backup });
    }

    unitChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.observation;
        backup.unit = value
        this.setState({ observation: backup });
    }

    render(){
        if(this.state.close){
            $('#createobservationModal').modal('hide');
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
                <div className="modal fade" id="createobservationModal" tabIndex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                    <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="exampleModalLongTitle">Please Enter Observation Details</h5>
                            </div>
                            <div className="modal-body">
                                <p className="text-danger">{this.state.error}</p>
                                <p>* Type Of Observation </p>
                                <input className="form-control form-control-lg" id="typeInput" type="text" placeholder="Name" name="type" onChange={this.typeChangeHandler} />
                                <p>* Date Observation Was Taken</p>
                                <input className="form-control form-control-lg" id="dateTakenInput" type="date" placeholder="yyyy-mm-dd" name="dateTaken" onChange={this.dateTakenChangeHandler} />
                                <p>* Result</p>
                                <input className="form-control form-control-lg" id="resultValueInput" type="text" placeholder="Result, i.e. 90" name="resultValueDescription" onChange={this.resultValueChangeHandler} />
                                <p>* Unit</p>
                                <input className="form-control form-control-lg" id="unitInput" type="text" placeholder="Unit, i.e. BPM" name="unit" onChange={this.unitChangeHandler} />
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
                <button type="button" onClick={this.submitObservation} className="btn btn-primary">Update Observation</button>
            </div>
        )
    }

    creationFooter(){
        return(
            <div className="modal-footer">
                <button type="button" className="btn btn-secondary" data-dismiss="modal">Cancel</button>
                <button type="button" onClick={this.submitObservation} className="btn btn-primary">Create New</button>
            </div>
        );
    }
}

export default ObservationModal;