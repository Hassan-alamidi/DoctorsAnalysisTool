import React from 'react';
import "../../resources/css/shared.scss"
import "bootstrap";
import EncounterCreationModal from "./encounterCreationModal"
import {SimpleEncounterList} from "../reportLists/encounterList"
import $ from "jquery";

const axios = require('axios').default;

class EncounterConfirmModal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            openEncounters:"",
            createNew:false,
            loading:true
        }
        this.createNew = this.createNew.bind(this);
    }

    componentDidMount() {

        axios('http://localhost:8080/encounter/open', { method: "get", withCredentials: true, headers: { "ppsn": this.props.patient.ppsn } })
            .then(function (response) {
                console.log(response)
                this.setState({ openEncounters: response.data, loading: false })
            }.bind(this)).finally(function(){
                $('#LoadingModal').modal('hide');
            });
            $('#LoadingModal').modal('show');
    }

    createNew(){
        this.setState({createNew:true})
    }

    render(){
        console.log(this.state.openEncounters);
        console.log(this.state.createNew);
        if(this.state.loading === true){
            return (
                <div className="modal fade" id="LoadingModal" tabIndex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                    <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="exampleModalLongTitle">Loading</h5>
                            </div>
                        </div>
                    </div>
                </div>
            );
        }else if(this.state.openEncounters.length > 0 && this.state.createNew === false){
            //$('#encounterModal').modal('show');
            return(
                <div className="modal fade" id="encounterModal" tabIndex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" data-backdrop="static" data-keyboard="false">
                    <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="exampleModalLongTitle">Open Encounters Found</h5>
                            </div>
                            <div className="modal-body">
                                <p>Open encounters Found, would you like to update and finalize one of these encounters below or create a new</p>
                                <SimpleEncounterList encounters={this.state.openEncounters} callback={this.props.callback}/>
                            </div>
                            <div className="modal-footer">
                                <button type="button" onClick={this.createNew} data-dismiss="modal" className="btn btn-primary">Create New</button>
                            </div>
                        </div>
                    </div>
                </div>
            );
        }else{
             //$('.modal').modal('hide');
             $('#LoadingModal').modal('hide');
            return(
                <EncounterCreationModal patient={this.props.patient} callback={this.props.callback} requestType="post" />
            );
        }
    }
}

export default EncounterConfirmModal;