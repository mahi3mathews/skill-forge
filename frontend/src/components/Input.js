import React from "react";
import { Form } from "react-bootstrap";
import "./styles/input.scss";

// Custom input component with common style for entire application
const Input = ({
    placeholder,
    className,
    value,
    postComponent,
    handleChange = () => {},
    type,
}) => {
    const onChange = (e) => {
        handleChange(e);
    };

    return (
        <div className='input-container'>
            <Form.Control
                className={`input ${className ?? ""}`}
                placeholder={placeholder ?? ""}
                value={value}
                onChange={onChange}
                type={type ?? "text"}
            />
            {postComponent ?? null}
        </div>
    );
};

export default Input;
