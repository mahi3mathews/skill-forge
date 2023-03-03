import { getApiCall, postApiCall } from "./axiosConfig";

const COMMON_API = "/skillforge";

// User REST API calls:

// to register user in the system
export const registerUserAsync = async (userDetails) => {
    try {
        const registerRes = await postApiCall(
            `${COMMON_API}/register`,
            userDetails
        );
        return registerRes;
    } catch (error) {
        return { isError: true, message: error.response.data };
    }
};
// to log in the user
export const loginAsync = async (userDetails) => {
    try {
        const { data } = await postApiCall(`${COMMON_API}/login`, userDetails);
        delete data.password;
        delete data.id;
        return data;
    } catch (error) {
        return { message: error.response.data, status: error.response.status };
    }
};

// to get all user details
export const getUserDetails = async (userId) => {
    try {
        const { data } = await getApiCall(`${COMMON_API}/user/${userId}`);
        delete data.password;
        delete data.id;
        return data;
    } catch (err) {
        return { message: err.response.data, status: err.response.status };
    }
};
