import React from 'react';
import "../resources/css/shared.scss"
import PatientInfoCard from "../components/PatientInfoCard"
const axios = require('axios').default;

class PatientOverviewPage extends React.Component {
  constructor(){
    super();
    this.state ={
        patientPPSN:"",
        patient:"",
        errorMessage:""
    }
  }

  render(){
    return (
      <div className="container">
        
      </div>
    );    
  }
}

export default PatientOverviewPage;