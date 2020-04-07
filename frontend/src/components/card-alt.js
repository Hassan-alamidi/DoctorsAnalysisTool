import React from 'react';
import "../resources/css/shared.scss"
import "bootstrap";


const CardAlt = (props) => {
   
    return(
        <div className="card-alt">
            <div className="card-body">
                <h5 className="card-title">{props.header}</h5>
                {props.children}
            </div>
        </div>
    );
}

export default CardAlt;