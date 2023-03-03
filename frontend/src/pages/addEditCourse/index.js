import { useEffect, useState } from "react";
import { Form } from "react-bootstrap";
import Button from "../../components/Button";
import DoneOutlineIcon from "@mui/icons-material/DoneOutline";
import ClearIcon from "@mui/icons-material/Clear";
import Header from "../../components/Header";
import Input from "../../components/Input";
import "./addCourse.scss";
import { useSelector } from "react-redux";
import { addCourseAsync, updateCourseAsync } from "../../api/courses";
import { COURSES_URL } from "../../router/urls";
import { useLocation, useNavigate } from "react-router-dom";

// Add/Edit course is the page to create or update a course to be linked to the training

const AddEditCourse = ({ isEdit }) => {
    const navigate = useNavigate();
    const location = useLocation();
    const [userId] = useSelector((state) => [state.user.userDetails.userId]);
    const [formData, setFormData] = useState({
        title: "",
        description: "",
        requiredSkills: [],
    });

    useEffect(() => {
        // Handle pre-fill course for edit state
        if (isEdit) {
            const course = location.state.course;
            setFormData({
                courseId: course?.courseId,
                title: course?.title,
                description: course?.description,
                requiredSkills: course?.requiredSkills,
            });
        }
    }, [isEdit]);

    const handleChange = (type, value) => {
        setFormData((prevState) => ({ ...prevState, [type]: value }));
    };
    const handleSkillUpdate = (value) => {
        setFormData((prevState) => {
            let skills = prevState.requiredSkills;
            skills.push(value);
            return {
                ...prevState,
                requiredSkills: skills,
            };
        });
    };
    const handleSkillClear = (value) => {
        setFormData((prevState) => {
            let skills = prevState.requiredSkills;
            const index = skills.indexOf(value);
            delete skills[index];
            return { ...prevState, requiredSkills: skills };
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        e.stopPropagation();
        let res;
        if (isEdit) {
            res = updateCourseAsync(formData);
        } else {
            res = addCourseAsync(formData, userId);
        }
        if (res.isError) {
        } else {
            setTimeout(() => navigate(COURSES_URL), 1000);
        }
    };

    return (
        <div className='add-course'>
            <Header type='fS28 fW500' className='add-course-header'>
                Add Course Form
            </Header>
            <Form onSubmit={handleSubmit}>
                <Form.Group className='add-course-form-grp'>
                    <Form.Label>
                        <Header type='fW600 fS18'>Title of training</Header>
                    </Form.Label>
                    <Input
                        value={formData.title}
                        placeholder='Course title'
                        type='text'
                        handleChange={(e) =>
                            handleChange("title", e.target.value)
                        }
                    />
                </Form.Group>
                <Form.Group className='add-course-form-grp'>
                    <Form.Label>
                        <Header type='fW600 fS18'>Description of course</Header>
                    </Form.Label>
                    <Input
                        value={formData.description}
                        placeholder='Course description'
                        type='text'
                        handleChange={(e) =>
                            handleChange("description", e.target.value)
                        }
                    />
                </Form.Group>
                <Form.Group className='add-course-form-grp'>
                    <Form.Label>
                        <Header type='fW600 fS18'>Required skills</Header>
                    </Form.Label>
                    <AddSkillSelector
                        handleInputChange={handleSkillUpdate}
                        handleClear={handleSkillClear}
                        skills={formData.requiredSkills}
                    />
                </Form.Group>
                <Form.Group className='add-course-submit-grp'>
                    <Button
                        type='submit'
                        variant='primary'
                        className='add-course-submit-btn'>
                        {isEdit ? "Update" : "Add"}
                    </Button>
                </Form.Group>
            </Form>
        </div>
    );
};

const AddSkillSelector = ({ handleInputChange, skills, handleClear }) => {
    const [text, setText] = useState("");

    const handlePush = () => {
        setText("");
        handleInputChange(text);
    };

    return (
        <div className='add-skill-selector'>
            <div className='add-skill-input'>
                <Input
                    placeholder='Add skill'
                    type='text'
                    value={text}
                    handleChange={(e) => setText(e.target.value)}
                    postComponent={
                        <Button
                            type='button'
                            variant='transparent'
                            onClick={handlePush}
                            className='add-skill-submit'>
                            <DoneOutlineIcon color='#0a1828' />
                        </Button>
                    }
                />
            </div>
            <div className='add-skill-menu'>
                {skills.map((skill, index) => (
                    <div className='add-skill-item' key={`${index}-skill-item`}>
                        <Header type='fW400 fS14' className='add-skill-text'>
                            {skill}
                        </Header>
                        <Button
                            type='button'
                            variant='transparent'
                            onClick={() => handleClear(skill)}
                            className='add-skill-submit'>
                            <ClearIcon color='#0a1828' fontSize='small' />
                        </Button>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default AddEditCourse;
