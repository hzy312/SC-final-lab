package intervalset_decorator;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import exception.IntervalConflictException;
import intervalset.IntervalSet;

public class NonOverlapIntervalSetTest {

	// TestingStrategy:
	// have overlapped area
	// have no overlapped area but have blank area
	// have no overlapped area and have no blank area
	
	@Test
	public void nonOverlapTest() throws Exception {
		IntervalSet<String> test_is = NonOverlapIntervalSet.create(IntervalSet.empty());
		assertTrue(test_is.insert(0, 10, "t1"));
		assertTrue(test_is.insert(10, 20, "t2"));
		assertTrue(test_is.insert(40, 50, "t3"));
	}
	
	@Test(expected = IntervalConflictException.class)
	public void nonOverlapTest2() throws Exception{
		IntervalSet<String> test_is = NonOverlapIntervalSet.create(IntervalSet.empty());
		assertTrue(test_is.insert(0, 10, "t1"));
		test_is.insert(5, 20, "t2");
	}
}
