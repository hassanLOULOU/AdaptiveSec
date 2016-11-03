package com.mkyong.try3;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

 class Node2 
{
	String data;
    Vector<Node2> children;

	Node2(String item) 
	{
		data = item;
    	children = new Vector<Node2>();
	}
}

public class Tree3 
{
	private static int deapth=0;
	int jk=0; 		boolean bb=false; 	boolean b =false;
	Node2 root1,root2;

boolean areIdentical(Node2 root1, Node2 root2) 
{
	/* base cases */
	if (root1 == null && root2 == null)
		return true;

	if ((root1 == null && root2 != null)) //|| (root1 != null && root2 == null))
		return false;
	
	if ((root1 != null && root2 == null)) //|| (root1 != null && root2 == null))
		return true;
	 
	if(root1!=null && root2!=null)	
		for(Node2 n:root2.children)
			for(Node2 nn:root1.children) 
				if(!root2.children.isEmpty() && ! root1.children.isEmpty())					 
					return ((root1.data.contains(root2.data)) && (areIdentical(nn, n)));
	return true;
}

	boolean isSubtree(Node2 T, Node2 S) 
	{
		if (S == null) 
			return true;

		if (T == null)
			return false;
      
		if (areIdentical(T, S)) 
			return true;

		for(int i=0;i<T.children.size();i++)
			bb=isSubtree(T.children.get(i), S);

		return bb; 

	}
	
	
	 private static boolean partialMatch(Node2 tree, Node2 searchTree) {
	    	Node2 subTree = findSubTreeInTree(tree, searchTree);
	    	if (subTree != null) {
	    		System.out.println("Found: " + subTree);
	    		return true;
	    	}
	    	System.out.println("Not Found: " + subTree);
	    	return false;
	    }

	  private static Node2 findSubTreeInTree(Node2 tree, Node2 node) {
	    	if (tree.data.contains(node.data)|| node.data.contains(tree.data)) {
	    		if (matchChildren(tree, node)) {
	    			return tree;
	    		}
	    	}

	    	Node2 result = null;
	    	for (Node2 child : tree.children) {
	    		result = findSubTreeInTree(child, node);

	    		if (result != null) {
	    			if (matchChildren(tree, result)) {
	    				return result;
	    			}
	    		}
	    	}

	    	return result;
	    }

	    private static boolean matchChildren(Node2 tree, Node2 searchTree) {
	    	if (!(tree.data.contains(searchTree.data) || searchTree.data.contains(tree.data) )) {
	    		return false;
	    	}

	    	if (tree.children.size() < searchTree.children.size()) {
	    		return false;
	    	}

	    	boolean result = true;
	    	int treeChildrenIndex = 0;

	    	for (int searchChildrenIndex = 0;
	    	         searchChildrenIndex < searchTree.children.size();
	    	         searchChildrenIndex++) {
	        	 treeChildrenIndex = 0;

	    		// Skip non-matching children in the tree.
	    		while (treeChildrenIndex < tree.children.size()
	    		      && !(result = matchChildren(tree.children.get(treeChildrenIndex),
	    		                                  searchTree.children.get(searchChildrenIndex)))) {
	    			treeChildrenIndex++;
	    		}

	    		if (!result) {
	    			return result;
	    		}
	    	}

	    	return result;
	    }


	public static boolean  detect() throws SAXException, IOException, ParserConfigurationException, TransformerException 
	{
		return  build();
	
	}
	
	
	
