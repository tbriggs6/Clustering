package cluster.agglom;

import java.util.LinkedList;

public class AgglomClusterThread implements Runnable {

 int cpu, numCPU;
 private LinkedList<AgglomCluster> clusters;
 private ClusterPair min;
 Object monitor;
 
 public AgglomClusterThread(int cpu, int numCpus, LinkedList<AgglomCluster> clusters)
 {
  this.cpu = cpu;
  this.numCPU = numCpus;
  this.clusters = clusters;
  this.monitor = new Object( );
 }
 
 public ClusterPair getMin( )
 {
  synchronized(monitor) {
   if (this.min == null)
    try {
     monitor.wait();
    } catch (InterruptedException e) {
     // TODO Auto-generated catch block
     e.printStackTrace();
    }
   
   return this.min;
  }
 }
 
 private ClusterPair findMinPair( )
 {
  synchronized(monitor) {
   ClusterPair min = new ClusterPair(this.clusters.get(0), this.clusters.get(1), 
     this.clusters.get(0).getDissimilarity(this.clusters.get(1)));
   
   for (int i = cpu; i < clusters.size(); i+=numCPU) 
   {
    for (int j = i+1; j < clusters.size(); j++)
    {
     double d = this.clusters.get(i).getDissimilarity(this.clusters.get(j));
     if (d < min.dissimilarity)
      min = new ClusterPair(this.clusters.get(i), this.clusters.get(j), d);
    } 
   }
   monitor.notifyAll();
   return min;
  }
 }
 
 public void run() {
  this.min = findMinPair( );
 }

}
