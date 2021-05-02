package cluster.agglom;

import java.util.LinkedList;

import cluster.Point;

public class GroupAverageFactory implements IAgglomClusterFactory {

 public AgglomCluster makeCluster() {
  return new GroupAverageLinkage( );
 }

 public AgglomCluster makeCluster(LinkedList<Point> points) {
   return new GroupAverageLinkage(points);
 }
 

}
