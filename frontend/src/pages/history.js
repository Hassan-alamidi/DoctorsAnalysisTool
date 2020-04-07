import React from 'react';
import "../resources/css/shared.scss"
import auth from "../components/auth"
import { Redirect } from "react-router-dom"

const axios = require('axios').default;

class HistoryPage extends React.Component {
    constructor() {
        super();
        this.state = {
            patientPPSN: "",
            patient: "",
            tokenExpired: false,
            patientNotFound: false,
            loading: true
        }

        this.getTitle = this.getTitle.bind(this);
    }

    componentDidMount() {
        document.getElementById("background").src = require("../resources/images/DNA.jpg");
        this.state.patientPPSN = sessionStorage.getItem("patient");
        if (this.state.patientPPSN === undefined || this.state.patientPPSN === "") {
            this.setState({ patientNotFound: true, loading: false });
            return;
        }

        axios('http://localhost:8080/patient', { method: "get", withCredentials: true, headers: { "ppsn": this.state.patientPPSN } })
            .then(function (response) {
                console.log(response)
                response.data.detailed = false;
                this.setState({ patient: response.data, loading: false })
            }.bind(this)).catch(function (error) {
                console.log(error)
                if (error.response.status === 404) {
                    this.setState({ patientNotFound: true, loading: false });
                } else if (error.response.status === 403) {
                    auth.logout();
                    this.setState({ tokenExpired: true, loading: false });
                } else {
                    this.setState({ patientNotFound: true, loading: false });
                }
            }.bind(this));
    }

    getTitle() {
        return (this.state.patient.firstName + " " + this.state.patient.lastName);
    }

    render() {
        if (!this.state.loading) {
            if (this.state.tokenExpired) {
                return (
                    <Redirect to={
                        {
                            pathname: "/",
                            state: {
                                from: "/patient"
                            }
                        }
                    } />
                )
            } else if (this.state.patientNotFound) {
                return (
                    <Redirect to={
                        {
                            pathname: "/home",
                            state: {
                                from: "/patient"
                            }
                        }
                    } />
                )
            } else {
                const header = this.getTitle()
                return (
                    <div className="container">
                    </div>
                );
            }
        } else {
            return (
                <div className="container">
                    <h1>"Loading Please Wait..."</h1>
                </div>
            )
        }
    }
}

export default HistoryPage;