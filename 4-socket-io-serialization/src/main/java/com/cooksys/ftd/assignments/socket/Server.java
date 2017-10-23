package com.cooksys.ftd.assignments.socket;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.Student;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Server extends Utils {

	/**
	 * Reads a {@link Student} object from the given file path
	 *
	 * @param studentFilePath
	 *            the file path from which to read the student config file
	 * @param jaxb
	 *            the JAXB context to use during unmarshalling
	 * @return a {@link Student} object unmarshalled from the given file path
	 */
	public static Student loadStudent(String studentFilePath, JAXBContext jaxb) {
		try {
			Unmarshaller unmarshaller = jaxb.createUnmarshaller();
			File file = new File(studentFilePath);
			Student student = (Student) unmarshaller.unmarshal(file);

			return student;
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * The server should load a
	 * {@link com.cooksys.ftd.assignments.socket.model.Config} object from the
	 * <project-root>/config/config.xml path, using the "port" property of the
	 * embedded {@link com.cooksys.ftd.assignments.socket.model.LocalConfig}
	 * object to create a server socket that listens for connections on the
	 * configured port.
	 *
	 * Upon receiving a connection, the server should unmarshal a
	 * {@link Student} object from a file location specified by the config's
	 * "studentFilePath" property. It should then re-marshal the object to xml
	 * over the socket's output stream, sending the object to the client.
	 *
	 * Following this transaction, the server may shut down or listen for more
	 * connections.
	 */
	public static void main(String[] args) {
		JAXBContext context = Server.createJAXBContext();
		Config config = Server.loadConfig("config/config.xml", Server.createJAXBContext());
		Student student = Server.loadStudent(config.getStudentFilePath(), context);

		ServerSocket server = null;
		Socket clientSocket = null;

		int port = config.getRemote().getPort();

		try {
			server = new ServerSocket(port);
			clientSocket = server.accept();
			
			DataOutputStream  dataOut = new DataOutputStream(clientSocket.getOutputStream());
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(student, dataOut);
			
			clientSocket.shutdownOutput();
			clientSocket.close();
		} catch (IOException e) {
			// TODO: handle exception
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				clientSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
