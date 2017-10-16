package application;

import javafx.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import application.Plan;
import application.Intersection;
import application.Troncon;

public class MainControleur {
	@FXML
	private Button btnloadmap;
	
	public void newIntersection(Element element, Map<Long, Intersection> intersections) {
		
		  long id;
	      double x;
	      double y;
	      id = Long.parseLong(element.getAttribute("id"));
	      x = Double.parseDouble(element.getAttribute("x"))/60;
	      y = Double.parseDouble(element.getAttribute("y"))/60;
	      Intersection intersection = new Intersection(id, x, y);
	 
	      intersections.put(id, intersection);
	}
	private Troncon getTroncon(Element element, Map<Long, Intersection> intersections,
	          Collection<Troncon> streetSections) {	 
	      Long idIntersectionStart;
	      Long idIntersectionEnd;
	      double longueur;
	 
	          idIntersectionStart = Long.parseLong(element.getAttribute("origine"));
	          idIntersectionEnd = Long.parseLong(element.getAttribute("destination"));
	          longueur = Double.parseDouble(element.getAttribute("longueur"));
	 
	      String rueNom = element.getAttribute("nomRue");
	 
	 
	      Intersection origine = intersections.get(idIntersectionStart);
	      Intersection destination = intersections.get(idIntersectionEnd);
	 
	      Troncon troncon = new Troncon (rueNom, destination,origine,longueur);
	      //Two street sections begin at the same intersection and end at the same intersection
	 
	      return troncon;
	  }
	public Plan getPlan(File xmlFile) throws IOException, SAXException, ParserConfigurationException {
	      Map<Long, Intersection> intersections = new TreeMap<Long, Intersection>();
	      ArrayList<Troncon> troncons = new ArrayList<Troncon>();
	 
	      Document mapDocument = null;
	      try {
	          mapDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
	      }catch (IOException e) {
	          throw e;
	      }
	 
	      NodeList nList = mapDocument.getElementsByTagName("noeud");
	 
	      for (int i = 0; i < nList.getLength(); i++) {
	          newIntersection((Element) nList.item(i), intersections);
	      }
	 
	      NodeList streetSectionList = mapDocument.getElementsByTagName("troncon");
	 
	      for (int i = 0; i < streetSectionList.getLength(); i++) {
	          troncons.add(getTroncon((Element) streetSectionList.item(i), intersections, troncons));
	      }
	 
	      return new Plan(intersections.values(), troncons);
	 
	  }
	 
	  public void drawPlan(GraphicsContext gc, Plan plan) {
	      gc.setFill(Color.BLACK);
	      List<Troncon> troncons = plan.getTroncon();
	      for (Troncon section : troncons) {
	          gc.setLineWidth(2);
	          gc.setStroke(Color.GREY);
	          gc.strokeLine(section.getOrigine().getX(), section.getOrigine().getY(),
	              section.getDestination().getX(), section.getDestination().getY());
	      }
	 
	      List<Intersection> intersections = plan.getIntersection();
	      for (Intersection inter : intersections) {
	          gc.fillOval(inter.getX() - 8 / 2, inter.getY() - 8 / 2,
	              8, 8);
	      }
	  }
	
	
	
	
	public void BAction(ActionEvent event) {
	     
		    FileChooser xml_map = new FileChooser();
		    xml_map.setTitle("Select XML Mapfile");
		    xml_map.getExtensionFilters().addAll(new ExtensionFilter("XML Files", "*.xml"));
		    File selectedFile = xml_map.showOpenDialog(null);
		    Plan plandDeVille = null;
		   
		    try {
		   
		    	plandDeVille = getPlan(selectedFile);
		        Stage stage = new Stage();
		        stage.setTitle("Optima - DisplayMap");
		        Group root = new Group();
		        Canvas canvas = new Canvas(800, 800);
		        GraphicsContext gc = canvas.getGraphicsContext2D();
		        drawPlan(gc, plandDeVille);
		        root.getChildren().add(canvas);
		        stage.setScene(new Scene(root));
		        stage.show();
		       
		    } catch (Exception e) {
		            e.printStackTrace();
		        }
	 }

}
