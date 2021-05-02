package cluster.qt;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

import cluster.CartesianPoint;
import cluster.Cluster;
import cluster.Point;
import cluster.RandomPoint;


public class TestCluster {
 
 @Test 
 public void testAdd( )
 {
  Cluster C = new QTCluster(false, 0.5, new CartesianPoint(0,0));
  assertTrue(C.add(new CartesianPoint(0.25, 0.25)));
  assertFalse(C.add(new CartesianPoint(1.0, 1.0)));
 }
 
 @Test
 public void testAdd2( )
 {
  Cluster C = new QTCluster(false, Math.sqrt(2), new CartesianPoint(0,0));
  LinkedList<RandomPoint> points = new LinkedList<RandomPoint>( );
  for (int i = 0; i < 100; i++)
  {
   points.add(new RandomPoint());
   assertTrue(C.add(points.get(i)));
  }
 }
 
 @Test
 public void testInCluster( )
 {
  QTCluster C = new QTCluster(false, 0.2, new CartesianPoint(0,0));
  assertTrue(C.isInClusterSpace(new CartesianPoint(0.01,0.01)));
  assertTrue(C.isInClusterSpace(new CartesianPoint(0.2, 0.0)));
  assertTrue(C.isInClusterSpace(new CartesianPoint(0.0, 0.2)));
  
  assertFalse(C.isInClusterSpace(new CartesianPoint(0.21, 0.0)));
  assertFalse(C.isInClusterSpace(new CartesianPoint(0, 0.21)));
  assertFalse(C.isInClusterSpace(new CartesianPoint(1.0, 1.0)));
 }
 
 @Test
 public void testContains( )
 {
  Cluster C = new QTCluster(false, Math.sqrt(2), new CartesianPoint(0,0));
  LinkedList<RandomPoint> points = new LinkedList<RandomPoint>( );
  for (int i = 0; i < 100; i++)
  {
   points.add(new RandomPoint());
   assertTrue(C.add(points.get(i)));
  }
  
  for (Point P : points)
  {
   assertTrue(C.contains(P));
  }
  
  assertTrue(C.containsAll(points));
  
  assertFalse(C.contains(new CartesianPoint(1.01, 0)));
  
 }
 
}