package com.bacon.mayo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Throwables;

public class ServerStandaloneLauncher {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServerStandaloneLauncher.class);


	public static void main(String[] args) {
		configureLogging();
		
		final Server server = start(Integer.parseInt(System.getProperty("bacon.mayo.port", "8889")));

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				LOGGER.info("Running shutdown hook");
				try {
					server.stop();
				} catch (Exception e) {
					LOGGER.error("Error while shutting down", e);
				}
			}
		});

		listenForCommands();
		return;
	}

	public static Server start(int port) {
		LOGGER.debug("Starting Server Standalone Module");

		Server server = new Server(port);

		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath("/");
		String webDir = ServerStandaloneLauncher.class.getClassLoader().getResource("webapp").toExternalForm();
		webapp.setWar(webDir);
		server.setHandler(webapp);

		try {
			LOGGER.info("Launching Server");
			server.start();
		} catch (Exception e) {
			Throwables.propagate(e);
		}

		return server;
	}

	private static void configureLogging() {
		PropertyConfigurator.configure((String) System.getProperty("bacon.logger.config.file", "bacon-log4j.properties"));
	}

	private static void listenForCommands() {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				System.in, "UTF-8"))) {

			System.out.println("Type 'exit' to shutdown.");
			while (true) {
				String token = null;
				try {
					token = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if ("exit".equalsIgnoreCase(token)) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					System.exit(0);
				}
			}
		} catch (IOException e1) {
			Throwables.propagate(e1);
		}
	}

}
