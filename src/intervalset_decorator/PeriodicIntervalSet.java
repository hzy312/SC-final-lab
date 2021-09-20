package intervalset_decorator;

import java.util.HashMap;
import java.util.Map;

import intervalset.IntervalSet;

public class PeriodicIntervalSet<L> extends IntervalSetDecorator<L> implements IntervalSet<L> {

	public static <L> PeriodicIntervalSet<L> create(IntervalSet<L> is) {
		return new PeriodicIntervalSet<>(is);
	}

	private Map<L, Integer> periodic_info = new HashMap<>();

	// AF:
	// represents an intervalset which intervals can repeat in a period
	
	// RI:
	// the period of every interval >= 0
	// the period of the repeated intervals >0
	
	// safety from rep exposure:
	// periodic_info is private

	PeriodicIntervalSet(IntervalSet<L> is) {
		super(is);
		for (L l : is.labels()) {
			this.periodic_info.put(l, 0);
		}
	}

	/**
	 * set the period of one interval
	 * 
	 * @param label the interval's label
	 * @param i     the period time of the interval
	 * @return true is the label exists, otherwise false
	 */
	public boolean setPeriodic(L label, Integer i) {
		if(!super.labels().contains(label)) {
			return false;
		}
		this.periodic_info.put(label, i);
		return true;
	}

	/**
	 * get the period of the interval
	 * 
	 * @param label the interval's label
	 * @return the period of the interval
	 * @return the period of the interval is the label exists, otherwise -1
	 */
	public int getPeriod(L label) {
		if(!super.labels().contains(label)) {
			return -1;
		}
		return periodic_info.get(label);
	}
}
