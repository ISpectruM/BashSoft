package bg.softuni.contracts;

import bg.softuni.models.SoftUniCourse;
import bg.softuni.models.SoftUniStudent;
import bg.softuni.dataStructures.SimpleSortedList;

import java.util.Comparator;

public interface Requester {

    void getStudentMarkInCourse(String courseName, String studentName);

    void getStudentsByCourse(String courseName);

    SimpleSortedList<SoftUniCourse> getAllCoursesSorted (Comparator<SoftUniCourse> cmp);

    SimpleSortedList<SoftUniStudent> getAllStudentsSorted (Comparator<SoftUniStudent> cmp);
}
