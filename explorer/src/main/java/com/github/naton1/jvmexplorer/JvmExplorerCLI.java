package com.github.naton1.jvmexplorer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class JvmExplorerCLI extends Application {

	public static final File APP_DIR = new File(System.getProperty("user.home"), "jvm-explorer");

	@Override
	public void start(Stage primaryStage) throws Exception {
		final FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/main.fxml"));
		final Parent root = loader.load();
		final JvmExplorerController jvmExplorerController = loader.getController();

		final Scene scene = new Scene(root);
		scene.getStylesheets().addAll("css/style-override.css", "css/java-keywords.css");
		primaryStage.setScene(scene);

		jvmExplorerController.initialize(primaryStage);

		primaryStage.show();
		log.debug("Started explorer");
	}

}
