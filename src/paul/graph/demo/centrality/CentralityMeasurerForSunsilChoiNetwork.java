package paul.graph.demo.centrality;

import java.io.File;
import java.util.Iterator;

import paul.graph.centrality.BetweennessCentrality;
import paul.graph.centrality.ClosenessCentrality;
import paul.graph.centrality.DegreeCentrality;
import paul.graph.centrality.EigenvectorCentrality;
import prefuse.data.Edge;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.Schema;
import prefuse.data.io.DataIOException;
import prefuse.data.io.GraphMLReader;
import prefuse.data.io.GraphMLWriter;

public class CentralityMeasurerForSunsilChoiNetwork {
		
	public static final String DIR_PATH = "network" + File.separator;
		
	public static void main(String[] args) {
		
		File dir = new File(DIR_PATH);
		if ( !dir.isDirectory() )
			dir.mkdirs();		
		
		Graph g = null;
		GraphMLReader reader = new GraphMLReader();
		try {
			g = reader.readGraph(DIR_PATH + "sunsilchoi_gephi.graphml");
		} catch (DataIOException e) {
			e.printStackTrace();
		}		
		
		Iterator<?> nIter = g.nodes();		
		while ( nIter.hasNext() ) {
			Node n = (Node)nIter.next();
			n.setInt("r", 255);
			n.setInt("g", 255);
			n.setInt("b", 255);
			//n.setString(Schema.KEY_LABEL, ""+n.getRow());
		}
		g.getEdges().addColumn("r", int.class, 0);
		g.getEdges().addColumn("g", int.class, 0);
		g.getEdges().addColumn("b", int.class, 0);
		g.getEdges().addColumn(Schema.KEY_LABEL, String.class, "");
		Iterator<?> eIter = g.edges();		
		while ( eIter.hasNext() ) {
			Edge e = (Edge)eIter.next();
			e.setDouble(Schema.KEY_WEIGHT, 1.0);			
			e.setString(Schema.KEY_LABEL, ""+e.getRow());
		}
		
		double[] values = null;
		
		// Degree Centrality
		g.getNodes().addColumn("DegreeCentrality", double.class, 0);
		DegreeCentrality dc = new DegreeCentrality();		
		dc.measure(g);
		values = dc.generalize();
		nIter = g.nodes();
		while ( nIter.hasNext() ) {
			Node n = (Node)nIter.next();
			n.setDouble("DegreeCentrality", values[n.getRow()]);
		}
		
		// Betweenness Centrality
		g.getNodes().addColumn("BetweennessCentrality", double.class);
		BetweennessCentrality bc = new BetweennessCentrality();
		bc.measure(g);		
		values = bc.generalize();
		nIter = g.nodes();
		while ( nIter.hasNext() ) {
			Node n = (Node)nIter.next();
			n.setDouble("BetweennessCentrality", values[n.getRow()]);
		}
		
		// Closeness Centrality
		g.getNodes().addColumn("ClosenessCentrality", double.class);
		ClosenessCentrality cc = new ClosenessCentrality();
		cc.measure(g);
		values = cc.generalize();
		nIter = g.nodes();
		while ( nIter.hasNext() ) {
			Node n = (Node)nIter.next();			
			n.setDouble("ClosenessCentrality", values[n.getRow()]);
		}
		
		// Eigenvector Centrality
		g.getNodes().addColumn("EigenvectorCentrality", double.class);
		EigenvectorCentrality ec = new EigenvectorCentrality();
		ec.measure(g);
		values = ec.generalize();
		nIter = g.nodes();
		while ( nIter.hasNext() ) {
			Node n = (Node)nIter.next();			
			n.setDouble("EigenvectorCentrality", values[n.getRow()]);
		}
		
		GraphMLWriter writer = new GraphMLWriter();		
		try {
			writer.writeGraph(g, DIR_PATH + "sunsilchoi_centrality.graphml");
		} catch (DataIOException e) {
			e.printStackTrace();
		}
		
		System.out.println("All done.");
		
	}

}
