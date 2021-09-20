package intervalset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public abstract class IntervalSetTest {

	// Testing strategy:

	// insert:
	// an interval not in the set
	// an interval already in the set
	// an interval already overlap with some intervals already in the set
	// start >= end
	// parameters are not illegal

	// labels:
	// empty IntervalSet
	// IntervalSet with some intervals already in

	// remove:
	// an interval not in the set
	// an interval already in the set

	// start:
	// an interval not in the set
	// an interval already in the set

	// end:
	// an interval not in the set
	// an interval already in the set
	
	// span:
	// no overlapped timeline
	// overlapped timeline

	/**
	 * Overridden by implementation-specific test classes
	 * 
	 * @return an empty IntervalSet of a specific implementation
	 */
	public abstract IntervalSet<String> empty();

	@Test
	public void insertTest() throws Exception {
		IntervalSet<String> is = empty();
		Set<String> test = new HashSet<>();
		assertEquals(test, is.labels());

		assertTrue(is.insert(0, 10, "t1"));
		test.add("t1");
		assertEquals(test, is.labels());
		assertEquals(0, is.start("t1"));
		assertEquals(10, is.end("t1"));

		assertEquals(test, is.labels());
		assertEquals(0, is.start("t1"));
		assertEquals(10, is.end("t1"));

		assertTrue(is.insert(5, 25, "t2"));
		assertTrue(is.labels().contains("t2"));
		test.add("t2");
		assertEquals(test, is.labels());
		assertEquals(0, is.start("t1"));
		assertEquals(10, is.end("t1"));

		assertTrue(is.insert(30, 40, "t3"));
		test.add("t3");
		assertTrue(is.insert(50, 60, "t4"));
		test.add("t4");
		assertEquals(test, is.labels());
		assertEquals(30, is.start("t3"));
		assertEquals(60, is.end("t4"));
	}

	@Test(expected = Exception.class)
	public void insertTest2() throws Exception {
		IntervalSet<String> is = empty();
		is.insert(-10, 0, null);
	}

	@Test
	public void labelsTest() throws Exception {
		IntervalSet<String> is = empty();
		Set<String> test = new HashSet<>();
		assertEquals(test, is.labels());

		is.insert(0, 10, "t1");
		is.insert(10, 20, "t2");
		is.insert(20, 30, "t3");
		test.add("t1");
		test.add("t2");
		test.add("t3");
		assertEquals(test, is.labels());

	}

	@Test
	public void removeTest() throws Exception {
		IntervalSet<String> is = empty();
		assertEquals(false, is.remove("t1"));

		Set<String> test = new HashSet<>();
		assertEquals(test, is.labels());
		is.insert(0, 10, "t1");
		is.insert(10, 20, "t2");
		test.add("t1");
		test.add("t2");
		assertEquals(test, is.labels());
		assertFalse(test.remove("t5"));
		assertTrue(is.remove("t1"));
		test.remove("t1");
		assertEquals(test, is.labels());

	}

	@Test
	public void startTest() throws Exception {
		IntervalSet<String> is = empty();
		is.insert(0, 10, "t1");
		is.insert(10, 20, "t2");

		assertEquals(0, is.start("t1"));
		assertEquals(10, is.start("t2"));
		assertEquals(-1, is.start("t3"));
		assertEquals(-1, is.start(null));
	}

	@Test
	public void endTest() throws Exception {
		IntervalSet<String> is = empty();
		is.insert(0, 10, "t1");
		is.insert(10, 20, "t2");

		assertEquals(10, is.end("t1"));
		assertEquals(20, is.end("t2"));
		assertEquals(-1, is.start("t3"));
		assertEquals(-1, is.start(null));
	}

	
	@Test
	public void spanTest() throws Exception {
		IntervalSet<String> is = empty();
		assertEquals(0, is.span());
		is.insert(0, 100, "t1");
		assertEquals(100, is.span());
		is.insert(0, 3000, "t2");
		is.insert(1000, 2000, "te");
		assertEquals(3000, is.span());
	}
}
