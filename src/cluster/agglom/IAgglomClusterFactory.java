package cluster.agglom;

import java.util.LinkedList;

import cluster.Point;

public interface IAgglomClusterFactory {

 public AgglomCluster makeCluster( );
 public AgglomCluster makeCluster(LinkedList<Point> points);
}
