package bg.softuni.io.commands;

import bg.softuni.annotations.Alias;
import bg.softuni.annotations.Inject;
import bg.softuni.exceptions.InvalidInputException;
import bg.softuni.io.IOManager;
import bg.softuni.io.OutputWriter;
import bg.softuni.judge.Tester;
import bg.softuni.models.SoftUniCourse;
import bg.softuni.models.SoftUniStudent;
import bg.softuni.network.DownloadManager;
import bg.softuni.repository.StudentsRepository;
import bg.softuni.dataStructures.SimpleSortedList;

import javax.naming.spi.DirectoryManager;
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
