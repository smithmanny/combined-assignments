package com.cooksys.ftd.assignments.socket;

import java.io.IOException;
import java.net.Socket;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.Student;

public class Client {

    /**
     * The client should load a {@link com.cooksys.ftd.assignments.socket.model.Config} object from the
     * <project-root>/config/config.xml path, using the "port" and "host" properties of the embedded
     * {@link com.cooksys.ftd.assignments.socket.model.RemoteConfig} object to create a socket that connects to
     * a {@link Server} listening on the given host and port.
     *
     * The client should expect the server to send a {@link com.cooksys.ftd.assignments.socket.model.Student} object
     * over the socket as xml, and should unmarshal that object before printing its details to the console.
     */
    public static void main(String[] args) {
        JAXBContext jaxb = Utils.createJAXBContext();
        Config config = Utils.loadConfig("config/config.xml", Utils.createJAXBContext());
        
        String host = config.getRemote().getHost();
        int port = config.getRemote().getPort();
        Socket server = null;
        Student student = null;
        
        try {
			server = new Socket(host, port);
			Unmarshaller unmarshaller = jaxb.createUnmarshaller();
			Source dataIn = new StreamSource(server.getInputStream());
	    	student = unmarshaller.unmarshal(dataIn, Student.class).getValue();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				server.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
}
