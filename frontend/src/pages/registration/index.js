import "./registration.scss";
import Card from "react-bootstrap/Card";
import Button from "../../components/Button";
import Form from "react-bootstrap/Form";
import Header from "../../components/Header";
import Input from "../../components/Input";
import { useNavigate } from "react-router-dom";
import { LOGIN_URL } from "../../router/urls";
import { useState } from "react";
import { registerUserAsync } from "../../api/user";
import validator from "validator";
import ErrorText from "../../components/ErrorText";

// User registration for attendees

const Registration = () => {
    const navigate = useNavigate();
    const [error, setError] = useState("");
    const [formDetails, setFormDetails] = useState({
        userName: "",
        email: "",
        mobNumber: "",
        password: "",
    });
    // Registration form details are validated here
    const isFormValid = () => {
        if (!validator.isEmail(formDetails.email)) {
            setError("Please enter a valid email.");
            return false;
        } else {
            let isFormNotEmpty = true;
            Object.values(formDetails).forEach((value) => {
                if (!value) {
                    setError("Please complete the form.");
                    isFormNotEmpty = false;
                }
            });
            return isFormNotEmpty;
        }
    };
    const handleSubmit = async (e) => {
        e.preventDefault();
        e.stopPropagation();
        if (isFormValid()) {
            const output = await registerUserAsync(formDetails);

            if (!output.isError) {
                navigate(LOGIN_URL);
            } else {
                setError(output.message);
            }
        }
    };
    const handleMobileChange = (e) => {
        const re = /^[0-9\b]+$/;
        if (
            (e.target.value === "" || re.test(e.target.value)) &&
            e?.target?.value?.length <= 11
        ) {
            setFormDetails((prevState) => ({
                ...prevState,
                mobNumber: e.target.value,
            }));
        }
    };
    const handleChange = (type, value) => {
        setFormDetails((prevState) => ({ ...prevState, [type]: value }));
    };
    return (
        <div className='registration-container'>
            <Card className='registration-box'>
                <div className='registration-header'>
                    <Header type='fS26 fW600'>Registration</Header>
                </div>
                <Form className='registration-form' onSubmit={handleSubmit}>
                    <Form.Group
                        className='registration-form-group'
                        controlId='formBasicEmail'>
                        <Form.Label>
                            <Header type='fW600 fS18'>Email</Header>
                        </Form.Label>
                        <Input
                            placeholder='Enter email'
                            type='email'
                            handleChange={(e) =>
                                handleChange("email", e.target.value)
                            }
                        />
                    </Form.Group>
                    <Form.Group
                        className='registration-form-group'
                        controlId='formBasicUsername'>
                        <Form.Label>
                            <Header type='fW600 fS18'>Username</Header>
                        </Form.Label>
                        <Input
                            placeholder='Enter username'
                            handleChange={(e) =>
                                handleChange("userName", e.target.value)
                            }
                        />
                    </Form.Group>
                    <Form.Group
                        className='registration-form-group'
                        controlId='formBasicPhNunber'>
                        <Form.Label>
                            <Header type='fW500 fS18'>Mobile number</Header>
                        </Form.Label>
                        <Input
                            type='text'
                            value={formDetails.mobNumber}
                            placeholder='Enter mobile number'
                            handleChange={handleMobileChange}
                        />
                    </Form.Group>
                    <Form.Group
                        className='registration-form-group'
                        controlId='formBasicEmail'>
                        <Form.Label>
                            <Header type='fW500 fS18'>Password</Header>
                        </Form.Label>
                        <Input
                            type='password'
                            placeholder='Enter password'
                            handleChange={(e) =>
                                handleChange("password", e.target.value)
                            }
                        />
                    </Form.Group>
                    <Form.Group className='registration-submit-group'>
                        <Button variant='primary' type='submit'>
                            Register
                        </Button>
                        {error && <ErrorText content={error} />}
                    </Form.Group>
                </Form>
            </Card>
        </div>
    );
};

export default Registration;
