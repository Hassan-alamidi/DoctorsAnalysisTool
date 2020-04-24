import React from 'react';
import "../resources/css/shared.scss"
import CardAlt from "../components/card-alt"
import { Redirect, withRouter } from "react-router-dom"
import $ from "jquery";

const axios = require('axios').default;

class PasswordPage extends React.Component {
    constructor() {
        super();
        this.state = {
            oldPassword:"",
            newPassword:"",
            success:false,
            error:""
        }
        this.changePassword = this.changePassword.bind(this);
        this.changeHandler = this.changeHandler.bind(this);
    }

    componentDidMount() {
        document.getElementById("background").src = require("../resources/images/woman-girl-silhouette-jogger-40751.jpg");
    }

    changePassword(){
        //need to componitize axios requests to reduce redundant code, in a rush now to get work done though so that will come later if time presents itself
        if(this.state.oldPassword !== undefined && this.state.oldPassword.trim() !== "" && this.state.newPassword !== undefined && this.state.newPassword.trim() !== ""){
            axios('http://localhost:8080/password', { 
                    method: "put", 
                    withCredentials: true, headers:{oldPassword:this.state.oldPassword, newPassword:this.state.newPassword}})
                .then(function (response) {
                    console.log(response.data)
                    this.setState({success:true});
                }.bind(this)).catch(function(error){
                    if (error.response.status === 401) {
                        this.setState({error:"Old password is incorrect"})
                    }
                }.bind(this));
        }else{
            this.setState({error:"You must enter both old and new password"})
        }
    }

    changeHandler(val) {
        const { name, value } = val.target;
        this.setState({ [name]: value });
    }

    render() {
        $('.modal').modal('hide');
        if(this.state.success){
            return(
                <Redirect push to={
                    {
                        pathname: "/info/account",
                        state: {
                            from: "/info/password"
                        }
                    }
                } />
            )
        }else{
            return(
                <div className="container">
                    <CardAlt header={"Personal Information"}>
                        {(this.state.error) && <p>{this.state.error}</p>}
                        <input className="form-control form-control-lg" type="text" placeholder="Please enter your old password" name="oldPassword" onChange={this.changeHandler} />
                        <input className="form-control form-control-lg" type="text" placeholder="Please enter your new password" name="newPassword" onChange={this.changeHandler} />
                        <div className="card-footer">
                            <button type="button" className="btn" id="left-panel-link" onClick={this.changePassword}>change Password</button>
                        </div>
                    </CardAlt>
                </div>
            )
        }
    }
}

export default withRouter(PasswordPage);