package de.tu_darmstadt.informatik.tk.tklein.ps.server;

import java.net.URL;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace="http://www.rbg.informatik.tu-darmstadt.de/digitalsignage")
public class Resource {
	protected URL source = null;
	protected String type = null;
	
	public URL getSource() {
		return source;
	}

	public void setSource(URL source) {
		this.source = source;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
