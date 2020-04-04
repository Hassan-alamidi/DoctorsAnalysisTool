import React from 'react';
import "../resources/css/shared.scss"

const PatientInfoCard = ({firstName, lastName, gender, dob, ppsn, address,patientConditions, detailed}) => {
    //remove time from date
    dob = dob.split('T')[0]
    return(
        <div className="card">
            <div className="card-body">
                <h5 className="card-title">Patient Found:  {firstName} {lastName}</h5>
                <ul className="list-group">
                    <li className="list-group-item list-group-item-success">Patient PPSN: {ppsn}</li>
                    <li className="list-group-item list-group-item-success">Patient Date of birth: {dob}</li>
                    <li className="list-group-item list-group-item-success">Patient Gender: {gender}</li>
                    <li className="list-group-item list-group-item-success">Patient Address: {address}</li>
                    {!(detailed) && 
                        
                            <div className="row">
                                <div className="card col-md-6">
                                    <div className="card-body">
                                        <h5 className="card-title">Patient Current Conditions</h5>
                                        <ul className="list-group">
                                            {patientConditions.map((condition, i) => {
                                                {condition.discovered = condition.discovered.split('T')[0]}
                                                return(<li key={"conditon" + i} className="list-group-item list-group-item-action list-group-item-dark"><p>{condition.name}</p> <p>Discovered: {condition.discovered}</p></li>)
                                            })}
                                        </ul>
                                    </div>
                                </div>
                                <div className="card col-md-6">
                                    <div className="card-body">
                                        <h5 className="card-title">Patient Current Medications</h5>
                                        <ul className="list-group">
                                            {patientConditions.map((condition, i) => {
                                                {condition.discovered = condition.discovered.split('T')[0]}
                                                return(<li key={"medication" + i} className="list-group-item list-group-item-action list-group-item-dark"><p>{condition.name}</p> <p>Discovered: {condition.discovered}</p></li>)
                                            })}
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        
                    }
                </ul>
            </div>
            <div className="card-footer">
            <button type="button" className="btn" id="left-panel-link" >View Patient Information</button>
            </div>
        </div>
    );
}

export default PatientInfoCard;