  static boolean build() throws SAXException, IOException, ParserConfigurationException, TransformerException {

		 Node2 root3,root4,root5;
	  Tree3 tree = new Tree3();
	  tree.root1 = new Node2("SecureUMLModel");
	  tree.root2 = new Node2("SecureUMLModel");
	  root3 = new Node2("SecureUMLModel");
	  root5 = new Node2("SecureUMLModel");
	  root4 = new Node2("SecureUMLModel");
      boolean issubtree=false;
      Node2 root_MUT = build_auto_MUT("m2.xml",tree.root1);
      Node2 root_anti=build_auto("sub1.xml",tree.root2);


	  System.out.println("\n  ==================================Model to Test============================");
	  print_tree(root_MUT);  //tree.root1);
	  
	  System.out.println("\n ===================================Anti==================================");
	  print_tree(root_anti);//  tree.root2);
	 
	  
	  System.out.println("\n *********m1 classes  info**********");
	  print_tree(build_auto_classes2("m2.xml",root5));
	  
	  System.out.println("\n *********sub1 classes  info**********");
	  print_tree(build_auto_classes2("sub1.xml",root3));
	  
      return partialMatch(root_MUT,root_anti);
 }

  
  static void print_tree(Node2 root){
		  if(root!=null)
		  		  {
			        if(root.data.contains("Association") )			        	
			        	System.out.println("\n \n"+"*****"+root.data+"*****");
			        
			        else if(root.data.contains("class"))
			        	System.out.println("\n "+root.data);
			        else
			       	System.out.println(root.data);
					for (int i=0; i<root.children.size(); i++)	
					{
					  		print_tree(root.children.get(i));						
					}
				  }
				  if(root.children == null)
					  	System.out.println(root.data);
  }
  
  
  static Node2 build_auto(String fname, Node2 treeroot) throws SAXException, IOException, ParserConfigurationException{
		 Node2 root3,root4,root5;
	    root3 = new Node2("SecureUMLModel");
	    root4 = new Node2("SecureUMLModel");
	    //System.out.println(" Vector<Node2> functional_classes=build_auto_classes2(sub1.xml,root3).children.get(0).children;");
		Vector<Node2> functional_classes=build_auto_classes2("sub1.xml",root3).children.get(0).children;
		
		Vector<Node2> functional_classes2=build_auto_classes2SansID("sub1.xml",root4).children.get(0).children;
		
		//Node2 functional_classes=build_auto_classes2("sub1.xml",root3).children.get(0);
	    int flag=-1; int secpack =-1; int classc=-1; int fclassc =-1; int secAss =-1; int secAssC =-1; int aclassc =-1; 

	    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document document = docBuilder.parse(new File(fname));

		Element eElement,nnn ;
		NodeList nodeList = document.getElementsByTagName("*");

		Node2 tree = treeroot;



		for (int i = 2; i < nodeList.getLength(); i++) {
		    Node node = nodeList.item(i);
		    eElement= (Element) node;
		    
		    for(int i1=0; i1<eElement.getAttributes().getLength();i1++)
		    {
		    	
		    	
		    	
		     //////////////////////////////Functional/////////////////////////////////
		      if(eElement.getAttributes().item(i1).getNodeValue().contains("Functional"))
		    		{
		    	       flag=1;
		    	      // System.out.println(eElement.getAttributes().item(0));
		    		   tree.children.add(new Node2("Functional"));
		    		}

		      if(eElement.getAttributes().item(i1).getNodeValue().contains("uml:Association") && flag==1)
		    	{ 		
		    		fclassc++;

		    		tree.children.get(0).children.add(new Node2("Association"));
		    		
		    		for(int cc=0;cc<eElement.getChildNodes().getLength();cc++)
		    			if(eElement.getChildNodes().item(cc).getAttributes() !=null)
		    				{
			    				if(eElement.getChildNodes().item(cc).toString().contains("ownedEnd"))	
			    				{
			    					
			    					for(int kk =0; kk<eElement.getChildNodes().item(cc).getAttributes().getLength();kk++)
				    					{
			    						
			    						
			    						if(eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeName().toString().contains("type"))
					    				{	    	
					    					
					    					for(int nn=0; nn< functional_classes.size();nn++)
											{	
				    				    	   for(int classChild=0; classChild<functional_classes.get(nn).children.size(); classChild++)
				    				    	   if(functional_classes.get(nn).children.get(classChild).data.contains(eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeValue().toString()) || eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeValue().toString().contains(functional_classes.get(nn).children.get(classChild).data))							    				    		  
				    				    		   {
									    	         System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFff");
									    	         //String ClassId=functional_classes2.get(nn).children.firstElement().data;
									    	         //System.out.println("ClassId  "+ClassId);
									    	         //functional_classes2.get(nn).children.remove(0);
				    				    		       tree.children.get(0).children.get(fclassc).children.add(functional_classes2.get(nn));								    				    		   
				    				    		       break;
				    				    		   }	
											}	
																					
					    				}	
					    					
				    					}
			    				}			    				
		    				}
	      	    }	      
		      
			    
			    
				//////////////////////////////Security/////////////////////////////////
			    if(eElement.getAttributes().item(i1).getNodeValue().contains("Policy") && flag==1)
	    		   {
		    	      System.out.println("\n \n \n "+eElement.getAttributes().item(0));

				    	tree.children.add(new Node2("Policy"));
				    	flag=2;
	    		   }
		
			      if(eElement.getAttributes().item(i1).getNodeValue().contains("uml:AssociationClass") && flag==2)
			    	{ 		
			    	    secAssC++;
	    				for(int kk =0; kk<eElement.getAttributes().getLength();kk++)
	    					if(eElement.getAttributes().item(kk).getNodeName().toString().contains("name"))
			    	        	tree.children.get(1).children.add(new Node2("AssociationClass"));//new Node2(eElement.getAttributes().item(0).toString()));
						    		for(int cc=0;cc<eElement.getChildNodes().getLength();cc++)
						    			//if(eElement.getChildNodes().item(cc).getAttributes() !=null)
						    				{ 
							    				if(eElement.getChildNodes().item(cc).toString().contains("ownedEnd"))	
							    					for(int kk =0; kk<eElement.getChildNodes().item(cc).getAttributes().getLength();kk++)
							    					{
						    						
						    						
						    						if(eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeName().toString().contains("type"))
								    				{	    	
								    					
								    					for(int nn=0; nn< functional_classes.size();nn++)
														{	
							    				    	   for(int classChild=0; classChild<functional_classes.get(nn).children.size(); classChild++)
							    				    	   if(functional_classes.get(nn).children.get(classChild).data.contains(eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeValue().toString()) || eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeValue().toString().contains(functional_classes.get(nn).children.get(classChild).data))							    				    		  
							    				    		   {
												    	         System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFff");
												    	         //String ClassId=functional_classes2.get(nn).children.firstElement().data;
												    	         //System.out.println("ClassId  "+ClassId);
												    	         //functional_classes.get(nn).children.remove(0);
							    				    		       tree.children.get(1).children.get(secAssC).children.add(functional_classes2.get(nn));								    				    		   
							    				    		       break;
							    				    		   }	
														}	
																								
								    				}	
								    					
							    					}
								    				/*for(int kk =0; kk<eElement.getChildNodes().item(cc).getAttributes().getLength();kk++)
								    					if(eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeName().toString().contains("name"))
											    				{	    
													    	         System.out.println("Anti ---- ownedEnd --- name---eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeValue().toString() "+eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeValue().toString()+ "  "+secAssC);
											    				     tree.children.get(1).children.get(secAssC).children.add(new Node2(eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeValue().toString()));
											    				     break;
											    				}
							    				*/
							    				
							    				if(eElement.getChildNodes().item(cc).toString().contains("ownedOperation"))	
								    				for(int kk =0; kk<eElement.getChildNodes().item(cc).getAttributes().getLength();kk++)
								    					if(eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeName().toString().contains("name"))
											    				{	    	
											    	                 System.out.println("Anti ---- ownedOperation --- name---eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeValue().toString() "+eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeValue().toString()+ "  "+secAssC);
											    				     tree.children.get(1).children.get(secAssC).children.add(new Node2(eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeValue().toString()));
											    				     break;
											    				}
							    				
						    				}
		      	    }	      

		   }		    
		}
		
		System.out.println("\n \n ");
		
      return tree;
  }
  
  
  
