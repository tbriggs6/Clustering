package cluster;

public class TriangularMatrix implements java.io.Serializable {

 private static final long serialVersionUID = -5545590071589359067L;

 double values[];
 int size;
 int rows[];
 
 public TriangularMatrix(int size)
 {
  this.size = size;
  int len = ((size+1) * (size + 2)) / 2; 
  values = new double[len];
  
  makeIndexes( );
 }
 
 private void makeIndexes( )
 {
  rows = new int[size];
  
  int sum = 0;
  for (int i = 0; i < size; i++) 
   rows[i] = sum += (size - i); 
 
 }

 public int getIndex(int a, int b)
 {
  if (b < a) {
   int t = a;
   a = b;
   b = t;
  }
  

  int rowIndex = a == 0 ? 0 : rows[a-1];
  int colIndex = b-a;

  if ((rowIndex+colIndex) >= values.length)
   throw new RuntimeException(String.format("Illegal indexes: %d,%d for size %d", a,b,size));
   
  return rowIndex + colIndex;
 }
 
 public void setValue(int a, int b, double v)
 {
  values[getIndex(a,b)] = v;
 }
 
 public double getvalue(int a, int b)
 {
  return values[getIndex(a,b)];
 }
}
