package hnn.models;

import java.util.Date;
import java.util.List;

public class Lightpath extends Path {
	
	private Date date;
	private long leaseTime;
	
	public Lightpath(Pair pair, List<Stretch> stretchs, Date date,
			long leaseTime) {
		super(pair, stretchs);
		this.date = date;
		this.leaseTime = leaseTime;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public long getLeaseTime() {
		return leaseTime;
	}
	public void setLeaseTime(long leaseTime) {
		this.leaseTime = leaseTime;
	}

}
