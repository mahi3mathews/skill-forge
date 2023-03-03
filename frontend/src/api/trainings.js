import {
    deleteApiCall,
    getApiCall,
    postApiCall,
    putApiCall,
} from "./axiosConfig";

const TRAINING_API = "/skillforge/trainings";
const ADMIN_TRAINING_API = "/admin/training";

// Training REST API calls:

// to get all trainings common to all users
export const getAllTrainingsAsync = async () => {
    try {
        const { data } = await getApiCall(TRAINING_API);
        return data;
    } catch (error) {
        return { isError: true, message: error.response.data };
    }
};

// to add training
export const addTrainingAsync = async (trainingDetails) => {
    try {
        let { data } = await postApiCall(
            `${ADMIN_TRAINING_API}/add`,
            trainingDetails
        );

        return data;
    } catch (error) {
        return { isError: true, message: error.response.data };
    }
};

// to update training
export const updateTrainingAsync = async (training) => {
    if (training?.trainingId) {
        return await putApiCall(
            `${ADMIN_TRAINING_API}/${training?.trainingId ?? ""}`,
            training
        );
    }
    return null;
};

// to delete training
export const deleteTraining = async (trainingId) => {
    if (!trainingId) {
        return { isError: true, message: "No training was provided." };
    }
    try {
        await deleteApiCall(`${ADMIN_TRAINING_API}/${trainingId}`);
    } catch (error) {
        return { isError: true, message: error.response.data };
    }
};
