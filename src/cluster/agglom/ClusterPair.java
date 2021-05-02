package cluster.agglom;


public class ClusterPair {
 AgglomCluster G,H;
 double dissimilarity;
 
 ClusterPair(AgglomCluster G, AgglomCluster H, double dissimilarity)
 {
  this.G = G;
  this.H = H;
  this.dissimilarity = dissimilarity;
 }
 
 public String toString( )
 {
  return String.format("%f : { %d, %d }", dissimilarity, G.size(), H.size());
 }
}
