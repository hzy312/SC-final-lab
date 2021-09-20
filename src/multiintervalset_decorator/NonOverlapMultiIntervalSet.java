package multiintervalset_decorator;

import java.util.HashMap;
import java.util.Map;
import exception.IntervalConflictException;
import exception.IntervalParamsWrongException;
import intervalset.IntervalSet;
import multiintervalset.MultiIntervalSet;

public class NonOverlapMultiIntervalSet<L> extends MultiIntervalSetDecorator<L> implements MultiIntervalSet<L> {

	
	// AF:
	// represents a intervalset with no intervals overlapped
	
	// RI:
	// each interval should not overlapped with other intervals

	// the rep of super class guarantees the client can't get the reference of the rep
		
	public static <L> NonOverlapMultiIntervalSet<L> create(IntervalSet<L> is) {
		return new NonOverlapMultiIntervalSet<>(is);
	}

	public static <L> NonOverlapMultiIntervalSet<L> create(MultiIntervalSet<L> mis) {
		return new NonOverlapMultiIntervalSet<>(mis);
	}

	public NonOverlapMultiIntervalSet(IntervalSet<L> is) {
		super(is);
	}

	public NonOverlapMultiIntervalSet(MultiIntervalSet<L> mis) {
		super(mis);
	}

	@Override
	public boolean insert(long start, long end, L label) throws Exception {
		if (start < 0 || end < 0 || start >= end || label == null) {
			throw new IntervalParamsWrongException("interval input error!");
		}

		Map<Long, Long> timeline = new HashMap<>();
		for (L l : super.labels()) {
			IntervalSet<Integer> temp_is = super.intervals(l);
			for (Integer i : temp_is.labels()) {
				long s = temp_is.start(i);
				long e = temp_is.end(i);
				if (timeline.containsKey(s)) {
					if (timeline.get(s) < e) {
						timeline.put(s, e);
					}
				} else {
					timeline.put(s, e);
				}
			}
		}
		for (Map.Entry<Long, Long> entry : timeline.entrySet()) {
			if ((start >= entry.getKey() && start < entry.getValue())
					|| (end > entry.getKey() && end <= entry.getValue())) {
				throw new IntervalConflictException("intervals can't overlap");
			}
		}
		super.insert(start, end, label);
		return true;
	}
}
