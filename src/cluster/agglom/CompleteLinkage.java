package cluster.agglom;

import java.util.LinkedList;

import cluster.Point;

public class CompleteLinkage extends AgglomCluster {

 public CompleteLinkage( )
 {
  super( );
 }
 public CompleteLinkage(LinkedList<Point> points) {
  super(points);
 }

 @Override
 public double getDissimilarity(AgglomCluster other) 
 {
  double sum = 0;

  for (int i = 0; i < this.size(); i++)
   for (int j = 0; j < other.size(); j++)
    sum += this.get(i).getDistance(other.get(j));
  
  double total = this.size() * other.size();
  return sum / total;
 }

}
