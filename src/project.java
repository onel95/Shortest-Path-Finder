import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;
import java.util.Map;
import java.util.Map.Entry;
import java.io.*;



/** 
* This program reads in a weighted graph from 
* two text files and implements two different algorithms 
* in order to find the shortest distance from the Node the 
* user chooses to Node Z.
* 
* NOTE: INPUT FILE NAMES ARE HARDCODED ON LINES 358 AND 398 FOR YOUR CONVENIENCE.
* 
* @author  Onel Hirmez  
* @date   April 27, 2020 
*/
public class project {
	
	
	/** 
	    * This class represents a Node in the graph and has different
	    * attributes such as the edges of each node. 
	    * @param name  This is a String to describe the letter name of node. 
	    * @param isVisited  Boolean to keep track of whether Node is visited. 
	    * @param directDistanceZ  Int to keep track of dd(node) to node Z. 
	    * @param edges Records the edges (adjacent nodes) of each node. 
	    */
	public static class Node {
		
		private String name;
		private Boolean isVisited;
		private int directDistanceZ;
		public Map<String, Integer> edges;
			
		// Instantiate an object with just the argument name.	
		public Node (String name) {
				
			this.name = name;
			this.isVisited = false;
			this.directDistanceZ = 0;
			this.edges = new HashMap<>();
		}
		
		// Method that returns the name of object.
		public String getName() {
			return name;
		}
		
		// Returns Boolean depending if node has been visited.
		public Boolean getIsVisited() {
			return isVisited;
		}
		
		// Returns int direct distance to Z.
		public int getDirectDistanceZ() {
			return directDistanceZ;
		}
		
		// Returns a hash map containing edges of the node.
		public Map<String, Integer> getEdges() {
			return edges;
		}
		
		// Enables user to set whether node has been visited.
		public void setIsVisited(Boolean visit) {
			this.isVisited = visit;
		}
		
		// Sets the direct distance of the node.
		public void setDirectDistanceZ(int dd) {
			this.directDistanceZ = dd;
		}
		