  static Node2 build_auto_MUT(String fname, Node2 treeroot) throws SAXException, IOException, ParserConfigurationException{
		Node2 root3,root4,root5;
	    root3 = new Node2("SecureUMLModel");
	    root4 = new Node2("SecureUMLModel");
	    root5 = new Node2("SecureUMLModel");

	    System.out.println(" Vector<Node2> functional_classes=build_auto_classes2(m2.xml,root3).children.get(0).children;");
		Vector<Node2> functional_classes=build_auto_classes2("m2.xml",root4).children.get(0).children;

		Vector<Node2> functional_classes2=build_auto_classes2SansID("m2.xml",root5).children.get(0).children;

	    //Vector<Node2> rootch = build_auto_classes_without_ids("m2.xml",root4).children;
		//Vector<Node2> functional_classes_without_ids=rootch.get(0).children;
		
	    int flag=-1; int secpack =-1; int classc=-1; int fclassc =-1; int secAss =-1; int secAssC =-1; int aclassc =-1; 

	    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document document = docBuilder.parse(new File(fname));

		Element eElement,nnn ;
		NodeList nodeList = document.getElementsByTagName("*");

		Node2 tree = treeroot;

		for (int i = 2; i < nodeList.getLength(); i++) {
		    Node node = nodeList.item(i);
		    eElement= (Element) node;
		    
		    for(int i1=0; i1<eElement.getAttributes().getLength();i1++)
		    {
		    		
		    	 //////////////////////////////Functional/////////////////////////////////
			      if(eElement.getAttributes().item(i1).getNodeValue().contains("Functional"))
			    		{
			    	       flag=1;
			    	      // System.out.println(eElement.getAttributes().item(0));
			    		   tree.children.add(new Node2("Functional"));
			    		}

			      if(eElement.getAttributes().item(i1).getNodeValue().contains("uml:Association") && flag==1)
			    	{ 		
			    		fclassc++;

			    		tree.children.get(0).children.add(new Node2("Association"));
			    		
			    		for(int cc=0;cc<eElement.getChildNodes().getLength();cc++)
			    			if(eElement.getChildNodes().item(cc).getAttributes() !=null)
			    				{
				    				if(eElement.getChildNodes().item(cc).toString().contains("ownedEnd"))	
				    				{
				    					
				    					for(int kk =0; kk<eElement.getChildNodes().item(cc).getAttributes().getLength();kk++)
					    					{
				    						
				    						
				    						if(eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeName().toString().contains("type"))
						    				{	    	
						    					
						    					for(int nn=0; nn< functional_classes.size();nn++)
												{	
					    				    	   for(int classChild=0; classChild<functional_classes.get(nn).children.size(); classChild++)
					    				    	   if(functional_classes.get(nn).children.get(classChild).data.contains(eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeValue().toString()) || eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeValue().toString().contains(functional_classes.get(nn).children.get(classChild).data))							    				    		  
					    				    		   {
										    	         System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFff");
										    	         //String ClassId=functional_classes2.get(nn).children.firstElement().data;
										    	         //System.out.println("ClassId  "+ClassId);
										    	         //functional_classes2.get(nn).children.remove(0);
					    				    		       tree.children.get(0).children.get(fclassc).children.add(functional_classes2.get(nn));								    				    		   
					    				    		       break;
					    				    		   }	
												}	
																						
						    				}	
						    					
					    					}
				    				}			    				
			    				}
		      	    }	      
			      
				    
				    
					//////////////////////////////Security/////////////////////////////////
				    if(eElement.getAttributes().item(i1).getNodeValue().contains("Policy") && flag==1)
		    		   {
			    	      System.out.println("\n \n \n "+eElement.getAttributes().item(0));

					    	tree.children.add(new Node2("Policy"));
					    	flag=2;
		    		   }
			
				      if(eElement.getAttributes().item(i1).getNodeValue().contains("uml:AssociationClass") && flag==2)
				    	{ 		
				    	    secAssC++;
		    				for(int kk =0; kk<eElement.getAttributes().getLength();kk++)
		    					if(eElement.getAttributes().item(kk).getNodeName().toString().contains("name"))
				    	        	tree.children.get(1).children.add(new Node2("AssociationClass"));//new Node2(eElement.getAttributes().item(0).toString()));
							    		for(int cc=0;cc<eElement.getChildNodes().getLength();cc++)
							    			//if(eElement.getChildNodes().item(cc).getAttributes() !=null)
							    				{ 
								    				if(eElement.getChildNodes().item(cc).toString().contains("ownedEnd"))	
								    					for(int kk =0; kk<eElement.getChildNodes().item(cc).getAttributes().getLength();kk++)
								    					{
							    						
							    						
							    						if(eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeName().toString().contains("type"))
									    				{	    	
									    					
									    					for(int nn=0; nn< functional_classes.size();nn++)
															{	
								    				    	   for(int classChild=0; classChild<functional_classes.get(nn).children.size(); classChild++)
								    				    	   if(functional_classes.get(nn).children.get(classChild).data.contains(eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeValue().toString()) || eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeValue().toString().contains(functional_classes.get(nn).children.get(classChild).data))							    				    		  
								    				    		   {
													    	         System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFff");
													    	         //String ClassId=functional_classes2.get(nn).children.firstElement().data;
													    	         //System.out.println("ClassId  "+ClassId);
													    	         //functional_classes.get(nn).children.remove(0);
								    				    		       tree.children.get(1).children.get(secAssC).children.add(functional_classes2.get(nn));								    				    		   
								    				    		       break;
								    				    		   }	
															}	
																									
									    				}	
									    					
								    					}
									    				/*for(int kk =0; kk<eElement.getChildNodes().item(cc).getAttributes().getLength();kk++)
									    					if(eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeName().toString().contains("name"))
												    				{	    
														    	         System.out.println("Anti ---- ownedEnd --- name---eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeValue().toString() "+eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeValue().toString()+ "  "+secAssC);
												    				     tree.children.get(1).children.get(secAssC).children.add(new Node2(eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeValue().toString()));
												    				     break;
												    				}
								    				*/
								    				
								    				if(eElement.getChildNodes().item(cc).toString().contains("ownedOperation"))	
									    				for(int kk =0; kk<eElement.getChildNodes().item(cc).getAttributes().getLength();kk++)
									    					if(eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeName().toString().contains("name"))
												    				{	    	
												    	                 System.out.println("Anti ---- ownedOperation --- name---eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeValue().toString() "+eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeValue().toString()+ "  "+secAssC);
												    				     tree.children.get(1).children.get(secAssC).children.add(new Node2(eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeValue().toString()));
												    				     break;
												    				}
								    				
							    				}
			      	    }	      

			   }		    
			}
			
			System.out.println("\n \n ");
			
	      return tree;
  		}
  
  
  static Node2 build_auto_classes(String fname, Node2 treeroot) throws SAXException, IOException, ParserConfigurationException{
	  int flag=-1; int secpack =-1; int classc=-1; int fclassc =-1; int secAss =-1; int secAssC =-1;

	    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document document = docBuilder.parse(new File(fname));

		Element eElement,nnn ;
		NodeList nodeList = document.getElementsByTagName("*");

		Node2 tree = treeroot;

		for (int i = 2; i < nodeList.getLength(); i++) {
		    Node node = nodeList.item(i);
		    eElement= (Element) node;
		    
		    for(int i1=0; i1<eElement.getAttributes().getLength();i1++)
		    {    	
		     //////////////////////////////Functional/////////////////////////////////
		      if(eElement.getAttributes().item(i1).getNodeValue().contains("Functional"))
		    		{
		    	       flag=1;
		    	       System.out.println(eElement.getAttributes().item(0));
		    		   tree.children.add(new Node2("Functional"));
		    		}  
		      
			    if(eElement.getAttributes().item(i1).getNodeValue().contains("uml:Class") && flag==1)
			    	{ 		
			    		fclassc++;
			    		tree.children.get(0).children.add(new Node2(eElement.getAttributes().item(0).toString()));
			    		for(int cc=0;cc<eElement.getChildNodes().getLength();cc++)
			    			if(eElement.getChildNodes().item(cc).getAttributes() !=null)
			    				{

				    				if(eElement.getChildNodes().item(cc).toString().contains("ownedAttribute"))	
					    				for(int kk =0; kk<eElement.getChildNodes().item(cc).getAttributes().getLength();kk++)
                                           if(eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeName().toString().contains("name"))
							    				{	    					
							    				     tree.children.get(0).children.get(fclassc).children.add(new Node2(eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeValue().toString()));
							    				}
			    							    							    				
				    				if(eElement.getChildNodes().item(cc).toString().contains("ownedOperation"))	
					    				for(int kk =0; kk<eElement.getChildNodes().item(cc).getAttributes().getLength();kk++)
                                           if(eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeName().toString().contains("name"))
							    				{	    					
							    				     tree.children.get(0).children.get(fclassc).children.add(new Node2(eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeValue().toString()));
							    				}

			    				}
		      	    }	    		
	      	       		        
		   }		    
		}
		
		System.out.println("\n \n \n");
		
      return tree;
  }
  
  
  static Node2 build_auto_classes2(String fname, Node2 treeroot) throws SAXException, IOException, ParserConfigurationException{
	  int flag=-1; int secpack =-1; int classc=-1; int fclassc =-1; int secAss =-1; int secAssC =-1;

	    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document document = docBuilder.parse(new File(fname));

		Element eElement,nnn ;
		NodeList nodeList = document.getElementsByTagName("*");

		Node2 tree3 = treeroot;



		for (int i = 2; i < nodeList.getLength(); i++) {
		    Node node = nodeList.item(i);
		    eElement= (Element) node;
		    
		    for(int i1=0; i1<eElement.getAttributes().getLength();i1++)
		    {

		     //////////////////////////////Functional/////////////////////////////////
		      if(eElement.getAttributes().item(i1).getNodeValue().contains("Functional"))
		    		{
		    	       flag=1;
		    	      // System.out.println(eElement.getAttributes().item(0));
		    		   tree3.children.add(new Node2("Functional"));
		    		}
      
		      
			    if(eElement.getAttributes().item(i1).getNodeValue().contains("uml:Class") && flag==1)
			    	{ 		
			    		fclassc++;    			 
			    		tree3.children.get(0).children.add(new Node2("class"));
			    		//Add ids
						tree3.children.get(0).children.get(fclassc).children.add(new Node2(eElement.getAttributes().item(1).getNodeValue()));

				    			 
			    		for(int cc=0;cc<eElement.getChildNodes().getLength();cc++)
			    			if(eElement.getChildNodes().item(cc).getAttributes() !=null)
			    				{

				    				if(eElement.getChildNodes().item(cc).toString().contains("ownedAttribute"))	
					    				for(int kk =0; kk<eElement.getChildNodes().item(cc).getAttributes().getLength();kk++)
                                           if(eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeName().toString().contains("name"))
							    				{	    					
							    				     tree3.children.get(0).children.get(fclassc).children.add(new Node2(eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeValue().toString()));
							    				}

				    				
				    				
				    				if(eElement.getChildNodes().item(cc).toString().contains("ownedOperation"))	
					    				for(int kk =0; kk<eElement.getChildNodes().item(cc).getAttributes().getLength();kk++)
                                           if(eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeName().toString().contains("name"))
							    				{	    		
                                        	   if(eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeValue().toString().contains("Link"))
                                        		   tree3.children.get(0).children.get(fclassc).children.add(new Node2("Link"));
                                        	   else
							    				     tree3.children.get(0).children.get(fclassc).children.add(new Node2(eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeValue().toString()));
							    				
							    				}

			    				}
		      	    }	    
		      
		   }		    
		}
		
		//System.out.println("\n \n \n");
		
      return tree3;
  }
			    
