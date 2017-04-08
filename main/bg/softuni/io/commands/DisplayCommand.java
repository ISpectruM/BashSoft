package main.bg.softuni.io.commands;

import main.bg.softuni.annotations.Alias;
import main.bg.softuni.annotations.Inject;
import main.bg.softuni.exceptions.InvalidInputException;
import main.bg.softuni.io.OutputWriter;
import main.bg.softuni.models.SoftUniCourse;
import main.bg.softuni.models.SoftUniStudent;
import main.bg.softuni.repository.StudentsRepository;
import main.bg.softuni.dataStructures.SimpleSortedList;

import java.util.Comparator;

@Alias(value = "display")
public class DisplayCommand extends Command {

    @Inject
    private StudentsRepository repository;

    public DisplayCommand(
            String input,
            String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        String[] data = this.getData();
        if (data.length != 3){
            throw new InvalidInputException(this.getInput());
        }

        String entityToDisplay = data[1];
        String sortType = data[2];
        if (entityToDisplay.equalsIgnoreCase("students")){
            Comparator<SoftUniStudent> comparator =
                    this.createStudentComparator(sortType);
            SimpleSortedList<SoftUniStudent> studentList =
                    this.repository.getAllStudentsSorted(comparator);
            OutputWriter.writeMessageOnNewLine(studentList.joinWith(System.lineSeparator()));
        } else if (entityToDisplay.equalsIgnoreCase("courses")){
            Comparator<SoftUniCourse> comparator =
                    this.createCoursesComparator(sortType);
            SimpleSortedList<SoftUniCourse> list =
                    this.repository.getAllCoursesSorted(comparator);
            OutputWriter.writeMessageOnNewLine(list.joinWith(System.lineSeparator()));
        } else {
            throw new InvalidInputException(this.getInput());
        }
    }

    private Comparator<SoftUniCourse> createCoursesComparator(String sortType) {
        if (sortType.equalsIgnoreCase("ascending")){
            return SoftUniCourse::compareTo;
        } else if (sortType.equalsIgnoreCase("descending")){
            return Comparator.reverseOrder();
        } else {
            throw new InvalidInputException(this.getInput());
        }
    }

    public Comparator<SoftUniStudent> createStudentComparator(String sortType){
        if (sortType.equalsIgnoreCase("ascending")){
            return SoftUniStudent::compareTo;
        } else if (sortType.equalsIgnoreCase("descending")){
            return Comparator.reverseOrder();
        } else {
            throw new InvalidInputException(this.getInput());
        }
    }

}
