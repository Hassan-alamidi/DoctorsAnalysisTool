import React from 'react';
import "../resources/css/shared.scss"
import "bootstrap";
import ConditionsList from "./conditionsList"
import MedicationList from "./medicationList"


const PatientInfoCard = ({header, patient, detailed, callback}) => {
    //remove time from date
    //for testing use this jsaofj
    patient.dob = patient.dob.split('T')[0]
    return(
        <div className="card">
            <div className="card-body">
                <h5 className="card-title">{header}</h5>
                <ul className="list-group">
                    <li className="list-group-item list-group-item-success">Patient PPSN: {patient.ppsn}</li>
                    <li className="list-group-item list-group-item-success">Patient Date of birth: {patient.dob}</li>
                    <li className="list-group-item list-group-item-success">Patient Gender: {patient.gender}</li>
                    <li className="list-group-item list-group-item-success">Patient Address: {patient.address}</li>
                    {(detailed) && 
                        <div className="row">
                            <div className="card col-md-6">
                                <ConditionsList conditions={patient.patientConditions} />
                            </div>
                            <div className="card col-md-6">
                                <MedicationList medications={patient.currentMedication} />
                            </div>
                        </div>
                    }
                </ul>
            </div>
            {(detailed) &&
                <div className="card-footer">
                    <button type="button" className="btn" id="left-panel-link" onClick={callback}>View and manage patient</button>
                </div>
            }
        </div>
    );
}

export default PatientInfoCard;