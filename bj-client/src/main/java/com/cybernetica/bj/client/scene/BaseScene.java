package com.cybernetica.bj.client.scene;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidParameterException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cybernetica.bj.client.Main;
import com.cybernetica.bj.client.exceptions.ClientException;
import com.cybernetica.bj.client.services.MessageService;

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
public class BaseScene<T extends BaseScene<T>> {
	protected static final Logger logger = LoggerFactory.getLogger(BaseScene.class);
	private static BaseScene<? extends BaseScene<?>> instance;
	private Stage stage;
	
	@SuppressWarnings("unchecked")
	public static <T> T create(Stage stage,Class<T> sceneClass) throws ClientException{
		logger.trace("creating "+sceneClass.getName());
		if(instance!=null)
			return (T) instance;
		//Class<T> lookupClass = (Class<T>) MethodHandles.lookup().lookupClass();
		try {
			instance= (BaseScene<? extends BaseScene<?>>) sceneClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new ClientException("error.scene.create", e);
		}
		instance.setStage(stage);
		return (T) instance;
	}
	
	public static BaseScene<?> get() {
		return instance;
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

	/**
	 * Returns current scene
	 * @return
	 */
	public Scene getScene(){
		return stage.getScene();
	}

	/**
	 * Override to add bindings / custom logic
	 */
	protected void postBuild() {

	}

	/**
	 * Load scene from resources
	 * @return
	 * @throws Exception
	 */
	public Parent replaceSceneContent() throws Exception {
		String name = this.getClass().getSimpleName().toLowerCase().replace("scene", "");
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
	public Parent replaceSceneContent(String fxml, String ccsFile) throws Exception {
		Parent page = (Parent) FXMLLoader.load(Main.class.getResource(fxml), null, new JavaFXBuilderFactory());
		Scene scene = getScene();
		if (scene == null) {
			scene = new Scene(page, 600, 450);
			scene.getStylesheets().add(Main.class.getResource(ccsFile).toExternalForm());
			stage.setScene(scene);
		} else {
			getScene().setRoot(page);
		}
		stage.sizeToScene();
		
		postBuild();
		
		return page;
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
			Method method = node.getClass().getDeclaredMethod("getText");
			return (String) method.invoke(node);
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
