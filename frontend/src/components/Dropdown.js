import { Dropdown as ReactDropdown } from "react-bootstrap";
import Header from "./Header";
import "./styles/dropdown.scss";
import { useEffect, useState } from "react";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";

// Custom dropdown component with common style for entire application
const Dropdown = ({
    className = "",
    handleClick,
    dropTitle,
    menuItems = [],
}) => {
    const [showMenu, setShowMenu] = useState(false);
    const [title, setTitle] = useState(dropTitle);

    useEffect(() => {
        setTitle(dropTitle);
    }, [dropTitle]);

    const handleItemClick = (item) => {
        handleClick(item.data);
        setTitle(item.label);
        setShowMenu(false);
    };

    return (
        <ReactDropdown className={`custom-dropdown ${className}`}>
            <ReactDropdown.Toggle
                id='dropdown-autoclose-true'
                className='custom-dropdown-toggle'
                variant='transparent'
                onClick={() => {
                    setShowMenu((prevState) => !prevState);
                }}>
                <Header
                    className='custom-dropdown-toggle-text'
                    type='fS16 fW400'>
                    {title}
                </Header>
                <ExpandMoreIcon color='#0a1828' />
            </ReactDropdown.Toggle>
            <div className='menu-container'>
                <div className={`menu ${showMenu ? "show" : "hide"}`}>
                    {menuItems.map((item, index) => (
                        <div
                            className='menu-item'
                            key={`${index}-drop-item`}
                            onClick={() => handleItemClick(item)}>
                            <Header type='fW500 fS16'>{item.label}</Header>
                        </div>
                    ))}
                </div>
            </div>
        </ReactDropdown>
    );
};

export default Dropdown;
