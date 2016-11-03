package com.emiliano;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.emiliano.fmframework.core.Configuration;
import com.emiliano.fmframework.core.Feature;
import com.emiliano.fmframework.core.FeatureModel;
import com.emiliano.fmframework.core.FeatureModelImpl;
import com.emiliano.fmframework.core.FeatureState;
import com.emiliano.fmframework.core.constraints.crossTreeConstraints.Exclude;
import com.emiliano.fmframework.core.constraints.crossTreeConstraints.Imply;
import com.emiliano.fmframework.core.constraints.treeConstraints.AlternativeGroup;
import com.emiliano.fmframework.core.constraints.treeConstraints.MandatoryFeature;
import com.emiliano.fmframework.core.constraints.treeConstraints.OptionalFeature;
import com.emiliano.fmframework.core.constraints.treeConstraints.TreeConstraint;
import com.emiliano.fmframework.operations.ConfOperations;
import com.emiliano.fmframework.optimization.csa.BacktrackingCSA;

public class Main3 {
	public static void main(String[] args) {
		
		FeatureModel model=new FeatureModelImpl();
		model.addFeature(new Feature("root"));
		
		
		
		//classes..........................
		model.addFeature(new Feature("Diagnoser"));
		model.addFeature(new Feature("Region"));
		model.addFeature(new Feature("Vehicle"));
		model.addFeature(new Feature("CarRecord"));
		model.addFeature(new Feature("RoadCamera"));
		
		
		
		//attach classes...
		model.addTreeConstraint(new MandatoryFeature("root","Diagnoser"));
		model.addTreeConstraint(new MandatoryFeature("root","Region"));
		model.addTreeConstraint(new MandatoryFeature("root","Vehicle"));
		model.addTreeConstraint(new MandatoryFeature("root","CarRecord"));
		model.addTreeConstraint(new OptionalFeature("root","RoadCamera"));
		
		//add functions
		model.addFeature(new Feature("DiagnoserRVehicle_LinkRegion"));
		model.addFeature(new Feature("diagPerms2_LinkRegion"));
		model.addFeature(new Feature("DiagnoserRVehicle_LinkRegion"));
		model.addFeature(new Feature("DiagnoserRVehicle_vehicleAttachementIsModifiedSince"));
		model.addFeature(new Feature("Vehicle_LinkRegion"));
		model.addFeature(new Feature("DiagCarPerms_Modify"));
		model.addFeature(new Feature("CarRecordPerms_Read"));
		model.addFeature(new Feature("CarRecordPerms_Create"));
		model.addFeature(new Feature("DiagCarPerms_RegionWithoutChangedInfoCons"));
		model.addFeature(new Feature("DiagCarPerms_NotRegionWithChangedInfoCons"));
		model.addFeature(new Feature("DiagCarPerms_NotRegionWithoutChangedInfoCons"));
		model.addFeature(new Feature("VCPerms_Read"));
		model.addFeature(new Feature("VCPerms_followMe"));

		model.addTreeConstraint(new OptionalFeature("Diagnoser","DiagnoserRVehicle_LinkRegion"));
		model.addTreeConstraint(new OptionalFeature("Diagnoser","diagPerms2_LinkRegion"));
		model.addTreeConstraint(new OptionalFeature("Diagnoser","DiagnoserRVehicle_LinkRegion"));
		
		model.addTreeConstraint(new OptionalFeature("Vehicle","DiagnoserRVehicle_vehicleAttachementIsModifiedSince"));
		model.addTreeConstraint(new OptionalFeature("Vehicle","Vehicle_LinkRegion"));
		
		model.addTreeConstraint(new MandatoryFeature("CarRecord","CarRecordPerms_Create"));
		model.addTreeConstraint(new MandatoryFeature("CarRecord","CarRecordPerms_Read"));
		model.addTreeConstraint(new OptionalFeature("CarRecord","DiagCarPerms_Modify"));
		model.addTreeConstraint(new OptionalFeature("CarRecord","DiagCarPerms_RegionWithoutChangedInfoCons"));
		model.addTreeConstraint(new OptionalFeature("CarRecord","DiagCarPerms_NotRegionWithChangedInfoCons"));
		model.addTreeConstraint(new OptionalFeature("CarRecord","DiagCarPerms_NotRegionWithoutChangedInfoCons"));
		
		////////excludes in the same branch
		model.addCrossTreeConstraint(new Exclude("DiagCarPerms_Modify","DiagCarPerms_RegionWithoutChangedInfoCons"));
		model.addCrossTreeConstraint(new Exclude("DiagCarPerms_Modify","DiagCarPerms_NotRegionWithChangedInfoCons"));	
		model.addCrossTreeConstraint(new Exclude("DiagCarPerms_Modify","DiagCarPerms_NotRegionWithoutChangedInfoCons"));
		
		model.addCrossTreeConstraint(new Exclude("DiagCarPerms_RegionWithoutChangedInfoCons","DiagCarPerms_NotRegionWithChangedInfoCons"));	
		model.addCrossTreeConstraint(new Exclude("DiagCarPerms_RegionWithoutChangedInfoCons","DiagCarPerms_NotRegionWithoutChangedInfoCons"));
		
		model.addCrossTreeConstraint(new Exclude("DiagCarPerms_NotRegionWithChangedInfoCons","DiagCarPerms_NotRegionWithoutChangedInfoCons"));
		
		///////////// tree constrains
		model.addTreeConstraint(new OptionalFeature("RoadCamera","VCPerms_Read"));
		model.addTreeConstraint(new OptionalFeature("RoadCamera","VCPerms_followMe"));
		
		
		model.addCrossTreeConstraint(new Imply("DiagCarPerms_Modify", "DiagnoserRVehicle_vehicleAttachementIsModifiedSince"));
		model.addCrossTreeConstraint(new Imply("DiagnoserRVehicle_LinkRegion", "Vehicle_LinkRegion"));
		model.addCrossTreeConstraint(new Imply("DiagnoserRVehicle_LinkRegion","DiagnoserRVehicle_LinkRegion"));
		model.addCrossTreeConstraint(new Imply("DiagCarPerms_NotRegionWithChangedInfoCons","DiagnoserRVehicle_vehicleAttachementIsModifiedSince"));
		
		Configuration conf=new Configuration(model);
		ConfOperations.selectFeature(conf, "root");
		
		List<Configuration> solutions=new LinkedList();
		BacktrackingCSA alg=new BacktrackingCSA();
		int numOfSolutions=alg.searchAllSolutions(conf,solutions);
		
		System.out.println(numOfSolutions);
		
/////////// Build maps for configuring secureUML by modifyXML function/////////////////////////////////////////////////////////////////////////////////////////////////
		int i=1; Vector <String> PermSets__OwnedOperations= new Vector<String>();
		Map <String,Vector <String>> PrmissionSetName_2_OwnedOperations = new HashMap<String, Vector<String>>(); Vector <String> OpNames = new Vector();
		String[] parts,parts2; String beforeFirstDot;
		//for each configuration take the keys of the Selected values and put them into a Map (, Vector of OwnedOperations)
		for(Configuration conf2:solutions)
			{
			    //Initialize PrmissionSetName_2_OwnedOperations for each configuration
			    //PrmissionSetName_2_OwnedOperations = null;
				System.out.println("\n*******Configuration..."+i++ +"********");
				for (Map.Entry<String, FeatureState> entry : conf2.getStates().entrySet()) 
				{
						System.out.println( entry.getKey() + "  :  " + entry.getValue());
						if (entry.getValue().toString().equals("S") && entry.getKey().toString().contains("_")){
							      PermSets__OwnedOperations.add(entry.getKey().toString());							
							    }								
				}
		        System.out.println("XXXXXXXXXXXXXXX"+PermSets__OwnedOperations);
		        
			
		
		        System.out.println("XXXXXXXXXXXXXXXPermSets__OwnedOperations.size()"+PermSets__OwnedOperations.size());
				//build map for each conf
		        for(int j=0; j<PermSets__OwnedOperations.size();j++) {
					parts = PermSets__OwnedOperations.get(j).split("\\_"); // String array, each element is text between dots
		        	if(parts[1].length()>1) 
				    {
		        		if(parts[1].length()>0) OpNames.add(parts[1]);
		        		for(int k=j+1; k<PermSets__OwnedOperations.size();k++) {
		        			if(PermSets__OwnedOperations.get(k).startsWith(parts[0]))
		        			{
		        				 parts2 = PermSets__OwnedOperations.get(k).split("\\_"); // String array, each element is text between dots
		        				 if(parts2[1].length()>0) OpNames.add(parts2[1]);		        				 
		        			}
		        		}
		        	    //System.out.println("parts[0],OpNames == "+ parts[0]+ OpNames);		        		
				    }
		        	if(!PrmissionSetName_2_OwnedOperations.keySet().contains(parts[0]) && OpNames.size()>0)
		        	{
		        		System.out.println("parts[0] = "+parts[0]+ "    " +"OpNames = "+OpNames);
			            PrmissionSetName_2_OwnedOperations.put(parts[0],OpNames); 	
		        	}
			        OpNames.removeAllElements();
				 }	
	        	System.out.println("PrmissionSetName_2_OwnedOperations="+PrmissionSetName_2_OwnedOperations +"\n\n\n");
		        PermSets__OwnedOperations.removeAllElements();
		        PrmissionSetName_2_OwnedOperations.clear();
			}
}
				//modifyXML(permNames, OpNames);
			
	
	
