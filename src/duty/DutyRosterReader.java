package duty;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DutyRosterReader {
	/**
	 * construct a duty roster from the file information
	 * @param filename the path of the 
	 * @return
	 */
	public static DutyRoster readfromfile(String filename) {
		DutyRoster dr = DutyRoster.create();
		StringBuffer contents_sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line = br.readLine();
			while (line != null) {
				contents_sb.append(line);
				line = br.readLine();
			}
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Cann't find " + filename);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Cann't find lines from:" + filename);
		}
		
		String contents = contents_sb.toString();


		Pattern regex1 = Pattern.compile("Period\\{(\\d{4}-\\d{2}-\\d{2}),(\\d{4}-\\d{2}-\\d{2})\\}");
		Matcher matcher1 = regex1.matcher(contents);
		if (matcher1.find()) {
			dr.setStartTime(matcher1.group(1));
			dr.setEndTime(matcher1.group(2));
		}
		contents=contents.replaceAll("Period\\{(\\d{4}-\\d{2}-\\d{2}),(\\d{4}-\\d{2}-\\d{2})\\}", "");

		
		Pattern regex2 = Pattern.compile("([a-zA-Z]+)\\{([a-zA-z]+|[a-zA-Z]+\\s[a-zA-Z]+),(\\d{3}-\\d{4}-\\d{4})\\}");
		Matcher matcher2 = regex2.matcher(contents);
		while (matcher2.find()) {
			dr.addStaff(matcher2.group(1), matcher2.group(2), matcher2.group(3));
		}


		Pattern regex3 = Pattern.compile("([a-zA-Z]+)\\{(\\d{4}-\\d{2}-\\d{2}),(\\d{4}-\\d{2}-\\d{2})\\}");
		Matcher matcher3 = regex3.matcher(contents);
		while (matcher3.find()) {
			String name = matcher3.group(1);
			if(dr.getStaff(name)==null) {
				System.out.println(name);
				System.out.println("This staff hasn't been added, can't be scheduled!");
				System.exit(0);
			}
			String position = dr.getStaff(name).getPosition();
			String phone = dr.getStaff(name).getPhone();
			String start = matcher3.group(2);
			String end = matcher3.group(3);
			long days = LocalDate.parse(end).getLong(ChronoField.EPOCH_DAY)
					- LocalDate.parse(start).getLong(ChronoField.EPOCH_DAY) + 1;
			dr.arrange_manually(name, position, phone, start, days);
		}
		
		dr.display();
		return dr;

	}
}
