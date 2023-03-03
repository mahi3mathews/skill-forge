import React, { useEffect } from "react";
import PageHeader from "../../components/PageHeader";
import "./courseView.scss";
import { Spinner } from "react-bootstrap";
import Card from "../../components/Card";
import { useDispatch, useSelector } from "react-redux";
import Header from "../../components/Header";
import { useNavigate } from "react-router-dom";
import { ADD_COURSE_URL, EDIT_COURSE_URL } from "../../router/urls";

import Button from "../../components/Button";
import { AddComponent } from "../trainingView";
import { getAllCoursesAsync } from "../../api/courses";
import { updateCoursesList } from "../../redux/courses/courseReducer";

// To view the list of courses that have been added to the system
// Admin only has access to this view

const CoursesView = () => {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const [courses, userRole, isLoading] = useSelector((states) => [
        states?.courses?.allCourses ?? [],
        states?.user?.userDetails?.role,
        states?.courses?.isAllCoursesLoading,
    ]);
    const handleAddCourse = () => navigate(ADD_COURSE_URL);

    const getAllCourses = async () => {
        let res = await getAllCoursesAsync();
        if (res?.isError) {
            // handle coures get err
        } else dispatch(updateCoursesList(res));
    };
    // To handle the edit click and navigate user to edit-course page
    const handleEditClick = (course) => {
        navigate(EDIT_COURSE_URL, { state: { course } });
    };

    useEffect(() => {
        if (userRole === "ADMIN") {
            getAllCourses();
        }
    }, [userRole]);

    return (
        <div className='courses-view'>
            <PageHeader content='All Courses' className='courses-header' />
            <div className='course-body'>
                {isLoading ? (
                    <Spinner animation='border' className='course-spinner' />
                ) : (
                    <div className='course-cards-container'>
                        {courses.map((course, index) => {
                            return (
                                <Card index={index} className='course-card'>
                                    <div className='course-card-body'>
                                        <Header
                                            type='fW500 fS21'
                                            className='course-card-title'>
                                            {course?.title}
                                        </Header>
                                        <Header
                                            type='fW400 fS18'
                                            className='course-card-content'>
                                            {course?.description}
                                        </Header>
                                    </div>
                                    <div className='course-card-footer'>
                                        <Button
                                            onClick={() =>
                                                handleEditClick(course)
                                            }
                                            variant='primary'
                                            className='course-card-apply'>
                                            Edit
                                        </Button>
                                    </div>
                                </Card>
                            );
                        })}
                        <AddComponent
                            url={ADD_COURSE_URL}
                            handleClick={handleAddCourse}
                            emptyMessage={
                                courses?.length < 0
                                    ? "You have no courses."
                                    : ""
                            }
                            contentMessage='Add new course.'
                        />
                    </div>
                )}
            </div>
        </div>
    );
};

export default CoursesView;
