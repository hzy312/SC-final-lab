package intervalset_decorator;

import java.util.Set;

import intervalset.IntervalSet;

public abstract class IntervalSetDecorator<L> implements IntervalSet<L> {
	private IntervalSet<L> is = IntervalSet.empty();

	public IntervalSetDecorator(IntervalSet<L> is) {
		this.is = is;
	}

	@Override
	public boolean insert(long start, long end, L label) throws Exception {
		return is.insert(start, end, label);
	}

	@Override
	public Set<L> labels() {
		return is.labels();
	}

	@Override
	public boolean remove(L label) {
		return is.remove(label);
	}

	@Override
	public long start(L label) {
		return is.start(label);
	}

	@Override
	public long end(L label) {
		return is.end(label);
	}
	
	@Override
	public long span() {
		return is.span();
	}

}
