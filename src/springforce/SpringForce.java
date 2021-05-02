package springforce;

import java.io.IOException;

import cluster.IrisDistances;

public class SpringForce {

	private static final double K1 = 0.5;
	private static final double K2 = 0.5;
	private static final double L = 0.1;
	private static final double DAMPING = 0.1;
	private static final double EPSILON = 0.01;
	private static final double TIMESTEP = 0.5;
	
	private IDistances distances;
	private Point2D[] points;
	private Vector2[] velocities;
	
	
	private class Point2D {
		double x, y;
	}

	private class Vector2 {
		double x, y;
		
		Vector2(double x, double y) { this.x = x; this.y = y; }
		double norm() { return Math.sqrt((x*x) + (y*y)); }
	}

	
	public SpringForce(IDistances distances) {
		this.distances = distances;
		this.points = new Point2D[distances.getNumPoints()];
		this.velocities = new Vector2[distances.getNumPoints()];
		
		for (int i = 0; i < points.length; i++) {
			points[i] = new Point2D();
			points[i].x = Math.random();
			points[i].y = Math.random();
			
			velocities[i] = new Vector2(0.0, 0.0);
		}
	}
	
	private boolean isNan(double ... vals)
	{
		for (double d : vals)
		{
			if (Double.isNaN(d)) 
				return true;
		}
		return false;
	}
	
	void computeForce(int v, Vector2 result)
	{
		double sumx = 0;
		double sumy = 0;
		for (int u = 0; u < distances.getNumPoints(); u++)
		{
			// \sum_E k^1_{uv} ( d(p_u, p_v) - l_{uv} ) \frac{x_v - x_u}{d(p_u, p_v)}
			double d = distances.getDistance(u, v);
			if (Math.abs(0-d) < 0.0001) continue;
			
			double term1 = (points[v].x - points[u].x) / d;
			double term2 = K1 * (d - L);
			
			// \sum_V \frac{k^2_{uv}}{d(p_u,p_v)^2 \frac{x_v-x_u}{d(p_u, p_v)}
			double term3 = K2 / (d * d);
			sumx = (term2 + term3) * term1;
			
			double term4 = (points[v].y - points[u].y) / d;
			sumy = (term4 + term3) * term4;
			
			if (isNan(d, term1, term2, term3, sumx, term4, sumy))
				throw new RuntimeException("Error: nan");

		}
		
		result.x = sumx; 
		result.y = sumy;
	}

	
	/**
	 *  set up initial node velocities to (0,0)
 set up initial node positions randomly // make sure no 2 nodes are in exactly the same position
 loop
     total_kinetic_energy := 0 // running sum of total kinetic energy over all particles
     for each node
         net-force := (0, 0) // running sum of total force on this particular node
         
         for each other node
             net-force := net-force + Coulomb_repulsion( this_node, other_node )
         next node
         
         for each spring connected to this node
             net-force := net-force + Hooke_attraction( this_node, spring )
         next spring
         
         // without damping, it moves forever
         this_node.velocity := (this_node.velocity + timestep * net-force) * damping
         this_node.position := this_node.position + timestep * this_node.velocity
         total_kinetic_energy := total_kinetic_energy + this_node.mass * (this_node.velocity)^2
     next node
 until total_kinetic_energy is less than some small number  //the simulation has stopped moving
	 */

	public void run( )
	{
		Vector2 netforce = new Vector2(0.0, 0.0);
		
		int iteration = 0;
		double totalKE = 0.0;
		do {
			totalKE = 0.0;
			
			for (int v = 0; v < distances.getNumPoints(); v++)
			{
				if (v == 2824) 
					System.out.println("here comes trouble.");
				computeForce(v, netforce);
				computeVelocity(netforce, velocities[v]);
				computePosition(v);
				
				double n = velocities[v].norm( );
				if (Double.isNaN(n))
					throw new RuntimeException("n went to nan");
				totalKE += (n * n);
			}
			
			System.out.format("timestep: %d total KE: %f\n", iteration++, totalKE);
		} while (totalKE > EPSILON);
	}
	
	private void computePosition(int v)
	{
		points[v].x = (points[v].x + TIMESTEP * velocities[v].x);
		points[v].y = (points[v].y + TIMESTEP * velocities[v].y);
	}
	
	private void computeVelocity(Vector2 netforce, Vector2 velocity)
	{
		velocity.x = (velocity.x + TIMESTEP * netforce.x) * DAMPING;
		velocity.y = (velocity.x + TIMESTEP * netforce.x) * DAMPING;

	}
	
	public static void main(String args[]) throws IOException, ClassNotFoundException
	{
		IrisDistances dist = IrisDistances.readObject("distances.out");
		SpringForce F = new SpringForce(dist);
		F.run();
	}
}

