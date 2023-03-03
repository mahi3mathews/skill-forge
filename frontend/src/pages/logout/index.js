import { useDispatch } from "react-redux";
import { reset } from "../../redux/trainings/trainingReducer";
import { resetUser } from "../../redux/users/userReducer";
import { resetCourse } from "../../redux/courses/courseReducer";
import { resetApplication } from "../../redux/applications/applicationReducer";

// Logout component to handle state removal of user.

const Logout = () => {
    const dispatch = useDispatch();
    localStorage.removeItem("isUserLoggedIn");
    localStorage.removeItem("userId");
    dispatch(reset());
    dispatch(resetUser());
    dispatch(resetCourse());
    dispatch(resetApplication());

    return <></>;
};

export default Logout;
