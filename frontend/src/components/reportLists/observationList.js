import React from 'react';
import "../../resources/css/shared.scss"
import "bootstrap";

export const ObservationsList = ({observations}) => {
    return(
        <div className="card-body" id="transparentList">
            <h5 className="card-title">Patient Recent Observations</h5>
            <div className="panel-group accordion" id="observation">
                {observations.map((observation, i) => {
                    observation.dateTaken = observation.dateTaken.split('T')[0]
                    return(
                    <div className="panel" key={"observationitem" + i} >
                        <a data-toggle="collapse" data-parent="#observation" href={"#observationId" + i} className="list-group-item list-group-item-action list-group-item-dark"><p>{observation.type}</p> <p>Taken on: {observation.dateTaken}</p></a>
                        <div id={"observationId" + i} className="panel-collapse collapse">
                            <div className="panel-body">
                                <p>Result: {observation.resultValue} {observation.unit}</p>
                            </div>
                        </div>
                    </div>)
                })}
            </div>
        </div>
    )
}

export const ObservationsListNoBody = ({callback, observations}) => {
    return(
        <div className="card-body" id="observationsDefault">
            <div className="panel-group accordion" id="observation">
                {observations.map((observation, i) => {
                    observation.dateTaken = observation.dateTaken.split('T')[0]
                    return(
                    <div className="panel" key={"observationitem" + i} >
                        <div className="list-group-item list-group-item-action list-group-item-dark">
                            <p>{observation.type}</p> 
                            <p>Taken on: {observation.dateTaken}</p>
                            <button type="button" className="btn btn-danger" onClick={() => {callback(observation.id)}}>Delete</button>
                        </div>
                    </div>)
                })}
            </div>
        </div>
    )
}

export const ObservationListTransparent = ({observations, header}) => {
    return(
        <div className="card-body" id="transparentList">
            <h5 className="card-title">{header}</h5>
            <ul className="panel-group accordion" id="observation">
                {observations.map((observation, i) => {
                    observation.dateTaken = observation.dateTaken.split('T')[0]
                    return(
                        <div className="panel" key={"observationitem" + i} >
                            <a data-toggle="collapse" data-parent="#observation" href={"#observationId" + i} className="list-group-item list-group-item-action list-group-item-dark"><p>{observation.type}</p> <p>Taken on: {observation.dateTaken}</p></a>
                            <div id={"observationId" + i} className="panel-collapse collapse">
                                <div className="panel-body">
                                    <p>Result: {observation.resultValue} {observation.unit}</p>
                                </div>
                            </div>
                        </div>)
                })}
            </ul>
        </div>
    )
}

export default ObservationsList;
