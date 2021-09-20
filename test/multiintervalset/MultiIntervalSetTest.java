package multiintervalset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import intervalset.IntervalSet;

public abstract class MultiIntervalSetTest {

	// Testing strategy:

	// insert:
	// an interval not in the set
	// an interval already in the set
	// an interval already overlap with some intervals already in the set
	// start >= end
	// parameters are illegal

	// labels:
	// empty IntervalSet
	// IntervalSet with some intervals already in
	// some labels marked more than one interval

	// remove:
	// an interval not in the set
	// an interval already in the set
	// an interval composed with more than one sub-intervals

	// intervals:
	// an interval not int the set
	// an interval already in the set, but only has one sub-interval
	// an interval in the set, ans has more than one sub-intervals in the set

	
	// span:
	// no overlapped timeline
	// overlapped timeline

	/**
	 * create a new MultiIntervalSet
	 * 
	 * @return an empty multiintervalset
	 */
	public abstract MultiIntervalSet<String> empty();

	@Test
	public void insertTest() throws Exception {
		MultiIntervalSet<String> mis = empty();
		Set<String> test = new HashSet<>();
		assertEquals(test, mis.labels());

		assertTrue(mis.insert(0, 10, "t1"));
		test.add("t1");
		assertEquals(test, mis.labels());
		IntervalSet<Integer> test_is = IntervalSet.empty();
		test_is.insert(0, 10, 0);
		assertEquals(test_is, mis.intervals("t1"));

		assertTrue(mis.insert(5, 20, "t2"));
		assertTrue(mis.labels().contains("t2"));
		test.add("t2");
		assertEquals(test, mis.labels());

		assertTrue(mis.insert(30, 40, "t3"));
		test.add("t3");
		assertTrue(mis.insert(50, 60, "t4"));
		test.add("t4");
		assertEquals(test, mis.labels());

		assertTrue(mis.insert(70, 80, "t1"));
		assertEquals(test, mis.labels());
		test_is.insert(70, 80, 1);
		assertEquals(test_is, mis.intervals("t1"));
	}

	@Test(expected = Exception.class)
	public void insertTest2() throws Exception {
		MultiIntervalSet<String> mis = empty();
		mis.insert(-10, 0, null);
	}

	@Test
	public void labelsTest() throws Exception {
		MultiIntervalSet<String> mis = empty();
		Set<String> test = new HashSet<>();
		assertEquals(test, mis.labels());

		mis.insert(0, 10, "t1");
		mis.insert(10, 20, "t2");
		mis.insert(20, 30, "t3");
		test.add("t1");
		test.add("t2");
		test.add("t3");
		mis.insert(30, 40, "t1");
		assertEquals(test, mis.labels());
	}

	@Test
	public void removeTest() throws Exception {
		MultiIntervalSet<String> mis = empty();
		assertEquals(false, mis.remove("t1"));

		Set<String> test = new HashSet<>();
		assertEquals(test, mis.labels());
		mis.insert(0, 10, "t1");
		mis.insert(10, 20, "t2");
		test.add("t1");
		test.add("t2");
		assertEquals(test, mis.labels());
		assertFalse(test.remove("t5"));
		assertTrue(mis.remove("t1"));
		test.remove("t1");
		assertEquals(test, mis.labels());
		mis.insert(30, 40, "t3");
		mis.insert(40, 50, "t3");
		test.add("t3");
		assertEquals(test, mis.labels());
		mis.remove("t3");
		test.remove("t3");
		assertEquals(test, mis.labels());
	}

	@Test
	public void intervalsTest() throws Exception {
		MultiIntervalSet<String> mis = empty();
		IntervalSet<Integer> is1 = IntervalSet.empty();
		assertEquals(mis.intervals("t1"), is1);
		mis.insert(0, 10, "t1");
		is1.insert(0, 10, 0);
		assertEquals(mis.intervals("t1"), is1);
		mis.insert(20, 30, "t2");
		mis.insert(30, 40, "t2");
		IntervalSet<Integer> is2 = IntervalSet.empty();
		is2.insert(20, 30, 0);
		is2.insert(30, 40, 1);
		assertEquals(mis.intervals("t2"), is2);
	}
	
	@Test
	public void spanTest() throws Exception {
		MultiIntervalSet<String> mis = empty();
		assertEquals(0, mis.span());
		mis.insert(10, 90, "t1");
		assertEquals(80, mis.span());
		mis.insert(200, 500, "t2");
		assertEquals(490, mis.span());
		mis.insert(0, 10000, "t3");
		assertEquals(10000, mis.span());
		
	}

}
