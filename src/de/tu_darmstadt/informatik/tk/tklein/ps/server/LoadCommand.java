package de.tu_darmstadt.informatik.tk.tklein.ps.server;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(namespace="http://www.rbg.informatik.tu-darmstadt.de/digitalsignage")
public class LoadCommand {
	@XmlTransient
	protected Resource resource = null;
	protected long displaySpan = 0;

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}
	
	public long getDisplaySpan() {
		return displaySpan;
	}
	
	public void setDisplaySpan(long displaySpan) {
		this.displaySpan = displaySpan;
	}
}
