package course;

import org.junit.Test;

public class CourseScheduleTest {

	@Test
	public void coursescheduleTest() {
		CourseSchedule cs = CourseSchedule.create();
		cs.setStartTime("2021-07-05");
		cs.setTotalWeeks(18);
		cs.addCourse("cs001", "data structure", "wang", "201", 6);
		cs.addCourse("cs002", "c", "li", "302", 8);
		cs.addCourse("cs003", "algorithm", "zhao", "401", 4);
		cs.arrangeCourse("cs001", 3, 8);
		cs.arrangeCourse("cs003", 2, 10);
		cs.arrangeCourse("cs002", 7, 13);
		cs.checkSchedule();
	}
	
}
