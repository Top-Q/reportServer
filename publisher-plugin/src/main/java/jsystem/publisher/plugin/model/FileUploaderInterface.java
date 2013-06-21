package jsystem.publisher.plugin.model;


public interface FileUploaderInterface {

	public void upload() throws Exception ;
	public void setDomain(String domain);
	public void setUrl(String url);
}
