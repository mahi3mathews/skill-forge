import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";

import { Route, Routes } from "react-router-dom";
import Home from "../pages/home";
import TrainingApplicationView from "../pages/trainingApplicationView";
import {
    LOGIN_URL,
    REGISTER_URL,
    HOME_URL,
    LOGOUT_URL,
    APPLICATIONS_URL,
    adminUrls,
    attendeeUrls,
    ADD_COURSE_URL,
    COURSES_URL,
    ADD_TRAINING_URL,
    ADD_APPLICATION_URL,
    EDIT_TRAINING_URL,
    EDIT_COURSE_URL,
} from "../router/urls";
import Login from "../pages/login";
import Registration from "../pages/registration";
import Logout from "../pages/logout";
import { useSelector } from "react-redux";
import AddEditCourse from "../pages/addEditCourse";
import CoursesView from "../pages/coursesView";
import AddEditTraining from "../pages/addEditTraining";
import AddApplication from "../pages/addApplication";

const AppRouter = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const [isLoading, setLoading] = useState(true);

    const [userRole] = useSelector((states) => [
        states?.user?.userDetails?.role ?? "",
    ]);

    useEffect(() => {
        // Checks to see if user is logged in
        const loggedOutUrls = [LOGIN_URL, REGISTER_URL];
        const isLoggedIn = localStorage.getItem("isUserLoggedIn") === "true";

        // Handle case-scenario where user is logged out or logged in
        if (!isLoggedIn && !loggedOutUrls.includes(location.pathname)) {
            navigate(LOGIN_URL);
        } else if (isLoggedIn && loggedOutUrls.includes(location.pathname)) {
            navigate(HOME_URL);
        } else if (
            isLoggedIn &&
            userRole === "ADMIN" &&
            !adminUrls.includes(location.pathname)
        ) {
            // Case-scenario where admin tries to access links not provided to them
            navigate(HOME_URL);
        } else if (
            isLoggedIn &&
            userRole === "ATTENDEE" &&
            !attendeeUrls.includes(location.pathname)
        ) {
            // Case-scenario where attendee tries to access links not provided to them
            navigate(HOME_URL);
        }
        setLoading(false);
    }, [location.pathname, navigate, userRole]);

    const componentLoader = (component) => (isLoading ? null : component);

    // Routes handles the different paths and what component to be displayed
    return (
        <Routes>
            <Route
                path={REGISTER_URL}
                element={componentLoader(<Registration />)}
            />
            <Route path={LOGIN_URL} element={componentLoader(<Login />)} />
            <Route path={HOME_URL} element={componentLoader(<Home />)} />
            <Route
                path={APPLICATIONS_URL}
                element={componentLoader(<TrainingApplicationView />)}
            />

            <Route path={LOGOUT_URL} element={componentLoader(<Logout />)} />
            <Route
                path={ADD_COURSE_URL}
                element={componentLoader(<AddEditCourse />)}
            />
            <Route
                path={COURSES_URL}
                element={componentLoader(<CoursesView />)}
            />
            <Route
                path={ADD_TRAINING_URL}
                element={componentLoader(<AddEditTraining />)}
            />
            <Route
                path={ADD_APPLICATION_URL}
                element={componentLoader(<AddApplication />)}
            />
            <Route
                path={EDIT_TRAINING_URL}
                element={componentLoader(<AddEditTraining isEdit />)}
            />
            <Route
                path={EDIT_COURSE_URL}
                element={componentLoader(<AddEditCourse isEdit />)}
            />
        </Routes>
    );
};

export default AppRouter;
