package api;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import intervalset.IntervalSet;
import multiintervalset.MultiIntervalSet;

public class APIsTest {
	@Test
	public void similarityTest() throws Exception {
		MultiIntervalSet<String> s1 = MultiIntervalSet.empty();
		MultiIntervalSet<String> s2= MultiIntervalSet.empty();
		s1.insert(0, 5, "A");
		s1.insert(20, 25, "A");
		s1.insert(10, 20, "B");
		s1.insert(25, 30, "B");
		
		s2.insert(20, 35, "A");
		s2.insert(10, 20, "B");
		s2.insert(0, 5, "C");
		assertEquals(0.42857142857142855, APIs.Similarity(s1, s2),1e-5);
	}
	
	@Test
	public void calcConflictRatioTest() throws Exception {
		IntervalSet<String> is = IntervalSet.empty();
		is.insert(0, 10, "t1");
		is.insert(5, 20, "t2");
		assertEquals(0.25, APIs.calcConflictRatio(is),1e-5);
		is.insert(30, 50, "t3");
		is.insert(40, 60, "t4");
		assertEquals(0.3, APIs.calcConflictRatio(is),1e-5);
	}
	
	@Test
	public void calcConflictRatioTest2() throws Exception {
		MultiIntervalSet<String> mis = MultiIntervalSet.empty();
		mis.insert(0, 10, "t1");
		mis.insert(0, 10, "t2");
		mis.insert(10, 20, "t4");
		mis.insert(20, 30, "t3");
		assertEquals(1.0/3, APIs.calcConflictRatio(mis, 0, 30),1e-5);
	}
	
	@Test
	public void calcFreeTimeRatio() throws Exception {
		IntervalSet<String> is = IntervalSet.empty();
		is.insert(0, 50, "t1");
		assertEquals(0, APIs.calcFreeTimeRatio(is,0,50),1e-5);
		is.insert(100, 200, "t2");
		assertEquals(0.25, APIs.calcFreeTimeRatio(is,0,200),1e-5);
	}
	
	
	@Test
	public void calcFreeTimeRatio1() throws Exception {
		MultiIntervalSet<String> mis = MultiIntervalSet.empty();
		mis.insert(0, 20, "t1");
		assertEquals(0, APIs.calcFreeTimeRatio(mis,0,20),1e-5);
		mis.insert(30, 40, "t1");
		assertEquals(0.25, APIs.calcFreeTimeRatio(mis,0,40),1e-5);
		mis.insert(40, 50, "t2");
		assertEquals(0.2, APIs.calcFreeTimeRatio(mis,0,50),1e-5);
	}
}
