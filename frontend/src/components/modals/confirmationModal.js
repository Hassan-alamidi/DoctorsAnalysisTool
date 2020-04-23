import React from 'react';
import "../../resources/css/shared.scss"
import "bootstrap";

export const GenericModal = (props) => {
    return(
        <div className="modal fade" id="genericModal" tabIndex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div className="modal-dialog modal-dialog-centered modal-lg" role="document">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">{props.header}</h5>
                    </div>
                    <div className="modal-body center">
                        {props.children}
                    </div>
                    <div className="modal-footer">
                        <button type="button" className="btn btn-secondary" data-dismiss="modal" >Cancel</button>
                        <button type="button" onClick={() => {props.callback()}} data-dismiss="modal" className="btn btn-primary">Confirm</button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default GenericModal;