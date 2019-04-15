/**
 * Project: My To Do
 * Author: Augusto A P Goncalez
 * Date: Apr. 17, 2019
 *
 * Description: This app allows a user to create new tasks with title, due date and description. It
 * also allows the user to edit any of the fields, as well as deleting a task.
 */

package sheridan.araujope.mytodo.utilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import sheridan.araujope.mytodo.database.TaskEntity;

public class SampleData {

    private static final String SAMPLE_TASK_TITLE_1 = "Finish Android assignment";
    private static final String SAMPLE_TASK_TITLE_2 = "Buy groceries";
    private static final String SAMPLE_TASK_TITLE_3 = "Study for Java final";

    private static final String SAMPLE_TASK_DESC_1 = "Create a todo list with options to add, edit" +
            "and delete notes";
    private static final String SAMPLE_TASK_DESC_2 = "Eggs\nMilk\nChocolate\nBananas";
    private static final String SAMPLE_TASK_DESC_3 = "Need to study Spring Boot and API Endpoints";


    private static Date getDate(int diff) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_YEAR, diff);
        return cal.getTime();
    }

    public static List<TaskEntity> getTasks() {
        List<TaskEntity> tasks = new ArrayList<>();
        // diff is used for sorting by date
        tasks.add(new TaskEntity(1, SAMPLE_TASK_TITLE_1, SAMPLE_TASK_DESC_1, getDate(0)));
        tasks.add(new TaskEntity(2, SAMPLE_TASK_TITLE_2, SAMPLE_TASK_DESC_2, getDate(3)));
        tasks.add(new TaskEntity(3, SAMPLE_TASK_TITLE_3, SAMPLE_TASK_DESC_3, getDate(5)));
        return tasks;
    }
}
