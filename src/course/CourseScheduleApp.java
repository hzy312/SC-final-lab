package course;

import java.util.Scanner;

public class CourseScheduleApp {
	public static void main(String[] args) {
		System.out.println("Welcome to the course schedule system!");
		System.out.println("--------------------------------------------");
		CourseSchedule cs = CourseSchedule.create();
		int choice = 0;
		Scanner sc = new Scanner(System.in);
		System.out.println("Menu:");
		System.out.println("1.add a course to the system");
		System.out.println("2.set the start time of the semester");
		System.out.println("3.set the total weeks of this semester");
		System.out.println("4.arrange a course");
		System.out.println("5.check the course schedule");
		System.out.println("6.chech one day of the schedule");
		System.out.println("7.exit");
		System.out.println();
		System.out.println("You must firstly set the start and total weeks! Or the system can't work properly!");
		choice = sc.nextInt();
		while(true) {
			switch (choice) {
			case 1:
				System.out.println("Please input the course information according to the instructions:");
				System.out.println("the format is course_id name teacher teachingposition hoursperweek"
						+ "(each term is delimted by a single blank space)");
				System.out.println("Attention: course_id is unique for every course,\n and the hoursperweek should be an even number.");
				System.out.println("Tips: cs001 DataStructure ZhangSan ZhengXin201 8 is a legal example~");
				String course_id=sc.next();
				String name=sc.next();
				String teacher=sc.next();
				String teachingposition=sc.next();
				int hoursperweek=sc.nextInt();
				if(!cs.addCourse(course_id, name, teacher, teachingposition, hoursperweek)) {
					System.out.println("Information input error!");
				}
				break;
			case 2:
				System.out.println("Please input the starttime of the semester.\nThe format is yyyy-MM-dd,"
						+ "and the start day of the semeste should be on Monday!");
				System.out.println("Tips: 2021-07-05 is a legal example~");
				String day = sc.next();
				if(!cs.setStartTime(day)) {
					System.out.println("Input error!");
				}
				break;
			case 3:
				System.out.println("Please the total weeks of the semester.");
				int weeks = sc.nextInt();
				cs.setTotalWeeks(weeks);
				break;
			case 4:
				System.out.println("Please input the course arrangement information according to the instructions:");
				System.out.println("the format is course_id weekday start_time(each term is delimted by a single blank space)");
				System.out.println("the weekday is 1~7, which represents Monday~Sunday");
				System.out.println("the course could only be scheduled on 8~10/ 10~12/ 13~15/ 15~17/ 19~21, so the start time you input must be 8/10/13/15/19");
				System.out.println("Tips: cs001 4 8 is a legal example which means cs001 was scheduled on Thursday during 8~10");
				String c = sc.next();
				int w = sc.nextInt();
				int s = sc.nextInt();
				if(!cs.arrangeCourse(c, w, s)) {
					System.out.println("Arrangement error!");
				}
				break;
			case 5:
				cs.checkSchedule();
				break;
			case 6:
				System.out.println("Please input the date, the format is yyyy-MM-dd");
				String day1 = sc.next();
				cs.checkScheduleofOneDay(day1);
				break;
			case 7:
				sc.close();
				System.out.println("Wish you a happy day!");
				System.exit(0);
				break;
			default:
				System.out.println("The input should be 1~7. Please input again.");
				break;
			}
			System.out.println("Menu:");
			System.out.println("1.add a course to the system");
			System.out.println("2.set the start time of the semester");
			System.out.println("3.set the total weeks of this semester");
			System.out.println("4.arrange a course");
			System.out.println("5.check the course schedule");
			System.out.println("6.chech one day of the schedule");
			System.out.println("7.exit");
			choice = sc.nextInt();
		}
	}
}
