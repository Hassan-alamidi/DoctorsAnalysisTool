import React from 'react';
import "../../resources/css/shared.scss"
import "bootstrap";
import { Redirect} from "react-router-dom"
import $ from "jquery";

const axios = require('axios').default;

class EncounterConfirmModal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            error:"",
            encounter: {
                description:"",
                type:"",
                reasonDescription:""
            },
            close:false
        }
        this.submitEncounter = this.submitEncounter.bind(this);
        this.returnToPatientPage = this.returnToPatientPage.bind(this);
        this.typeChangeHandler = this.typeChangeHandler.bind(this);
        this.descriptionChangeHandler = this.descriptionChangeHandler.bind(this);
        this.reasonChangeHandler = this.reasonChangeHandler.bind(this);
        this.updateFooter = this.updateFooter.bind(this);
        this.creationFooter = this.creationFooter.bind(this);
        this.createRequestData = this.createRequestData.bind(this);
    }

    componentDidMount() {
        if(this.props.currentEncounter !== undefined){
            //if(this.state.encounter === undefined){
                this.setState({encounter:this.props.currentEncounter});
            //}
            document.getElementById("typeInput").value=this.props.currentEncounter.type;
            document.getElementById("descriptionInput").value=this.props.currentEncounter.description;
            if((this.props.currentEncounter.reasonDescription) && this.props.currentEncounter.reasonDescription.trim() !== ""){
                document.getElementById("reasonInput").value=this.props.currentEncounter.reasonDescription;
            }
        }else if(this.props.requestType === "post"){
            $('#createEncounterModal').modal('show');
            //$('#LoadingModal').modal('hide');
        }
    }

    submitEncounter(){
        if(this.state.encounter !== undefined && this.state.encounter.description !== undefined && this.state.encounter.type !== undefined 
            && this.state.encounter.description.trim() !== "" && this.state.encounter.type.trim() !== ""){
            const request = this.createRequestData();
            axios('/encounter', { 
                data:request,
                    method: this.props.requestType, 
                    withCredentials: true})
                .then(function (response) {
                    console.log(response)
                    $('#createEncounterModal').modal('show');
                    this.setState({ encounter: response.data, loading: false })
                    this.props.callback(response.data)
                }.bind(this));
        }else{
            this.setState({error:"You must enter the type and description of encounter"})
        }
    }

    createRequestData(){
        let request = this.props.currentEncounter === undefined ? this.state.encounter : this.props.currentEncounter;
        request.type = this.state.encounter.type;
        request.description = this.state.encounter.description;
        request.reasonDescription = this.state.encounter.reasonDescription;
        request.patient = this.props.patient;
        return request;
    }

    returnToPatientPage(){
        this.setState({close:true});
    }

    typeChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.encounter;
        backup.type = value
        this.setState({ encounter: backup });
    }

    descriptionChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.encounter;
        backup.description = value
        this.setState({ encounter: backup });
    }

    reasonChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.encounter;
        backup.reasonDescription = value
        this.setState({ encounter: backup });
    }

    render(){
        if(this.state.close){
            $('.modal').modal('hide');
            $('body').removeClass('modal-open');
            $('.modal-backdrop').remove();
            return (
                <Redirect push to={
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
                <div className="modal fade" id="createEncounterModal" tabIndex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" data-backdrop="static" data-keyboard="false">
                    <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="exampleModalLongTitle">Please Enter Encounter Details</h5>
                            </div>
                            <div className="modal-body">
                                <p className="text-danger">{this.state.error}</p>
                                <p>* Encounter Type, e.g. wellness, ambulatory </p>
                                <input className="form-control form-control-lg" id="typeInput" type="text" placeholder="*Encounter Type" name="type" onChange={this.typeChangeHandler} />
                                <p>* Description, e.g. General Examination</p>
                                <input className="form-control form-control-lg" id="descriptionInput" type="text" placeholder="*Description" name="description" onChange={this.descriptionChangeHandler} />
                                <p>Reason for Visit, e.g. Pregnancy</p>
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
                <button type="button" className="btn btn-secondary" data-dismiss="modal">Cancel</button>
                <button type="button" onClick={this.submitEncounter} data-dismiss="modal" className="btn btn-primary">Update Encounter</button>
            </div>
        )
    }

    creationFooter(){
        return(
            <div className="modal-footer">
                <button type="button" className="btn btn-secondary" data-dismiss="modal" onClick={this.returnToPatientPage}>Return To Patient Menu</button>
                <button type="button" onClick={this.submitEncounter} data-dismiss="modal" className="btn btn-primary">Create New</button>
            </div>
        );
    }
}

export default EncounterConfirmModal;