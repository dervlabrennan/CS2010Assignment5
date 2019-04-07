import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

/*
 * 1. Justify the choice of the data structures used in CompetitionDijkstra and CompetitionFloydWarshall
 * 
 * 
 * 
 * 
 * 2. Explain theoretical differences in the performance of Dijkstra and Floyd-Warshall algorithms
 * in the given problem. Also explain how would their relative performance be affected by the density of 
 * the graph. Which would you choose in which set of circumstances and why?
 * 
 * 
 * 
 */
/**
 *  Test class for CompetitionDijkstra.java and CompetitionFloydWarshall.java
 *  
 *  @author Dervla Brennan
 *  @version HT 2019
 */

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

