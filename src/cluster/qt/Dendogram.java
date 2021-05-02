package cluster.qt;

import java.util.LinkedList;

import cluster.Point;


public class Dendogram {

 QTCluster parent;
 LinkedList<Dendogram> children;
 boolean root = false;
 
 public Dendogram( QTCluster parent )
 {
  this.parent = parent;
  this.children = new LinkedList<Dendogram>( );
 }

 public boolean isRoot( )
 {
  return root;
 }
 
 public void setRoot(boolean r)
 {
  this.root = r;
 }
 
 public QTCluster getParent() {
  return parent;
 }

 public LinkedList<Dendogram> getChildren() {
  return children;
 }

 public boolean addChild(QTCluster child)
 {
  if (child.isWrapup() || (parent.isInClusterSpace(child.getExemplar())))
  {
   Dendogram D = new Dendogram(child);
   return children.add(D);
  }
  else
   return false;
 }
 
 public boolean contains(Point P)
 {
  if (!parent.isInClusterSpace(P)) return false;
  
  else if (!this.root && (parent.isWrapup()))
   return parent.contains(P);
  
  for (Dendogram D : children)
  {
   if (D.contains(P)) return true;
  }
  
  return false;
 }
 
 private String toString(int depth)
 {
  StringBuffer buff = new StringBuffer( );
  for (int i = 0; i < depth; i++)
   buff.append(" ");
 
  buff.append(String.format("cluster @%d, |%d|, children = { ", depth/2, parent.size()));
  int count = 0;
  for (Dendogram D : children)
  {
   if (count++ > 0) buff.append(", ");
   buff.append(D.getParent().size());
  }
  buff.append(" }\n");
  
  for (Dendogram D : children)
  {
   buff.append(D.toString(depth+2));
  }
  
  buff.append("\n");
  return buff.toString();
 }

 public String toString( )
 {
  return this.toString(0);
 }
}
