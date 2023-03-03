import "./styles/button.scss";

// Custom button component with common style for entire application
const Button = ({ onClick, type, className, variant, children }) => {
    return (
        <button
            onClick={onClick ?? (() => {})}
            type={type}
            className={`button ${className ?? ""} ${variant}`}>
            {children}
        </button>
    );
};

export default Button;
