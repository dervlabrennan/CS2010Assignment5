import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
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
 *  This class implements the competition using Floyd-Warshall algorithm
 *  @author Dervla Brennan
 *  @version HT 2019
 */

public class CompetitionFloydWarshall {
	private int sA, sB, sC, N;
	boolean read;
	private double [][] distanceTable;
	private ArrayList <Street> streets;

    /**
     * @param filename: A filename containing the details of the city road network
     * @param sA, sB, sC: speeds for 3 contestants
     * @throws IOException 
     */
    CompetitionFloydWarshall (String filename, int sA, int sB, int sC) 
    {
    	this.sA = sA;
    	this.sB = sB;
    	this.sC = sC;
    	this.read = readFile(filename);
    }
    
    public boolean readFile(String filename) 
    {
    	if(filename != null)
    	{
    		try
    		{
    			BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
    			
    			this.N = Integer.parseInt(bufferedReader.readLine());
    			int edges = Integer.parseInt(bufferedReader.readLine());
    			this.distanceTable = new double[N][N];
    			this.streets = new ArrayList <Street>();
    			String line = bufferedReader.readLine();
    			while(line!=null)
    			{	
    				String[] split = line.trim().split("\\s+");
    				Street s = new Street(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Double.parseDouble(split[2]));
    				streets.add(s);
    				line = bufferedReader.readLine();
    			}
    			return true;
    		}
    		catch(FileNotFoundException e)
    		{
    			return false;
    		}
    		catch(IOException e)
    		{
    			return false;
    		}
    	}
    	return false;
    }


    /**
     * @return int: minimum minutes that will pass before the three contestants can meet
     */
    public int timeRequiredforCompetition()
    {
    	if(read)
    	{
    		runFloydWarshall();
        	//find longest distance
        	double longestDistance = 0;
        	for(double[]a : distanceTable)
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
    
    public void runFloydWarshall()
    {
    	for(int v = 0; v < this.N; v++)
    	{
    		for(int w = 0; w < this.N; w++)
    		{
    			this.distanceTable[v][w] = Double.POSITIVE_INFINITY;
    		}
    	}
    		
    	for(int i = 0; i < this.N; i++)
    	{
    		distanceTable[i][i] = 0.0;
    		for(Street s : streets)
    		{
    			distanceTable[s.v][s.w] = s.length;
    		}
    	}
    		
    	for(int k = 0; k < this.N; k++)
    	{
    		for(int i = 0; i < this.N; i++)
    		{
    			for(int j = 0; j < this.N; j++)
    			{
    				if(distanceTable[i][j] > distanceTable[i][k] + distanceTable[k][j])
    				{
    					distanceTable[i][j] = distanceTable[i][k] + distanceTable[k][j];
    				}
    			}
    		}			
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
    }

}
