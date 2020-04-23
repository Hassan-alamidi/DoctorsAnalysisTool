import React from 'react';
import "../../resources/css/shared.scss"
import "bootstrap";

export const SimpleEncounterList = ({encounters, callback}) => {
    return(
        <div className="card-body" id="transparentSmallList">
            <div className="panel-group accordion" id="encounter">
                {encounters.map((encounter, i) => {
                    encounter.dateVisited = encounter.dateVisited.split('T')[0]
                    return(
                    <div className="panel" key={"encounteritem" + i} >
                        <div href={"#encounterId" + i} className="list-group-item list-group-item-action list-group-item-dark">
                            <p>Description: {encounter.description}</p> 
                            <p>Visited On: {encounter.dateVisited}</p>
                            <button type="button" className="btn btn-secondary" data-dismiss="modal" onClick={() => {callback(encounter)}}>Update</button>
                        </div>
                    </div>)
                })}
            </div>
        </div>
    )
}

export const EncounterList = ({encounters, header}) => {
    return(
        <div className="card-body" id="transparentList">
            <h5 className="card-title">{header}</h5>
            <div className="panel-group accordion" id="encounter">
                {encounters.map((encounter, i) => {
                    encounter.dateVisited = encounter.dateVisited.split('T')[0]
                    return(
                    <div className="panel" key={"encounteritem" + i} >
                        <a data-toggle="collapse" data-parent="#encounter" href={"#encounterId" + i} className="list-group-item list-group-item-action list-group-item-dark">
                            <p>Description: {encounter.description}</p> 
                            <p>Visited On: {encounter.dateVisited}</p>
                        </a>
                        <div id={"encounterId" + i} className="panel-collapse collapse">
                            <div className="panel-body">
                                <p>Type of encounter: {encounter.type}</p>
                                {(encounter.observations.length > 0) && <p>Observations Taken</p>}
                                {encounter.observations.map((observation, i) => {
                                        return(
                                            <div className="panel observations" key={"observation"+i}>
                                                <p>Type: {observation.type}</p>  
                                                <p>Result: {observation.resultValue} {observation.unit}</p>
                                            </div>)
                                    })}
                                {(encounter.conditions.length > 0) && <p>condition</p>}
                                    {encounter.conditions.map((condition, i) => {
                                    return(
                                        <div className="panel observations"  key={"condition"+i}>
                                            <p>Name: {condition.name}</p>  
                                            <p>Start:{condition.discovered}</p>
                                        </div>)
                                })}
                
                                {(encounter.medications.length > 0) && <p>Perscribed Medication</p>}
                                    {encounter.medications.map((medication, i) => {
                                    return(
                                        <div className="panel observations"  key={"medication"+i}>
                                            <p>Name: {medication.description}</p>  
                                            <p>Start: {medication.treatmentStart}</p>
                                            {(medication.treatmentEnd) && <p>End: {medication.treatmentEnd}</p>}
                                        </div>)
                                    })}
                                {(encounter.procedures.length > 0) && <p>Procedure Undergone</p>}
                                    {encounter.procedures.map((procedure, i) => {
                                    return(
                                        <div className="panel observations"  key={"procedure"+i}>
                                            <p>Name: {procedure.description}</p> {(procedure.reasonDescription) &&<p>Reason for: {procedure.reasonDescription}</p>}
                                        </div>)
                                    })}
                            </div>
                        </div>
                    </div>)
                })}
            </div>
        </div>
    )
}

export default EncounterList;
