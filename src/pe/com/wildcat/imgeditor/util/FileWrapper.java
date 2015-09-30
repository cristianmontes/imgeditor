/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.com.wildcat.imgeditor.util;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 *
 * @author cmontes
 */
public class FileWrapper {
    
    public void writeNew(String name){
        try { 
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element projectElement = doc.createElement("project");
            doc.appendChild(projectElement);
            
            Attr attrName = doc.createAttribute("name");
            attrName.setValue(name);
            projectElement.setAttributeNode(attrName);

            Element images = doc.createElement("images");
            projectElement.appendChild(images);

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileName));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result); 
	  } catch (Exception pce) {
		pce.printStackTrace();
	  }
    }
    public void updateImage(String idImage, String nameImage, String strImage, int width, int height ){        
        try { 
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document document = docBuilder.parse(fileName);
            
            Node images = document.getElementsByTagName("images").item(0);
            
            Element image = document.createElement("image");
            images.appendChild(image);
            
            Attr attrId = document.createAttribute("id");
            attrId.setValue(idImage);
            image.setAttributeNode(attrId);

            Attr attrName = document.createAttribute("name");
            attrName.setValue(nameImage);
            image.setAttributeNode(attrName);
            Attr attrWidth = document.createAttribute("width");
            attrWidth.setValue(""+width);
            image.setAttributeNode(attrWidth);
            Attr attrHeight = document.createAttribute("height");
            attrHeight.setValue(""+height);
            image.setAttributeNode(attrHeight);
             
            Element dataimage = document.createElement("dataimage");
            image.appendChild(dataimage);
            
            Text text = document.createTextNode(strImage);
            dataimage.appendChild(text);
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(fileName));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

	  
	  } catch (Exception tfe) {
		tfe.printStackTrace();
	  }
    }
    
    public void updateFigure(XmlComponent xmlComponent, String idImage){
        try { 
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document document = docBuilder.parse(fileName);
            
            NodeList nList = document.getElementsByTagName("image");            
            for (int temp = 0; temp < nList.getLength(); temp++) { 
		Node nNode = nList.item(temp);		
		if (nNode.getNodeType() == Node.ELEMENT_NODE) { 
                    Element imageSelected = (Element) nNode;
                    if(idImage.equalsIgnoreCase(imageSelected.getAttribute("id"))){
                        Element component = document.createElement("component");
                        imageSelected.appendChild(component);
                        Attr attrOrden = document.createAttribute("orden");
                        attrOrden.setValue(""+xmlComponent.getOrden());
                        component.setAttributeNode(attrOrden);
                        Attr attrType = document.createAttribute("type");
                        attrType.setValue(""+xmlComponent.getType());
                        component.setAttributeNode(attrType);
                        Gson gson = new Gson();
                        
                        Text text = document.createTextNode(gson.toJson(xmlComponent.getListPixel()));
                        component.appendChild(text);
                    }
		}
            }
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(fileName));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

	  
	  } catch (Exception tfe) {
		tfe.printStackTrace();
	  }
    }
    
    public Project read1(){
        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            File file = new File(fileName);
            Document doc = dBuilder.parse(file);
            
            //optional, but recommended	
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            
            
            Project project = new Project();
            project.setName(doc.getDocumentElement().getAttribute("name"));
            project.setParent(file.getParent());
            
            NodeList nList = doc.getElementsByTagName("image");            
            for (int temp = 0; temp < nList.getLength(); temp++) { 
		Node nNode = nList.item(temp);		
		if (nNode.getNodeType() == Node.ELEMENT_NODE) { 
                    Element eElement = (Element) nNode;
                    ImgComponent imgComponent = new ImgComponent(eElement.getAttribute("name"), eElement.getAttribute("id"));

                    imgComponent.setWidth(Integer.parseInt(eElement.getAttribute("width")));
                    imgComponent.setHeight(Integer.parseInt(eElement.getAttribute("height")));
                    imgComponent.setbFile(Utils.bytefromString(((Element)eElement.getElementsByTagName("dataimage").item(0)).getTextContent()));
                    
                    project.getImages().add(imgComponent);
		}
            }
            return project;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    //public Project read(File xmlFile){
    public Project read(){
        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            
            File file = new File(fileName);
            Document doc = dBuilder.parse(file);
            //Document doc = dBuilder.parse(xmlFile);
            
            //optional, but recommended	
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            
            
            Project project = new Project();
            project.setName(doc.getDocumentElement().getAttribute("name"));
            project.setParent(file.getParent());
            
            NodeList nList = doc.getElementsByTagName("image");            
            for (int temp = 0; temp < nList.getLength(); temp++) { 
		Node nNode = nList.item(temp);		
		if (nNode.getNodeType() == Node.ELEMENT_NODE) { 
                    Element eElement = (Element) nNode;
                    ImgComponent imgComponent = new ImgComponent(eElement.getAttribute("name"), eElement.getAttribute("id"));

                    imgComponent.setWidth(Integer.parseInt(eElement.getAttribute("width")));
                    imgComponent.setHeight(Integer.parseInt(eElement.getAttribute("height")));
                    //imgComponent.setbFile(Utils.bytefromString(eElement.getTextContent()));
                    imgComponent.setbFile(Utils.bytefromString(((Element)eElement.getElementsByTagName("dataimage").item(0)).getTextContent()));
                    
                    
                    Gson gson = new Gson();
                    NodeList nComponent = eElement.getElementsByTagName("component");                    
                    for(int i=0; i<nComponent.getLength(); i++){
                        Node nNodeComponent = nComponent.item(i);		
                        if (nNodeComponent.getNodeType() == Node.ELEMENT_NODE) {
                            Element eElementComponent = (Element) nNodeComponent;
                            //if(Constantes.COMPONENT_TYPE_CONNECTOR == Integer.parseInt(eElementComponent.getAttribute("type"))){
                                XmlComponent xmlComponent = new XmlComponent();
                                xmlComponent.setOrden(Integer.parseInt(eElementComponent.getAttribute("orden")));
                                xmlComponent.setType(Integer.parseInt(eElementComponent.getAttribute("type")));
                                Type listType = new TypeToken<List<Pixel>>(){}.getType();
                                List<Pixel> listpixels = gson.fromJson(eElementComponent.getTextContent(), listType);
                                xmlComponent.setListPixel(listpixels);
                            /*}else if(Constantes.COMPONENT_TYPE_FIGURE == Integer.parseInt(eElementComponent.getAttribute("type"))){
                                XmlComponent xmlComponent = new XmlComponent();
                                xmlComponent.setOrden(Integer.parseInt(eElementComponent.getAttribute("orden")));
                                xmlComponent.setType(Integer.parseInt(eElementComponent.getAttribute("type")));
                                Type listType = new TypeToken<List<Pixel>>(){}.getType();
                                List<Pixel> listpixels = gson.fromJson(eElementComponent.getTextContent(), listType);
                                xmlComponent.setListPixel(listpixels);
                            }*/
                            imgComponent.getListXmlComponent().add(xmlComponent);
                        }
                    }
                            
                    project.getImages().add(imgComponent);
		}
            }
            return project;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    private String fileName;
    private static FileWrapper filewrapper = null;
    
    private FileWrapper(String fileName){
        this.fileName = fileName;
    }
    
    private FileWrapper(){
    }
    
    public static FileWrapper initializeInstance(String fileName){
        if (filewrapper == null){
            filewrapper = new FileWrapper(fileName);
        }
        return filewrapper;
    }
    
    public static FileWrapper getInstance(){
        if (filewrapper == null){
            filewrapper = new FileWrapper();
        }
        return filewrapper;
    }
    
    /*
    public static void main(String[] args){
        FileWrapper fil = new FileWrapper("D:\\pr\\prueba.xml");
        Project project = fil.read();
        System.out.println(project.getName());
    }*/
    
}
