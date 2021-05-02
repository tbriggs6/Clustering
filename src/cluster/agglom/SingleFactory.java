package cluster.agglom;

import java.util.LinkedList;

import cluster.Point;

public class SingleFactory implements IAgglomClusterFactory {

 public AgglomCluster makeCluster() {
  return new SingleLinkage( );
 }

 public AgglomCluster makeCluster(LinkedList<Point> points) {
   return new SingleLinkage(points);
 }
 

}
