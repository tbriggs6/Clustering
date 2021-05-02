package cluster;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import cluster.agglom.AgglomCluster;

public class ClusterStats extends JPanel {

 int PIXELS = 10;
 int PIXELS_WIDTH = 20;
 
 private class TreeDimensions {
  double distance;
  int height;
  public int width;
 }
 
 private class ClusterPos {
  AgglomCluster curr;
  int x, y, depth;
  
  public ClusterPos(AgglomCluster curr, int x, int y, int depth)
  {
   this.curr = curr;
   this.depth = depth;
   this.x = x;
   this.y = y;
  }
 }
 
 private class ClusterHeight {
  int depth;
  double dist;
  AgglomCluster curr;
  
  ClusterHeight(int d, double dist, AgglomCluster c)
  {
   this.depth = d;
   this.dist = dist;
   this.curr = c;
  }
 }

 JScrollPane pane;
 JPanel panel;
 AgglomCluster root;
 
 public ClusterStats(AgglomCluster root)
 {
  this.root = root;
  
  panel = new JPanel( );
  panel.setSize(500,500);
  
  pane = new JScrollPane( );  
  pane.setAutoscrolls(true);
  pane.add(panel);
  
 
  this.setLayout(new GridBagLayout());
  GridBagConstraints gc = new GridBagConstraints();
  gc.weightx = 1.0;
  gc.weighty = 1.0;
  gc.gridx = 0;
  gc.gridy = 1;
  this.add(pane, gc);
 }
 
 
 @Override
 public void paint(Graphics g)
 {
  super.paint(g);
  
  TreeDimensions T = getClusterDepth();
  HashMap<Integer, Integer> nodes = new HashMap<Integer, Integer>( );
  
//  int height = Math.max(400, 50 + (int) T.distance);
//  
//  panel.setSize(panel.getWidth(), height);
//  panel.invalidate();
  
  System.out.format("width: %d  height: %d dist: %f\n", T.width, T.height, T.distance);

  double width = 500.0 / (double) T.width;
  
  LinkedList<ClusterPos> open = new LinkedList<ClusterPos>( );
  open.add(new ClusterPos(root,0,0,0));
  
  g.setColor(Color.BLUE);
  
  while(open.size() > 0)
  {
   ClusterPos curr = open.removeFirst();

   System.out.format("Drawing at (%d,%d) depth: %d\n", curr.x, curr.y, curr.depth);
   g.drawOval(curr.x, curr.y, 5, 5);
   
   if (curr.curr.getClusters().size() == 0) continue;  // continue on leaf node
   
   AgglomCluster left = curr.curr.getClusters().get(0);
   AgglomCluster right = curr.curr.getClusters().get(1);
   
//   if (left.getClusters().size() == 0)
//   {
//    AgglomCluster tmp = left;
//    left = right;
//    right = tmp;
//   }
   
   if (!nodes.containsKey(curr.depth + 1))
    nodes.put(curr.depth+1, 1);
   else
    nodes.put(curr.depth+1, nodes.get(curr.depth+1)+1);
   
   int hpos = nodes.get(curr.depth+1) - 1;
   
   double d = Math.min(3.0, Math.log(1 + left.getDissimilarity(right)));
   double y2 = curr.y + ((d  / T.distance) * 400);
   double xr = curr.x + (hpos * width);

   open.add(new ClusterPos(left, curr.x, (int) y2, curr.depth + 1));
   open.add(new ClusterPos(right, (int) xr, (int) y2, curr.depth + 1));
  }
  
  
 }
 
 
 private TreeDimensions getClusterDepth( )
 {
  int maxHeight = 0;
  double maxDiss = 0;
  HashMap<Integer,Integer> nodes = new HashMap<Integer,Integer>( );
  
  LinkedList<ClusterHeight> open = new LinkedList<ClusterHeight>( );
  open.add(new ClusterHeight(1, 0.0, root));
  
  while(open.size() > 0)
  {
   ClusterHeight curr = open.removeFirst();
   maxHeight = Math.max(maxHeight, curr.depth);
   maxDiss = Math.max(maxDiss, curr.dist);

    if (!nodes.containsKey(curr.depth))
     nodes.put(curr.depth,1);
    else
     nodes.put(curr.depth, nodes.get(curr.depth)+1);
   
   int numClusters = curr.curr.getClusters().size();
   if (!((numClusters == 0) || (numClusters == 2)))
    throw new RuntimeException("Clusters must be either leaf, or binary splits.");

   if (numClusters == 0) continue;
   
   AgglomCluster left = curr.curr.getClusters().get(0);
   AgglomCluster right = curr.curr.getClusters().get(1);
   
   double d = Math.min(3.0, Math.log( 1 + left.getDissimilarity(right)));
   
   open.add(new ClusterHeight(curr.depth + 1, curr.dist + d, left));
   open.add(new ClusterHeight(curr.depth + 1, curr.dist + d, right));
  }

  TreeDimensions T = new TreeDimensions( );
  
  T.width = 0;
  for (Integer value : nodes.values())
   T.width = Math.max(T.width, value);
  
  T.height = maxHeight;
  T.distance = maxDiss;

  
  return T;
 }
 

}
