package duty;

import intervalobject.Employee;

/**
 * mutable adt, represents a DutyRoster system.
 * @author Huang ZiYang
 *
 */
public interface DutyRoster {

	public static ConcreteDutyRoster create(String starttime, String endtime) {
		return new ConcreteDutyRoster(starttime,endtime);
	}
	
	public static ConcreteDutyRoster create() {
		return new ConcreteDutyRoster();
	}
	
	/**
	 * add a person to the list of staffs
	 * @param name the name of the person
	 * @param position the work position of the person
	 * @param phone the phone number of the person
	 * @return true if addition succeeds, otherwise false
	 */
	boolean addStaff(String name, String position, String phone);
	
	/**
	 * delete a person from the list of the staffs
	 * @param name the person you want to delete
	 * @return true if deletion succeeds, otherwise false 
	 */
	boolean deleteStaff(String name, String position, String phone);
	
	/**
	 * get the staff's information according to the name
	 * @param name
	 * @return a Employee object, represents the staff, if the staff doesn't exist, return the null
	 */
	Employee getStaff(String name);
	/**
	 * set the start time of the duty interval
	 * @param time the start time
	 */
	void setStartTime(String starttime);
	
	/**
	 * set the end time of the duty interval
	 * @param time the end time
	 */
	void setEndTime(String endtime);

	/**
	 * check whether the staff has been arranged or not
	 * @param name
	 * @param position
	 * @param phone
	 * @return true if the staff has been arranged, otherwise false
	 */
	boolean isarranged(String name, String position, String phone);
	
	/**
	 * delete the arrangement of the staff
	 * @param name 
	 * @param position
	 * @param phone
	 * @return true if the deletion succeeds, otherwise false
	 */
	boolean deleteArrangement(String name, String position, String phone);
	
	/**
	 * arrange a staff to be on duty for days from starttime
	 * @param name the name of the staff
	 * @param position the work position of the person
	 * @param phone the phone number of the person
	 * @param startime the starttime of the duty interval
	 * @param days the lasting time of the duty interval
	 * @return true if arrange succeeds, otherwise false
	 */
	boolean arrange_manually(String name, String position, String phone,String startime, long days);
	
	/**
	 * automatically arrange all the staff to be on duty
	 * @return true if the arrangement succeeds, otherwise false
	 */
	boolean arrange_automatically();
	
	/**
	 * display the duty interval
	 */
	void display();
	
	/**
	 * check whether the duty table has been fully arranged
	 * @return true is the duty table has no blank area, otherwise false
	 */
	boolean isNoBlank();
}
