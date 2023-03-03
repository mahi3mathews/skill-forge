import { useDispatch, useSelector } from "react-redux";
import {
    updateAllTrainingLoader,
    updateTrainingList,
} from "../../redux/trainings/trainingReducer";
import TrainingView from "../trainingView";
import { useEffect } from "react";
import { getAllTrainingsAsync } from "../../api/trainings";
import "./home.scss";
import PageHeader from "../../components/PageHeader";

// Home is where all the trainings are listed for all users to view.

const Home = () => {
    const dispatch = useDispatch();
    const [userName, userId] = useSelector((states) => [
        states?.user?.userDetails?.userName ?? "",
        states?.user?.userDetails?.userId ?? "",
    ]);

    const getAllTrainings = async () => {
        dispatch(updateAllTrainingLoader(true));
        const allTrainings = await getAllTrainingsAsync(userId);
        if (allTrainings?.length > 0) {
            dispatch(updateTrainingList(allTrainings));
        }
        dispatch(updateAllTrainingLoader(false));
    };

    useEffect(() => {
        if (userId) getAllTrainings();
    }, [userId]);

    return (
        <div className='home'>
            <PageHeader
                className='home-header'
                content={` Welcome ${userName},`}
            />
            <div className='home-training-list'>
                {/* Training list component */}
                <TrainingView />
            </div>
        </div>
    );
};

export default Home;
