package multiintervalset_decorator;

import java.util.HashMap;
import java.util.Map;

import intervalset.IntervalSet;
import multiintervalset.MultiIntervalSet;

public class PeriodicMultiIntervalSet<L> extends MultiIntervalSetDecorator<L> implements MultiIntervalSet<L>{

	public static <L> PeriodicMultiIntervalSet<L>  create(IntervalSet<L> is){
		return new PeriodicMultiIntervalSet<>(is);
	}
	
	public static <L> PeriodicMultiIntervalSet<L> create(MultiIntervalSet<L> mis){
		return new PeriodicMultiIntervalSet<>(mis);
	}
	
	
	// AF:
	// represents an intervalset which intervals can repeat in a period
	
	// RI:
	// the period of every interval >= 0
	// the period of the repeated intervals >0
	
	// safety from rep exposure:
	// periodic_info is private
	
	private Map<L, Integer> periodic_info = new HashMap<>();
	
	public PeriodicMultiIntervalSet(IntervalSet<L> is) {
		super(is);
		for(L l:super.labels()) {
			periodic_info.put(l, 0);
		}
	}
	
	public PeriodicMultiIntervalSet(MultiIntervalSet<L> mis) {
		super(mis);
		for(L l:super.labels()) {
			periodic_info.put(l, 0);
		}
	}

	public boolean setPeriodic(L label, Integer i) {
		if(!super.labels().contains(label)) {
			return false;
		}
		periodic_info.put(label, i);
		return true;
	}
	
	public int getPeriod(L label) {
		if(!super.labels().contains(label)) {
			return -1;
		}
		return periodic_info.get(label);
	}

	
	
}
