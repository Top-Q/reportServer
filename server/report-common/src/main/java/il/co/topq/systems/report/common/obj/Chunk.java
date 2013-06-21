package il.co.topq.systems.report.common.obj;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Chunk {

	private Integer startIndex;
	private Integer length;

	public Chunk() {
	}

	public Chunk(final Integer startIndex, final Integer length) {
		this.startIndex = startIndex;
		this.length = length;
	}

	public void setStartIndex(final Integer startIndex) {
		this.startIndex = startIndex;
	}

	public Integer getStartIndex() {
		return startIndex;
	}

	public void setLength(final Integer length) {
		this.length = length;
	}

	public Integer getLength() {
		return length;
	}

	@Override
	public String toString() {
		return "startIndex=" + startIndex + "&length=" + length;
	}

}
