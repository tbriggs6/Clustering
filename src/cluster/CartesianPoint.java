package cluster;

public class CartesianPoint implements Point {

 double x,y;
 private static int numDistance = 0;
 private static int numEquals = 0;
 
 public CartesianPoint(double x, double y)
 {
  this.x = x;
  this.y = y;
 }

 public static int getNumDistance( ) 
 {
  return numDistance;
 }
 
 public static int getNumEquals( )
 {
  return numEquals;
 }
 
 public double getDistance(Point other) {

  numDistance++;
  
  CartesianPoint R = (CartesianPoint) other;
  
  return Math.sqrt(Math.pow(this.x - R.x, 2) + Math.pow(this.y - R.y,2));
 }

 @Override
 public int hashCode() {
  final int prime = 31;
  int result = 1;
  long temp;
  temp = Double.doubleToLongBits(x);
  result = prime * result + (int) (temp ^ (temp >>> 32));
  temp = Double.doubleToLongBits(y);
  result = prime * result + (int) (temp ^ (temp >>> 32));
  return result;
 }

 @Override
 public boolean equals(Object obj) {
  numEquals++;
  
  if (this == obj)
   return true;
  if (obj == null)
   return false;
  if (getClass() != obj.getClass())
   return false;
  CartesianPoint other = (CartesianPoint) obj;
  if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
   return false;
  if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
   return false;
  return true;
 }
 
 
 public String toString()
 {
  return String.format("(%f,%f)", x, y);
 }
}
