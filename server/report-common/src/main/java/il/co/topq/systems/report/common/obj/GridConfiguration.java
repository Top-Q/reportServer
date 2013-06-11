package il.co.topq.systems.report.common.obj;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * This class represents all the grid configurations in the application client
 * side.<br>
 * It will hold the grids
 * 
 * @author Eran Golan & Tomer Gafner
 * 
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class GridConfiguration implements Serializable {

	public static final Integer NO_ID = -1;

	/**
	 * This ENUM will be state the grid which the configuration is being saved
	 * for.
	 */
	public enum Type {
		SCENARIO_REPORT("scenarioReport"), TEST_REPORT("testReport"), SCENARIO_DETAILS("scenarioDetails"), SCENARIO_CUSTOM_REPORT(
				"scenarioCustomReport"), TEST_CUSTOM_REPORT("testCustomReport");

		private String value;

		Type(String value) {
			this.value = value;
		}

	}

	/**
	 * This will be used for custom reports grid.<br>
	 * Each custom report has a unique id, will save customized configuration
	 * for each custom report<br>
	 */
	private Integer id = NO_ID;

	private Type gridConfigurationType;

	public Type getGridConfigurationType() {
		return gridConfigurationType;
	}

	public void setGridConfigurationType(Type gridConfigurationType) {
		this.gridConfigurationType = gridConfigurationType;
	}

	@XmlTransient
	private final int DEFAULT_CHUNK_SIZE = 30;

	/**
	 * will indicate how many elements are presented in the grid.
	 */
	protected int chunkSize = DEFAULT_CHUNK_SIZE;
	
	@XmlElementWrapper(name = "gridColumns")
	private List<String> columns;

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	public Integer getId() {
		return id;
	}

	public int getChunkSize() {
		return chunkSize;
	}

	public void setChunkSize(int chunkSize) {
		this.chunkSize = chunkSize;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
