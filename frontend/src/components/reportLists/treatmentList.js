import React from 'react';
import "../../resources/css/shared.scss"
import "bootstrap";

export const TreatmentList = ({callback, treatments, header}) => {
    return(
        <div className="card-body" id="transparentList">
            <h5 className="card-title">{header}</h5>
            <ul className="panel-group accordion" id="treatment">
                {treatments.map((treatment, i) => {
                    treatment.startDate = treatment.startDate.split('T')[0];
                    const endDate = treatment.endDate ? treatment.endDate.split('T')[0] : "Not Specified";
                    return(
                        <div className="panel" key={"treatmentitem" + i} >
                            <a data-toggle="collapse" data-parent="#treatment" href={"#treatmentId" + i} className="list-group-item list-group-item-action list-group-item-dark"><p>{treatment.description}</p></a>
                            <div id={"treatmentId" + i} className="panel-collapse collapse">
                                <div className="panel-body">
                                    {(callback && treatment.endDate === null) && 
                                        <button type="button" style={{float:"right", padding:"5px"}} className="btn btn-success" onClick={() => {callback("/treatment/current/end/" + treatment.id, "put")}}>Mark Treatment As Over</button>
                                    }
                                    <p>Start Date: {treatment.startDate}</p> <br />
                                    <p>End Date: {endDate}</p> <br />
                                    <p>Reason: {treatment.reasonDescription}</p>
                                </div>
                            </div>
                        </div>
                    )
                })}
            </ul>
        </div>
    )
}

export const TreatmentsListNoBody = ({callback, treatments}) => {
    return(
        <div className="card-body" id="treatmentsDefault">
            <div className="panel-group accordion" id="treatment">
                {treatments.map((treatment, i) => {
                    treatment.endDate = treatment.endDate ? treatment.endDate.split('T')[0] : "Not Specified";
                    return(
                    <div className="panel" key={"treatmentitem" + i} >
                        <div className="list-group-item list-group-item-action list-group-item-dark">
                            <p>{treatment.description}</p>
                            <p>End Date: {treatment.endDate}</p>
                            <button type="button" className="btn btn-danger" onClick={() => {callback(treatment.id)}}>Delete</button>
                        </div>
                    </div>)
                })}
            </div>
        </div>
    )
}

export default TreatmentList;