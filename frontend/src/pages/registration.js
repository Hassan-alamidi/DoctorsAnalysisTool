import React from 'react';
import "../resources/css/shared.scss"
import CardAlt from "../components/card-alt"
import { Redirect, withRouter } from "react-router-dom"
import $ from "jquery";

const axios = require('axios').default;

class RegistrationPage extends React.Component {
    constructor() {
        super();
        this.state = {
            doctor:{
                phoneNumber:0
            },
            loading:false,
            error:"",
            success:"",
            endpoint: window.location.pathname
        }

        this.registerAccount = this.registerAccount.bind(this);
        this.medicalLicenceNumberChangeHandler = this.medicalLicenceNumberChangeHandler.bind(this);
        this.firstNameChangeHandler = this.firstNameChangeHandler.bind(this);
        this.lastNameChangeHandler = this.lastNameChangeHandler.bind(this);
        this.DOBChangeHandler = this.DOBChangeHandler.bind(this);
        this.PPSNChangeHandler = this.PPSNChangeHandler.bind(this);
        this.privilegeLevelChangeHandler = this.privilegeLevelChangeHandler.bind(this);
        this.addressChangeHandler = this.addressChangeHandler.bind(this);
        this.phoneNumberChangeHandler = this.phoneNumberChangeHandler.bind(this);
        this.passwordChangeHandler = this.passwordChangeHandler.bind(this);
    }

    componentDidMount(){
        document.getElementById("background").src = require("../resources/images/woman-girl-silhouette-jogger-40751.jpg");
    }

    registerAccount() {
        if(this.state.doctor !== undefined && Object.keys(this.state.doctor).length > 1){
            this.setState({loading: true, error:"", success:"" })
            const privilegeLevel = this.state.doctor.privilegeLevel === undefined || 
                                    this.state.doctor.privilegeLevel === "Please Select Privilege Level" ? "User" : this.state.doctor.privilegeLevel;
            let doctor = this.state.doctor;
            doctor.privilegeLevel = privilegeLevel;
            axios(this.state.endpoint, { data:doctor, method: "post", withCredentials: true})
                .then(function (response) {
                    console.log(response)
                    this.setState({ doctor: response.data, loading: false,  success: "Doctor Successfully Registered" })
                }.bind(this)).catch(function (error) {
                    console.log(error);
                    if(error.response.status !== undefined && error.response.status === 401){
                        this.setState({ loading: false, error: "You are not authorized to register yourself as an admin"});
                    }else{
                        this.setState({ loading: false, error: "Something when wrong please contact admin"});
                    }
                }.bind(this));
        }else{
            this.setState({error:"Please enter the doctors details before submitting"})
        }
    }

    medicalLicenceNumberChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.doctor;
        backup.medicalLicenceNumber = value
        this.setState({ doctor: backup });
    }

    firstNameChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.doctor;
        backup.firstName = value
        this.setState({ doctor: backup });
    }

    lastNameChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.doctor;
        backup.lastName = value
        this.setState({ doctor: backup });
    }

    DOBChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.doctor;
        backup.dob = value
        this.setState({ doctor: backup });
    }

    PPSNChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.doctor;
        backup.ppsn = value
        this.setState({ doctor: backup });
    }

    privilegeLevelChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.doctor;
        backup.privilegeLevel = value
        this.setState({ doctor: backup });
    }

    addressChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.doctor;
        backup.address = value
        this.setState({ doctor: backup });
    }

    phoneNumberChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.doctor;
        backup.phoneNumber = value.replace(/\D/g, "")
        this.setState({ doctor: backup });
    }

    passwordChangeHandler(val){
        const value = val.target.value;
        let backup = this.state.doctor;
        backup.password = value
        this.setState({ doctor: backup });
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
                        <input className="form-control form-control-md" id="licenceNumberInput" type="text" placeholder="Medical Licence Number" onChange={this.medicalLicenceNumberChangeHandler} />
                        <input className="form-control form-control-md" id="firstNameInput" type="text" placeholder="First Name" onChange={this.firstNameChangeHandler} />
                        <input className="form-control form-control-md" id="lastNameInput" type="text" placeholder="Last Name" onChange={this.lastNameChangeHandler} />
                        <p style={{marginLeft:"10%"}}>Date Of Birth</p>
                        <input className="form-control form-control-md" id="DOBInput" type="date" placeholder="yyyy-mm-dd" onChange={this.DOBChangeHandler} />
                        <input className="form-control form-control-md" id="ppsnInput" type="text" placeholder="PPSN" onChange={this.PPSNChangeHandler} />
                        <input className="form-control form-control-md" id="addressInput" type="text" placeholder="Address" onChange={this.addressChangeHandler} />
                        <input className="form-control form-control-md" id="phoneNumberInput" type="text" placeholder="Phone Number" value={this.state.doctor.phoneNumber} onChange={this.phoneNumberChangeHandler} />
                        {(this.state.endpoint === "/register/admin" && 
                            <input className="form-control form-control-md" id="passwordInput" type="text" placeholder="password" onChange={this.passwordChangeHandler}/>)
                        }
                        {(this.state.endpoint === "/register" && 
                            <select className="form-control form-control-md" id="privilegeInput" onChange={this.privilegeLevelChangeHandler}>
                                <option>Please Select Privilege Level</option>
                                <option>Standard</option>
                                <option>Admin</option>
                            </select>)
                        }

                        <button style={{marginLeft:"5%"}} type="button" className="btn space-please" id="left-panel-link" onClick={() => {this.registerAccount()}}>Register</button>
                    </CardAlt>
                </div>
            )
        }
    }
}

export default withRouter(RegistrationPage);