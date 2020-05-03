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

export const MedicationListNoBody = ({callback, medications}) => {
    return(
        <div className="card-body" id="medicationsDefault">
            <div className="panel-group accordion" id="medication">
                {medications.map((medication, i) => {
                    medication.treatmentStart = medication.treatmentStart.split('T')[0];
                    medication.treatmentEnd = medication.treatmentEnd ? medication.treatmentEnd.split('T')[0] : "Not Specified";
                    return(
                    <div className="panel" key={"medicationitem" + i} >
                        <div className="list-group-item list-group-item-action list-group-item-dark">
                            <p>{medication.description}</p> <br />
                            <p>Start Date: {medication.treatmentStart}</p> 
                            <p>End Date: {medication.treatmentEnd}</p>
                            <button type="button" className="btn btn-danger" onClick={() => {callback(medication.id)}}>Delete</button>
                        </div>
                    </div>)
                })}
            </div>
        </div>
    )
}

export const MedicationListTransparent = ({callback, medications, header}) => {
    return(
        <div className="card-body" id="transparentList">
            <h5 className="card-title">{header}</h5>
            <ul className="panel-group accordion" id="medication">
                {medications.map((medication, i) => {
                    medication.treatmentStart = medication.treatmentStart.split('T')[0];
                    const treatmentEnd = medication.treatmentEnd ? medication.treatmentEnd.split('T')[0] : "Not Specified";
                    return(
                        <div className="panel" key={"medicationitem" + i} >
                            <a data-toggle="collapse" data-parent="#medication" href={"#medicationId" + i} className="list-group-item list-group-item-action list-group-item-dark"><p>{medication.description}</p></a>
                            <div id={"medicationId" + i} className="panel-collapse collapse">
                                <div className="panel-body">
                                {(callback && medication.treatmentEnd === null) && <button type="button" style={{float:"right", padding:"5px"}} className="btn btn-success" onClick={() => {callback("/medication/current/end/" + medication.id, "put")}}>Mark Treatment As Over</button>}
                                    <p>Start Date: {medication.treatmentStart}</p> <br />
                                    <p>End Date: {treatmentEnd}</p> <br />
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