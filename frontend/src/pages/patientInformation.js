import React from 'react';
import "../resources/css/shared.scss"
import CardAlt from "../components/card-alt"
import { Redirect, withRouter } from "react-router-dom"
import auth from '../components/auth'
import $ from "jquery";

const axios = require('axios').default;

class PatientInformationPage extends React.Component {
    constructor() {
        super();
        this.state = {
            patient:{
                firstName:"",
                lastName:"",
                dod:"",
                address:""
            },
            loading:false,
            error:"",
            success:"",
            endpoint: window.location.pathname
        }

        this.updatePatientInformation = this.updatePatientInformation.bind(this);
        this.firstNameChangeHandler = this.firstNameChangeHandler.bind(this);
        this.lastNameChangeHandler = this.lastNameChangeHandler.bind(this);
        this.DODChangeHandler = this.DODChangeHandler.bind(this);
        this.addressChangeHandler = this.addressChangeHandler.bind(this);
    }

    componentDidMount(){
        document.getElementById("background").src = require("../resources/images/woman-girl-silhouette-jogger-40751.jpg");
        const patientPPSN = sessionStorage.getItem("patient");
        
        if (patientPPSN === undefined || patientPPSN === "") {
            this.setState({ patientNotFound: true, loading: false });
            return;
        }

        axios('/patient', { method: "get", withCredentials: true, headers: { "ppsn": patientPPSN } })
            .then(function (response) {
                console.log(response)
                this.setState({ patient: response.data, loading: false })
            }.bind(this)).catch(function (error) {
                console.log(error)
                if (error.response.status === 403) {
                    auth.logout();
                    this.setState({ tokenExpired: true, loading: false });
                } else {
                    this.setState({ patientNotFound: true, loading: false });
                }
            }.bind(this));
    }

    updatePatientInformation() {
        if(this.state.patient !== undefined && Object.keys(this.state.patient).length > 1){
            this.setState({error:"", success:"" })
            axios('/patient', { data:this.state.patient, method: "put", withCredentials: true})
                .then(function (response) {
                    console.log(response)
                    this.setState({ doctor: response.data, loading: false,  success: "Patient Successfully Updated" })
                }.bind(this)).catch(function (error) {
                    console.log(error);
                    this.setState({ loading: false, error: "Failed to update patient"});
                }.bind(this));
        }else{
            this.setState({error:"Please enter patient Information before attempting to update"})
        }
    }

    firstNameChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.patient;
        backup.firstName = value
        this.setState({ patient: backup });
    }

    lastNameChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.patient;
        backup.lastName = value
        this.setState({ patient: backup });
    }

    DODChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.patient;
        backup.dod = value
        this.setState({ patient: backup });
    }

    addressChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.patient;
        backup.address = value
        this.setState({ patient: backup });
    }

    render() {
        $('.modal').modal('hide');
        if (this.state.loading === true) {
            return(
                <div className="container">
                    <h1>Loading Please Wait....</h1>
                </div>
            )
        } else { 
            return(
                <div className="container">
                    <CardAlt header={"Doctor Registration"}>
                        {(this.state.error) && <p className="text-danger">{this.state.error}</p>}
                        {(this.state.success) && <p className="text-success">{this.state.success}</p>}
                        <input className="form-control form-control-md" id="firstNameInput" type="text" placeholder="First Name" value={this.state.patient.firstName} onChange={this.firstNameChangeHandler} />
                        <input className="form-control form-control-md" id="lastNameInput" type="text" placeholder="Last Name" value={this.state.patient.lastName} onChange={this.lastNameChangeHandler} />
                        <input className="form-control form-control-md" id="addressInput" type="text" placeholder="address" value={this.state.patient.address} onChange={this.addressChangeHandler} />
                        <p className="form-control-md">Date Of Death, if applicable</p>
                        <input className="form-control form-control-md" id="DODInput" type="date" placeholder="yyyy-mm-dd" onChange={this.DODChangeHandler} />

                        <button type="button" className="btn space-please" id="left-panel-link" onClick={() => {this.updatePatientInformation()}}>Update Patient Information</button>
                    </CardAlt>
                </div>
            )
        }
    }
}

export default withRouter(PatientInformationPage);