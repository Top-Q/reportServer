package il.co.topq.systems.report.common.obj;

import il.co.topq.systems.report.common.model.ScenarioProperty;
import il.co.topq.systems.report.common.model.TestProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlSeeAlso({ ScenarioProperty.class, TestProperty.class })
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractQuery {

	@XmlElement
	protected Chunk chunk;

	@XmlElement
	protected TimeRange timeRange;

	@XmlElement
	protected SortingColumn sortingColumn;

	@XmlElement
	protected String searchName;

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public Chunk getChunk() {
		return chunk;
	}

	public void setChunk(Chunk chunk) {
		this.chunk = chunk;
	}

	public TimeRange getTimeRange() {
		return timeRange;
	}

	public void setTimeRange(TimeRange timeRange) {
		this.timeRange = timeRange;
	}

	public SortingColumn getSortingColumn() {
		return sortingColumn;
	}

	public void setSortingColumn(SortingColumn sortingColumn) {
		this.sortingColumn = sortingColumn;
	}

}