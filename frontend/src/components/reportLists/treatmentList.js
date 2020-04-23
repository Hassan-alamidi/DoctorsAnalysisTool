import React from 'react';
import "../../resources/css/shared.scss"
import "bootstrap";

export const TreatmentList = ({treatments, header}) => {
    return(
        <div className="card-body" id="transparentList">
            <h5 className="card-title">{header}</h5>
            <ul className="panel-group accordion" id="treatment">
                {treatments.map((treatment, i) => {
                    treatment.startDate = treatment.startDate.split('T')[0];
                    treatment.endDate = treatment.endDate ? treatment.endDate.split('T')[0] : "Not Specified";
                    return(
                        <div className="panel" key={"treatmentitem" + i} >
                            <a data-toggle="collapse" data-parent="#treatment" href={"#treatmentId" + i} className="list-group-item list-group-item-action list-group-item-dark"><p>{treatment.description}</p></a>
                            <div id={"treatmentId" + i} className="panel-collapse collapse">
                                <div className="panel-body">
                                    <p>Start Date: {treatment.startDate}</p> <br />
                                    <p>End Date: {treatment.endDate}</p> <br />
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

export default TreatmentList;