package multiintervalset_decorator;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import exception.IntervalConflictException;
import multiintervalset.MultiIntervalSet;

public class NonOverlapMultiIntervalSetTest {
	// TestingStrategy:
	// have overlapped area
	// have no overlapped area but have blank area
	// have no overlapped area and have no blank area

	@Test
	public void noOverlappedAreaTest() throws Exception {
		MultiIntervalSet<String> mis = NonOverlapMultiIntervalSet.create(MultiIntervalSet.empty());
		assertTrue(mis.insert(0, 10, "t1"));
		assertTrue(mis.insert(10, 20, "t1"));
		assertTrue(mis.insert(30, 50, "t2"));
		assertTrue(mis.insert(300, 700, "t3"));
	}

	@Test(expected = IntervalConflictException.class)
	public void noOverlappedAreaTest2() throws Exception {
		MultiIntervalSet<String> mis = NonOverlapMultiIntervalSet.create(MultiIntervalSet.empty());
		assertTrue(mis.insert(0, 10, "t1"));
		mis.insert(5, 100, "t2");
	}
}
