package aa;

import paul.graph.community.Community;
import paul.graph.community.CommunityList;
import paul.graph.community.finder.IC.originalcode._IC_CommunityFinder;
import paul.graph.dataset.ExampleGraphReader;
import prefuse.data.Graph;

public class CommunityDetectionDemo {
	
	public static void main(String[] args) {
		
		
		
		System.out.println("Community Detection Demo");
		System.out.println();
		
		Graph g = ExampleGraphReader.getJazzNetwork();		
		System.out.println("Example network : Jazz (nodes:" + g.getNodeCount() + ", edges:" + g.getEdgeCount() + ")");
		System.out.println("\tDetected : " + g.isDirected());
		System.out.println("\tWeighted : " + g.isWeighted());
		System.out.println("\tNodes : " + g.getNodeCount());
		System.out.println("\tEdges : " + g.getEdgeCount());
		System.out.println();
		
	}
	
	public static void find(Graph g) {
		
		System.out.println("Method : IC (Interaction Communities)");
		_IC_CommunityFinder icFinder = new _IC_CommunityFinder();
		icFinder.find(g);
		while ( true ) {
			try { Thread.sleep(200); } catch (Exception e) { e.printStackTrace(); }
			if ( icFinder.isCompleted() ) break;
		}
		CommunityList cl = icFinder.getCommunityList();
		int allCommNum = cl.size();
		int commNum = 0;
		for ( Community c : cl ) {
			if ( c.getNodeCount() > 2 )
				commNum++;
		}		
		System.out.println("\tMax threshold : " + icFinder.getMaxModulairty());
		System.out.println("\tMax distance : " + icFinder.getMaxDistance());
		System.out.println("\tAll communities : " + allCommNum);
		System.out.println("\tCommunities (|N|>=3) : " + commNum);
		System.out.println("\tSimilarity calculation time : " + icFinder.getSimilarityCalcTime() + " ms");
		System.out.println("\tClustering calculation time : " + icFinder.getClusteringCalcTime() + " ms");
		System.out.println("\tNodeComm generation time : " + icFinder.getNodeCommGenerationTime() + " ms");
		System.out.println("\tTotal time : " + icFinder.getTotalCalcTime() + " ms");
		
		
		
		
		System.out.println();
		System.out.println("All Done.");
		
	}

}
