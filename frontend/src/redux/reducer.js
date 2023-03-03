import { combineReducers } from "@reduxjs/toolkit";
import courseReducer from "./courses/courseReducer";
import trainingReducer from "./trainings/trainingReducer";
import userReducer from "./users/userReducer";
import applicationReducer from "./applications/applicationReducer";

// Combine all reducers(state) of the application into one reducer
const rootReducer = combineReducers({
    training: trainingReducer,
    user: userReducer,
    courses: courseReducer,
    applications: applicationReducer,
});

export default rootReducer;