  static Node2 build_auto_classes2SansID(String fname, Node2 treeroot) throws SAXException, IOException, ParserConfigurationException{
	  int flag=-1; int secpack =-1; int classc=-1; int fclassc =-1; int secAss =-1; int secAssC =-1;

	    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document document = docBuilder.parse(new File(fname));

		Element eElement,nnn ;
		NodeList nodeList = document.getElementsByTagName("*");

		Node2 tree3 = treeroot;



		for (int i = 2; i < nodeList.getLength(); i++) {
		    Node node = nodeList.item(i);
		    eElement= (Element) node;
		    
		    for(int i1=0; i1<eElement.getAttributes().getLength();i1++)
		    {

		     //////////////////////////////Functional/////////////////////////////////
		      if(eElement.getAttributes().item(i1).getNodeValue().contains("Functional"))
		    		{
		    	       flag=1;
		    	      // System.out.println(eElement.getAttributes().item(0));
		    		   tree3.children.add(new Node2("Functional"));
		    		}
      
		      
			    if(eElement.getAttributes().item(i1).getNodeValue().contains("uml:Class") && flag==1)
			    	{ 		
			    		fclassc++;    			 
			    		tree3.children.get(0).children.add(new Node2("class"));
			    		//Add ids
						//tree3.children.get(0).children.get(fclassc).children.add(new Node2(eElement.getAttributes().item(1).getNodeValue()));

				    			 
			    		for(int cc=0;cc<eElement.getChildNodes().getLength();cc++)
			    			if(eElement.getChildNodes().item(cc).getAttributes() !=null)
			    				{

				    				if(eElement.getChildNodes().item(cc).toString().contains("ownedAttribute"))	
					    				for(int kk =0; kk<eElement.getChildNodes().item(cc).getAttributes().getLength();kk++)
                                           if(eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeName().toString().contains("name"))
							    				{	    					
							    				     tree3.children.get(0).children.get(fclassc).children.add(new Node2(eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeValue().toString()));
							    				}

				    				
				    				
				    				if(eElement.getChildNodes().item(cc).toString().contains("ownedOperation"))	
					    				for(int kk =0; kk<eElement.getChildNodes().item(cc).getAttributes().getLength();kk++)
                                           if(eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeName().toString().contains("name"))
							    				{	    		
                                        	   if(eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeValue().toString().contains("Link"))
                                        		   tree3.children.get(0).children.get(fclassc).children.add(new Node2("Link"));
                                        	   else
							    				     tree3.children.get(0).children.get(fclassc).children.add(new Node2(eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeValue().toString()));
							    				
							    				}

			    				}
		      	    }	    
		      
		   }		    
		}
		
		//System.out.println("\n \n \n");
		
      return tree3;
  }
  
  
  static Node2 build_auto_classes_without_ids(String fname, Node2 treeroot) throws SAXException, IOException, ParserConfigurationException{
	  int flag=-1; int secpack =-1; int classc=-1; int fclassc =-1; int secAss =-1; int secAssC =-1;

	    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document document = docBuilder.parse(new File(fname));

		Element eElement,nnn ;
		NodeList nodeList = document.getElementsByTagName("*");

		Node2 tree3 = treeroot;



		for (int i = 2; i < nodeList.getLength(); i++) {
		    Node node = nodeList.item(i);
		    eElement= (Element) node;
		    
		    for(int i1=0; i1<eElement.getAttributes().getLength();i1++)
		    {

		     //////////////////////////////Functional/////////////////////////////////
		      if(eElement.getAttributes().item(i1).getNodeValue().contains("Functional"))
		    		{
		    	       flag=1;
		    		   tree3.children.add(new Node2("Functional"));
		    		}
      
		      
			    if(eElement.getAttributes().item(i1).getNodeValue().contains("uml:Class") && flag==1)
			    	{ 		
			    		fclassc++;    			 
			    		tree3.children.get(0).children.add(new Node2("class"));

				    			 
			    		for(int cc=0;cc<eElement.getChildNodes().getLength();cc++)
			    			if(eElement.getChildNodes().item(cc).getAttributes() !=null)
			    				{

				    				if(eElement.getChildNodes().item(cc).toString().contains("ownedAttribute"))	
					    				for(int kk =0; kk<eElement.getChildNodes().item(cc).getAttributes().getLength();kk++)
                                           if(eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeName().toString().contains("name"))
							    				{	    					
							    				     //tree3.children.get(0).children.get(fclassc).children.add(new Node2(eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeValue().toString()));
                                        	         tree3.children.get(0).children.get(fclassc).children.add(new Node2("Att"));
							    				}

				    				
				    				
				    				if(eElement.getChildNodes().item(cc).toString().contains("ownedOperation"))	
					    				for(int kk =0; kk<eElement.getChildNodes().item(cc).getAttributes().getLength();kk++)
                                           if(eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeName().toString().contains("name"))
							    				{	    					
							    				     //tree3.children.get(0).children.get(fclassc).children.add(new Node2(eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeValue().toString()));
							    			     	if(eElement.getChildNodes().item(cc).getAttributes().item(kk).getNodeValue().toString().contains("Link"));
							    			     	tree3.children.get(0).children.get(fclassc).children.add(new Node2("Link"));
							    				          
							    				}

			    				}
		      	    }	    
		      
		   }		    
		}
		
		
      return tree3;
  }
  
  }
			
