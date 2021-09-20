package process;

public interface ProcessSchedule {

	public static ProcessSchedule create() {
		return new ConcreteProcessSchedule();
	}

	/**
	 * create a new process to the process group
	 * 
	 * @param pid               the pid of the process, the pid is the unique for
	 *                          every process
	 * @param name              the name of the process
	 * @param shortest_exe_time the shortest executed time of the process
	 * @param longest_exe_time  the longest executed time of the process
	 * @return true if the new process was forked, otherwise false
	 */
	boolean fork(String pid, String name, long shortest_exe_time, long longest_exe_time);

	/**
	 * stochastically schedule a process
	 * 
	 * @return true if the schedule succeeds, otherwise false
	 */
	boolean schedule_stochastic();

	/**
	 * schedule a process according to the shortest process first strategy
	 * 
	 * @return true if the schedule succeeds, otherwise false
	 */
	boolean schedule_shortestfirst();

	/**
	 * check whether all the process has been terminated or not
	 * 
	 * @return true if all the process have been terminated
	 */
	boolean isAllTerminated();

	/**
	 * display the process schedule situation
	 */
	void display();

	/**
	 * cpu work with no process running
	 */
	void noProcessRun();

	/**
	 * schedule all the process automatically
	 */
	void schedule_automatically();
}
