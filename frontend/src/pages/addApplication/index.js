import { useState } from "react";
import { Form } from "react-bootstrap";
import Button from "../../components/Button";
import Header from "../../components/Header";

import "./addApplication.scss";
import { useSelector } from "react-redux";
import { APPLICATIONS_URL } from "../../router/urls";
import { useNavigate, useLocation } from "react-router-dom";
import { addApplicationAsync } from "../../api/applications";
import ErrorText from "../../components/ErrorText";

// Add application is the page for attendee to complete a form to apply for a training.

const AddApplication = () => {
    const navigate = useNavigate();
    const location = useLocation();

    const training = location?.state?.training ?? {};
    const [userId] = useSelector((state) => [state.user.userDetails.userId]);
    const [formData, setFormData] = useState({
        trainingId: training?.trainingId,
        attendeeId: userId,
        statement: "",
    });
    const [isError, setError] = useState("");

    const handleChange = (value) => {
        setFormData((prevState) => ({
            ...prevState,
            statement: value,
        }));
    };

    // Form validation checks to proceed
    const isFormValid = () => {
        if (!formData.statement) {
            setError("Please provide a statement.");
            return false;
        }
        return true;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        e.stopPropagation();
        if (isFormValid()) {
            const res = await addApplicationAsync(formData);
            if (res.isError) {
                setError(res.message);
            } else {
                navigate(APPLICATIONS_URL);
            }
        }
    };

    return (
        <div className='add-application'>
            <Header type='fS28 fW500' className='add-application-header'>
                Application Form for {training?.title}
            </Header>
            <Form onSubmit={handleSubmit}>
                <Form.Group className='add-application-form-grp'>
                    <Form.Label>
                        <Header type='fW600 fS18'>Applicant statement</Header>
                    </Form.Label>
                    <Form.Control
                        className='add-application-statement'
                        placeholder='Statement'
                        type='text'
                        as='textarea'
                        onChange={(e) => handleChange(e.target.value)}
                    />
                </Form.Group>

                <Form.Group className='add-application-submit-grp'>
                    <Button
                        type='submit'
                        variant='primary'
                        className='add-application-submit-btn'>
                        Apply
                    </Button>
                    {isError && <ErrorText content={isError} />}
                </Form.Group>
            </Form>
        </div>
    );
};

export default AddApplication;
