package duty;

import java.util.Scanner;

public class DutyRosterApp {

	public static void main(String[] args) throws Exception {
		DutyRoster dr = DutyRoster.create();
		String name,position,phone;
		Scanner sc = new Scanner(System.in);
		int choice=0;
		System.out.println("Welcome to the DutyRosterApp!");
		System.out.println("---------------------------------------------------------");
		System.out.println("Menu:");
		System.out.println("1.Set the start time of duty interval\n"
		+"2.Set the end time of duty interval\n"
		+"3.Add a staff information\n"
		+"4.Delete a staff information\n"
		+"5.Arrange a staff to be on duty\n"
		+"6.Arrange the duty time table automatically\n"
		+"7.Check the duty time table arrangement situation\n"
		+"8.Delete the arrangemet of a staff\n"
		+"9.Arrange the duty time according to the input file\n"
		+"10.Exit\n");
		
		System.out.println();
		System.out.println("You must firstly set the start and end time! Or the system can't work properly!");
		choice = sc.nextInt();
		
		while(true){
			switch (choice) {
			case 1:
				System.out.println("Please the start time, the format is yyyy-MM-dd:");
				String start = sc.next();
				dr.setStartTime(start);
				break;
			case 2:
				System.out.println("Please the end time, the format is yyyy-MM-dd:");
				String end = sc.next();
				dr.setEndTime(end);
				break;
			case 3:
				boolean flag = true;
				while(flag) {
					System.out.println("Please input the staff's information according to the instructions!");
					System.out.println("Please input the name: ");
					name = sc.next();
					System.out.println("Please input the position:");
					position = sc.next();
					System.out.println("Please input the phone number:");
					phone = sc.next();
					dr.addStaff(name, position, phone);
					System.out.println("If you want to continue to add the staff, please input 1!");
					if(!sc.next().equals("1")) {
						flag = false;
					}
				}
				break;
			case 4:
				System.out.println("Please input the staff's information according to the instructions!");
				System.out.println("Please input the name: ");
				name = sc.next();
				System.out.println("Please input the position:");
				position = sc.next();
				System.out.println("Please input the phone number:");
				phone = sc.next();
				dr.deleteStaff(name, position, phone);
				break;
			case 5:
				System.out.println("Please input the staff's information and time interval according to the instructions!");
				System.out.println("Please input the name: ");
				name = sc.next();
				System.out.println("Please input the position:");
				position = sc.next();
				System.out.println("Please input the phone number:");
				phone = sc.next();
				System.out.println("Please input the start time, the format is yyyy-MM-dd:");
				String starttime = sc.next();
				System.out.println("Please input the lasting time:");
				long lasting = sc.nextLong();
				dr.arrange_manually(name, position, phone, starttime, lasting);
				break;
			case 6:
				try {
					dr.arrange_automatically();
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case 7:
				dr.display();
				break;
			case 8:
				System.out.println("Please input the staff's information according to the instructions!");
				System.out.println("Please input the name: ");
				name = sc.next();
				System.out.println("Please input the position:");
				position = sc.next();
				System.out.println("Please input the phone number:");
				phone = sc.next();
				dr.deleteArrangement(name, position, phone);
				break;
			case 9:
				System.out.println("Input the filepath, which should be src/dutyreaderfile/test1.txt  ~ src/dutyreaderfile/test8.txt");
				String filename = sc.next();
				if(!filename.matches("src/dutyreaderfile/test[1-8]{1}.txt")) {
					System.out.println("Input file format error!");
					System.out.println("You should input src/dutyreaderfile/test1.txt  ~  ./src/dutyreaderrile/test8.txt");
					break;
				}
				dr = DutyRosterReader.readfromfile(filename);
				break;
			case 10:
				sc.close();
				System.out.println("Thanks for using this DutyRosterApp! Wish you a happy day!");
				System.exit(0);
			default:
				System.out.println("The input should be 1~9, please input again!\n"+"Input 9 to exit the app!");
				break;
			}
			System.out.println("Menu:");
			System.out.println("1.Set the start time of duty interval\n"
			+"2.Set the end time of duty interval\n"
			+"3.Add a staff information\n"
			+"4.Delete a staff information\n"
			+"5.Arrange a staff to be on duty\n"
			+"6.Arrange the duty time table automatically\n"
			+"7.Check the duty time table arrangement situation\n"
			+"8.Delete the arrangemet of a staff\n"
			+"9.Arrange the duty time according to the input file\n"
			+"10.Exit\n");
			choice = sc.nextInt();
		}
		
	}
	

}
