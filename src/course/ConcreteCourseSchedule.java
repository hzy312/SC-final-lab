package course;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.HashMap;
import java.util.Map;

import api.APIs;
import exception.IntervalConflictException;
import intervalobject.Course;
import intervalset.IntervalSet;
import multiintervalset.MultiIntervalSet;
import multiintervalset_decorator.NonOverlapMultiIntervalSet;
import multiintervalset_decorator.PeriodicMultiIntervalSet;

public class ConcreteCourseSchedule implements CourseSchedule {

	// rep
	private Map<String, Course> courseset = new HashMap<>();
	private Map<String, Integer> arranged_time = new HashMap<>();
	private PeriodicMultiIntervalSet<Course> courseschedule = PeriodicMultiIntervalSet
			.create(NonOverlapMultiIntervalSet.create(MultiIntervalSet.empty()));
	private long starttime = 0;
	private int totalweeks = 0;

	// AF
	// represents a course schedule system

	// RI
	// every course can repeat week by week

	// safety from rep exposure
	// all the fields are private

	@Override
	public boolean setStartTime(String start) {
		LocalDate ld = LocalDate.parse(start);
		if (ld.getDayOfWeek() != DayOfWeek.MONDAY) {
			System.out.println("The start day of the semester should be on Monday!");
			return false;
		}
		starttime = ld.getLong(ChronoField.EPOCH_DAY);
		return true;
	}

	@Override
	public void setTotalWeeks(int totalweeks) {
		this.totalweeks = totalweeks;
	}

	@Override
	public boolean addCourse(String course_id, String name, String teacher, String teachingposition, int hoursperweek) {
		if (courseset.containsKey(course_id)) {
			System.out.println("This course_id should be unique for every course, this course_id has been used.");
			return false;
		} else if (hoursperweek <= 1) {
			System.out.println("The hours per week should be at least 2!");
		} else if (hoursperweek % 2 == 1) {
			System.out.println("The hours per week must be an even number!");
			return false;
		}
		Course course = new Course(course_id, name, teacher, teachingposition, hoursperweek);
		arranged_time.put(course_id, 0);
		courseset.put(course_id, course);
		return true;
	}

	@Override
	public boolean arrangeCourse(String course_id, int day, int s_time) {
		if (!courseset.containsKey(course_id)) {
			System.out.println("This course doesn't exist!");
			return false;
		}
		if (s_time != 8 && s_time != 10 && s_time != 13 && s_time != 15 && s_time != 19) {
			System.out.println("The course could only be arranged on: 8~10/ 10~12/ 13~15/ 15~17/ 19~21");
			return false;
		}
		if (arranged_time.get(course_id) >= courseset.get(course_id).getHoursperweek()) {
			System.out.println("The course has reached the upper limit of hours per week!");
			return false;
		}
		long s = (day - 1) * 5;
		switch (s_time) {
		case 8:
			s += 0;
			break;
		case 10:
			s += 1;
			break;
		case 13:
			s += 2;
			break;
		case 15:
			s += 3;
			break;
		case 19:
			s += 4;
			break;
		default:
			break;
		}
		arranged_time.put(course_id, arranged_time.get(course_id) + 2);
		try {
			courseschedule.insert(s, s + 1, courseset.get(course_id));
		} catch (IntervalConflictException e2) {
			System.out.println("Courses can't overlap!");
			return false;
		}catch (Exception e) {
			e.printStackTrace();
		}
		courseschedule.setPeriodic(courseset.get(course_id), 7 * 5);
		return true;
	}

	@Override
	public void checkSchedule() {
		System.out.println(this.toString());
	}

	@Override
	public void checkScheduleofOneDay(String day) {
		StringBuffer str = new StringBuffer();
		str.append(day);
		str.append('\n');
		LocalDate ld = LocalDate.parse(day);
		if (ld.isAfter(LocalDate.ofEpochDay(starttime))
				&& (ld.getLong(ChronoField.EPOCH_DAY) - starttime) / 7 >= totalweeks) {
			System.out.println("The day you input should in the the semester!");
			return;
		}
		if (ld.isBefore(LocalDate.ofEpochDay(starttime))) {
			System.out.println("The day you input should in the the semester!");
			return;
		}
		DayOfWeek d = ld.getDayOfWeek();
		long x = 0;
		switch (d) {
		case MONDAY:
			str.append("Monday");
			x = 0;
			break;
		case TUESDAY:
			str.append("Tuesday");
			x = 1;
			break;
		case WEDNESDAY:
			str.append("Wednesday");
			x = 2;
			break;
		case THURSDAY:
			str.append("Thursday");
			x = 3;
			break;
		case FRIDAY:
			str.append("Friday");
			x = 4;
			break;
		case SATURDAY:
			str.append("Saturday");
			x = 5;
			break;
		case SUNDAY:
			str.append("Sunday");
			x = 6;
			break;
		default:
			break;
		}
		str.append('\n');
		for (Course c : courseschedule.labels()) {
			IntervalSet<Integer> tempIntervalSet = courseschedule.intervals(c);
			for (Integer i : tempIntervalSet.labels()) {
				long s = tempIntervalSet.start(i);
				if (s / 5 == x) {
					switch ((int) s % 5) {
					case 0:
						str.append("8~10\t");
						break;
					case 1:
						str.append("10~12\t");
						break;
					case 2:
						str.append("13~15\t");
						break;
					case 3:
						str.append("15~17\t");
						break;
					case 4:
						str.append("19~21\t");
						break;
					default:
						break;
					}
					str.append(c.toString());
				}
			}
		}
		System.out.println(str.toString());
	}

	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("Courses hasn't been arranged:\n");
		boolean empty = true;
		for (Map.Entry<String, Integer> entry : arranged_time.entrySet()) {
			String temp_cid = entry.getKey();
			Integer temp_time = entry.getValue();
			if (temp_time == 0) {
				ret.append(courseset.get(temp_cid).toString());
				ret.append('\n');
				empty = false;
			}
		}
		if (empty) {
			ret.append("NULL\n");
		}

		if (courseschedule.labels().size() == 0) {
			return ret.toString();
		}

		ret.append("Schedule:\n");
		for (Course c : courseschedule.labels()) {
			ret.append(c.toString());
			ret.append('\n');
			IntervalSet<Integer> tempIntervalSet = courseschedule.intervals(c);
			for (Integer i : tempIntervalSet.labels()) {
				long s = tempIntervalSet.start(i);
				switch ((int) s / 5) {
				case 0:
					ret.append("Moday\t");
					break;
				case 1:
					ret.append("Tuesday\t");
					break;
				case 2:
					ret.append("Wednesday\t");
					break;
				case 3:
					ret.append("Thursday\t");
					break;
				case 4:
					ret.append("Friday\t");
					break;
				case 5:
					ret.append("Saturday\t");
					break;
				case 6:
					ret.append("Sunday\t");
					break;
				default:
					break;
				}
				switch ((int) s % 5) {
				case 0:
					ret.append("8~10\n");
					break;
				case 1:
					ret.append("10~12\n");
					break;
				case 2:
					ret.append("13~15\n");
					break;
				case 3:
					ret.append("15~17\n");
					break;
				case 4:
					ret.append("19~21\n");
					break;
				default:
					break;
				}
			}
		}

		ret.append("\nBlank Ratio:\n");
		ret.append(APIs.calcFreeTimeRatio(courseschedule, 0, 35));
		ret.append("\nConflict Ratio:\n");
		ret.append(APIs.calcConflictRatio(courseschedule, 0, 35));
		return ret.toString();
	}
}
