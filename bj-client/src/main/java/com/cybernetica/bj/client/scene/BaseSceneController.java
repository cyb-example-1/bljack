package com.cybernetica.bj.client.scene;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidParameterException;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cybernetica.bj.client.Main;
import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.client.services.MessageService;
import com.cybernetica.bj.common.interfaces.Singleton;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Base scene
 * 
 * @author dmitri
 *
 */
public class BaseSceneController<T extends BaseSceneController<T>> implements Singleton<T> {
	protected static final Logger logger = LoggerFactory.getLogger(BaseSceneController.class);

	private Stage stage;
	private Scene scene;
	
	public static <T  extends BaseSceneController<T>> T create(Stage stage,Class<T> sceneClass) throws ClientException{
		logger.trace("creating "+sceneClass.getName());
		T instance = Singleton.getSingleton(sceneClass);
		instance.setStage(stage);
		return (T) instance;
	}

	/**
	 * Returns stage
	 * @return
	 */
	public Stage getStage() {
		return stage;
	}
	
	/**
	 * Sets active stage
	 * @param stage
	 */
	protected void setStage(Stage stage) {
		this.stage = stage;

	}
	
	public Scene getScene(){
		return getScene(null,null);
	}
	/**
	 * Returns current scene
	 * @param cssFile 
	 * @param fxml 
	 * @return
	 * @throws IOException 
	 */
	public Scene getScene(String fxml, String cssFile){
		if (scene == null) {
			Parent page;
			try {
				page = (Parent) FXMLLoader.load(Main.class.getResource(fxml), null, new JavaFXBuilderFactory());
			} catch (IOException e) {
				logger.error("scene error",e);
				throw new RuntimeException("system error");
			}
			scene = new Scene(page, 600, 450);
			scene.getStylesheets().add(Main.class.getResource(cssFile).toExternalForm());
			postBuild();
		} 
		return scene;
	}

	/**
	 * Override to add bindings / custom logic
	 */
	protected void postBuild() {

	}
	
	
	/**
	 * triggers when scene has been bound to stage or re-activated
	 */
	protected void postActivate(){
		
		
	}

	/**
	 * Load scene from resources
	 * @return
	 * @throws Exception
	 */
	public Parent replaceSceneContent() throws Exception {
		String name = this.getClass().getSimpleName().toLowerCase().replace("scenecontroller", "");
		name= "/form/"+name+"/"+name;
		String fxml = name + ".fxml";
		String cssFile = name + ".css";
		return replaceSceneContent(fxml, cssFile);
	}

	/**
	 * Load scene from resources
	 * @return
	 * @throws Exception
	 */
	public Parent replaceSceneContent(String fxml, String cssFile) throws Exception {
		Scene scene = getScene(fxml,cssFile);
		stage.setScene(scene);
		stage.sizeToScene();
		
		this.postActivate();
		return scene.getRoot();
	}
	
	/**
	 * Finds element by id
	 * @param id
	 * @return
	 */
	public Node getElementById(String id){
		return getScene().lookup("#"+id);
	}
	
	/**
	 * Get element text
	 * @param id
	 * @return
	 */
	public String getElementTextById(String id){
		Node node = getElementById(id);
		if(node==null)
			return null;
		try {
			return (String) MethodUtils.invokeMethod(node, "getText");
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			logger.warn("getElementTextById failed  "+node.getClass().getName());
			return null;
		}
		
	}
	
	/**
	 * Set element text
	 * @param id
	 * @param messageCode
	 * @return
	 */
	//will use straight approach without method invoke
	public String setElementTextById(String id,String messageCode){
		Node node = getElementById(id);
		return setElementText(node,messageCode);

	}	

	/**
	 * Set element text
	 * @param node
	 * @param messageCode
	 * @return
	 */
	protected String setElementText(Node node, String messageCode) {
		if(node==null)
			return null;
		String text=MessageService.message(messageCode);
		if(node instanceof Button){
			Button elem=(Button) node;
			elem.setText(text);
		}
		else if (node instanceof Label){
			Label elem=(Label) node;
			elem.setText(text);
		}
		else {
			logger.error("setElementText failed  "+node.getClass().getName());
			throw new InvalidParameterException("setElementTextById: " +node.getClass().getName());
		}
		
		return text;
		
	}
}
