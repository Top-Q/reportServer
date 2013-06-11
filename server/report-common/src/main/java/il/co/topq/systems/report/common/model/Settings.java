package il.co.topq.systems.report.common.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;

public class Settings<T> implements Serializable {

	@SuppressWarnings("UnusedDeclaration")
	public Settings() {
//		setIdentifier();
	}

	public Settings(String type, Blob instance) {
		this.type = type;
		this.blobData = instance;
//		setIdentifier();
	}

	// private static final long serialVersionUID = 1L;
	// private static int identifierGenerator = 0;
	// private int identifier;

	private Integer id;

	private String type;

	private Blob blobData;

	private byte[] data;

	@SuppressWarnings("unchecked")
	public T getInstance() throws IOException, ClassNotFoundException, SQLException {
		ByteArrayInputStream bis = new ByteArrayInputStream(blobData.getBytes(1, (int) blobData.length()));
		ObjectInputStream in = new ObjectInputStream(bis);
		return (T) in.readObject();
	}

	public static <T> Settings getSettings(Class<T> clazz, Blob blobObject) throws IOException {
		return new Settings(clazz.toString(), blobObject);
	}

	public Blob getBlobData() {
		return blobData;
	}

	public void setBlobData(Blob blobData) {
		this.blobData = blobData;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@SuppressWarnings("UnusedDeclaration")
	public String getType() {
		return type;
	}

	@SuppressWarnings("UnusedDeclaration")
	public void setType(String type) {
		this.type = type;
	}

//	public void setIdentifier() {
//		this.identifier = identifierGenerator;
//		identifierGenerator++;
//	}
}
