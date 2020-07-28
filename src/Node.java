import java.util.HashMap;
import java.util.Map;
import java.io.*;


public class Node {
	
	public static class Nodes {
		private String name;
		private Boolean isVisited;
		private int directDistanceZ;
		public Map<String, Integer> edges;
		
		
		public Nodes (String name) {
			
			this.name = name;
			this.isVisited = false;
			this.directDistanceZ = 0;
			this.edges = new HashMap<>();
		}
		
		public String getName() {
			return name;
		}
		
		public Boolean getIsVisited() {
			return isVisited;
		}
		
		public int getDirectDistanceZ() {
			return directDistanceZ;
		}
		
		public Map<String, Integer> getEdges() {
			return edges;
		}
		
		public void setIsVisited(Boolean visit) {
			this.isVisited = visit;
		}
		
		public void setDirectDistanceZ(int dd) {
			this.directDistanceZ = dd;
		}
	}
}