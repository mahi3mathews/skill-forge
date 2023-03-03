import React from "react";
import "./styles/header.scss";

// Custom text component with common style for entire application
const Header = ({ type, children, handleClick, className }) => {
    return (
        <div
            className={`header ${type ?? "fS16"}${
                handleClick ? " pointer" : ""
            } ${className ?? ""}`}
            onClick={handleClick ?? (() => {})}>
            {children}
        </div>
    );
};

export default Header;
