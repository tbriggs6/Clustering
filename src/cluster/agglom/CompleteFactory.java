package cluster.agglom;

import java.util.LinkedList;

import cluster.Point;

public class CompleteFactory implements IAgglomClusterFactory {

 public AgglomCluster makeCluster() {
  return new CompleteLinkage( );
 }

 public AgglomCluster makeCluster(LinkedList<Point> points) {
   return new CompleteLinkage(points);
 }
 

}
