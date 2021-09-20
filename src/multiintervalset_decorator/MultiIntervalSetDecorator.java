package multiintervalset_decorator;

import java.util.Set;

import intervalset.IntervalSet;
import multiintervalset.MultiIntervalSet;

public abstract class MultiIntervalSetDecorator<L> implements MultiIntervalSet<L> {
	private MultiIntervalSet<L> mis = MultiIntervalSet.empty();
	
	public MultiIntervalSetDecorator(MultiIntervalSet<L> mis) {
		this.mis = mis;
	}
	
	public MultiIntervalSetDecorator(IntervalSet<L> is) {
		this.mis = MultiIntervalSet.create_from_intervalset(is);
	}
	
	@Override
	public boolean insert(long start, long end, L label) throws Exception {
		return mis.insert(start, end, label);
	}

	@Override
	public Set<L> labels() {
		return mis.labels();
	}

	@Override
	public boolean remove(L label) {
		return mis.remove(label);
	}

	@Override
	public IntervalSet<Integer> intervals(L label) {
		return mis.intervals(label);
	}
	
	@Override
	public long span() {
		return mis.span();
	}
}
