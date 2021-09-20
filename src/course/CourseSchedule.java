package course;

public interface CourseSchedule {

	public static CourseSchedule create() {
		return new ConcreteCourseSchedule();
	}
	
	/**
	 * set the start time of this semester
	 * @param start 
	 */
	boolean setStartTime(String start);
	
	/**
	 * set the total weeks of this semester
	 * @param totalweeks
	 */
	void setTotalWeeks(int totalweeks);
	
	/**
	 * add a course to the system, every course has unique a course_id
	 * @param course_id this is unique for every course
	 * @param name
	 * @param teacher
	 * @param teachingposition
	 * @param hoursperweek must be even number
	 */
	boolean addCourse(String course_id, String name, String teacher, String teachingposition, int hoursperweek);
	
	/**
	 * arrange a course
	 * @param course_id
	 * @param day 1~7 represent Monday~Sunday
	 * @param s_time the start time of this course
	 * @return true if the arrangement succeeds, otherwise false
	 */
	boolean arrangeCourse(String course_id, int day, int s_time);
	
	/**
	 * check which courses have not been arranged
	 * check the blank ratio
	 * check the conflict ratio
	 */
	void checkSchedule();
	
	/**
	 * check the schedule of one day
	 * @param day
	 */
	void checkScheduleofOneDay(String day);
	
}