		// Sets the edges of the node as read from the input text.
		public void setEdges(HashMap<String, Integer> edges) {
			this.edges = edges;
		}
	}
	
	
	/** 
	    * This method receives the name of a node and returns the 
	    * reference to the actual object of the node. 
	    * @param letter  This is a String to describe the letter name of wanted node. 
	    * @param nodeArray  Array of string names of all the nodes. 
	    * @param nodeObjects  ArrayList of the objects of all the nodes. 
	    * @return Node Object  Returns reference to the node object with the given name. 
	    */
	public static Node getObject(String letter, String[] nodeArray, ArrayList<Node> nodeObjects) {
		// Get index of the letter in nodeArray.
		int index = Arrays.asList(nodeArray).indexOf(letter);
		// Use that index to grab the actual object since they have the same indexes.
		Node object = nodeObjects.get(index);
		return object;
	}
	
	
	
	
	/** 
	    * This method implements the first algorithm to find the shortest 
	    * distance to node Z. This algorithm just picks the next node from 
	    * the edges depending on the shortest direct distance to Z.
	    * @param input  This is a String to describe the start node chosen by the user. 
	    * @param nodeArray  Array of string names of all the nodes. 
	    * @param nodeObjects  ArrayList of the objects of all the nodes.  
	    */
	public static void algorithm1(String input, String[] nodeArray, ArrayList<Node> nodeObjects) {
		
		// Path taken including backtracks.
		ArrayList<String> path= new ArrayList<>();
		// Stack to represent the shortest path taken.
		Stack<String> shortestPath = new Stack<>();
		
		// Grab the index of the user input from nodeArray.
		int index = Arrays.asList(nodeArray).indexOf(input);
		// Use that index to get the actual object from nodeObjects.
		Node curNode = nodeObjects.get(index);
		// Keep track of the index of Z (destination).
		int zIndex = Arrays.asList(nodeArray).indexOf("Z");
		
		// Keep looping until current Node is Z.
		while (curNode != nodeObjects.get(zIndex)) {
			
			// Add the current node to ArrayList path and our stack.
			path.add(curNode.getName());
			shortestPath.add(curNode.getName());
			// Set current Node to visited so we don't go back to it.
			curNode.setIsVisited(true);
			
			// ArrayList of nodes to compare their direct distances.
			ArrayList<Node> compareDD = new ArrayList<>();
			
			// Loop to populate compareDD with the edges of our current Node.
			for (Entry<String, Integer> entry : curNode.getEdges().entrySet()) {
				// Get the actual object of the edge name in our edges hashmap.
				Node minObject = getObject(entry.getKey(), nodeArray, nodeObjects);
				// If the edge node is visited, skip it.
				if (minObject.getIsVisited()) {
					continue;
				}
				// Add the node to our new ArrayList.
				compareDD.add(minObject);
				
			}
			
			// If we had no edges that weren't already visited, then backtrack.
			if (compareDD.size() == 0) {
				// Pop the last entry from our stack to backtrack.
				shortestPath.pop();
				// Set current node to the previous one.
				curNode = getObject(shortestPath.pop(), nodeArray, nodeObjects);
				continue;
			}
			
			int min = Integer.MAX_VALUE;
			// Loop to compare the direct distances of the edge nodes.
			for (int i = 0; i < compareDD.size(); i++) {
				
				if (min > compareDD.get(i).getDirectDistanceZ()) {
					min = compareDD.get(i).getDirectDistanceZ();
					// Set current Node to the one with shortest direct distance.
					
					curNode = compareDD.get(i);
				}
				
			}
			
			// Get next closest node as current Node.
			index = Arrays.asList(nodeArray).indexOf(curNode.getName());
			curNode = nodeObjects.get(index);
			
		}
		
		// Add the final node Z to the path after the loop exits.
		path.add(curNode.getName());
		shortestPath.add(curNode.getName());
		
		// Compute the path distance using the final shortestPath ArrayList.
		int pathDistance = 0;
		for (int i = 0; i < shortestPath.size()-1; i++) {
			Node tempNode = getObject(shortestPath.get(i), nodeArray, nodeObjects);
			pathDistance = pathDistance + tempNode.getEdges().get(shortestPath.get(i+1));
		}
		
		// For loops to print the output in a presentable way.
		System.out.println("");
		System.out.print("Algorithm 1: ");
		System.out.println("\n");
		System.out.print("Sequence of all nodes: ");
		for (int count = 0; count<path.size(); count++) {
			if (count == path.size()-1) {
				System.out.println(path.get(count));
			}
			else {
				System.out.print(path.get(count) + " -> ");
			}
		}
		
		System.out.print("Shortest path: ");
		for (int count = 0; count<shortestPath.size(); count++) {
			if (count == shortestPath.size()-1) {
				System.out.println(shortestPath.get(count));
			}
			else {
				System.out.print(shortestPath.get(count) + " -> ");
			}
		}
		
		System.out.print("Shortest path length: " + pathDistance);
		System.out.println("\n");
	}
	
	
	
