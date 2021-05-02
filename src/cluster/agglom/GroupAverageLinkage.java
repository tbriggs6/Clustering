package cluster.agglom;

import java.util.LinkedList;

import cluster.Point;

public class GroupAverageLinkage extends AgglomCluster {

 public GroupAverageLinkage(LinkedList<Point> points) {
  super(points);
 }

 public GroupAverageLinkage() {

 }

 @Override
 public double getDissimilarity(AgglomCluster other) 
 {

  double diss = this.get(0).getDistance(other.get(0));
  for (int i = 0; i < this.size(); i++)
   for (int j = i; j < other.size(); j++)
    diss = Math.min(diss, this.get(i).getDistance(other.get(j)));
  
  return diss;
 }

}
