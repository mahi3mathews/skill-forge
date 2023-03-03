import PageHeader from "../../components/PageHeader";
import { useEffect } from "react";
import "./applicationView.scss";
import { Spinner } from "react-bootstrap";
import Card from "../../components/Card";
import { useDispatch, useSelector } from "react-redux";
import Header from "../../components/Header";
import Button from "../../components/Button";
import {
    deleteApplicationAsync,
    getAllApplicationAsync,
    getUserApplicationsAsync,
    updateApplicationStatusAsync,
} from "../../api/applications";
import DeleteOutlineIcon from "@mui/icons-material/DeleteOutline";
import { updateApplicationsList } from "../../redux/applications/applicationReducer";
import { HOME_URL } from "../../router/urls";
import { useNavigate } from "react-router-dom";

// All applications are displayed for admin whereas, only attendee's are shown to them

const TrainingApplicationView = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [applications, userId, userRole, isLoading] = useSelector(
        (states) => [
            states?.applications?.allApplications ?? [],
            states?.user?.userDetails?.userId,
            states?.user?.userDetails?.role,
            states?.applications?.isAllApplicationsLoading,
        ]
    );

    // Handle removing the application by the attendee user
    const handleDeleteApplication = async (application) => {
        if (userRole != "ADMIN") {
            let res = await deleteApplicationAsync(application?.applicationId);
            if (res.isError) {
                // handleError
            } else {
                getAllApplications();
            }
        }
    };

    // To get the respective list of applications for each user
    const getAllApplications = async () => {
        let res;
        if (userRole === "ADMIN") {
            res = await getAllApplicationAsync();
        } else {
            res = await getUserApplicationsAsync(userId);
        }
        if (res?.isError) {
            // handle coures get err
        } else dispatch(updateApplicationsList(res));
    };

    const getParticipantsLeft = (application) => {
        if (application.totalSeats <= 0) {
            return "No seats left";
        } else {
            let seatsLeft =
                Number(application.totalSeats) -
                Number(application.participants.length);
            return `Available seats: ${seatsLeft}`;
        }
    };

    const getSeatAvailability = (application) => {
        let seatsLeft =
            Number(application.totalSeats) -
            Number(application.participants.length);
        if (seatsLeft <= 0) return false;
        return true;
    };
    const handleStatusUpdate = async (status, application) => {
        let res = updateApplicationStatusAsync(
            status,
            application?.applicationId
        );
        if (res.isError) {
            // handle error
        } else {
            setTimeout(() => dispatch(getAllApplications()), 1000);
        }
    };

    useEffect(() => {
        if (userId) {
            getAllApplications();
        }
    }, [userId]);

    return (
        <div className='application'>
            <PageHeader
                className='application-header'
                content='Training Application'
            />
            <div className='application-body'>
                {isLoading ? (
                    <Spinner
                        animation='border'
                        className='application-spinner'
                    />
                ) : (
                    <div className='application-cards-container'>
                        {applications?.length <= 0 && (
                            <Card className='application-cards-empty'>
                                <Header type='fW600 fS21'>
                                    {userRole === "ADMIN"
                                        ? "There are no applications"
                                        : "You have no applications."}
                                </Header>
                                <Button
                                    variant='primary'
                                    onClick={() => navigate(HOME_URL)}>
                                    View trainings
                                </Button>
                            </Card>
                        )}
                        {applications.map((application, index) => {
                            return (
                                <Card
                                    index={index}
                                    className='application-card'>
                                    <div className='application-card-body'>
                                        <Header
                                            type='fW500 fS21'
                                            className='application-card-title'>
                                            {application?.trainingTitle}
                                        </Header>
                                        <Header
                                            type='fW400 fS21'
                                            className='application-card-title'>
                                            {application?.statement}
                                        </Header>
                                        <Header
                                            type='fW600 fS18'
                                            className='application-card-content'>
                                            {application?.status}
                                        </Header>
                                        {userRole === "ADMIN" && (
                                            <Header type='fW500 fS18'>
                                                {getParticipantsLeft(
                                                    application
                                                )}
                                            </Header>
                                        )}
                                    </div>

                                    <div className='application-card-footer'>
                                        {userRole !== "ADMIN" ? (
                                            application.status !==
                                                "ACCEPTED" && (
                                                <Button
                                                    variant='primary'
                                                    onClick={() =>
                                                        handleDeleteApplication(
                                                            application
                                                        )
                                                    }
                                                    className='application-card-delete'>
                                                    <DeleteOutlineIcon />
                                                    <Header type='fW600 fS14 secondary'>
                                                        Delete
                                                    </Header>
                                                </Button>
                                            )
                                        ) : (
                                            <ApplicationStatus
                                                status={application.status}
                                                handleClick={(status) =>
                                                    handleStatusUpdate(
                                                        status,
                                                        application
                                                    )
                                                }
                                                isSeatLeft={getSeatAvailability(
                                                    application
                                                )}
                                            />
                                        )}
                                    </div>
                                </Card>
                            );
                        })}
                    </div>
                )}
            </div>
        </div>
    );
};
const ApplicationStatus = ({ status, handleClick, isSeatLeft }) => {
    return (
        <div className='application-status'>
            {status == "PENDING" && (
                <>
                    {isSeatLeft && (
                        <Button
                            variant='primary'
                            className='application-status-accept'
                            onClick={() => handleClick("ACCEPTED")}>
                            Accept
                        </Button>
                    )}
                    <Button
                        variant='primary'
                        onClick={() => handleClick("REJECTED")}>
                        Reject
                    </Button>
                </>
            )}
        </div>
    );
};

export default TrainingApplicationView;
