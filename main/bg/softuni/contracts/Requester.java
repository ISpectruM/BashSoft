package main.bg.softuni.contracts;

import main.bg.softuni.models.SoftUniCourse;
import main.bg.softuni.models.SoftUniStudent;
import main.bg.softuni.dataStructures.SimpleSortedList;

import java.util.Comparator;

public interface Requester {

    void getStudentMarkInCourse(String courseName, String studentName);

    void getStudentsByCourse(String courseName);

    SimpleSortedList<SoftUniCourse> getAllCoursesSorted (Comparator<SoftUniCourse> cmp);

    SimpleSortedList<SoftUniStudent> getAllStudentsSorted (Comparator<SoftUniStudent> cmp);
}
