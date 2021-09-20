package multiintervalset;

import java.util.Set;

import intervalset.IntervalSet;

/**
 * mutable ADT represents a set of intervals on the timeline each interval has a
 * label, and the type of the label must be immutable but some intervals can
 * share the same label
 * 
 * @author Huang ZiYang
 *
 * @param <L> type of the label of the intervalset, should be immutable
 */
public interface MultiIntervalSet<L> {
	/**
	 * 
	 * create a new MultiIntervalSet object
	 * 
	 * @param <L>
	 * @return an empty commonintervalset
	 */
	public static <L> MultiIntervalSet<L> empty() {
		return new CommonMultiIntervalSet<L>();
	}

	/**
	 * 
	 * @param <L>
	 * @param is the source of new MultiIntervalSet
	 * @return a new MultiIntervalSet
	 * @throws Exception
	 */
	public static <L> MultiIntervalSet<L> create_from_intervalset(IntervalSet<L> is){
		return new CommonMultiIntervalSet<>(is);
	}
	
	/**
	 * this method insert an interval into the timeline,start < end
	 * 
	 * @param start the start time of the interval ( >= 0 )
	 * @param end   the end time of the interval ( >= 0 )
	 * @param label the label of a interval, can't be null
	 * @return true if insertion succeed, otherwise false
	 * @throws if the start or the end is illegal
	 */
	boolean insert(long start, long end, L label) throws Exception;

	/**
	 * get all the labels in the set
	 * 
	 * @return the set of labels
	 */
	Set<L> labels();

	/**
	 * remove an interval in the set
	 * 
	 * @param label the label of the interval
	 * @return true if the removal succeed, otherwise false
	 */
	boolean remove(L label);

	/**
	 * get the sub-intervals set of one interval labeled with label in ascending
	 * order according to the start time of every sub-intervals
	 * 
	 * @param label the label of the interval
	 * @return an IntervalSet<Interger> includes all the sub-intervals of the input
	 *         interval if the input interval doesn't exist in the timeline, return
	 *         an empty IntervalSet
	 * @throws Exception if the construction of intervals is illegal
	 */
	IntervalSet<Integer> intervals(L label);
	
	/**
	 * calculate the span of timeline
	 * @return the max time - the min time
	 */
	long span();
}
