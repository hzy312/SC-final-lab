package multiintervalset_decorator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import multiintervalset.MultiIntervalSet;

public class NoBlankMultiIntervalSetTest {

	// Testing Strategy:
	// have no blank area
	// have no blank area but have overlapped area
	// have blank area

	@Test
	public void noBlankAreaTest() throws Exception {
		NoBlankMultiIntervalSet<String> mis = NoBlankMultiIntervalSet.create(MultiIntervalSet.empty());
		mis.setStart(0);
		mis.setEnd(70);
		assertTrue(mis.insert(0, 10, "t1"));
		assertTrue(mis.insert(10, 20, "t1"));
		assertTrue(mis.insert(5, 30, "t2"));
		assertTrue(mis.insert(30, 70, "t3"));
	}

	@Test
	public void noBlankAreaTest2() throws Exception {
		NoBlankMultiIntervalSet<String> mis = NoBlankMultiIntervalSet.create(MultiIntervalSet.empty());
		mis.setStart(0);
		mis.setEnd(50);
		assertTrue(mis.insert(0, 10, "t1"));
		assertTrue(mis.insert(40, 50, "t1"));
		assertFalse(mis.isNoBlank());
	}
}
