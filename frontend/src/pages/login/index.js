import Card from "react-bootstrap/Card";
import Button from "../../components/Button";
import Form from "react-bootstrap/Form";
import Header from "../../components/Header";
import "./login.scss";
import Input from "../../components/Input";
import { useNavigate } from "react-router-dom";
import { HOME_URL, REGISTER_URL } from "../../router/urls";
import { useState } from "react";
import { loginAsync } from "../../api/user";
import { useDispatch } from "react-redux";
import { setUserDetails } from "../../redux/users/userReducer";

// Login page for all users.

const Login = () => {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const [error, setError] = useState("");

    const [loginForm, setLoginForm] = useState({ userName: "", password: "" });

    const handleSubmit = async (e) => {
        // handleApi call to login
        e.preventDefault();
        let res = await loginAsync(loginForm);
        if (res?.userId) {
            localStorage.setItem("isUserLoggedIn", true);
            localStorage.setItem("userId", res?.userId);
            dispatch(setUserDetails(res));
            navigate(HOME_URL);
        } else {
            setError(res.message);
        }
    };

    const handleChange = (type, value) => {
        setLoginForm((prevState) => ({ ...prevState, [type]: value }));
    };

    const handleRegisterClick = () => {
        navigate(REGISTER_URL);
    };

    return (
        <div className='login-container'>
            <Card className='login-box'>
                <div className='login-header'>
                    <Header type='fS26 fW600'>Login</Header>
                </div>
                <Form className='login-form' onSubmit={handleSubmit}>
                    <Form.Group
                        className='login-form-group'
                        controlId='formBasicEmail'>
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
                        className='login-form-group'
                        controlId='formBasicPwd'>
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
                    <Form.Group className='login-submit-group'>
                        <Button variant='primary' type='submit'>
                            Login
                        </Button>
                    </Form.Group>
                </Form>
                <div className='login-register'>
                    <Header type='fS18 fW500'>Don't have an account?</Header>
                    <Button
                        variant='transparent'
                        className='login-register-btn'
                        onClick={handleRegisterClick}>
                        Register here!
                    </Button>
                </div>
            </Card>
        </div>
    );
};

export default Login;
