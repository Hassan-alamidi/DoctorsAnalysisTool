import React from 'react';
import "../../resources/css/shared.scss"
import "bootstrap";

export const ProcedureListTransparent = ({procedures, header}) => {
    return(
        <div className="card-body" id="transparentList">
            <h5 className="card-title">{header}</h5>
            <div className="panel-group accordion" id="procedure">
                {procedures.map((procedure, i) => {
                    procedure.carriedOutOn = procedure.carriedOutOn ? procedure.carriedOutOn.split('T')[0] : "Not Specified";
                    return(
                    <div className="panel" key={"procedureitem" + i} >
                        <a data-toggle="collapse" data-parent="#procedure" href={"#procedureId" + i} className="list-group-item list-group-item-action list-group-item-dark"><p>{procedure.description}</p> <p>Carried Out On: {procedure.carriedOutOn}</p></a>
                        <div id={"procedureId" + i} className="panel-collapse collapse">
                            {(procedure.reasonDescription) &&
                                <div className="panel-body">
                                    <p>Reason For Procedure: {procedure.reasonDescription}</p>
                                </div>
                            }
                        </div>
                    </div>)
                })}
            </div>
        </div>
    )
}

export default ProcedureListTransparent;
