package cluster;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestTriangularMatrix {

 
 
 @Test
 public void testTriangularMatrix( )
 {
  TriangularMatrix T = new TriangularMatrix(10);
  for (int i = 0; i < 10; i++)
   for (int j = i; j < 10; j++)
    T.setValue(i, j, i*10+j);
  
  for (int i = 0; i < 10; i++)
   for (int j = i; j < 10; j++) {
    assertEquals((i*10)+j, T.getvalue(i, j));
   }

  
 }
}
