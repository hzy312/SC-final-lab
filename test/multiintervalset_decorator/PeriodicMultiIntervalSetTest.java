package multiintervalset_decorator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import multiintervalset.MultiIntervalSet;

public class PeriodicMultiIntervalSetTest {

	// Testing strategy:
	// set a period for a non-existent interval
	// set a period for a existent interval
	// get a period of a non-existent interval
	// set a period of a existent interval
	// insert more than one intervals to by one label
	
	
	@Test
	public void periodTest() throws Exception {
		PeriodicMultiIntervalSet<String> mis = PeriodicMultiIntervalSet.create(MultiIntervalSet.empty());
		mis.insert(0, 10, "t1");
		mis.insert(20, 30, "t2");
		assertTrue(mis.setPeriodic("t1", 7));
		assertFalse(mis.setPeriodic("t5", 34));
		mis.setPeriodic("t2", 30);
		assertEquals(7, mis.getPeriod("t1"));
		assertEquals(30, mis.getPeriod("t2"));
		assertEquals(-1, mis.getPeriod("t7"));
		mis.insert(40, 50, "t2");
		assertEquals(30, mis.getPeriod("t2"));
	}
}
