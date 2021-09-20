package intervalset_decorator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//import exception.IntervalBlankException;
import intervalset.IntervalSet;

public class NoBlankIntervalSetTest {
	
	// Testing Strategy:
	// have no blank area
	// have no blank area but have overlapped area
	// have blank area 

	
	@Test
	public void noBlankAreaTest() throws Exception {
		NoBlankIntervalSet<String> test_is = NoBlankIntervalSet.create(IntervalSet.empty());
		test_is.setStart(0);
		test_is.setEnd(60);
		assertTrue(test_is.insert(0, 10, "t1"));
		assertFalse(test_is.isNoBlank());
		assertTrue(test_is.insert(10, 20, "t2"));
		assertTrue(test_is.insert(20, 30, "t3"));
		assertTrue(test_is.insert(30, 50, "t4"));
		assertTrue(test_is.insert(40, 60, "t5"));
		assertTrue(test_is.isNoBlank());
	}
	
	@Test
	public void noBlankAreaTest2() throws Exception {
		NoBlankIntervalSet<String> test_is = NoBlankIntervalSet.create(IntervalSet.empty());
		test_is.setStart(0);
		test_is.setEnd(30);
		assertTrue(test_is.insert(0, 10, "t1"));
		assertTrue(test_is.insert(20, 30, "t2"));
		assertFalse(test_is.isNoBlank());
	}
}
