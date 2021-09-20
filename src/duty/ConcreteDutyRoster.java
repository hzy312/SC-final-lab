package duty;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import intervalobject.Employee;
import intervalset.IntervalSet;
import multiintervalset.MultiIntervalSet;
import multiintervalset_decorator.NoBlankMultiIntervalSet;
import multiintervalset_decorator.NonOverlapMultiIntervalSet;

public class ConcreteDutyRoster implements DutyRoster {
	// rep
	private NoBlankMultiIntervalSet<Employee> calendar = NoBlankMultiIntervalSet
			.create(NonOverlapMultiIntervalSet.create(MultiIntervalSet.empty()));
	private Set<Employee> staffset = new HashSet<>();
	private long start;
	private long end;

	// AF
	// represents a duty roster system
	
	// RI
	// dutytime can't overlap
	
	// safety from rep exposure
	// all the fields are private
	
	public ConcreteDutyRoster(String starttime, String endtime) {
		long start = LocalDate.parse(starttime).getLong(ChronoField.EPOCH_DAY);
		long end = LocalDate.parse(endtime).getLong(ChronoField.EPOCH_DAY);
		this.start = start;
		this.end = end + 1;
		calendar.setStart(start);
		calendar.setEnd(end + 1);
	}

	public ConcreteDutyRoster() {

	}

	public boolean addStaff(String name, String position, String phone) {
		Employee employee = new Employee(name, position, phone);
		if (staffset.contains(employee)) {
			System.out.println("this person has been added!");
			return false;
		} else {
			staffset.add(employee);
			return true;
		}
	}

	@Override
	public boolean deleteStaff(String name, String position, String phone) {
		Employee employee = new Employee(name, position, phone);
		if (staffset.contains(employee)) {
			if (isarranged(name, position, phone)) {
				System.out
						.println("This staff has been arranged to be on duty, you can't delete him/her from the system!"
								+ "\nIf you want to delete her/him, you should delete the arrangement of her/him!");
				return false;
			} else {
				staffset.remove(employee);
				calendar.remove(employee);
				return true;
			}
		} else {
			System.out.println("This person is not a staff in the system!");
			return false;
		}
	}

	@Override
	public void setStartTime(String starttime) {
		long start = LocalDate.parse(starttime).getLong(ChronoField.EPOCH_DAY);
		calendar.setStart(start);
		this.start = start;
	}

	@Override
	public void setEndTime(String endtime) {
		long end = LocalDate.parse(endtime).getLong(ChronoField.EPOCH_DAY);
		calendar.setEnd(end + 1);
		this.end = end + 1;
	}

	@Override
	public boolean isarranged(String name, String position, String phone) {
		if (calendar.labels().contains(new Employee(name, position, phone))) {
			return true;
		}
		return false;
	}

	@Override
	public boolean arrange_manually(String name, String position, String phone, String startime, long days) {
		long start = LocalDate.parse(startime).getLong(ChronoField.EPOCH_DAY);
		if (start < this.start || start >= this.end || start + days > this.end) {
			System.out.println("The interval time you input should during the start and the end time!");
			return false;
		}
		if (isarranged(name, position, phone)) {
			try {
				calendar.insert(start, start + days, new Employee(name, position, phone));
			} catch (Exception e) {
				System.out.println("Intervals can't overlapped!");
				return false;
			}
			return true;
		} else {
			if (staffset.contains(new Employee(name, position, phone))) {
				try {
					calendar.insert(start, start + days, new Employee(name, position, phone));
				} catch (Exception e) {
					System.out.println("Intervals can't overlapped!");
					return false;
				}
				return true;
			} else {
				System.out.println("This person isn't a staff, please add person to the system first!");
				return false;
			}
		}
	}

	@Override
	public boolean arrange_automatically() {
		int i = 0;
		long now = this.start;
		for (Employee e : staffset) {
			if (i == staffset.size() - 1) {
				if(!arrange_manually(e.getName(), e.getPosition(), e.getPhone(), LocalDate.ofEpochDay(now).toString(),
						this.end - now)) {
					return false;
				}
			} else {
				if(!arrange_manually(e.getName(), e.getPosition(), e.getPhone(), LocalDate.ofEpochDay(now).toString(), 1)) {
					return false;
				}
			}
			i++;
			now++;
		}
		return true;
	}

	@Override
	public void display() {
		System.out.print(this.toString());
	}

	@Override
	public boolean isNoBlank() {
		return calendar.isNoBlank();
	}

	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer();
		List<Long> startList = new ArrayList<>();
		Map<Long, Long> timeline = new HashMap<>();
		Map<Long, Employee> staffduty = new HashMap<>();
		long blank_sum = 0;
		double ratio = 0;
		for (Employee e : calendar.labels()) {
			IntervalSet<Integer> is = calendar.intervals(e);
			long start_time = 0;
			long end_time = 0;
			for(Integer i:is.labels()) {
				start_time= is.start(i);
				end_time = is.end(i);
				startList.add(start_time);
				timeline.put(start_time, end_time);
				staffduty.put(start_time, e);
			}
		}
		
		if(startList.size()!=0) {
			Collections.sort(startList);
			for(long l:startList) {
				ret.append("duty time: " + LocalDate.ofEpochDay(l).toString() + " -- "
						+ LocalDate.ofEpochDay(timeline.get(l)).minusDays(1).toString() + '\t' + staffduty.get(l).toString() + '\n');
			}
		}
		
		if (!isNoBlank()) {
			ret.append("\nNo arranged interval: \n");
			Collections.sort(startList);
			if(startList.size()==0) {
				ret.append(LocalDate.ofEpochDay(start).toString() + " -- "
						+ LocalDate.ofEpochDay(end).minusDays(1).toString() + '\n');
				return ret.toString();
			}
			long now = timeline.get(startList.get(0));
			if (startList.get(0) != this.start) {
				ret.append(LocalDate.ofEpochDay(start).toString() + " -- "
						+ LocalDate.ofEpochDay(startList.get(0)).minusDays(1).toString() + '\n');
				blank_sum += startList.get(0) - this.start;
			}
			for (int i = 1; i < startList.size(); i++) {
				if (now != startList.get(i)) {
					ret.append(LocalDate.ofEpochDay(now).toString() + " -- "
							+ LocalDate.ofEpochDay(startList.get(i)).minusDays(1).toString() + '\n');
				}
				blank_sum += (startList.get(i) - now);
				now = timeline.get(startList.get(i));
			}
			if (now < this.end) {
				ret.append(LocalDate.ofEpochDay(now).toString() + " -- "
						+ LocalDate.ofEpochDay(this.end).minusDays(1).toString() + '\n');
				blank_sum += (this.end - now);
			}
			ratio = (double) blank_sum / (double) (this.end - this.start);
			ret.append('\n');
			ret.append("Blank ratio: " + ratio);
		} else {
			ret.append("\nThe duty time has been fully arranged!");
		}
		ret.append("\n");
		return ret.toString();
	}

	@Override
	public boolean deleteArrangement(String name, String position, String phone) {
		if (!staffset.contains(new Employee(name, position, phone))) {
			System.out.println("This is person isn't a staff!Please add him/her into the system first!");
			return false;
		}
		if (calendar.labels().contains(new Employee(name, position, phone))) {
			calendar.remove(new Employee(name, position, phone));
			return true;
		}
		System.out.println("This staff has't been arranged!");
		return false;
	}

	@Override
	public Employee getStaff(String name) {
		for(Employee e:staffset) {
			if(e.getName().equals(name)) {
				return e;
			}
		}
		return null;
	}

}
