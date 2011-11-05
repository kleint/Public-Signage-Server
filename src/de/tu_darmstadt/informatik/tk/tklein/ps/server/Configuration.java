package de.tu_darmstadt.informatik.tk.tklein.ps.server;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(namespace="http://www.rbg.informatik.tu-darmstadt.de/digitalsignage")
public class Configuration {
	
	protected String address = null;
	protected int port = 0;
	protected String topic = null;
	protected List<LoadCommand> loadCommandList = null;
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public String getTopic() {
		return topic;
	}
	
	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void setLoadCommandList(List<LoadCommand> loadCommandList) {
		this.loadCommandList = loadCommandList;
	}
	
	public List<LoadCommand> getLoadCommandList() {
		return loadCommandList;
	}
}
