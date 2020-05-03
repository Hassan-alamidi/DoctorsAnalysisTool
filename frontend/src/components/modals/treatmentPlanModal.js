import React from 'react';
import "../../resources/css/shared.scss"
import {TreatmentsListNoBody} from '../reportLists/treatmentList'
import "bootstrap";
import $ from "jquery";

const axios = require('axios').default;

class TreatmentPlanModal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            error:"",
            create:false,
            treatmentPlan:{
                //treatmentPlans were originally meant to be searchable via autosuggestion, which would then add a treatmentPlans assigned code.
                //but that is low priority on my list of things todo and it is unlikly I will have time to get this completed so setting code to ignore
                code:"ignored",
                reasonDescription:"",
                reasonCode:"ignored"
            }
        }

        this.submitTreatmentPlan = this.submitTreatmentPlan.bind(this);
        this.descriptionChangeHandler = this.descriptionChangeHandler.bind(this);
        this.reasonChangeHandler = this.reasonChangeHandler.bind(this);
        this.startDateChangeHandler = this.startDateChangeHandler.bind(this);
        this.endDateChangeHandler = this.endDateChangeHandler.bind(this);
        this.updateFooter = this.updateFooter.bind(this);
        this.creationFooter = this.creationFooter.bind(this);
        this.resetObjectState = this.resetObjectState.bind(this);
        this.deleteTreatment = this.deleteTreatment.bind(this);
        this.listFooter = this.listFooter.bind(this);
    }

    resetObjectState(){
        this.setState({
            treatmentPlan:{
                code:"ignored",
                reasonDescription:"",
                reasonCode:"ignored"
            }
        });
    }

    submitTreatmentPlan(){
        if(this.state.treatmentPlan !== undefined && this.state.treatmentPlan.description !== undefined && this.state.treatmentPlan.description.trim() !== "" 
            && this.state.treatmentPlan.startDate !== undefined && this.state.treatmentPlan.startDate.trim() !== ""){
            let requestData = this.state.treatmentPlan;
            requestData.patient = this.props.patient;
            requestData.encounter = this.props.currentEncounter;
            axios('http://localhost:8080/treatment', { 
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
                            this.props.callback(response.data)
                        }.bind(this))

                }.bind(this));
        }else{
            this.setState({error:"You must enter the name and date of treatmentPlan"})
        }
    }

    deleteTreatment(id){
        axios('http://localhost:8080/treatment/'+id, { 
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

    descriptionChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.treatmentPlan;
        backup.description = value
        this.setState({ treatmentPlan: backup });
    }

    reasonChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.treatmentPlan;
        backup.reasonDescription = value
        this.setState({ treatmentPlan: backup });
    }

    startDateChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.treatmentPlan;
        backup.startDate = value
        this.setState({ treatmentPlan: backup });
    }

    endDateChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.treatmentPlan;
        backup.endDate = value
        this.setState({ treatmentPlan: backup });
    }

    render(){
        if(this.props.currentEncounter && this.props.currentEncounter.treatments.length > 0 && !this.state.create){
            return(
                <div className="modal fade" id="createTreatmentPlanModal" tabIndex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                        <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                            <div className="modal-content">
                                <div className="modal-header">
                                    <h5 className="modal-title" id="exampleModalLongTitle">Current Documented Treatments</h5>
                                </div>
                                <div className="modal-body">
                                    <TreatmentsListNoBody callback={this.deleteTreatment} treatments={this.props.currentEncounter.treatments} />
                                </div>
                                <this.listFooter />
                        </div>
                    </div>
                </div>
            )
        }else{
            return(
                <div className="modal fade" id="createTreatmentPlanModal" tabIndex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                    <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="exampleModalLongTitle">Please Enter Treatment Plan Details</h5>
                            </div>
                            <div className="modal-body">
                                <p className="text-danger">{this.state.error}</p>
                                <p>* description Of Treatment Plan </p>
                                <input className="form-control form-control-lg" id="descriptionInput" type="text" placeholder="Description" name="description" onChange={this.descriptionChangeHandler} />
                                <p>* Date Treatment Starts on</p>
                                <input className="form-control form-control-lg" id="startDateInput" type="date" placeholder="yyyy-mm-dd" name="startDate" onChange={this.startDateChangeHandler} />
                                <p>Date Treatment Ends</p>
                                <input className="form-control form-control-lg" id="endDateInput" type="date" placeholder="yyyy-mm-dd" name="endDate" onChange={this.endDateChangeHandler} />
                                <p>Reason For Treatment, i.e. name of condition</p>
                                <input className="form-control form-control-lg" id="reasonInput" type="text" placeholder="Reason" name="reasonDescription" onChange={this.reasonChangeHandler} />
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
                <button type="button" className="btn btn-secondary" data-dismiss="modal"  onClick={() => {this.setState({create:false})}}>Cancel</button>
                <button type="button" onClick={this.submitTreatmentPlan} className="btn btn-primary">Update Treatment Plan</button>
            </div>
        )
    }

    creationFooter(){
        return(
            <div className="modal-footer">
                <button type="button" className="btn btn-secondary" data-dismiss="modal"  onClick={() => {this.setState({create:false})}}>Cancel</button>
                <button type="button" onClick={this.submitTreatmentPlan} className="btn btn-primary">Create New</button>
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

export default TreatmentPlanModal;