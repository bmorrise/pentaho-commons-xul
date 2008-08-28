/**
 * 
 */
package org.pentaho.ui.xul;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.pentaho.ui.xul.containers.XulWindow;

/**
 * @author OEM
 *
 */
public class XulWindowContainer {
  private List<XulWindow> windows;
  private List<Document> documents;
  
  private Map<String, XulEventHandler> eventHandlers;
  
  public XulWindowContainer(){
    windows = new ArrayList<XulWindow>();
    documents = new ArrayList<Document>();
    eventHandlers = new HashMap<String, XulEventHandler>();
  }

  public Document getDocumentRoot(){
    return this.documents.get(0);
  }

  public void addDocument(Document document){
    this.documents.add(document);
  }
  
  public Document remoteCall(XulServiceCall serviceUrl){
    return null;
  }
  
  
  public void addEventHandler(String id, String eventClassName){
    if(eventHandlers.containsKey(id)){ //if already registered
      return;
    }
    
    try{
      Class cls = Class.forName(eventClassName);
      XulEventHandler eventHandler = (XulEventHandler) cls.newInstance();
      eventHandler.setXulWindowContainer(this);
      eventHandlers.put(id, eventHandler);
      
    } catch(ClassNotFoundException e){
      System.out.println("backing class not found");
      e.printStackTrace(System.out);
    } catch(Exception e){
      System.out.println("Error with Backing class creation");
      e.printStackTrace(System.out);
    }
  }
  
  public XulEventHandler getEventHandler(String key) throws XulException{
    if(eventHandlers.containsKey(key)){
      return eventHandlers.get(key);
    } else {
      throw new XulException(String.format("Could not find Event Handler with the key : %s", key));
    }
  }
}