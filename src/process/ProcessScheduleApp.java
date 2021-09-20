package process;

import java.util.Scanner;

public class ProcessScheduleApp {

	public static void main(String[] args) {
		System.out.println("Welcome to ProcessSechdule simulator!");
		System.out.println("----------------------------------------------------");
		ProcessSchedule ps = ProcessSchedule.create();
		System.out.println("Please input a set of process, "
				+ "\nthe process format is pid name shortest_exe_time longest_exe_time(each term delimted by one blank space):");
		boolean input_flag = true;
		String pid, name;
		long shortest_exe_time, longest_exe_time;
		Scanner sc = new Scanner(System.in);
		pid = sc.next();
		name = sc.next();
		shortest_exe_time = sc.nextLong();
		longest_exe_time = sc.nextLong();
		while(!ps.fork(pid, name, shortest_exe_time, longest_exe_time)) {
			System.out.println("Please input again!");
			pid = sc.next();
			name = sc.next();
			shortest_exe_time = sc.nextLong();
			longest_exe_time = sc.nextLong();
		}
		System.out.println("If you want to fork another process, input 1." + "\nOtherwise to terminate the input.");
		if (sc.nextInt() != 1) {
			input_flag = false;
		}
		while (input_flag) {
			System.out.println("Please input a set of process, "
					+ "\nthe process format is pid name shortest_exe_time longest_exe_time(each term delimted by one blank space):");
			pid = sc.next();
			name = sc.next();
			shortest_exe_time = sc.nextLong();
			longest_exe_time = sc.nextLong();
			while(!ps.fork(pid, name, shortest_exe_time, longest_exe_time)) {
				System.out.println("Please input again!");
				pid = sc.next();
				name = sc.next();
				shortest_exe_time = sc.nextLong();
				longest_exe_time = sc.nextLong();
			}
			System.out.println("If you want to fork another process, input 1." + "\nOtherwise to terminate the input.");
			if (!sc.next().equals("1")) {
				input_flag = false;
			}
		}

		System.out.println();
		System.out.println("Attention: if all the processes have been terminated, "
				+ "\nthe app will exit automatically and display the schedule situation!");
		int r = 0;
		while (true) {
			System.out.println("Menu:");
			System.out.println("1.schedule a process stochastically");
			System.out.println("2.schedule a process with shortestfrist algorithm");
			System.out.println("3.cpu works with no process running");
			System.out.println("4.display the current situation");
			System.out.println("5.schedule all the processes automatically");
			System.out.println("6.exit");
			r = sc.nextInt();
			switch (r) {
			case 1:
				ps.schedule_stochastic();
				break;
			case 2:
				ps.schedule_shortestfirst();
				break;
			case 3:
				ps.noProcessRun();
				break;
			case 4:
				ps.display();
				break;
			case 5:
				ps.schedule_automatically();
				break;
			case 6:
				ps.display();
				sc.close();
				System.exit(0);
			default:
				System.out.println("the choice you input should be 1~6");
				break;
			}
			if (ps.isAllTerminated()) {
				break;
			}
		}
		ps.display();
		sc.close();
	}
}
