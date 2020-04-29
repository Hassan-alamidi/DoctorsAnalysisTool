import React from 'react';
import "../resources/css/shared.scss"
import CardAlt from "../components/card-alt"
import { Redirect, withRouter } from "react-router-dom"
import $ from "jquery";

const axios = require('axios').default;

class AccountPage extends React.Component {
    constructor() {
        super();
        this.state = {
            doctor:undefined,
            loading:true,
            changePassword:false,
            error:""
        }
        
    }

    componentDidMount() {
        document.getElementById("background").src = require("../resources/images/woman-girl-silhouette-jogger-40751.jpg");

        axios('http://localhost:8080/personal-details', { method: "get", withCredentials: true})
            .then(function (response) {
                response.data.detailed = false;
                console.log(response)
                this.setState({ doctor: response.data, loading: false })
            }.bind(this)).catch(function (error) {
                this.setState({ error: "Something when wrong please contact admin"});
            }.bind(this));
    }

    render() {
        $('.modal').modal('hide');
        if (this.state.loading === true) {
            
            return(
                <div className="container">
                    <h1>Loading Please Wait....</h1>
                </div>
            )
        } else if (this.state.changePassword === true) {
            
            return(
                <Redirect push to={
                    {
                        pathname: "/info/password",
                        state: {
                            from: "/info/account"
                        }
                    }
                } />
            )
        }else { 
            return(
                <div className="container">
                    <CardAlt header={"Personal Information"}>
                        <ul className="list-group">
                            <li className="list-group-item list-group-item-success">Medical Licence: {this.state.doctor.medicalLicenceNumber}</li>
                            <li className="list-group-item list-group-item-success">Name: {this.state.doctor.firstName} {this.state.doctor.lastName}</li>
                            <li className="list-group-item list-group-item-success">Date Of Birth: {this.state.doctor.dob}</li>
                            <li className="list-group-item list-group-item-success">PPSN: {this.state.doctor.ppsn}</li>
                            <li className="list-group-item list-group-item-success">Address: {this.state.doctor.address}</li>
                            <li className="list-group-item list-group-item-success">phone Number: {this.state.doctor.phoneNumber}</li>
                        </ul>
                        <div className="card-footer">
                            <button type="button" className="btn" id="left-panel-link" onClick={() => {this.setState({changePassword:true})}}>change Password</button>
                        </div>
                    </CardAlt>
                </div>
            )
        }
    }
}

export default withRouter(AccountPage);