package api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import intervalset.IntervalSet;
import multiintervalset.MultiIntervalSet;


/**
 * APIs: to calculate the similarity, coflictratio, freetimeration
 * @author Huang ZiYang
 *
 * @param <L> the type of the label
 */
public interface APIs<L> {

	
	/**
	 * calculate the similarity between two MultiIntervalSet s1 and s2
	 * @param <L> the type of label
	 * @param s1 the first MultiIntervalSet
	 * @param s2 the second MultiIntervalSet
	 * @return the similarity of two set
	 */
	public static <L> double Similarity(MultiIntervalSet<L> s1, MultiIntervalSet<L> s2) {
		double sim = 0;
		long x1_start = Long.MAX_VALUE, x2_start = Long.MAX_VALUE;
		long x1_end = Long.MIN_VALUE, x2_end = Long.MIN_VALUE;
		long start, end;
		for (L l : s1.labels()) {
			IntervalSet<Integer> temp = s1.intervals(l);
			Long start_temp = temp.start(0);
			Long end_temp = temp.end(temp.labels().size() - 1);
			if (start_temp < x1_start) {
				x1_start = start_temp;
			}
			if (end_temp > x1_end) {
				x1_end = end_temp;
			}
		}
		for (L l : s2.labels()) {
			IntervalSet<Integer> temp = s2.intervals(l);
			Long start_temp = temp.start(0);
			Long end_temp = temp.end(temp.labels().size() - 1);
			if (start_temp < x2_start) {
				x2_start = start_temp;
			}
			if (end_temp > x2_end) {
				x2_end = end_temp;
			}
		}
		for (L l : s1.labels()) {
			if (!s2.labels().contains(l)) {
				continue;
			} else {
				IntervalSet<Integer> x1 = s1.intervals(l);
				IntervalSet<Integer> x2 = s2.intervals(l);
				for (int i = 0; i < x1.labels().size(); i++) {
					for (int j = 0; j < x2.labels().size(); j++) {
						long x1s = x1.start(i);
						long x1e = x1.end(i);
						long x2s = x2.start(j);
						long x2e = x2.end(j);
						if (x1s >= x2s && x1s < x2e && x1e > x2e) {
							sim += (double) (x2e - x1s);
						} else if (x1e <= x2e && x1e > x2s && x1s < x2s) {
							sim += (double) (x1e - x2s);
						} else if (x1e <= x2e && x1s >= x2s) {
							sim += (double) (x1e - x1s);
						}

					}
				}
			}
		}
		start = x1_start < x2_start ? x1_start : x2_start;
		end = x1_end > x2_end ? x1_end : x2_end;
		sim /= end - start;
		return sim;
	}

	
	/**
	 * calculate the conflict ratio of the IntervalSet
	 * @param <L> the type of label
	 * @param set the IntervalSet
	 * @return the conflict ratio
	 */
	public static <L> double calcConflictRatio(IntervalSet<L> set) {
		long time = 0;
		long conflict = 0;
		List<List<Long>> timeList = new ArrayList<>();
		for (L l : set.labels()) {
			long s = set.start(l);
			long e = set.end(l);
			time += e - s;
			List<Long> temp = new ArrayList<>();
			temp.add(s);
			temp.add(e);
			timeList.add(temp);
		}

		for (int i = 0; i < timeList.size(); i++) {
			long is = timeList.get(i).get(0);
			long ie = timeList.get(i).get(1);
			for (int j = i + 1; j < timeList.size(); j++) {
				long js = timeList.get(j).get(0);
				long je = timeList.get(j).get(1);
				if (js >= is && js < ie) {
					if (je <= ie) {
						conflict += je - js;
						time -= je-js;
					} else {
						conflict += ie - js;
						time -= ie-js;
					}
				} else if (je <= ie && je > is) {
					if (js >= is) {
						conflict += je - js;
						time -= je-js;
					} else {
						conflict += je - is;
						time -= je-is;
					}
				}
			}
		}
		return (double) conflict / (double) time;
	}

	
	/**
	 * calculate the conflict ratio of the MultiIntervalSet
	 * @param <L> the type of label
	 * @param set the MultiIntervalSet
	 * @param start the start time of the MultiIntervalSet
	 * @param end the end time of the MultiIntervalSet
	 * @return the conflict ratio
	 */
	public static <L> double calcConflictRatio(MultiIntervalSet<L> set, long start,long end) {
		if(set.labels().isEmpty())return 0;
		long conflict = 0;
		List<Long> startList = new ArrayList<>();
		Map<Long, Long> timeline = new HashMap<>();
		for(L l: set.labels()) {
			IntervalSet<Integer> temp = set.intervals(l);
			for(Integer i:temp.labels()) {
				long s = temp.start(i);
				long e = temp.end(i);
				if(startList.contains(s)) {
					if(timeline.get(s)==e) {
						conflict+=e-s;
					}
				}else {
					startList.add(s);
					timeline.put(s, e);
				}
			}
		}
		return (double)conflict/(end-start);
	}

	/**
	 * calculate the free time ratio of the IntervalSet
	 * @param <L> the type of the ratio
	 * @param set the IntervalSet
	 * @param start the start time of the IntervalSet
	 * @param end the end time of the IntervalSet
	 * @return the free time ratio
	 */
	public static <L> double calcFreeTimeRatio(IntervalSet<L> set,long start,long end) {
		if(set.labels().isEmpty())return 0;
		long blank = 0;
		List<Long> startList = new ArrayList<>();
		Map<Long, Long> timeline = new HashMap<>();
		for(L l:set.labels()) {
			long s = set.start(l);
			long e = set.end(l);
			if(startList.contains(s)) {
				if(timeline.get(s) < e) {
					timeline.put(s, e);
				}
			}else {
				startList.add(s);
				timeline.put(s, e);
			}
		}
		
		Collections.sort(startList);
		int length = startList.size();
		long now = timeline.get(startList.get(0));
		for(int i=1;i<length;i++) {
			if(startList.get(i) > now) {
				blank += startList.get(i) - now;
			}
			now = timeline.get(startList.get(i));
		}
		
		return (double)blank/(end-start);
	}

	/**
	 * calculate the free time ratio of the MultiIntervalSet
	 * @param <L> the type of the ratio
	 * @param set the MultiIntervalSet
	 * @param start the start time of the MultiIntervalSet
	 * @param end the end time of the MultiIntervalSet
	 * @return the free time ratio
	 */
	public static <L> double calcFreeTimeRatio(MultiIntervalSet<L> set,long start, long end) {
		if(set.labels().isEmpty())return 0;
		long blank = 0;
		List<Long> startList = new ArrayList<>();
		Map<Long, Long> timeline = new HashMap<>();
		for(L l:set.labels()) {
			IntervalSet<Integer> tempIntervalSet = set.intervals(l);
			for(Integer i: tempIntervalSet.labels()) {
				long s = tempIntervalSet.start(i);
				long e = tempIntervalSet.end(i);
				if(startList.contains(s)) {
					if(timeline.get(s) < e) {
						timeline.put(s, e);
					}
				}else {
					startList.add(s);
					timeline.put(s, e);
				}
			}
		}
		
		Collections.sort(startList);
		int length = startList.size();
		if(startList.get(0)>start) {
			blank+=startList.get(0)-start;
		}
		long now = timeline.get(startList.get(0));
		for(int i=1;i<length;i++) {
			if(startList.get(i) > now) {
				blank += startList.get(i) - now;
			}
			now = timeline.get(startList.get(i));
		}
		if(end>now)blank +=end-now;
		return (double)blank/(end-start);
	}

}
