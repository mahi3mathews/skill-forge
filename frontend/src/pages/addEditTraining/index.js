import { useEffect, useState } from "react";
import { Form } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { getAllCoursesAsync } from "../../api/courses";
import Dropdown from "../../components/Dropdown";
import { updateCoursesList } from "../../redux/courses/courseReducer";
import Header from "../../components/Header";
import Input from "../../components/Input";
import PageHeader from "../../components/PageHeader";
import "./addTraining.scss";
import DatePicker from "react-date-picker";
import Button from "../../components/Button";
import { addTrainingAsync, updateTrainingAsync } from "../../api/trainings";
import { HOME_URL } from "../../router/urls";
import { useLocation, useNavigate } from "react-router-dom";
import validator from "validator";
import ErrorText from "../../components/ErrorText";

// Add/Edit training is the page where admin can create or update the trainings.

const AddEditTraining = ({ isEdit }) => {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const location = useLocation();

    const [userId, courses] = useSelector((state) => [
        state.user.userDetails.userId,
        state?.courses?.allCourses ?? [],
    ]);

    const [isError, setError] = useState("");
    const [applyChanges, setApplyChanges] = useState(false);
    const [formData, setFormData] = useState({
        title: "",
        description: "",
        imgUrl: "",
        date: new Date(),
        totalSeats: 0,
        courseId: "",
    });

    const [courseTitle, setCourseTitle] = useState("Select a course");

    const handleChange = (type, value) => {
        setFormData((prevState) => ({ ...prevState, [type]: value }));
    };

    const handleFormUpdate = (training) => {
        setFormData({
            title: training?.title,
            description: training?.description,
            imgUrl: training.imgUrl,
            date: new Date(training.date),
            totalSeats: training.totalSeats,
            courseId: training.courseId,
            trainingId: training.trainingId,
        });
    };

    const getAllCourses = async () => {
        const courses = await getAllCoursesAsync(userId);
        if (courses.length <= 0) {
            // To-do: show disabled dropdown item for You have to add course first
        } else {
            dispatch(updateCoursesList(courses));
        }
    };

    useEffect(() => {
        getAllCourses();
        // Pre-fill update for edit state
        if (isEdit) {
            handleFormUpdate(location?.state?.training);
        }
    }, []);
    useEffect(() => {
        if (courses?.length > 0) {
            getCourseDropTitle();
        }
    }, [courses]);

    const handleApplyCourseDetails = (courseId = null) => {
        const singleCourse = courses.find(
            (course) => course.courseId === courseId
        );
        handleChange("title", singleCourse?.title);
        handleChange("description", singleCourse?.description);
    };

    const handleApplyChanges = (e) => {
        setApplyChanges(e.target.checked);
        if (e.target.checked) {
            handleApplyCourseDetails(formData.courseId);
        } else {
            handleChange("title", "");
            handleChange("description", "");
        }
    };

    // To see if the form details are valid
    const isFormValid = () => {
        if (!validator.isURL(formData.imgUrl)) {
            setError("Please provide a valid image url.");
            return false;
        } else {
            let isFormNotEmpty = true;
            Object.values(formData).forEach((item) => {
                if (!item) {
                    setError("Please complete the form.");
                    isFormNotEmpty = false;
                }
            });
            return isFormNotEmpty;
        }
    };

    const handleAddTraining = async (e) => {
        e.preventDefault();
        let res;
        if (isFormValid()) {
            if (isEdit) {
                res = await updateTrainingAsync(formData);
            } else {
                res = await addTrainingAsync(formData);
            }
            if (res.isError) {
                setError(res.message);
            } else {
                setTimeout(() => navigate(HOME_URL), 1000);
            }
        }
    };
    const handleDropChange = (courseId) => {
        handleChange("courseId", courseId);
        if (applyChanges) {
            handleApplyCourseDetails(courseId);
        }
    };
    const getCourseDropTitle = () => {
        if (formData.courseId && courses?.length > 0) {
            setCourseTitle(
                courses.find((item) => item.courseId === formData.courseId)
                    ?.title
            );
        }
    };

    const getCourseMenuItems = () =>
        courses.map((course) => ({
            label: course.title,
            data: course.courseId,
        }));

    return (
        <div className='add-training'>
            <PageHeader
                className='add-training-header'
                content='Add training form'
            />
            <Form onSubmit={handleAddTraining}>
                <Form.Group className='add-training-form-grp'>
                    <Form.Label>
                        <Header type='fW600 fS18'>Training course</Header>
                    </Form.Label>
                    <Dropdown
                        className='add-training-dropdown'
                        dropTitle={courseTitle}
                        menuItems={getCourseMenuItems()}
                        handleClick={(courseId) => handleDropChange(courseId)}
                    />
                    <Form.Check
                        className='add-training-check'
                        onClick={handleApplyChanges}
                        color='#bfa181'
                        type='checkbox'
                        label={
                            <Header type='fW500 fS14'>
                                Apply course details
                            </Header>
                        }
                    />
                </Form.Group>
                <Form.Group className='add-training-form-grp'>
                    <Form.Label>
                        <Header type='fW600 fS18'>Title of training</Header>
                    </Form.Label>
                    <Input
                        value={formData.title}
                        placeholder='Training title'
                        type='text'
                        handleChange={(e) =>
                            handleChange("title", e.target.value)
                        }
                    />
                </Form.Group>
                <Form.Group className='add-training-form-grp'>
                    <Form.Label>
                        <Header type='fW600 fS18'>
                            Description of training
                        </Header>
                    </Form.Label>
                    <Input
                        value={formData.description}
                        placeholder='Training description'
                        type='text'
                        handleChange={(e) =>
                            handleChange("description", e.target.value)
                        }
                    />
                </Form.Group>
                <Form.Group className='add-training-form-grp'>
                    <Form.Label>
                        <Header type='fW600 fS18'>
                            Training thumbnail URL
                        </Header>
                    </Form.Label>
                    <Input
                        value={formData?.imgUrl}
                        placeholder='Image URL'
                        type='text'
                        handleChange={(e) =>
                            handleChange("imgUrl", e.target.value)
                        }
                    />
                </Form.Group>
                <Form.Group className='add-training-form-grp'>
                    <Form.Label>
                        <Header type='fW600 fS18'>Training Date & Time</Header>
                    </Form.Label>
                    <DatePicker
                        className='add-training-date'
                        onChange={(value) => handleChange("date", value)}
                        value={formData.date}
                    />
                </Form.Group>
                <Form.Group className='add-training-form-grp'>
                    <Form.Label>
                        <Header type='fW600 fS18'>
                            Total seats available: {formData.totalSeats}
                        </Header>
                    </Form.Label>
                    <Form.Range
                        className='add-training-range'
                        onChange={(e) =>
                            handleChange("totalSeats", Number(e.target.value))
                        }
                    />
                </Form.Group>
                <Form.Group className='add-training-submit'>
                    <Button type='submit' variant='primary'>
                        {isEdit ? "Update" : "Submit"}
                    </Button>
                    {isError && <ErrorText content={isError} />}
                </Form.Group>
            </Form>
        </div>
    );
};

export default AddEditTraining;
