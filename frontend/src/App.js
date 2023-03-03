import "./App.scss";
import NavBar from "./components/navigation/Navbar";
import { useState, useEffect } from "react";
import AppRouter from "./router/AppRouter";
import { useDispatch, useSelector } from "react-redux";
import { updateUserDetails } from "./redux/users/userReducer";
import { getUserDetails } from "./api/user";

// Application layout with navbar and pages to render
const App = () => {
    const dispatch = useDispatch();

    const [isLoggedIn, setLoggedIn] = useState(false);
    const [userId, userRole] = useSelector((states) => [
        states?.user?.userDetails?.userId ?? "",
        states?.user?.userDetails?.role ?? "",
    ]);

    const setUserDetails = async () => {
        let userRes = await getUserDetails(localStorage.getItem("userId"));
        if (userRes?.userId) {
            dispatch(updateUserDetails(userRes));
        }
    };

    useEffect(() => {
        // Set up check to see if user is still logged in.
        const isLoggedIn = localStorage.getItem("isUserLoggedIn") === "true";
        setLoggedIn(isLoggedIn);
        if (isLoggedIn && !userId) {
            setUserDetails();
        }
    }, [userId]);

    return (
        <div
            className={`app-container ${!isLoggedIn ? "background-dark" : ""}`}>
            {/* Logged in user layout changes */}
            {isLoggedIn && (
                <div className='app-nav'>
                    <NavBar userRole={userRole} />
                </div>
            )}
            {/* Page renderer */}
            <div className='app-body'>
                <AppRouter isLoggedIn={isLoggedIn} />
            </div>
        </div>
    );
};

export default App;
