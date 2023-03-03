import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    allCourses: [],
    courseDetails: {},
    addCourseDetails: {},
    isAllCoursesLoading: false,
};

const courseSlice = createSlice({
    initialState,
    name: "courses",
    reducers: {
        updateCoursesList: (state, action) => {
            state.allCourses = action.payload;
        },
        updateCourseDetails: (state, action) => {
            state.courseDetails = {
                ...state.courseDetails,
                ...action.payload,
            };
        },
        updateAddCourseDetails: (state, action) => {
            state.addCourseDetails = {
                ...state.addCourseDetails,
                ...action.payload,
            };
        },
        updateAllCourseLoading: (state, action) => {
            state.isAllCoursesLoading = action.payload;
        },
        resetCourse: () => initialState,
    },
});

export const {
    updateAddCourseDetails,
    updateCourseDetails,
    resetCourse,
    updateCoursesList,
} = courseSlice.actions;
export default courseSlice.reducer;
