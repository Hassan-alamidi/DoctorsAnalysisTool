import React from 'react';
import "../resources/css/shared.scss"
import PatientInfoCard from "../components/PatientInfoCard"
import auth from "../components/auth"
import {Redirect} from "react-router-dom"
const axios = require('axios').default;

class HomePage extends React.Component {
  constructor(){
    super();
    document.getElementsByTagName("body")[0].classList.add("homePage");
    this.state ={
        patientPPSN:"",
        patient:"",
        errorMessage:"",
        tokenExpired:false
    }

    this.changeHandler = this.changeHandler.bind(this);
    this.requestPatient = this.requestPatient.bind(this);
  }

  changeHandler(val) {
    const {name, value} = val.target;
    this.setState({ [name]: value });
  }

  requestPatient(){
    this.setState({patient:"",errorMessage:""})
    if(this.state.patientPPSN !== ""){
      axios('http://localhost:8080/patient', {method:"get", withCredentials: true, headers:{"ppsn":this.state.patientPPSN}})
            .then(function(response){
                console.log(response)
                response.data.detailed = false;
                this.setState({ patient:response.data})
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
      document.getElementsByTagName("body")[0].classList.remove("homePage");
      return (
        <Redirect to={
            {
                pathname:"/",
                state:{
                  from:"/home"
                }
            }
        } />
      );
    }else if(this.state.patient === 'undefined' || this.state.patient === ""){
      return (
        <div className="customForm container">
          <h1 className="heading smallPadding">Medical Hub</h1>
          <h4 className="errorMessage smallPadding">{this.state.errorMessage}</h4>
          <input className="form-control form-control-lg" type="text" placeholder="Please enter Patient PPSN" name="patientPPSN" onChange={this.changeHandler} />
          <button onClick={this.requestPatient} type="submit" className="btn btn-primary">Search</button>
        </div>
      );
    }else{
      return(
        <div className="customForm container">
          <h1 className="heading smallPadding">Medical Hub</h1>
          <input className="form-control form-control-lg" type="text" placeholder="Please enter Patient PPSN" name="patientPPSN" onChange={this.changeHandler} />
          <button onClick={this.requestPatient} type="submit" className="btn btn-primary">Search</button>
          <PatientInfoCard {...this.state.patient}/>
        </div>
        )
    }
  }
}

export default HomePage;