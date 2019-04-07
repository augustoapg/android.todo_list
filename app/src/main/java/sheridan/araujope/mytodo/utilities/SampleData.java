package sheridan.araujope.mytodo.utilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import sheridan.araujope.mytodo.model.TaskEntity;

public class SampleData {

    private static final String SAMPLE_TASK_DESC_1 = "A simple task";
    private static final String SAMPLE_TASK_DESC_2 = "A simple task with \na line feed";
    private static final String SAMPLE_TASK_DESC_3 = "Lorem ipsum dolor sit amet, consectetur adipiscing " +
            "elit. Sed porta purus nec nisi sodales, sit amet ultrices neque finibus. " +
            "Sed condimentum egestas nunc, in semper erat elementum vitae. Curabitur et accumsan " +
            "lectus, eget lacinia mi. In eget tellus accumsan, mollis magna eget, pharetra justo. " +
            "Sed a cursus mauris. Class aptent taciti sociosqu ad litora torquent per conubia " +
            "nostra, per inceptos himenaeos. Orci varius natoque penatibus et magnis dis parturient " +
            "montes, nascetur ridiculus mus. Sed molestie, elit in commodo suscipit, enim eros " +
            "vestibulum urna, nec scelerisque ante diam semper justo. Curabitur non dui hendrerit " +
            "ex placerat commodo vitae volutpat sapien. In tempus justo quam, sed iaculis odio " +
            "fringilla ac. Pellentesque maximus metus massa, non commodo lectus malesuada a.";

    private static final String SAMPLE_TASK_TITLE_1 = "Simple";
    private static final String SAMPLE_TASK_TITLE_2 = "With break";
    private static final String SAMPLE_TASK_TITLE_3 = "Long";

    private static Date getDate(int diff) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.MILLISECOND, diff);
        return cal.getTime();
    }

    public static List<TaskEntity> getTasks() {
        List<TaskEntity> tasks = new ArrayList<>();
        // diff is used for sorting by date
        tasks.add(new TaskEntity(1, SAMPLE_TASK_TITLE_1, SAMPLE_TASK_DESC_1, getDate(0)));
        tasks.add(new TaskEntity(1, SAMPLE_TASK_TITLE_2, SAMPLE_TASK_DESC_2, getDate(-1)));
        tasks.add(new TaskEntity(1, SAMPLE_TASK_TITLE_3, SAMPLE_TASK_DESC_3, getDate(-2)));
        return tasks;
    }
}
