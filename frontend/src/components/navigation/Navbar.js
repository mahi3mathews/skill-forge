import React from "react";
import { Link, useNavigate } from "react-router-dom";
import {
    APPLICATIONS_URL,
    HOME_URL,
    LOGOUT_URL,
    COURSES_URL,
} from "../../router/urls";
import Header from "../Header";
import "../styles/navbar.scss";

// Navigation bar for the application
const NavBar = ({ userRole }) => {
    const navigate = useNavigate();
    // Allowed url for attendees to be displayed in navbar
    const attendeeUrls = [
        { url: HOME_URL, title: "Trainings" },
        { url: APPLICATIONS_URL, title: "Applications" },
        { url: LOGOUT_URL, title: "Logout" },
    ];
    // Allowed url for admin to be displayed in navbar
    const adminUrls = [
        { url: HOME_URL, title: "Trainings" },
        { url: APPLICATIONS_URL, title: "Applications" },
        { url: COURSES_URL, title: "Courses" },
        { url: LOGOUT_URL, title: "Logout" },
    ];
    const handleHomeClick = () => navigate(HOME_URL);

    const urlToBeMapped = () =>
        userRole === "ADMIN" ? adminUrls : attendeeUrls;

    return (
        <div className='nav-bar'>
            <div className='nav-bar-title'>
                <Header type='fS24 fW600 primary' handleClick={handleHomeClick}>
                    SkillForge
                </Header>
            </div>
            <div className='nav-bar-link-container'>
                {urlToBeMapped().map((item, index) => {
                    return (
                        <div key={`${index}-link`} className='nav-link'>
                            <Link to={item?.url}>
                                <Header type='fS21 fW500 primary'>
                                    {item?.title}
                                </Header>
                            </Link>
                        </div>
                    );
                })}
            </div>
        </div>
    );
};

export default NavBar;
