package cluster.qt;

import java.util.LinkedList;

import cluster.Cluster;
import cluster.Point;

public class QTCluster extends Cluster
{
 double distance;
 protected boolean wrapup;
 protected Point exemplar;

 public QTCluster(boolean wrapup, double distance, Point exemplar) {
  this.wrapup = wrapup;
  this.distance = distance;
  this.exemplar = exemplar;
  this.points = new LinkedList<Point>();
 }

 public boolean isInClusterSpace(Point P)
 {
  if (wrapup) return true;
  return (P.getDistance(exemplar) <= this.distance);
 }
 
 public boolean isWrapup() {
  return wrapup;
 }

 public Point getExemplar() {
  return exemplar;
 }

 public void setExemplar(Point exemplar) {
  this.exemplar = exemplar;
 }

 public double getDistance() {
  return distance;
 }

 public void setDistance(double distance) {
  this.distance = distance;
 }

 @Override
 public String toString( )
 {
  StringBuffer buff = new StringBuffer( );
  buff.append(exemplar.toString() + " " + this.distance + " :");
  for (int i = 0; i < points.size(); i++)
  {
   if (i > 0) buff.append(",");
   buff.append(String.format("%s/%f",points.get(i).toString(), points.get(i).getDistance(exemplar)));
  }

  return buff.toString();
 }

 public void setWrapUp(boolean b) {
  this.wrapup = b;
 }
 
 public boolean add(Point o) {
  if (!wrapup && ((o.getDistance(exemplar) > this.distance)))
   return false;
  
  return super.add(o);
 }

}
