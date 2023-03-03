import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    allApplications: [],
    isAllApplicationLoading: false,
};

const applicationSlice = createSlice({
    initialState,
    name: "applications",
    reducers: {
        updateApplicationsList: (state, action) => {
            let newArr = [...action.payload];
            state.allApplications = newArr;
        },
        updateApplicationLoading: (state, action) => {
            state.isAllApplicationLoading = action.payload;
        },
        resetApplication: () => initialState,
    },
});

export const {
    updateApplicationsList,
    updateApplicationLoading,
    resetApplication,
} = applicationSlice.actions;
export default applicationSlice.reducer;
