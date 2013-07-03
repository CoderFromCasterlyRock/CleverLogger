package org.coder.from.casterly.rock.cleverlogger.core;

import java.text.*;
import java.util.*;


public class DetailedBenchmarker extends Benchmarker{
	
    private int size;
	private TreeMap<Long, Integer> results = new TreeMap<Long, Integer>();
	
	public DetailedBenchmarker( ){
	    super();
    }

	public DetailedBenchmarker( int warmup ){
	    super( warmup );
    }
	
	
	@Override
	public void reset() {
		super.reset();
		size = 0;
		results.clear();
	}
	
	
	@Override
	public boolean measure( long lastTime ){
		boolean counted = super.measure(lastTime);
		if (counted) {
			Integer count = results.get(lastTime);
			if (count == null) {
				results.put(lastTime, 1);
			} else {
				results.put(lastTime, count.intValue() + 1);
			}
			size++;
		}
		return counted;
	}
	
	
	private String formatPercentage(double x) {
		NumberFormat percentFormat = NumberFormat.getPercentInstance();
		percentFormat.setMaximumFractionDigits(3);
		return percentFormat.format(x);
	}
	
	private void addPercentile(StringBuilder sb, double perc) {
		
		if (results.isEmpty()) {
			return;
		}

		long max = -1;
		
		long x = Math.round(perc * size);
		Iterator<Map.Entry<Long, Integer>> iter = results.entrySet().iterator();
		int i = 0;
		long sum = 0;
		while(iter.hasNext()) {
			Map.Entry<Long, Integer> entry = iter.next();
			long time = entry.getKey();
			int count = entry.getValue();
			i += count;
			sum += (count * time);
			if (i >= x) {
				max = time;
				break;
			}
		}
		sb.append(" | ").append(formatPercentage(perc)).append(": avg=").append(sum / i).append(" max=").append(max);
	}
	
	@Override
	public String results() {
		StringBuilder sb = new StringBuilder(super.results());

		addPercentile(sb, 0.75D);
		addPercentile(sb, 0.9D);
		addPercentile(sb, 0.99D);
		addPercentile(sb, 0.999D);
		addPercentile(sb, 0.9999D);
		addPercentile(sb, 0.99999D);
		
		return sb.toString();
	}
	
}
