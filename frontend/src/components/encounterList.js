import React from 'react';
import "../resources/css/shared.scss"
import "bootstrap";

export const EncounterList = ({encounters}) => {
    return(
        <div className="card-body" id="transparentList">
            <h5 className="card-title">Patient Current Conditions</h5>
            <div className="panel-group accordion" id="encounter">
                {encounters.map((encounter, i) => {
                    encounter.dateVisited = encounter.dateVisited.split('T')[0]
                    return(
                    <div className="panel" key={"encounteritem" + i} >
                        <a data-toggle="collapse" data-parent="#encounter" href={"#encounterId" + i} className="list-group-item list-group-item-action list-group-item-dark"><p>Description: {encounter.description}</p> <p>Visited On: {encounter.dateVisited}</p></a>
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
                                {(encounter.condition) && 
                                <div>
                                    <p>condition</p>
                                    <div className="panel observations">
                                        <p>Name: {encounter.condition.name}</p>  
                                        <p>Start:{encounter.condition.discovered}</p>
                                    </div>
                                </div>}
                                {(encounter.medication) && 
                                <div>
                                    <p>Perscribed Medication</p>
                                    <div className="panel observations">
                                        <p>Name: {encounter.medication.description}</p>  
                                        <p>Start: {encounter.medication.treatmentStart}</p>
                                        {(encounter.medication.treatmentEnd) && <p>End: {encounter.medication.treatmentEnd}</p>}
                                    </div>
                                </div>}
                                {(encounter.procedure) && 
                                <div>
                                    <p>Procedure Undergone</p>
                                    <div className="panel observations">
                                        <p>Name: {encounter.procedure.name}</p>  
                                    </div>
                                </div>}
                            </div>
                        </div>
                    </div>)
                })}
            </div>
        </div>
    )
}

export default EncounterList;
