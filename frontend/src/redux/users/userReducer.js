import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    userDetails: {},
};

const userSlice = createSlice({
    initialState,
    name: "user",
    reducers: {
        setUserDetails: (state, action) => {
            state.userDetails = action.payload;
        },
        updateUserDetails: (state, action) => {
            state.userDetails = { ...state.userDetails, ...action.payload };
        },
        resetUser: () => initialState,
    },
});

export const { setUserDetails, updateUserDetails, resetUser } =
    userSlice.actions;
export default userSlice.reducer;
