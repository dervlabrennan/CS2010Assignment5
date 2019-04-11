import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/*
 * 1. Justify the choice of the data structures used in CompetitionDijkstra and CompetitionFloydWarshall
 * 
 * In order to successfully implement CompetitionDijkstra the structures I used included:
 * 	- CityMap (represents an EdgeWeightedDigraph): 
 * 				class to represent the map of the city, including all vertices (intersections) and edges (streets)
 * 				has a HashSet of Street representing the streets that lead to and from each intersection
 *  - Street (represents a DirectedWeightedEdge):
 *  			class to represent each street in the city, has from, to and length member variables which describe
 *  			the intersection (vertex) the street goes from and to and the length (weight) describes the length of
 *  			the street in km
 *  - PriorityQueue: the intersection with the shortest length from the starting intersection will get the highest
 *  			 priority. 
 * 
 * In order to successfully implement CompetitionFloydWarshall the structures I used included:
 *  - double[][] distanceTable: represents the matrix of shortest distances to each vertex 
 *  - ArrayList <Street> streets: used to iterate through all the streets in the city
 * 
 * 
 * 
 * 2. Explain theoretical differences in the performance of Dijkstra and Floyd-Warshall algorithms
 * in the given problem. Also explain how would their relative performance be affected by the density of 
 * the graph. Which would you choose in which set of circumstances and why?
 * 
 * For this problem, all-pairs shortest paths had to be found. 
 * In order to find this using Dijkstra's algorithm, it had to be run for each vertex, giving an asymptotic runtime 
 * of O(V^3logV). Array implementation of the PQ is more optimal for dense graphs, whereas binary heap is much faster
 * for sparse graphs. 
 * Using Floyd-Warshall, the shortest paths of all pairs can be found with asymptotic runtime of O(V^3). In a dense 
 * graph where edges ~V^2: time complexity in terms of edges would be O(E^3/2) and in a sparse graph where edges ~V:
 * time complexity in terms of edges would be O(E^3)
 * 
 */
/**
 *  Test class for CompetitionDijkstra.java and CompetitionFloydWarshall.java
 *  
 *  @author Dervla Brennan
 *  @version HT 2019
 */

@RunWith(JUnit4.class)
public class CompetitionTests 
{
    @Test
    public void testDijkstraConstructor() 
    {
    	CompetitionDijkstra map = new CompetitionDijkstra("src/tinyEWD.txt", 55, 65, 75);
        int time = map.timeRequiredforCompetition();
        assertEquals("tinyEWD.txt test", 34, time);
        
        CompetitionDijkstra map2 = new CompetitionDijkstra("src/1000EWD.txt", 55, 65, 75);
        int time2 = map2.timeRequiredforCompetition();
        assertEquals("1000EWD.txt test", 26, time2);
        
        CompetitionDijkstra map3 = new CompetitionDijkstra("src/tinyEWD.txt", -55, 65, 75);
        int time3 = map3.timeRequiredforCompetition();
        assertEquals("Negative speed declined", -1, time3);
        
        CompetitionDijkstra map4 = new CompetitionDijkstra("wenewij", -55, 65, 75);
        int time4 = map4.timeRequiredforCompetition();
        assertEquals("Invalid file name", -1, time4);
        
        CompetitionDijkstra map5 = new CompetitionDijkstra(null, -55, 65, 75);
        int time5 = map5.timeRequiredforCompetition();
        assertEquals("Invalid file name", -1, time5);
        
    }

    @Test
    public void testFWConstructor()
    {
    	CompetitionFloydWarshall map = new CompetitionFloydWarshall("src/tinyEWD.txt", 55, 65, 75);
        int time = map.timeRequiredforCompetition();
        assertEquals("tinyEWD.txt test", 34, time); 
        
        CompetitionFloydWarshall map2 = new CompetitionFloydWarshall("src/1000EWD.txt", 55, 65, 75);
        int time2 = map2.timeRequiredforCompetition();
        assertEquals("1000EWD.txt test", 26, time2);
        
        CompetitionFloydWarshall map3 = new CompetitionFloydWarshall("src/tinyEWD.txt", -55, 65, 75);
        int time3 = map3.timeRequiredforCompetition();
        assertEquals("Negative speed declined", -1, time3);
        
        CompetitionFloydWarshall map4 = new CompetitionFloydWarshall("wenewij", -55, 65, 75);
        int time4 = map4.timeRequiredforCompetition();
        assertEquals("Invalid file name", -1, time4);
        
        CompetitionFloydWarshall map5 = new CompetitionFloydWarshall(null, -55, 65, 75);
        int time5 = map5.timeRequiredforCompetition();
        assertEquals("Invalid file name", -1, time5);
    }
}

