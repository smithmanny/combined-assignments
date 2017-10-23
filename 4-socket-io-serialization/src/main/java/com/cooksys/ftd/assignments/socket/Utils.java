package com.cooksys.ftd.assignments.socket;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.Student;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * Shared static methods to be used by both the {@link Client} and
 * {@link Server} classes.
 */
public class Utils {
	/**
	 * @return a {@link JAXBContext} initialized with the classes in the
	 *         com.cooksys.socket.assignment.model package
	 */
	public static JAXBContext createJAXBContext() {
		JAXBContext context;

		try {
			context = JAXBContext.newInstance(Config.class, Student.class);
			return context;
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Reads a {@link Config} object from the given file path.
	 *
	 * @param configFilePath
	 *            the file path to the config.xml file
	 * @param jaxb
	 *            the JAXBContext to use
	 * @return a {@link Config} object that was read from the config.xml file
	 */
	public static Config loadConfig(String configFilePath, JAXBContext jaxb) {
		if (jaxb == null)
			return null;

		try {
			Unmarshaller unmarshaller = jaxb.createUnmarshaller();

			Source data = new StreamSource(new File(configFilePath));
			Config config = unmarshaller.unmarshal(data, Config.class).getValue();

			return config;
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}
}
