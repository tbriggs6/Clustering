package cluster.agglom;

import java.util.LinkedList;

import cluster.Cluster;
import cluster.Point;

public abstract class AgglomCluster extends Cluster implements java.io.Serializable 
{
 LinkedList<AgglomCluster> clusters;

 public AgglomCluster() {
  this.clusters = new LinkedList<AgglomCluster>();

 }

 public AgglomCluster(LinkedList<Point> points) {
  this.points.addAll(points);
 }

 public LinkedList<AgglomCluster> getClusters() {
  return clusters;
 }

 public void addCluster(AgglomCluster cluster) {
  this.points.addAll(cluster);
  this.clusters.add(cluster);
 }

 private String toString(int depth) {
  StringBuffer buff = new StringBuffer();

  for (int i = 0; i < depth; i++)
   buff.append(" ");

  buff.append(String.format("%d points", points.size()));

  buff.append("{");
  boolean first = true;
  for (AgglomCluster C : clusters) {
   if (!first)
    buff.append(",");
   buff.append(C.size());
   first = false;
  }
  buff.append("}\n");
  for (AgglomCluster C : clusters)
   buff.append(C.toString(depth + 1));

  return buff.toString();
 }

 @Override
 public String toString() {
  return toString(0);
 }

 public abstract double getDissimilarity(AgglomCluster other);

}
