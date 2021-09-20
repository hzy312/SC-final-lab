package intervalset_decorator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import intervalset.IntervalSet;

public class PeriodicIntervalSetTest {
	
	// Testing strategy:
	// set a period for a non-existent interval
	// set a period for a existent interval
	// get a period of a non-existent interval
	// set a period of a existent interval
	
	
	@Test
	public void periodTest() throws Exception {
		PeriodicIntervalSet<String> is = PeriodicIntervalSet.create(IntervalSet.empty());
		is.insert(0, 10, "t1");
		is.insert(20, 30, "t2");
		assertTrue(is.setPeriodic("t1", 7));
		assertFalse(is.setPeriodic("t5", 34));
		is.setPeriodic("t2", 30);
		assertEquals(7, is.getPeriod("t1"));
		assertEquals(30, is.getPeriod("t2"));
		assertEquals(-1, is.getPeriod("t7"));
	}
}
