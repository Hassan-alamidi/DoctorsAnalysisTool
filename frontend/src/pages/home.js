import React from 'react';
import "../resources/css/shared.scss"
import PatientInfoCard from "../components/PatientInfoCard"
import auth from "../components/auth"
import {Redirect, withRouter} from "react-router-dom"
const axios = require('axios').default;

class HomePage extends React.Component {
  constructor(){
    super();
    this.state ={
        patientPPSN:"",
        patient:"",
        errorMessage:"",
        tokenExpired:false,
        managePatient:false
    }

    this.changeHandler = this.changeHandler.bind(this);
    this.requestPatient = this.requestPatient.bind(this);
    this.managingPatient = this.managingPatient.bind(this);
  }

  componentDidMount(){
    document.getElementById("background").src = require("../resources/images/abstract-art.jpg")
  }

  changeHandler(val) {
    const {name, value} = val.target;
    this.setState({ [name]: value });
  }

  managingPatient(){
    this.setState({ managePatient:true });
  }

  requestPatient(){
    this.setState({patient:"",errorMessage:""})
    if(this.state.patientPPSN === undefined || this.state.patientPPSN !== ""){
      axios('/patient', {method:"get", withCredentials: true, headers:{"ppsn":this.state.patientPPSN}})
            .then(function(response){
                console.log(response)
                response.data.detailed = false;
                this.setState({ patient:response.data})
                //this is not the most secure method but system is designed to be used by staff and api is secured aswell so risk should be very low
                sessionStorage.setItem("patient", response.data.ppsn)
            }.bind(this)).catch(function(error){
              if(error.response.status === 404){
                this.setState({errorMessage:"patient PPSN not found"})
              }else if(error.response.status === 403){
                auth.logout()
                this.setState({tokenExpired:true})
              }else{
                this.setState({errorMessage:"Something has gone wrong please try again"})
              }
            }.bind(this));
    }else{
      this.setState({errorMessage:"You must enter Patients PPSN to search"})
    }
  }

  render(){
    if(this.state.tokenExpired){
      return (
        <Redirect to={
            {
                pathname:"/",
                state:{
                  from:"/hub/home"
                }
            }
        } />
      );
    }else if(this.state.managePatient === true){
      return(
        //redirect here
        <Redirect push to={
          {
              pathname:"/hub/patient",
              state:{
                from:"/hub/home"
              }
          }
      } />
      )
    } else{
      const header = "Patient Found: " + this.state.patient.firstName + " " + this.state.patient.lastName;
      return( 
        <div className="customForm container">    
            <h1 className="heading smallPadding">Medical Hub</h1>
            <input className="form-control form-control-lg" type="text" placeholder="Please enter Patient PPSN" name="patientPPSN" onChange={this.changeHandler} />
            <button onClick={this.requestPatient} type="submit" className="btn btn-primary">Search</button>
            {!(this.state.patient === 'undefined' || this.state.patient === "") && 
                <PatientInfoCard header={header} patient={this.state.patient} detailed={true} callback={this.managingPatient} />}
        </div>
      )
    }
  }
}

export default withRouter(HomePage);