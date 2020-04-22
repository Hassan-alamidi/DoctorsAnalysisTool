import React from 'react';
import "../../resources/css/shared.scss"
import "bootstrap";

export const ConditionsList = ({conditions}) => {
    return(
        <div className="card-body" id="conditonsDefault">
            <h5 className="card-title">Patient Conditions</h5>
            <div className="panel-group accordion" id="condition">
                {conditions.map((condition, i) => {
                    condition.discovered = condition.discovered.split('T')[0]
                    return(
                    <div className="panel" key={"conditionitem" + i} >
                        <a data-toggle="collapse" data-parent="#condition" href={"#conditionId" + i} className="list-group-item list-group-item-action list-group-item-dark"><p>{condition.name}</p> <p>Discovered: {condition.discovered}</p></a>
                        <div id={"conditionId" + i} className="panel-collapse collapse">
                            <div className="panel-body">
                                <p>Observations</p>
                                {condition.encounter.observations.map((observation, i) => {
                                        return(
                                            <div className="panel observations" key={"observation"+i}>
                                                <p>Type: {observation.type}</p>  
                                                <p>Result:{observation.resultValue} {observation.unit}</p>
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

export const ConditionsListTransparent = ({conditions, header}) => {
    return(
        <div className="card-body" id="transparentList">
            <h5 className="card-title">{header}</h5>
            <div className="panel-group accordion" id="condition">
                {conditions.map((condition, i) => {
                    condition.discovered = condition.discovered.split('T')[0]
                    return(
                    <div className="panel" key={"conditionitem" + i} >
                        <a data-toggle="collapse" data-parent="#condition" href={"#conditionId" + i} className="list-group-item list-group-item-action list-group-item-dark"><p>{condition.name}</p> <p>Discovered: {condition.discovered}</p></a>
                        <div id={"conditionId" + i} className="panel-collapse collapse">
                            <div className="panel-body">
                                {(condition.encounter.observations.length > 0) && <p>Observations</p>}
                                {condition.encounter.observations.map((observation, i) => {
                                        return(
                                            <div className="panel observations" key={"observation"+i}>
                                                <p>Type: {observation.type}</p>  
                                                <p>Result:{observation.resultValue} {observation.unit}</p>
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

export default ConditionsList;