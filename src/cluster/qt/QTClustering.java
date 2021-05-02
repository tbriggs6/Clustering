package cluster.qt;

import java.util.LinkedList;

import cluster.Point;


public class QTClustering 
{

 public static final double alpha = 0.0005;
 public static final double beta = 0.0025;
 public static final int minSize = 1;  
 
 
 public QTCluster makeCluster(LinkedList<Point> points)
 {
  QTCluster C = new QTCluster(true, alpha, null);
  C.addAll(points);
  
  return C;
 }
 
 
 public Dendogram cluster(LinkedList<Point> points)
 {
   Dendogram root = new Dendogram(makeCluster(points));
   root.setRoot(true);
   cluster(root);
   return root;
 }
 
 
 protected void cluster(Dendogram parent)
 {
  LinkedList<Point> remainingNodes = new LinkedList<Point>( );
  remainingNodes.addAll(parent.getParent());
  
  double distance = parent.getParent().getDistance() * beta;
  
  do {
  
   QTCluster child = findMaxCluster(distance, remainingNodes);
   if (child.isWrapup())
    remainingNodes.clear();
   else
    remainingNodes.removeAll(child);
 
    parent.addChild(child);
  } while(remainingNodes.size() > 0);
  
  for (Dendogram D : parent.getChildren())
  {
   if (D.getParent().isWrapup()) continue;
   else if (D.getParent().size() <= minSize) {
    D.getParent().setWrapUp(true);
    continue;
   }
   else
     cluster(D);
  }


 }
 
 /**
  * Select the points that are within the given distance of the given centroid.
  * @param distance - the max distance
  * @param points - the points to check
  * @param centroid - the centroid
  * @return - the list of points that are within distance of the centroid
  */
 protected LinkedList<Point> findPointsInRange(double distance, LinkedList<Point> points, Point centroid)
 {
  LinkedList<Point> inrange = new LinkedList<Point>( );
  for (Point P : points)
  {
   if (P.getDistance(centroid) <= distance)
    inrange.add(P);
  }
  
  return inrange;
 }
 
 /**
  * Find the maximum cluster that has points within the given distance
  * @param distance - The distance
  * @param nodes - The set of nodes
  * @return
  */
 protected QTCluster findMaxCluster(double distance, LinkedList<Point> points)
 {
  int maxCentroid = -1;
  LinkedList<Point> maxPoints = new LinkedList<Point>( );
  
  for (int i = 0; i < points.size(); i++)
  {
   // make a cluster using the ith point
   LinkedList<Point> inrange = findPointsInRange(distance, points, points.get(i));
   if (inrange.size() > maxPoints.size())
   {
    maxCentroid = i;
    maxPoints = inrange;
   }
  }

  // select whether the cluster is a wrap-up (i.e. no points in range) or is a valid clustering.
  if (maxCentroid > -1) {
   QTCluster C = new QTCluster(false, distance, points.get(maxCentroid));
   C.addAll(maxPoints);
   
   return C;
  }
  else
  {
   QTCluster C = new QTCluster(true, distance, null);
   C.addAll(points);
   return C;
  }
 }
}
