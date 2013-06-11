package il.co.topq.systems.report.common.model;



import java.util.Collection;

public interface CustomReport<P> {

	public Collection<ReportProperty> getProperties();

	public void setProperties(Collection<P> properties);
}
