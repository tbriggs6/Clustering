package cluster.qt;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import cluster.CartesianPoint;
import cluster.Point;
import cluster.RandomPoint;

public class TestQTClustering {

 LinkedList<Point> points;
 
 @Before
 public void setup( )
 {
  points = new LinkedList<Point>( );
  
  points.add(new CartesianPoint(0,0));
  points.add(new CartesianPoint(0.5, 0));
  points.add(new CartesianPoint(0.25, 0));
  points.add(new CartesianPoint(0.45, 0));
  points.add(new CartesianPoint(0.75, 0.75));
  
 }
 
 @Test
 public void testQTCluster( )
 {
  QTClustering qt = new QTClustering( );
  Dendogram d = qt.cluster(points);
  
  assertEquals(Math.sqrt(2), d.getParent().getDistance(), 0.001);
  assertEquals(5, d.getParent().size());
  assertEquals(2, d.getChildren().size());
  assertEquals(4, d.getChildren().get(0).getParent().size());
  assertEquals(1, d.getChildren().get(1).getParent().size());
  assertTrue(d.getChildren().get(0).parent.isInClusterSpace(new CartesianPoint(0.25, 0)));
  assertFalse(d.getChildren().get(0).parent.isInClusterSpace(new CartesianPoint(1.5, 1.5)));
 }
 
 @Test
 public void testQTCluster2( )
 {
  QTClustering qt = new QTClustering( );
  Dendogram d = qt.cluster(points);
 
  for (Point P : points)
  {
   assertTrue(d.contains(P));
  }
  
  assertFalse(d.contains(new CartesianPoint(20,20)));
  assertFalse(d.contains(new CartesianPoint(1,1)));
  assertFalse(d.contains(new CartesianPoint(0, 1)));
  assertFalse(d.contains(new CartesianPoint(1, 0)));
 }
 
 @Test 
 public void testQTCluster3( )
 {
  LinkedList<Point> points = new LinkedList<Point>( );
  for (int i = 0; i < 10000; i++)
   points.add(new RandomPoint());
  
  QTClustering qt = new QTClustering( );
  Dendogram d = qt.cluster(points);
  System.out.println("done building");
  int before = CartesianPoint.getNumDistance();
  int beforeEq = CartesianPoint.getNumEquals( );
  
//  for (Point P : points)
//  {
//   assertTrue(d.contains(P));
//  }
  d.contains(points.get(0));
  int after = CartesianPoint.getNumDistance();
  int afterEq = CartesianPoint.getNumEquals();
  
  int numComp = after-before;
  int numEq = afterEq - beforeEq;
  System.out.format("%d/%d\n", numComp, numEq);
  
//  before = CartesianPoint.getNumDistance();
//  beforeEq = CartesianPoint.getNumEquals();
//  int count = 0;
//  for (Point P : points)
//  {
//   for (Point Q : points)
//    if (P.equals(Q)) count++;
//  }
//  after = CartesianPoint.getNumDistance();
//  afterEq = CartesianPoint.getNumEquals();
//  
//  numComp = after-before;
//  numEq = afterEq - beforeEq;
//  System.out.format("%d/%d\n", numComp, numEq);
 }
}
