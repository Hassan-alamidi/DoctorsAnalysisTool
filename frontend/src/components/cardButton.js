import React from 'react';
import "../resources/css/shared.scss"
import "bootstrap";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export const CardButton = ({header,details, icon, callback}) => {

    return(
        <div className="cardButton col" onClick={() => {callback()}}>
            <h5>{header}</h5>
            <hr />
            <div>
                <p>{details}</p>
                <FontAwesomeIcon icon={icon} />
            </div>
        </div>
    );
}

export const CardButtonModal = ({header,details, icon, callback, modalId}) => {

    return(
        <div className="cardButton col" data-toggle="modal" data-target={modalId} onClick={() => {if(callback !== undefined){callback()}}} >
            <h5>{header}</h5>
            <hr />
            <div>
                <p>{details}</p>
                <FontAwesomeIcon icon={icon} />
            </div>
        </div>
    );
}

export default CardButton;