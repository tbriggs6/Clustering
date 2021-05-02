package cluster.agglom; 

import java.util.LinkedList;

import cluster.Point;

public class AgglomClustering  {

 private LinkedList<Point> points;
 private LinkedList<AgglomCluster> clusters;
 private IAgglomClusterFactory factory;
 
 public final static int numThreads = 8;
 
 public AgglomClustering(IAgglomClusterFactory factory, LinkedList<Point> points)
 {
  this.factory = factory;
  this.points = points;
  this.clusters = new LinkedList<AgglomCluster>( );
  
  initialize( );
 }
 
 private void initialize( )
 {
  for (Point P : points)
  {
   AgglomCluster C = factory.makeCluster();
   C.add(P);
   
   clusters.add(C);
  }
 }
 
 protected double getDissimilarity(AgglomCluster G, AgglomCluster H)
 {
  return G.getDissimilarity(H);
 }
 
 
 
 
 private ClusterPair findMinPair( )
 {
  ClusterPair min = null;
  AgglomClusterThread worker[] = new AgglomClusterThread[numThreads];
  Thread threads[] = new Thread[numThreads];
  
  for (int cpu = 0; cpu < numThreads; cpu++) 
  {
   worker[cpu] = new AgglomClusterThread(cpu, numThreads, clusters);
   threads[cpu]= new Thread(worker[cpu]);
   threads[cpu].start();
  }
  

  for (int cpu = 0; cpu < numThreads; cpu++)
  {
   try {
    threads[cpu].join();
    if (cpu == 0)
     min = worker[0].getMin( );
    else
     if (worker[cpu].getMin().dissimilarity < min.dissimilarity)
      min = worker[cpu].getMin();
   } 
   catch (InterruptedException e) {
    e.printStackTrace();
   }
  }
  return min;
 }
 
 public int numPoints( )
 {
  int sum = 0;
  for (AgglomCluster C: clusters)
  {
   sum += C.size();
  }
  return sum;
 }
 
 
 public void makeClusters( )
 {
  for (int i = 0; i < points.size()-1; i++)
  {
   int oldSum = numPoints( );
   
   System.out.format("Beginning iteration %d %d\n", i, oldSum);
   ClusterPair min = findMinPair( );
   if (!(clusters.remove(min.G) && clusters.remove(min.H)))
    throw new RuntimeException("Could not remove min clusters.");
   
   AgglomCluster n = factory.makeCluster();
   n.addCluster(min.G);
   n.addCluster(min.H);
   clusters.add(n);
   
   int sum = numPoints( );
   
   if (sum != oldSum)
   {
    throw new RuntimeException("Lost points!");
   }
  }
 }
 
 public LinkedList<AgglomCluster> getClusters()
 {
  return clusters;
 }
 
 @Override
 public String toString( )
 {
  StringBuffer buff = new StringBuffer( );
  
  buff.append("AG-Clusters: ");
  for (int i = 0; i < clusters.size(); i++) {
   buff.append(clusters.get(i).toString());
   buff.append("\n");
  }
  return buff.toString();
 }
 
}
