import React from 'react';
import "../../resources/css/shared.scss"
import "bootstrap";

export const MedicationList = ({medications}) => {
    return(
        <div className="card-body">
            <h5 className="card-title">Patient Current Medications</h5>
            <ul className="panel-group accordion" id="medication">
                {medications.map((medication, i) => {
                    medication.treatmentStart = medication.treatmentStart.split('T')[0];
                    medication.treatmentEnd = medication.treatmentEnd ? medication.treatmentEnd.split('T')[0] : "Not Specified";
                    return(
                        <div className="panel" key={"medicationitem" + i} >
                            <a data-toggle="collapse" data-parent="#medication" href={"#medicationId" + i} className="list-group-item list-group-item-action list-group-item-dark"><p>{medication.description}</p></a>
                            <div id={"medicationId" + i} className="panel-collapse collapse">
                                <div className="panel-body">
                                    <p>Start Date: {medication.treatmentStart}</p> <br />
                                    <p>End Date: {medication.treatmentEnd}</p> <br />
                                    <p>Reason: {medication.reasonDescription}</p>
                                </div>
                            </div>
                        </div>
                    )
                })}
            </ul>
        </div>
    )
}

export const MedicationListTransparent = ({medications, header}) => {
    return(
        <div className="card-body" id="transparentList">
            <h5 className="card-title">{header}</h5>
            <ul className="panel-group accordion" id="medication">
                {medications.map((medication, i) => {
                    medication.treatmentStart = medication.treatmentStart.split('T')[0];
                    medication.treatmentEnd = medication.treatmentEnd ? medication.treatmentEnd.split('T')[0] : "Not Specified";
                    return(
                        <div className="panel" key={"medicationitem" + i} >
                            <a data-toggle="collapse" data-parent="#medication" href={"#medicationId" + i} className="list-group-item list-group-item-action list-group-item-dark"><p>{medication.description}</p></a>
                            <div id={"medicationId" + i} className="panel-collapse collapse">
                                <div className="panel-body">
                                    <p>Start Date: {medication.treatmentStart}</p> <br />
                                    <p>End Date: {medication.treatmentEnd}</p> <br />
                                    <p>Reason: {medication.reasonDescription}</p>
                                </div>
                            </div>
                        </div>)
                })}
            </ul>
        </div>
    )
}

export default MedicationList;