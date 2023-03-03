import React from "react";
import "./trainingView.scss";
import { Image, Spinner } from "react-bootstrap";
import Card from "../../components/Card";
import { useSelector } from "react-redux";
import Header from "../../components/Header";
import { useNavigate } from "react-router-dom";
import {
    ADD_APPLICATION_URL,
    ADD_TRAINING_URL,
    EDIT_TRAINING_URL,
} from "../../router/urls";
import AddCircleOutlineIcon from "@mui/icons-material/AddCircleOutline";
import PageHeader from "../../components/PageHeader";
import Button from "../../components/Button";

// Training list component to view all trainings created by admin.

const TrainingView = () => {
    const navigate = useNavigate();
    const [trainings, isLoading, userRole] = useSelector((states) => [
        states?.training?.allTrainings ?? [],
        states?.training?.isLoadingAllTraining ?? false,
        states?.user?.userDetails?.role,
    ]);

    const getTrainingDateFormatted = (date) => {
        const newDate = new Date(date);
        return `${
            newDate.getMonth() + 1
        }/${newDate.getDate()}/${newDate.getFullYear()}`;
    };

    const handleAddTraining = () => navigate(ADD_TRAINING_URL);

    const handleTrainingClick = (training) => {
        const isAdmin = userRole === "ADMIN";
        if (isAdmin) {
            navigate(EDIT_TRAINING_URL, { state: { training: training } });
        } else {
            navigate(ADD_APPLICATION_URL, { state: { training: training } });
        }
    };

    return (
        <div className='training-view'>
            <PageHeader className='training-header' content='All Trainings' />

            <div className='training-body'>
                {isLoading ? (
                    <Spinner animation='border' className='training-spinner' />
                ) : (
                    <div className='training-cards-container'>
                        {trainings.map((training, index) => {
                            return (
                                <Card index={index} className='training-card'>
                                    <div className='training-card-header'>
                                        <Image
                                            thumbnail
                                            fluid
                                            src={training?.imgUrl}
                                            className='training-card-img'
                                        />
                                    </div>
                                    <div className='training-card-body'>
                                        <Header
                                            type='fW500 fS21'
                                            className='training-card-title'>
                                            {training?.title}
                                        </Header>
                                        <Header
                                            type='fW400 fS18'
                                            className='training-card-content'>
                                            {training?.description}
                                        </Header>
                                        <Header type='fW500 fS18'>
                                            {getTrainingDateFormatted(
                                                training?.date
                                            )}
                                        </Header>
                                    </div>
                                    <div className='training-card-footer'>
                                        <Button
                                            onClick={() =>
                                                handleTrainingClick(training)
                                            }
                                            variant='primary'
                                            className='training-card-apply'>
                                            {userRole === "ADMIN"
                                                ? "Edit"
                                                : "Apply"}
                                        </Button>
                                    </div>
                                </Card>
                            );
                        })}
                        {userRole === "ADMIN" && (
                            <AddComponent
                                handleClick={handleAddTraining}
                                emptyMessage={
                                    trainings?.length < 0
                                        ? "You have no trainings."
                                        : ""
                                }
                                contentMessage='Add new training.'
                            />
                        )}
                    </div>
                )}
            </div>
        </div>
    );
};

// Card component to let admin create more trainings/courses
export const AddComponent = ({ handleClick, emptyMessage, contentMessage }) => {
    return (
        <div className='add-training-container'>
            <Card className='add-training-card' onClick={handleClick}>
                <AddCircleOutlineIcon
                    className='add-training-icon'
                    color='#0a1828'
                    fontSize='large'
                />
                <Header type='fS21 fW500' className='add-training-text'>
                    {emptyMessage && <div>{emptyMessage}</div>}
                    <div>{contentMessage}</div>
                </Header>
            </Card>
        </div>
    );
};

export default TrainingView;
