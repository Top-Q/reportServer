package il.co.topq.systems.report.common.jaxbWrappers;

import il.co.topq.systems.report.common.obj.Chunk;
import il.co.topq.systems.report.common.obj.TimeRange;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Chunk with time range
 *
 * @author Daniel Haimov;
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TimeRangedChunk {

    private Chunk chunk;

    private TimeRange timeRange;

    public TimeRangedChunk() {
        chunk = null;
        timeRange = null;
    }

    public TimeRangedChunk(Chunk chunk, TimeRange timeRange) {
        this.chunk = chunk;
        this.timeRange = timeRange;
    }

    public void setChunk(Chunk chunk) {
        this.chunk = chunk;
    }

    public void setTimeRange(TimeRange timeRange) {
        this.timeRange = timeRange;
    }

    public Chunk getChunk() {
        return chunk;
    }

    public TimeRange getTimeRange() {
        return timeRange;
    }
}
