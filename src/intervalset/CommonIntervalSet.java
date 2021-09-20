package intervalset;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import exception.IntervalParamsWrongException;

public class CommonIntervalSet<L> implements IntervalSet<L> {


	// rep
	private List<interval> intervals = new ArrayList<>();

	// AF:
	// represents a timeline, each entry in the intervals represent an interval
	// from start to end, labeled with label

	// RI:
	// two intervals can't have the same label
	// for any interval in the set, start < end

	// safety from rep exposure:
	// intervals is private, and we don't offer alias of intervals to the
	// client
	
	
	private class interval {
		public Long start;
		public Long end;
		public L label;

		interval(Long start, Long end, L label) {
			this.start = start;
			this.end = end;
			this.label = label;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((end == null) ? 0 : end.hashCode());
			result = prime * result + ((label == null) ? 0 : label.hashCode());
			result = prime * result + ((start == null) ? 0 : start.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null)
				return false;
			@SuppressWarnings("unchecked")
			interval other = (interval) o;
			if (this.start.equals(other.start) && this.end.equals(other.end) && this.label.equals(other.label)) {
				return true;
			}
			return false;
		}
	}



	private void checkRep() {
		for (int i = 0; i < intervals.size(); i++) {
			for (int j = i + 1; j < intervals.size(); j++) {
				assert !intervals.get(i).label.equals(intervals.get(j).label);
			}
		}

		for (int i = 0; i < intervals.size(); i++) {
			assert intervals.get(i).start < intervals.get(i).end;
		}
	}

	@Override
	public boolean insert(long start, long end, L label) throws Exception {
		if (start < 0 || end < 0 || start >= end || label == null) {
			throw new IntervalParamsWrongException("input interval error!");
		}

		for (interval i : intervals) {
			if (i.label.equals(label)) {
				checkRep();
				return false;
			}
		}
		intervals.add(new interval(start, end, label));
		checkRep();
		return true;
	}

	@Override
	public Set<L> labels() {
		Set<L> ans = new HashSet<>();
		for (interval i : intervals) {
			ans.add(i.label);
		}
		checkRep();
		return ans;
	}

	@Override
	public boolean remove(L label) {
		for (interval i : intervals) {
			if (i.label.equals(label)) {
				intervals.remove(i);
				checkRep();
				return true;
			}
		}
		checkRep();
		return false;
	}

	@Override
	public long start(L label) {
		for (interval i : intervals) {
			if (i.label.equals(label)) {
				return i.start;
			}
		}
		checkRep();
		return -1;
	}

	@Override
	public long end(L label) {
		for (interval i : intervals) {
			if (i.label.equals(label)) {
				return i.end;
			}
		}
		checkRep();
		return -1;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommonIntervalSet<?> other = (CommonIntervalSet<?>) obj;
		if (intervals == null) {
			if (other.intervals != null)
				return false;
		} else if (!intervals.equals(other.intervals))
			return false;
		return true;
	}
	
	@Override
	public long span() {
		if(intervals.size()==0)return 0;
		List<Long> startList = new ArrayList<>();
		List<Long> endList = new ArrayList<>();
		for(interval i : intervals) {
			startList.add(i.start);
			endList.add(i.end);
		}
		Collections.sort(startList);
		Collections.sort(endList);
		return endList.get(endList.size()-1) - startList.get(0);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for(interval i:intervals) {
			sb.append(i.label);
			sb.append(": ");
			sb.append(i.start);
			sb.append("  ---  ");
			sb.append(i.end);
			sb.append('\n');
		}	
		return sb.toString();
	}

}
