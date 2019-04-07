import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Scanner;

/*
 * A Contest to Meet (ACM) is a reality TV contest that sets three contestants at three random
 * city intersections. In order to win, the three contestants need all to meet at any intersection
 * of the city as fast as possible.
 * It should be clear that the contestants may arrive at the intersections at different times, in
 * which case, the first to arrive can wait until the others arrive.
 * From an estimated walking speed for each one of the three contestants, ACM wants to determine the
 * minimum time that a live TV broadcast should last to cover their journey regardless of the contestants’
 * initial positions and the intersection they finally meet. You are hired to help ACM answer this question.
 * You may assume the following:
 *     Each contestant walks at a given estimated speed.
 *     The city is a collection of intersections in which some pairs are connected by one-way
 * streets that the contestants can use to traverse the city.
 */

/**
 *  This class implements the competition using Dijkstra's algorithm
 *  
 *  @author Dervla Brennan
 *  @version HT 2019
 */
public class CompetitionDijkstra {

    private int sA, sB, sC;
    private CityMap map;
    
	/**
     * @param filename: A filename containing the details of the city road network
     * @param sA, sB, sC: speeds for 3 contestants
    */
	CompetitionDijkstra (String filename, int sA, int sB, int sC)
    {
    	this.sA = sA;
    	this.sB = sB;
    	this.sC = sC;
    	if(filename != null)
    	{
    		File file = new File(filename);
    		if(new In(file).scanner != null)
    		{
    			CityMap map = new CityMap(new In(file));
        		this.map = map;
    		}
    	}
    	else { this.map = null; }	
    }


    /**
    * @return int: minimum minutes that will pass before the three contestants can meet
     */
    public int timeRequiredforCompetition()
    {
    	if(map != null)
    	{
    		//run Dijkstra's algorithm
        	double distances[][] = new double [map.V][map.V];
        	for(int i = 0; i < map.V; i++)
        	{
        		distances[i] = runDijkstra(i);
        	}  	
        	//find longest distance
        	double longestDistance = 0;
        	for(double[] a : distances)
        	{
        		for(double distance : a)
        		{
        			if(longestDistance < distance)
        			{
        				longestDistance = distance; //longest distance in metres
        			}
        		}
        	}
        	
        	//find slowest speed
        	if(this.sA >= 50 && this.sA <= 100 && this.sB >= 50 && this.sB <= 100 && this.sC >= 50 && this.sC <= 100)
        	{
        		int [] speeds = {this.sA, this.sB, this.sC};
        		Arrays.sort(speeds);
            	int slowestSpeed = speeds[0]; // slowest speed in metres per min
            	return (int) Math.ceil((longestDistance*1000)/slowestSpeed); //returns rounded up worst-case time in minutes
        	}
    	}
    	return -1;
    }
    
    public double[] runDijkstra(int intersection)
    {
    	double [] distTo = new double[map.V];
    	for(int i = 0; i < map.V; i++)
    	{
    		distTo[i] = Double.POSITIVE_INFINITY; //set each intersection distance from vertex to infinity
    	}
    	distTo[intersection] = 0.0;
    	Comparator <Street> comparator = new StreetComparator();
    	PriorityQueue <Street> routes = new PriorityQueue <Street> (map.V, comparator);
    	routes.add(new Street(intersection, distTo[intersection]));
    	while(!routes.isEmpty())
    	{
    		Street street = routes.poll();
    		for(Street s : map.adj[street.v])
    		{
    			int v = s.v;
    			int w = s.w;
    			if(distTo[w] > distTo[v] + s.length)
    			{
    				distTo[w] = distTo[v] + s.length;
    				if(!alternate(routes, w))
    				{
    					routes.add(new Street(w, distTo[w]));
    				}
    				else
    				{
    					routes = replace(routes, w, distTo[w]);
    				}
    			}
    		}
    	}
    	return distTo;
    }
    
    private boolean alternate(PriorityQueue<Street> routes, int w) 
    {
        for (Street s : routes)
        {
            if (s.v == w)
            {
            	return true;
            }         
        }
        return false;
    }
    
    private PriorityQueue<Street> replace(PriorityQueue<Street> routes, int w, double newWeight) 
    {
        for (Street s : routes) 
        {
            if (s.v == w) 
            {
                routes.remove(s);
                s.length = newWeight;
                routes.add(s);
                break;
            }
        }
        return routes;
    }

    public final class In
    {
    	private Scanner scanner;
    	public In(File file) 
    	{
            if(file != null)
            {
            	try {
                    FileInputStream fis = new FileInputStream(file);
                    scanner = new Scanner(new BufferedInputStream(fis));
                }
                catch (IOException ioe) {
                    scanner = null;
                	//throw new IllegalArgumentException("Could not open " + file, ioe);
                }
            }	
        }
        public int readInt() {
            try {
                return scanner.nextInt();
            }
            catch (InputMismatchException e) {
                String token = scanner.next();
                throw new InputMismatchException("Expecting 'int' value but the next token is '" + token + "'");
            }
            catch (NoSuchElementException e) {
                throw new NoSuchElementException("Expecting 'int' value but no more tokens available");
            }
        }
        public double readDouble() {
            try {
                return scanner.nextDouble();
            }
            catch (InputMismatchException e) {
                String token = scanner.next();
                throw new InputMismatchException("Expecting 'double' value but the next token is '" + token + "'");
            }
            catch (NoSuchElementException e) {
                throw new NoSuchElementException("Expecting 'double' value but no more tokens are available");
            }
        }
    }
    
    public class CityMap 
    {
        public int V;
        public int E;
        public HashSet<Street>[] adj;

        CityMap(int V) {
            this.V = V;
            adj = (HashSet<Street>[]) new HashSet[V];
            for (int v = 0; v < V; v++)
            {
                adj[v] = new HashSet<Street>();
            }
        }
        public CityMap(In in)
		{
			this(in.readInt());
	        int E = in.readInt();
	        for (int i = 0; i < E; i++)
	        {
	            int v = in.readInt();
	            int w = in.readInt();
	            double weight = in.readDouble();
	            addStreet(v, w, weight);
	        }
		}
        void addStreet(int v, int w, double weight){
            try {
                validateIntersection(v);
                validateIntersection(w);
                Street e = new Street(v, w, weight);
                this.adj[v].add(e);
                this.E++;
            }
            catch(IllegalArgumentException ignore) {}
        }
        private void validateIntersection(int v) 
        {
            if (v < 0 || v >= V)
                throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
        }
    }
    
    public class Street 
    {
        public int v;
        public int w;
        public double length;

        public Street(int v, int w, double length){
            this.v = v;
            this.w = w;
            this.length = length;
        }
        public Street(int v, double length)
        {
        	this.v = v;
        	this.length = length;
        }
    }
  
    class StreetComparator implements Comparator <Street>
    {
    	@Override
    	public int compare(Street street, Street alternativeStreet) 
    	{
    		return Double.compare(street.length, alternativeStreet.length);
    	}
    }
}
