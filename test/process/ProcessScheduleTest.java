package process;

import org.junit.Test;


public class ProcessScheduleTest {
	@Test
	public void processTest() {
		ProcessSchedule ps = ProcessSchedule.create();
		ps.fork("0001", "p1", 10, 20);
		ps.fork("0002", "p2", 30, 50);
		ps.fork("0003", "p3", 15, 30);
		while(!ps.isAllTerminated()) {
			ps.schedule_stochastic();
		}
		ps.display();
	}
	
	@Test
	public void processTest2() {
		ProcessSchedule ps = ProcessSchedule.create();
		ps.fork("0001", "p1", 10, 20);
		ps.fork("0002", "p2", 30, 50);
		ps.fork("0003", "p3", 15, 30);
		while(!ps.isAllTerminated()) {
			ps.schedule_shortestfirst();
		}
		ps.display();
	}
}
