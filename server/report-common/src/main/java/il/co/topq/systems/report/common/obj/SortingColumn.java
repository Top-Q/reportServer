package il.co.topq.systems.report.common.obj;

/**
 * This class represents a pair of property and the sorting functionality selected
 * 
 * @author Erango
 */
public class SortingColumn {

	private Boolean asc;

	// TODO: read startTime value out of consts class
	private String name = "startTime";

	public SortingColumn(String name, Boolean asc) {
		this.name = name;
		this.asc = asc;
	}

	public Boolean getAsc() {
		return asc;
	}

	public SortingColumn() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean isAsc() {
		return asc;
	}

	public void setAsc(Boolean asc) {
		this.asc = asc;
	}

	@Override
	public String toString() {
		return "SortingColumn [asc=" + asc + ", name=" + name + "]";
	}

}
