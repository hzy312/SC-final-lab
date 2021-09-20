package multiintervalset_decorator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//import exception.IntervalBlankException;
import intervalset.IntervalSet;
import multiintervalset.MultiIntervalSet;

public class NoBlankMultiIntervalSet<L> extends MultiIntervalSetDecorator<L> implements MultiIntervalSet<L> {

	public static <L> NoBlankMultiIntervalSet<L> create(IntervalSet<L> is){
		return new NoBlankMultiIntervalSet<>(is);
	}
	
	public static <L> NoBlankMultiIntervalSet<L> create(MultiIntervalSet<L> mis){
		return new NoBlankMultiIntervalSet<>(mis);
	}
	// rep
	private long start;
	private long end;

	// AF:
	// represents a MultiIntervalSet with no blank intervals
	
	// RI:
	// start < end
	// when all the intervals have been added, the isNoBlank() should return true
	
	// safety from rep exposure:
	// the rep is private an mutable
	
	NoBlankMultiIntervalSet(IntervalSet<L> is) {
		super(is);
	}

	NoBlankMultiIntervalSet(MultiIntervalSet<L> mis) {
		super(mis);
	}
	
	/**
	 * set the start time of the intervalset
	 * @param start
	 */
	public void setStart(long start) {
		this.start = start;
	}

	/**
	 * set the end time of the intervalset
	 * @param end
	 */
	public void setEnd(long end) {
		this.end = end;
	}


	/**
	 * check there exists blank area in the timeline
	 * @return true if the there exists no blank area, otherwise fasle
	 */
	public boolean isNoBlank() {
		List<Long> startList = new ArrayList<>();
		Map<Long, Long> timeline = new HashMap<>();
		for (L l : super.labels()) {
			IntervalSet<Integer> temp_is = super.intervals(l);
			for (Integer i : temp_is.labels()) {
				long start_time = temp_is.start(i);
				long end_time = temp_is.end(i);
				if(startList.contains(start_time)) {
					if(end_time <= timeline.get(start_time)) {
						continue;
					}else {
						timeline.put(start_time, end_time);
					}
				}else {
					startList.add(start_time);
					timeline.put(start_time, end_time);
				}
			}
		}
		if(timeline.get(this.start)==null) {
			return false;
		}
		long now = timeline.get(this.start);
		for (int i = 1; i < startList.size(); i++) {
			if (startList.get(i) <= now && timeline.get(startList.get(i)) > now) {
				now = timeline.get(startList.get(i));
			}
		}
		if (now == end) {
			return true;
		}
		return false;
	}

//	@Override
//	public boolean insert(long start, long end, L label) throws Exception {
//		if(super.labels().isEmpty()) {
//			boolean flag = super.insert(start, end, label);
//			set_timeboundary();
//			return flag;
//		}
//		List<Long> endList = new ArrayList<>();
//		for (L l : super.labels()) {
//			IntervalSet<Integer> temp_is = super.intervals(l);
//			for (Integer i : temp_is.labels()) {
//				endList.add(temp_is.end(i));
//			}
//		}
//		
//		
//		if (start <= endList.get(endList.size() - 1)) {
//			boolean flag = super.insert(start, end, label);
//			set_timeboundary();
//			if (!isNoBlank()) {
//				throw new IntervalBlankException("the timeline can't have blank area");
//			}
//			return flag;
//		} else {
//			throw new IntervalBlankException("the timeline can't have blank area");
//		}
//	}
	
//	private void set_timeboundary() {
//	List<Long> startList = new ArrayList<>();
//	List<Long> endList = new ArrayList<>();
//	for (L l : super.labels()) {
//		IntervalSet<Integer> temp_is = super.intervals(l);
//		for (Integer i : temp_is.labels()) {
//			startList.add(temp_is.start(i));
//			endList.add(temp_is.end(i));
//		}
//	}
//	Collections.sort(startList);
//	Collections.sort(endList);
//
//	this.start = startList.get(0);
//	this.end = endList.get(endList.size() - 1);
//}

}