	public static void modifyXML(Vector <String>  PermNames,Vector <String> ownedOperationNames) {

		try {
			XPath xPath; 
			String expression; 
			Node node;

		    // Create a document by parsing a XML file
		    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder builder = builderFactory.newDocumentBuilder();
		    Document document = builder.parse(new File("C:/Users/hassan.loulou/workspace/FeatureFramework5/src/secureUML1.xml"));

		    for(String PermName : PermNames)
		    {
		    // Get a node using XPath
		     xPath = XPathFactory.newInstance().newXPath();
		     expression = "/XMI/Model/packagedElement/packagedElement/packagedElement/packagedElement[@name='"+PermName+"']";
		     node = (Node) xPath.evaluate(expression, document, XPathConstants.NODE);

		    //For each permission
		    // New Element
		    for(String ownedOperationName : ownedOperationNames){
		    Element ownedOperation = document.createElement("OwnedOperation"); 
		    ownedOperation.setAttribute("id", "_Q7KrkW6pEeaQM9XOZUNTlw");
		    ownedOperation.setAttribute("name", ownedOperationName);
		    
		    // Add node
            node.appendChild(ownedOperation);
		    }
		    }
		    // Write changes to a file
		    Transformer transformer = TransformerFactory.newInstance().newTransformer();
		    transformer.transform(new DOMSource(document), new StreamResult(new File("C:/Users/hassan.loulou/workspace/FeatureFramework5/src/secureUML2.xml")));

		} catch (Exception e) {
            e.printStackTrace();
		}
	}
}
