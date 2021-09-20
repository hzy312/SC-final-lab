package intervalset;

import java.util.Set;

/**
 * mutable ADT represents a set of intervals on the timeline each interval has a
 * unique label, and the type of the label must be immutable
 * 
 * @author Huang ZiYang
 *
 * @param <L> L should be immutable
 */
public interface IntervalSet<L> {
	/**
	 * create a new IntervalSet object
	 * 
	 * @param <L>
	 * @return an empty IntervalSet
	 */
	public static <L> IntervalSet<L> empty(){
		return new CommonIntervalSet<L>();
	}

	/**
	 * insert an interval in the set£¬start < end
	 * 
	 * @param start the start time of the interval( >= 0 )
	 * @param end   the end time of the interval( >= 0 )
	 * @param label the label of this interval, can't be null
	 * @return true if insertion succeed, otherwise false
	 * @throws if start or the end or the label is illegal
	 */
	boolean insert(long start, long end, L label) throws Exception;

	/**
	 * get all the labels in the set
	 * 
	 * @return a set that includes all the labels in the IntervalSet, if the
	 *         IntervalSet is empty, then return an empty set
	 */
	Set<L> labels();

	/**
	 * remove an interval in this IntervalSet
	 * 
	 * @param label the label of one interval
	 * @return true if the removal succeed, otherwise false
	 */
	boolean remove(L label);

	/**
	 * get the start time of one interval
	 * 
	 * @param label an interval label
	 * @return the start time of the interval if the interval is in the set,
	 *         otherwise -1
	 */
	long start(L label);

	/**
	 * get the end time of one interval
	 * 
	 * @param label an interval label
	 * @return the end time of this interval if the interval is in the set,
	 *         otherwise -1
	 */
	long end(L label);
	
	/**
	 * calculate the span of timeline
	 * @return the max time - the min time
	 */
	long span();
}
