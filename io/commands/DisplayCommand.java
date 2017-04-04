package bg.softuni.io.commands;

import bg.softuni.exceptions.InvalidInputException;
import bg.softuni.io.IOManager;
import bg.softuni.io.OutputWriter;
import bg.softuni.judge.Tester;
import bg.softuni.models.SoftUniCourse;
import bg.softuni.models.SoftUniStudent;
import bg.softuni.network.DownloadManager;
import bg.softuni.repository.StudentsRepository;
import bg.softuni.dataStructures.SimpleSortedList;

import java.util.Comparator;

public class DisplayCommand extends Command {
    public DisplayCommand(String input, String[] data, Tester tester, StudentsRepository repository, DownloadManager downloadManager, IOManager ioManager) {
        super(input, data, tester, repository, downloadManager, ioManager);
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
                    this.getRepository().getAllStudentsSorted(comparator);
            OutputWriter.writeMessageOnNewLine(studentList.joinWith(System.lineSeparator()));
        } else if (entityToDisplay.equalsIgnoreCase("courses")){
            Comparator<SoftUniCourse> comparator =
                    this.createCoursesComparator(sortType);
            SimpleSortedList<SoftUniCourse> list =
                    this.getRepository().getAllCoursesSorted(comparator);
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
