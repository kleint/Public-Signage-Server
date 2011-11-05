package de.tu_darmstadt.informatik.tk.tklein.ps.server;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import pk.aamir.stompj.Connection;
import pk.aamir.stompj.ErrorHandler;
import pk.aamir.stompj.ErrorMessage;
import pk.aamir.stompj.Message;
import pk.aamir.stompj.MessageHandler;
import pk.aamir.stompj.StompJException;

public class PublicSignageServer {
	
	protected Connection connection = null;
	protected Configuration configuration = null;
	protected int count = 1;
	
	public static void main(String[] args) {
		if(args.length == 1){
			PublicSignageServer server = new PublicSignageServer();
			server.loadConfiguration(new File(args[0]));
			server.initialize();
			server.start();
			//server.saveConfiguration(new File("res/config2.xml"));
		}else{
			System.out.println("Missing configuration parameter");
		}
	}
	
	public void initialize(){	
		//Stomp connection
        connection = new Connection(configuration.getAddress(), configuration.getPort());
        
        //Stomp Error handling
 		connection.setErrorHandler(new ErrorHandler() {
		    public void onError(ErrorMessage errorMsg) {
		        System.out.println(errorMsg.getMessage());
		        System.out.println(errorMsg.getContentAsString());
		    }
		});
 		
        //Stomp connect
        try {
			connection.connect();
			System.out.println("Connected to "+configuration.getAddress()+":"+configuration.getPort());
		} catch (StompJException e) {
			e.printStackTrace();
		}
	}
	
	public void start(){
		List<LoadCommand> loadCommandList = configuration.getLoadCommandList();
		int listSize = loadCommandList.size();
		int i = 0;
		LoadCommand loadCommand = null;
		long displaySpan = 0;
		while(true){
			if(i < listSize){
				loadCommand = loadCommandList.get(i);
				displaySpan = loadCommand.getDisplaySpan();
				sendCommand(loadCommand);
				try {
					Thread.sleep(displaySpan);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				i++;
			}else{
				i = 0;
			}
		}
	}
	
	public void sendCommand(LoadCommand loadCommand){
		String source = loadCommand.getResource().getSource().toString();
		String type = loadCommand.getResource().getType();
		String command = type+" "+source;
		connection.send(command, configuration.getTopic());
		System.out.println(count+" Command transmitted: "+command);
		count++;
	}
	
	public void loadConfiguration(File file){
		JAXBContext context = null;
		Unmarshaller unmarshaller = null;
		
		try {
			// Prepare context and marshaller
			context = JAXBContext.newInstance(Configuration.class);
			unmarshaller = context.createUnmarshaller();
			
			// Unmarshal XML to bean
			configuration = (Configuration)unmarshaller.unmarshal(file);	
			System.out.println("Configuration loaded: "+file.getAbsolutePath());
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	public void saveConfiguration(File file){
		JAXBContext context = null;
		Marshaller marshaller = null;

		try {
			// Prepare context and marshaller
			context = JAXBContext.newInstance(Configuration.class);
			marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			
			// Marshal bean to XML
			marshaller.marshal(configuration, file);
			System.out.println("Configuration saved: "+file.getAbsolutePath());
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	public void disconnect(){
		connection.disconnect();
		System.out.println("DigitalSignageServer disconnected");
	}
	
	protected void addCommand(){
		LoadCommand command = new LoadCommand();
		command.setDisplaySpan(1234);
		Resource resource = new Resource();
		try {
			resource.setSource(new URL("http://www.spiegel.de"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		resource.setType("web");
		command.setResource(resource);
		List<LoadCommand> list = new LinkedList<LoadCommand>();
		configuration.setLoadCommandList(list);
		configuration.getLoadCommandList().add(command);
	}
}
