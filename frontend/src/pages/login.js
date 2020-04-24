import React from 'react';
import { withRouter } from 'react-router-dom'
import auth from '../components/auth'
import '../resources/css/login.scss';

const axios = require('axios').default;

class LoginPage extends React.Component {
    constructor() {
        super();

        this.state = {
            medicalLicenceNumber: "",
            password: ""
        }

        this.login = this.login.bind(this);
        this.changeHandler = this.changeHandler.bind(this)
        this.toHome = this.toHome.bind(this);
    }

    componentDidMount(){
        document.getElementById("background").src = require("../resources/images/loginPageBackground.jpg")
    }

    changeHandler(val) {
        const { name, value } = val.target;
        this.setState({ [name]: value });
    }

    login() {
        if (this.state.medicalLicenceNumber !== "" && this.state.password !== "") {
            axios('http://localhost:8080/login', { data: this.state, method: "post", withCredentials: true })
                .then(function (response) {
                    if (response.status === 200) {
                        //create a non-http session cookie and redirect to home
                        
                        axios('http://localhost:8080/personal-details', {method: "get", withCredentials: true })
                        .then(function(response){
                            console.log(response);
                            this.toHome(response.data.privilegeLevel)
                        }.bind(this));
                        
                    }
                }.bind(this));
        } else {
            alert("Please enter both your medical licence number and password")
        }
    }

    toHome(privilegeLevel) {
        auth.login(privilegeLevel);
        this.props.history.push("/hub/home");
    }

    render() {
        return (
            <div className="customForm container largePadding">
                <h1 className="heading">Medical Hub</h1>
                <input className="form-control form-control-lg" type="text" placeholder="Medical Licence Number" name="medicalLicenceNumber" onChange={this.changeHandler} />
                <input className="form-control form-control-lg" type="password" placeholder="password" name="password" onChange={this.changeHandler} />
                <button onClick={this.login} type="submit" className="btn btn-primary">Login</button>
            </div>
        );
    }
}

export default withRouter(LoginPage);
