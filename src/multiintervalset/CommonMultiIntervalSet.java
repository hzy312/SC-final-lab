package multiintervalset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import exception.IntervalParamsWrongException;
import intervalset.IntervalSet;
import intervalset_decorator.NonOverlapIntervalSet;

public class CommonMultiIntervalSet<L> implements MultiIntervalSet<L> {

	// rep
	private final Map<L, IntervalSet<Integer>> mis = new HashMap<>();


	// AF:
	// represents MultiIntervalSet, which means more
	// than one intervals can linked to one label

	// RI:
	// for any sub-interval in the set, start < end

	// safety from rep exposure:
	// the mis is private and final, and the client can't get the reference of the
	// mis

	private void checkRep() {
		if (mis.size() == 0)
			return;
		for (Map.Entry<L, IntervalSet<Integer>> entry : mis.entrySet()) {
			IntervalSet<Integer> temp = entry.getValue();
			for (Integer i : temp.labels()) {
				assert temp.start(i) < temp.end(i);
			}
		}
	}

	

	/**
	 * constructor with no parameters
	 */
	CommonMultiIntervalSet() {

	}
	/**
	 * constructor with parameter initial to initialize an object with existing data
	 * 
	 * @param initial
	 * @throws Exception
	 */
	CommonMultiIntervalSet(IntervalSet<L> initial) {
		for (L l : initial.labels()) {
			IntervalSet<Integer> is = NonOverlapIntervalSet.create(IntervalSet.empty());
			try {
				is.insert(initial.start(l), initial.end(l), 0);
			} catch (Exception e) {
				System.out.println("input interval error");
				e.printStackTrace();
			}

		}
	}

	@Override
	public boolean insert(long start, long end, L label) throws Exception {
		if (start < 0 || end < 0 || start >= end || label == null) {
			throw new IntervalParamsWrongException("input interval error!");
		}

		if (mis.containsKey(label)) {
			IntervalSet<Integer> temp = mis.get(label);
			temp.insert(start, end, temp.labels().size());
		} else {
			IntervalSet<Integer> is = NonOverlapIntervalSet.create(IntervalSet.empty());
			is.insert(start, end, 0);
			mis.put(label, is);
		}
		checkRep();
		return true;
	}

	@Override
	public Set<L> labels() {
		Set<L> ans = new HashSet<>();
		for (Map.Entry<L, IntervalSet<Integer>> entry : mis.entrySet()) {
			ans.add(entry.getKey());
		}
		checkRep();
		return ans;
	}

	@Override
	public boolean remove(L label) {
		if (mis.containsKey(label)) {
			mis.remove(label);
			checkRep();
			return true;
		}
		checkRep();
		return false;
	}

	@Override
	public IntervalSet<Integer> intervals(L label) {
		IntervalSet<Integer> intervalSet = mis.get(label);
		IntervalSet<Integer> ret = IntervalSet.empty();
		if (intervalSet == null) {
			checkRep();
			return ret;
		}
		List<Long> startList = new ArrayList<>();
		Map<Long, Long> temp = new HashMap<>();
		for (Integer i : intervalSet.labels()) {
			startList.add(intervalSet.start(i));
			temp.put(intervalSet.start(i), intervalSet.end(i));
		}
		Collections.sort(startList);
		for (int i = 0; i < startList.size(); i++) {
			try {
				ret.insert(startList.get(i), temp.get(startList.get(i)), i);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		checkRep();
		return ret;
	}

	@Override
	public long span() {
		if (mis.size() == 0) {
			return 0;
		}
		List<Long> startList = new ArrayList<>();
		List<Long> endList = new ArrayList<>();
		for (Map.Entry<L, IntervalSet<Integer>> entry : mis.entrySet()) {
			IntervalSet<Integer> tempIntervalSet = entry.getValue();
			for (Integer i : tempIntervalSet.labels()) {
				startList.add(tempIntervalSet.start(i));
				endList.add(tempIntervalSet.end(i));
			}
		}
		Collections.sort(startList);
		Collections.sort(endList);
		checkRep();
		return endList.get(endList.size() - 1) - startList.get(0);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if(mis.size()==0)return null;
		for(Map.Entry<L, IntervalSet<Integer>> entry : mis.entrySet()) {
			sb.append(entry.getKey().toString());
			sb.append(":");
			IntervalSet<Integer> temp = entry.getValue();
			for(Integer i: temp.labels()) {
				sb.append("\t");
				sb.append(temp.start(i));
				sb.append("  ---  ");
				sb.append(temp.end(i));
			}
			sb.append('\n');
		}
		return sb.toString();
	}
	
}
