import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    allTrainings: [],
    trainingDetails: {},
    addTrainingDetails: {},
    isLoadingAllTraining: false,
};

const trainingSlice = createSlice({
    initialState,
    name: "training",
    reducers: {
        updateTrainingList: (state, action) => {
            state.allTrainings = action.payload;
        },
        updateTrainingDetails: (state, action) => {
            state.trainingDetails = {
                ...state.trainingDetails,
                ...action.payload,
            };
        },
        updateAddTrainingDetails: (state, action) => {
            state.updateAddTrainingDetails = {
                ...state.addTrainingDetails,
                ...action.payload,
            };
        },
        updateAllTrainingLoader: (state, action) => {
            state.isLoadingAllTraining = action.payload;
        },
        reset: () => initialState,
    },
});

export const {
    updateAddTrainingDetails,
    updateTrainingDetails,
    reset,
    updateTrainingList,
    updateAllTrainingLoader,
} = trainingSlice.actions;
export default trainingSlice.reducer;
