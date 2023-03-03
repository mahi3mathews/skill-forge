import {
    deleteApiCall,
    getApiCall,
    postApiCall,
    putApiCall,
} from "./axiosConfig";

const applicantApplicationUrl = (userId) =>
    `/attendee/${userId ? `${userId}/` : ""}application`;
const ADMIN_APPLICATIONS_URL = "/admin/application";

// Training Application REST API calls:

// to update the application status
export const updateApplicationStatusAsync = async (status, applicationId) => {
    try {
        const { data } = await putApiCall(
            `${ADMIN_APPLICATIONS_URL}/${applicationId}/status`,
            { status }
        );
        return data;
    } catch (error) {
        return {
            isError: true,
            message: error.response.data,
        };
    }
};

// to get all applications
export const getAllApplicationAsync = async () => {
    try {
        const { data } = await getApiCall(`${ADMIN_APPLICATIONS_URL}s`);
        return data;
    } catch (error) {
        return {
            isError: true,
            message: error.response.data,
        };
    }
};

// to get all applications of user
export const getUserApplicationsAsync = async (userId) => {
    try {
        const { data } = await getApiCall(
            `${applicantApplicationUrl(userId)}s`
        );
        return data;
    } catch (error) {
        return {
            isError: true,
            message: error.response.data,
        };
    }
};

// to create an application
export const addApplicationAsync = async (application) => {
    try {
        const { data } = await postApiCall(
            `${applicantApplicationUrl()}/create`,
            application
        );
        return data;
    } catch (error) {
        return {
            isError: true,
            message: error.response.data,
        };
    }
};

// to delete an application
export const deleteApplicationAsync = async (applicationId) => {
    try {
        const { data } = await deleteApiCall(
            `${applicantApplicationUrl()}/${applicationId}`
        );
        return data;
    } catch (error) {
        return {
            isError: true,
            message: error.response.data,
        };
    }
};
