import Header from "./Header";
import "./styles/errorText.scss";
// Custom error component with common style for entire application
const ErrorText = ({ content }) => {
    return (
        <Header className='error-text' type='fS14 fW500 error'>
            {content}
        </Header>
    );
};
export default ErrorText;
