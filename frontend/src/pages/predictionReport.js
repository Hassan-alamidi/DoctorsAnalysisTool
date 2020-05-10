import React from 'react';
import { Redirect, withRouter } from 'react-router-dom'
import CardAlt from '../components/card-alt'
import '../resources/css/login.scss';
import auth from "../components/auth"

const axios = require('axios').default;

class PredictionPage extends React.Component {
    constructor() {
        super();

        this.state= {
            loading:true,
            predictionReport:"",
            toPatientPanel:false,
            toLogin:false
        }
    }

    componentDidMount(){
        document.getElementById("background").src = require("../resources/images/loginPageBackground.jpg")
        
        //set state not used as it triggers page reload and redirects back to home
        const patientPPSN = sessionStorage.getItem("patient");
        if (patientPPSN === undefined || patientPPSN === "") {
            this.setState({ redirect: true, loading: false });
            return;
        }

        axios('/prediction', { method: "post", withCredentials: true, headers: { "ppsn": patientPPSN } })
            .then(function (response) {
                console.log(response)
                this.setState({ predictionReport: response.data, loading: false })
            }.bind(this)).catch(function (error) {
                console.log(error)
                if (error.response.status === 403) {
                    console.log("Token expired");
                    auth.logout();
                    this.setState({ toLogin: true});
                } else {
                    console.log("Something went wrong")
                    this.setState({ toPatientPanel: true});
                }
            }.bind(this));
    }


    render() {
        if(this.state.toPatientPanel){
            return(
                <Redirect push to={
                    {
                        pathname: "/hub/Patient",
                        state: {
                            from: "/hub/patient/prediction"
                        }
                    }
                }/>
            );
        }else if(this.state.toLogin){
            return(
                <Redirect to={
                    {
                        pathname: "/",
                        state: {
                            from: "/hub/patient/prediction"
                        }
                    }
                }/>
            );
        }else if(this.state.loading){
            return (
                <CardAlt>
                    <h1>Loading Please Wait...</h1>
                </CardAlt>
                );
        }else{
            return (
                <div className="container">
                    <CardAlt>
                        <h1>Generated Prediction</h1>
                        <p>Analysis of this patients most recent encounter has shown that:<br/>
                            {this.state.predictionReport.result}<br/>
                        </p>
                        <p>The primary values this result is based on are:</p><br/>
                        {this.state.predictionReport.basedOn.split(',').map((value,i) => {
                            return(
                                <div key={"observation"+i}>
                                    <p>{value}</p><br/>
                                </div>
                            );
                        })}
                        <p>The System Is {this.state.predictionReport.confidence} confident that this prediction is accurate</p><br />
                        <button type="button" className="btn space-please" id="left-panel-link" onClick={() => {this.setState({toPatientPanel:true})}}>Return</button>
                    </CardAlt>
                </div>
                );
        }
    }
}

export default withRouter(PredictionPage);
