package il.co.topq.systems.report.common.obj;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TimeRange {

    private Long lowBoundDate;

    private Long upBoundDate;

    public TimeRange() {
        super();
    }

    public TimeRange(Long lowBoundDate, Long upBoundDate) {
        super();
        this.lowBoundDate = lowBoundDate;
        this.upBoundDate = upBoundDate;
    }

    public void setLowBoundDate(Long lowBoundDate) {
        this.lowBoundDate = lowBoundDate;
    }

    public Long getLowBoundDate() {
        return lowBoundDate;
    }

    public void setUpBoundDate(Long upBoundDate) {
        this.upBoundDate = upBoundDate;
    }

    public Long getUpBoundDate() {
        return upBoundDate;
    }

    public boolean isEmpty() {
        return (null == lowBoundDate) && (null == upBoundDate);
    }

	@Override
	public String toString() {
		return "TimeRange [lowBoundDate=" + lowBoundDate + ", upBoundDate=" + upBoundDate + "]";
	}
    
    
}
