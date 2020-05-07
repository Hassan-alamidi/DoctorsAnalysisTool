import React from 'react';
import "../../resources/css/shared.scss"
import "bootstrap";

export const predictionList = ({predictions, header}) => {
    return(
        <div className="card-body" id="transparentList">
            <h5 className="card-title">{header}</h5>
            <ul className="panel-group accordion" id="prediction">
                {predictions.map((prediction, i) => {
                    return(
                        <div className="panel" key={"predictionitem" + i} >
                            <a data-toggle="collapse" data-parent="#prediction" href={"#predictionId" + i} className="list-group-item list-group-item-action list-group-item-dark"> 
                            <p>Generated On: {prediction.dateGenerated}</p></a>
                            <div id={"predictionId" + i} className="panel-collapse collapse">
                                <div className="panel-body">
                                    <p>Result: {prediction.result}</p> 
                                    <p>These results are based on the following Observations:</p>
                                    {prediction.basedOn.split(',').map((value,i) => {
                                        return(
                                            <div key={"observationValue"+i}>
                                                <p>{value}</p><br/>
                                            </div>
                                        );
                                    })}
                                </div>
                            </div>
                        </div>)
                })}
            </ul>
        </div>
    )
}

export default predictionList;