package cluster;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import static java.lang.Math.max;

public class Cluster  implements Collection<Point>, List<Point> , java.io.Serializable
{

 protected LinkedList<Point> points;
 
 public Cluster() {
  super();
  points = new LinkedList<Point>( );
 }

 public void add(int index, Point element) {
  points.add(index, element);
 }

 public boolean add(Point o) {
  return points.add(o);
 }

 public boolean addAll(Collection<? extends Point> c) {
  return points.addAll(c);
 }

 public boolean addAll(int index, Collection<? extends Point> c) {
  return points.addAll(index, c);
 }

 public void addFirst(Point o) {
  points.addFirst(o);
 }

 public void addLast(Point o) {
  points.addLast(o);
 }

 public void clear() {
  points.clear();
 }

 public Object clone() {
  return points.clone();
 }

 public boolean contains(Object o) {
  return points.contains(o);
 }

 public boolean containsAll(Collection<?> c) {
  return points.containsAll(c);
 }

 public Iterator<Point> descendingIterator() {
  return points.descendingIterator();
 }

 public Point element() {
  return points.element();
 }

 public boolean equals(Object o) {
  return points.equals(o);
 }

 public Point get(int index) {
  return points.get(index);
 }

 public Point getFirst() {
  return points.getFirst();
 }

 public Point getLast() {
  return points.getLast();
 }

 public int hashCode() {
  return points.hashCode();
 }

 public int indexOf(Object o) {
  return points.indexOf(o);
 }

 public boolean isEmpty() {
  return points.isEmpty();
 }

 public Iterator<Point> iterator() {
  return points.iterator();
 }

 public int lastIndexOf(Object o) {
  return points.lastIndexOf(o);
 }

 public ListIterator<Point> listIterator() {
  return points.listIterator();
 }

 public ListIterator<Point> listIterator(int index) {
  return points.listIterator(index);
 }

 public boolean offer(Point o) {
  return points.offer(o);
 }

 public boolean offerFirst(Point e) {
  return points.offerFirst(e);
 }

 public boolean offerLast(Point e) {
  return points.offerLast(e);
 }

 public Point peek() {
  return points.peek();
 }

 public Point peekFirst() {
  return points.peekFirst();
 }

 public Point peekLast() {
  return points.peekLast();
 }

 public Point poll() {
  return points.poll();
 }

 public Point pollFirst() {
  return points.pollFirst();
 }

 public Point pollLast() {
  return points.pollLast();
 }

 public Point pop() {
  return points.pop();
 }

 public void push(Point e) {
  points.push(e);
 }

 public Point remove() {
  return points.remove();
 }

 public Point remove(int index) {
  return points.remove(index);
 }

 public boolean remove(Object o) {
  return points.remove(o);
 }

 public boolean removeAll(Collection<?> c) {
  return points.removeAll(c);
 }

 public Point removeFirst() {
  return points.removeFirst();
 }

 public boolean removeFirstOccurrence(Object o) {
  return points.removeFirstOccurrence(o);
 }

 public Point removeLast() {
  return points.removeLast();
 }

 public boolean removeLastOccurrence(Object o) {
  return points.removeLastOccurrence(o);
 }

 public boolean retainAll(Collection<?> c) {
  return points.retainAll(c);
 }

 public Point set(int index, Point element) {
  return points.set(index, element);
 }

 public int size() {
  return points.size();
 }

 public List<Point> subList(int fromIndex, int toIndex) {
  return points.subList(fromIndex, toIndex);
 }

 public Object[] toArray() {
  return points.toArray();
 }

 public <T> T[] toArray(T[] a) {
  return points.toArray(a);
 }

 public double getDiameter( )
 {
  double maxDistance = Double.NEGATIVE_INFINITY;
  
  for (int i = 0; i < points.size(); i++)
   for (int j = i+1; j < points.size(); j++) 
    maxDistance = max(points.get(i).getDistance(points.get(j)), maxDistance);
  return maxDistance;  
 }
 
}