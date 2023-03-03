import { getApiCall, postApiCall, putApiCall } from "./axiosConfig";

const ADMIN_COURSE_API = "/admin/courses";

// Course REST API calls:

// to update course
export const updateCourseAsync = async (courseDetails, userId) => {
    try {
        const { data } = await putApiCall(
            `${ADMIN_COURSE_API}/${courseDetails?.courseId}`,
            courseDetails
        );
        return data;
    } catch (error) {
        return {
            isError: true,
            message: error.response.data,
        };
    }
};
// to add course
export const addCourseAsync = async (courseDetails, userId) => {
    try {
        const { data } = await postApiCall(`${ADMIN_COURSE_API}/add`, {
            course: courseDetails,
            userId: userId,
        });
        return data;
    } catch (error) {
        return {
            isError: true,
            message: error.response.data,
        };
    }
};

// to get all courses
export const getAllCoursesAsync = async () => {
    try {
        const { data } = await getApiCall(ADMIN_COURSE_API);
        return data;
    } catch (err) {
        return {
            isError: true,
            message: err.response.data,
        };
    }
};
