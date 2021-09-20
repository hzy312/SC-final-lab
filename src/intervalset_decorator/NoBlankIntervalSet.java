package intervalset_decorator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import intervalset.IntervalSet;

public class NoBlankIntervalSet<L> extends IntervalSetDecorator<L> implements IntervalSet<L> {

	public static <L> NoBlankIntervalSet<L> create(IntervalSet<L> is) {
		return new NoBlankIntervalSet<>(is);
	}

	// rep
	private long start = 0;// the start time of the timeline
	private long end = 0;// the end time of the timeline

	// AF
	// represents a IntervalSet with no blank intervals

	// RI
	// start < end
	// when all the intervals have been added, the isNoBlank() should return true

	// safety from rep exposure:
	// the rep is private an mutable

	NoBlankIntervalSet(IntervalSet<L> is) {
		super(is);
	}

	/**
	 * set the start time of timeline
	 * 
	 * @param start
	 */
	public void setStart(long start) {
		this.start = start;
	}

	/**
	 * set the end time of the timeline
	 * 
	 * @param end
	 */
	public void setEnd(long end) {
		this.end = end;
	}

	/**
	 * check if there exists blank area
	 * 
	 * @return true if there exists no blank area, otherwise false
	 */
	public boolean isNoBlank() {
		List<Long> startList = new ArrayList<>();
		Map<Long, Long> timeline = new HashMap<>();
		for (L l : super.labels()) {
			long start_time = super.start(l);
			long end_time = super.end(l);
			if (startList.contains(start_time)) {
				if (end_time <= timeline.get(start_time)) {
					continue;
				} else {
					timeline.put(start_time, end_time);
				}
			} else {
				startList.add(start_time);
				timeline.put(start_time, end_time);
			}
		}
		Collections.sort(startList);
		if (startList.size() == 0) {
			System.out.println("no intervals");
			return false;
		}
		if (startList.get(0) != this.start) {
			return false;
		}
		long now = timeline.get(startList.get(0));
		for (int i = 1; i < startList.size(); i++) {
			if (startList.get(i) <= now && timeline.get(startList.get(i)) > now) {
				now = timeline.get(startList.get(i));
			}
		}

		if (now == this.end) {
			return true;
		} else {
			return false;
		}
	}

//	@Override
//	public boolean insert(long start, long end, L label) throws Exception{
//		if(super.labels().isEmpty()) {
//			boolean flag = super.insert(start, end, label);
//			set_timeboundary();
//			return flag;
//		}
//		List<Long> endList = new ArrayList<>();
//		for(L l:super.labels()) {
//			endList.add(super.end(l));
//		}
//		Collections.sort(endList);
//		
//		if(start<=endList.get(endList.size()-1)) {
//			boolean flag = super.insert(start, end, label);
//			set_timeboundary();
//			if(!isNoBlank()) {
//				throw new IntervalBlankException("the timeline can't have blank area!");
//			}
//			return flag;
//		}else {
//			throw new IntervalBlankException("the timeline can't have blank area!");
//		}
//	}

//	/**
//	 * set the start time and end time of the timeline
//	 */
//	private void set_timeboundary() {
//		List<Long> startList = new ArrayList<>();
//		List<Long> endList = new ArrayList<>();
//		for(L l: super.labels()) {
//			startList.add(super.start(l));
//			endList.add(super.end(l));
//		}
//		Collections.sort(startList);
//		Collections.sort(endList);
//		this.start = startList.get(0);
//		this.end = endList.get(endList.size() - 1);
//	}

}
