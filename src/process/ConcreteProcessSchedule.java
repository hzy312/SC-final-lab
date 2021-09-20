package process;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import intervalobject.MyProcess;
import intervalset.IntervalSet;
import multiintervalset.MultiIntervalSet;
import multiintervalset_decorator.NonOverlapMultiIntervalSet;

public class ConcreteProcessSchedule implements ProcessSchedule {

	/**
	 * represents the state of process, every process must be in one of these states
	 */
	private enum ProcessState {
		RUNNING, SUSPENDED, TERMINATED
	}

	// rep
	private MultiIntervalSet<MyProcess> process_table = NonOverlapMultiIntervalSet.create(MultiIntervalSet.empty());
	private Map<String, MyProcess> process_group = new HashMap<>();
	private Set<MyProcess> suspended_group = new HashSet<>();
	private Map<MyProcess, Long> running_time = new HashMap<>();
	private MyProcess running_instance = null;
	private Map<MyProcess, ProcessState> process_state = new HashMap<>();
	private long now = 0;

	
	// AF
	// represents a process schedule system
	
	// RI
	// each process can't overlap with each other
	
	// safety from rep exposure
	// all filed are private, and the client can't get the reference of process_table
	
	@Override
	public boolean fork(String pid, String name, long shortest_exe_time, long longest_exe_time) {
		if (shortest_exe_time >= longest_exe_time) {
			System.err.println("The shortest_exe_time should be less than longest_exe_time!");
			return false;
		}
		if (process_group.containsKey(pid)) {
			System.out.println("This process has been forked!");
			return false;
		} else {
			MyProcess mp = new MyProcess(pid, name, shortest_exe_time, longest_exe_time);
			running_time.put(mp, 0L);
			process_group.put(pid, mp);
			suspended_group.add(mp);
			process_state.put(mp, ProcessState.SUSPENDED);
			return true;
		}
	}

	@Override
	public boolean schedule_stochastic() {
		if (suspended_group.size() == 0) {
			if (running_instance != null) {
				stop();
				return false;
			}
			System.out.println("There is no process can be scheduled!");
			return false;
		}
		if (running_instance != null)
			stop();
		Random random_number = new Random(System.currentTimeMillis());
		Object[] entries = suspended_group.toArray();
		MyProcess mp = (MyProcess) entries[random_number.nextInt(entries.length)];
		running_instance = mp;
		suspended_group.remove(mp);
		process_state.put(mp, ProcessState.RUNNING);
		return true;
	}

	@Override
	public boolean schedule_shortestfirst() {
		if (suspended_group.size() == 0) {
			if (running_instance != null) {
				stop();
				return false;
			}
			System.out.println("There is no process can be scheduled!");
			return false;
		}
		if (running_instance != null)
			stop();
		long dist = Long.MAX_VALUE;
		for (MyProcess mp : suspended_group) {
			if (mp.getLongest_exe_time() - running_time.get(mp) < dist) {
				dist = mp.getLongest_exe_time() - running_time.get(mp);
				running_instance = mp;
			}
		}
		suspended_group.remove(running_instance);
		process_state.put(running_instance, ProcessState.RUNNING);
		return true;
	}

	private void stop() {
		if (running_instance == null) {
			System.out.println("There is no process running now!");
		}
		Random random_number = new Random(System.currentTimeMillis());
		int length = (int) (running_instance.getLongest_exe_time() - running_time.get(running_instance));
		long r_time = random_number.nextInt(length) + 1;
		try {
			process_table.insert(now, now + r_time, running_instance);
			running_time.put(running_instance, running_time.get(running_instance) + r_time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		now += r_time;
		if (running_time.get(running_instance) < running_instance.getShortest_exe_time()) {
			suspended_group.add(running_instance);
			process_state.put(running_instance, ProcessState.SUSPENDED);
		} else {
			process_state.put(running_instance, ProcessState.TERMINATED);
		}
		running_instance = null;
	}

	private boolean isTerminated(String pid) {
		if (process_state.get(process_group.get(pid)) == ProcessState.TERMINATED) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isAllTerminated() {
		if (process_group.size() == 0) {
			System.out.println("The process group is empty!");
			return false;
		}
		for (Map.Entry<String, MyProcess> entry : process_group.entrySet()) {
			if (!isTerminated(entry.getKey())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void display() {
		System.out.println(this.toString());
	}

	@Override
	public String toString() {
		if (process_table.labels().size() == 0 && running_instance != null) {
			return "Running process: \n" + running_instance.toString();
		}
		if (process_table.labels().size() == 0) {
			return "The cpu hasn't begun to work!";
		}
		StringBuffer ret = new StringBuffer();
		ret.append("The process running situation:\n");
		List<Long> startList = new ArrayList<>();
		Map<Long, MyProcess> name = new HashMap<>();
		Map<Long, Long> timeline = new HashMap<>();
		for (MyProcess mp : process_table.labels()) {
			IntervalSet<Integer> temp = process_table.intervals(mp);
			for (Integer i : temp.labels()) {
				long s = temp.start(i);
				long e = temp.end(i);
				startList.add(temp.start(i));
				timeline.put(s, e);
				name.put(s, mp);
			}
		}
		Collections.sort(startList);
		ret.append(startList.get(0) + " -- " + timeline.get(startList.get(0)) + ": "
				+ name.get(startList.get(0)).toString());
		ret.append('\n');
		long now = timeline.get(startList.get(0));
		for (int i = 1; i < startList.size(); i++) {
			long s = startList.get(i);
			long e = timeline.get(startList.get(i));
			if (s == now) {
				ret.append(s + " -- " + e + ": " + name.get(s).toString());
				ret.append('\n');
			} else if (s > now) {
				ret.append(now + " -- " + s + ": " + "no process is running");
				ret.append('\n');
				ret.append(s + " -- " + e + ": " + name.get(s).toString());
				ret.append('\n');
			}
			now = e;
		}
		if (!isAllTerminated()) {
			ret.append("\n" + "Running process: \n");
			ret.append(running_instance.toString());
		}
		return ret.toString();
	}

	@Override
	public void noProcessRun() {
		Random random = new Random(System.currentTimeMillis());
		now += random.nextInt(20);
	}

	@Override
	public void schedule_automatically() {
		int r = new Random(System.currentTimeMillis()).nextInt(3);
		while (true) {
			switch (r) {
			case 0:
				schedule_stochastic();
				break;
			case 1:
				schedule_shortestfirst();
			case 2:
				noProcessRun();
			default:
				break;
			}
			r = new Random(System.currentTimeMillis()).nextInt(3);
			if (isAllTerminated())
				break;
		}
	}
}