	/** 
	    * This method implements the second algorithm to find the shortest 
	    * distance to node Z. This algorithm picks the next node from 
	    * the edges depending on the shortest direct distance to Z plus 
	    * the distance from the current node.
	    * @param input  This is a String to describe the start node chosen by the user. 
	    * @param nodeArray  Array of string names of all the nodes. 
	    * @param nodeObjects  ArrayList of the objects of all the nodes.  
	    */
	public static void algorithm2(String input, String[] nodeArray, ArrayList<Node> nodeObjects) {
		
		// Path taken including backtracks.
		ArrayList<String> path= new ArrayList<>();
		// Final shortest path using a stack.
		Stack<String> shortestPath = new Stack<>();
		
		// Grab the index of the user input from nodeArray.
		int index = Arrays.asList(nodeArray).indexOf(input);
		// Use that index to get the actual object from nodeObjects.
		Node curNode = nodeObjects.get(index);
		// Temporary node to keep track of direct distances and edge distances.
		Node tempNode = nodeObjects.get(index);
		// Keep track of index of Z (destination).
		int zIndex = Arrays.asList(nodeArray).indexOf("Z");
		
		// Keep looping until current Node is Z.
		while (curNode != nodeObjects.get(zIndex)) {
			
			// Add the current node to ArrayList path and our stack.
			path.add(curNode.getName());
			shortestPath.add(curNode.getName());
			// Set current Node to visited so we don't go back to it.
			curNode.setIsVisited(true);
			
			// ArrayList of nodes to compare their direct distances and edge weights.
			ArrayList<Node> compareWeight = new ArrayList<>();
			
			// Loop to populate compareWeight with the edges of our current Node.
			for (Entry<String, Integer> entry : curNode.getEdges().entrySet()) {
				// Get the actual object of the edge name in our edges hashmap.
				Node minObject = getObject(entry.getKey(), nodeArray, nodeObjects);
				// Skip if already visited.
				if (minObject.getIsVisited()) {
					continue;
				}
				// Add node to compareWeight.
				compareWeight.add(minObject);
				
			}
			
			// If we had no edges that weren't already visited, then backtrack.
			if (compareWeight.size() == 0) {
				// Pop the last entry from our stack to backtrack.
				shortestPath.pop();
				// Set current node to the previous one.
				curNode = getObject(shortestPath.pop(), nodeArray, nodeObjects);
				continue;
				
			}
			
			int min = Integer.MAX_VALUE;
			// Loop to compare the direct distances and edge weights of the edge nodes.
			for (int i = 0; i < compareWeight.size(); i++) {
				// Set current Node to the one with shortest direct distance plus edge weight.
				if (min > (compareWeight.get(i).getDirectDistanceZ() + compareWeight.get(i).getEdges().get(curNode.getName()))) {
					min = compareWeight.get(i).getDirectDistanceZ() + compareWeight.get(i).getEdges().get(curNode.getName());
					// Temporarily store current node with minimum direct distance plus edge weight.
					tempNode = compareWeight.get(i);
				}
			}
			
			// Get next closest node.
			index = Arrays.asList(nodeArray).indexOf(tempNode.getName());
			curNode = nodeObjects.get(index);
		}
		
		// Add the current Node to our shortest path and total path.
		path.add(curNode.getName());
		shortestPath.add(curNode.getName());
		
		// Compute the path distance using the final shortestPath ArrayList.
		int pathDistance = 0;
		for (int i = 0; i < shortestPath.size()-1; i++) {
			Node tempNode2 = getObject(shortestPath.get(i), nodeArray, nodeObjects);
			pathDistance = pathDistance + tempNode2.getEdges().get(shortestPath.get(i+1));
		}
		
		// For loops to print the output in a presentable way.
		System.out.println("");
		System.out.print("Algorithm 2: ");
		System.out.println("\n");
		System.out.print("Sequence of all nodes: ");
		for (int count = 0; count<path.size(); count++) {
			if (count == path.size()-1) {
				System.out.println(path.get(count));
			}
			else {
				System.out.print(path.get(count) + " -> ");
			}
		}
		
		System.out.print("Shortest path: ");
		for (int count = 0; count<shortestPath.size(); count++) {
			if (count == shortestPath.size()-1) {
				System.out.println(shortestPath.get(count));
			}
			else {
				System.out.print(shortestPath.get(count) + " -> ");
			}
		}
		
		System.out.print("Shortest path length: " + pathDistance);
		System.out.println("\n");
	}
	
	
	
	
	/** 
	    * This is the main method and it is in charge of reading the text files
	    * and pulling the data out of them and creating Node objects with the
	    * data. It also asks the user for input and uses that to call 
	    * algorithm1 and algorithm2.
	    */
	public static void main(String[] args) throws IOException {
		
		// Instantiate the map to store matrix and the node edges.
		Map<String, HashMap<String, Integer>> nodes = new HashMap<>();
		
		// Another map to store direct distances.
		Map<String, Integer> directDistances = new HashMap<>();
		
		
		/*************** INPUT FILE BELOW ***************/
		
		//Input file has weighted graph
		Scanner fileInput = new Scanner (new File("graph_input.txt"));
	
		// New string array to store the rows of data.
		String[] aRow;
		// Read the top row in the input file and create nodeArray.
		aRow = fileInput.nextLine().trim().split("\\s+");
		int numNodes = aRow.length;
		
		// Uses for loop to store the letter names of the nodes.
		String[] nodeArray = new String[numNodes];
		for (int j=0; j<aRow.length; j++) {
			nodeArray[j] = aRow[j];
		}
		
		// Loop through entire file until no more lines.
		int k = 0;
		while (fileInput.hasNext()){
			// Create new HashMap to store edges.
			HashMap<String, Integer> edges = new HashMap<>();
			// Read and split next line.
			aRow = fileInput.nextLine().split("\\s+");
			// Store the integers of the row that represent the edges.
			for (int j=1; j<aRow.length; j++) {
				int e = Integer.parseInt(aRow[j]);
				// If edge is not zero store edge.
				if(e != 0) {
					edges.put(nodeArray[j-1], e);
				}
			}
			// Store the node name as key and the hashmap edges as value of nodes.
			nodes.put(nodeArray[k], edges);
			k+=1;
			
		}
		// Close the opened file.
		fileInput.close();
		
		/*************** INPUT FILE BELOW ***************/
		
		// Open the second file to get direct distance data.
		Scanner fileInput2 = new Scanner (new File("direct_distance.txt"));
		
		
		// Loop through lines of file.
		String[] line;
		while(fileInput2.hasNext()) {
			// Read next line.
			line = fileInput2.nextLine().split("\\s+");
			// Store integer of directDistance
			int distance = Integer.parseInt(line[1]);
			// Add node name as key and distance as value.
			directDistances.put(line[0], distance);
		}
		
		// ArrayList to store objects of Node class.
		ArrayList<Node> nodeObjects = new ArrayList<>();
		
		// Loop through entries of nodes to store them as objects.
		for (Entry<String, HashMap<String, Integer>> entry : nodes.entrySet()) {
			// Create new object of class Node for every entry.
			Node curNode = new Node(entry.getKey());
			// HashMap to store the edges of nodes.
			HashMap<String, Integer> curEdge = new HashMap<String, Integer>();
			// Add the node object and its edges to the new ArrayList nodeObjects.
			curEdge = entry.getValue();
			curNode.setEdges(curEdge);
			nodeObjects.add(curNode);	
		}
		
		// Loop through entries of direct distances.
		int j = 0;
		for (Entry<String, Integer> entry : directDistances.entrySet()) {
			// Call the wanted node in nodeObjects.
			Node curNode = nodeObjects.get(j);
			// Set the direct distance of the node to the given value.
			curNode.setDirectDistanceZ(entry.getValue());
			j++;
		}
		
		// Loop that keeps asking user for input until they input a valid node name.
		String input;
		while (true) {
			// Ask user for start node.
			Scanner userNode = new Scanner(System.in);  // Create a Scanner object
		    System.out.println("Please enter start node: ");
		    
		    // Read node and convert to upper case.
		    input = userNode.nextLine();  
		    input = input.toUpperCase();
		    
		    // If node is in data then break off loop.
		    if (Arrays.asList(nodeArray).contains(input)) {
		    	break;
		    }
		}
		
		// Call the first algorithm to find minimum distance.
		algorithm1(input, nodeArray, nodeObjects);
		
		// Reset the .isVisited attribute of the nodes so it doesn't affect the second algorithm.
		for (int i = 0; i < nodeObjects.size(); i++) {
			nodeObjects.get(i).setIsVisited(false);
		}
		// Call second algorithm.
		algorithm2(input, nodeArray, nodeObjects);
	}
}