import Header from "./Header";
import "./styles/pageheader.scss";

// Custom page header component with common style for entire application
const PageHeader = ({ content, className }) => {
    return (
        <div className={`page-header ${className}`}>
            <Header type='fS24 fW600'>{content}</Header>
        </div>
    );
};

export default PageHeader;
