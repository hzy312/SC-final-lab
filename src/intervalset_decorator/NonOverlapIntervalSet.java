package intervalset_decorator;

import exception.IntervalConflictException;
import exception.IntervalParamsWrongException;
import intervalset.IntervalSet;

public class NonOverlapIntervalSet<L> extends IntervalSetDecorator<L> implements IntervalSet<L> {

	public static <L> NonOverlapIntervalSet<L> create(IntervalSet<L> is) {
		return new NonOverlapIntervalSet<>(is);
	}

	public NonOverlapIntervalSet(IntervalSet<L> is) {
		super(is);
	}

	// AF:
	// represents a intervalset with no intervals overlapped

	// RI:
	// each interval should not overlapped with other intervals

	// safety from rep exposure:
	// the rep of super class guarantees the client can't get the reference of the rep

	@Override
	public boolean insert(long start, long end, L label) throws Exception {
		if (start < 0 || end < 0 || start >= end || label == null) {
			throw new IntervalParamsWrongException("interval input error!");
		}
		for (L l : super.labels()) {
			long s = super.start(l);
			long e = super.end(l);
			if ((start >= s && start < e) || (end > s && end <= e)) {
				throw new IntervalConflictException("intervals can't overlap!");
			}
		}
		return super.insert(start, end, label);
	}
}
