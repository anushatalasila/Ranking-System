import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
 
public class FileHandler {
   
    Map<String, Integer> totmatches = new HashMap<>();
    Map<String, Integer> totmarginOfVictory = new HashMap<>();
    Map<String, Integer> totmarginOfLoss = new HashMap<>();
    Map<String,Integer> listOfOpponents = new HashMap<String, Integer>();
    Map<String, Map<String,Integer>> teamAndOpponents = new HashMap<>();
    Map<String, Integer> maxmarginOfVictory = new HashMap<>();
    Map<String, Integer> maxmarginOfLoss = new HashMap<>();

    private static String fileName = "EdgeEPLData.csv";
    //private static String outputFileName = "RankingData.csv";
    //public List<String> write_data = new ArrayList<String>();

    //Delimiter used in CSV file
    private static final String COMMA_DELIMITER = ",";
 
    /**
     * Write csv file
     * @param fileName
     * @param write_data : a list of strings
     * Returns nothing
     */
    public void writeTextFile(String outputFileName, List<String> write_data)    {      
        try(BufferedWriter out = new BufferedWriter( new FileWriter(outputFileName, true))) {
            /**
             * write a line at a time 
             */
                out.write("OUTPUT STARTS HERE");
                out.write("\n");
                for (String line : write_data) {
                    out.write(line);
                    out.newLine();
                }        
        }
        catch (Exception e) {
            e.printStackTrace();
        }        
    }    
    
    /**
    * Read csv file
    * @param fileName
    * Returns dataArray which is a list of strings
    */    
    public List<RankingClass> readTextFile() {              
        List<RankingClass> dataArray = new ArrayList<RankingClass>();          
        try {
            String thisLine = null;
            FileReader fr;
            fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);           
            br.readLine();
            while ((thisLine = br.readLine()) != null) {    
                
                String[] tokens = thisLine.split(COMMA_DELIMITER);                
                               
                String hometeamName = tokens[0];
                String awayteamName = tokens[1];
                int homenoOfgoals = Integer.parseInt(tokens[2]);
                int awaynoOfgoals = Integer.parseInt(tokens[3]);
                String ftr = tokens[4];
                
                int goalDiff = homenoOfgoals-awaynoOfgoals;
                
                team hometeam = new team(hometeamName);   
                team awayteam = new team(awayteamName);
                /*
        		 * Store Goal Diff of each team with all the teams it has played
        		 */
//                if(!teamAndOpponents.containsKey(hometeam)) {
//                	teamAndOpponents.put(hometeamName, new HashMap<String, Integer>());
//                	teamAndOpponents.get(hometeamName).put(awayteamName, goalDiff);
//                	
//                }
//                else {
//                	teamAndOpponents.get(hometeamName).put(awayteamName, goalDiff);
//                	
//                }
                
                
                /*
                 * Add RankingClass object
                 */
                RankingClass obj = new RankingClass(hometeam, awayteam, ftr);           
                dataArray.add(obj);   
                
                /*
                 * Calculate total number of match for each team
                 */
                if (totmatches.size() == 0) {
                	totmatches.put(hometeamName, 1);
                	totmatches.put(awayteamName, 1);
                }
                else if (totmatches.containsKey(hometeamName) && totmatches.containsKey(awayteamName)) {
                	totmatches.put(hometeamName, totmatches.get(hometeamName) + 1);
                	totmatches.put(awayteamName, totmatches.get(awayteamName) + 1);              		
                	}
                else {
                	totmatches.put(hometeamName, 1);
                	totmatches.put(awayteamName, 1);
                }
                
                /*
                 * Calculate total Margin Of Victory for each team
                 */
                int hometeammarginofVictory = homenoOfgoals - awaynoOfgoals;
                int awayteammarginofVictory = awaynoOfgoals - homenoOfgoals;
                               
                if (totmarginOfVictory.size() == 0) {
                	totmarginOfVictory.put(hometeamName, hometeammarginofVictory);
                	totmarginOfVictory.put(awayteamName, awayteammarginofVictory);
                }
                else if (totmarginOfVictory.containsKey(hometeamName) && totmarginOfVictory.containsKey(awayteamName)) {
                	totmarginOfVictory.put(hometeamName, totmarginOfVictory.get(hometeamName) + hometeammarginofVictory);
                	totmarginOfVictory.put(awayteamName, totmarginOfVictory.get(awayteamName) + awayteammarginofVictory);              		
                	}
                else {
                	totmarginOfVictory.put(hometeamName, hometeammarginofVictory);
                	totmarginOfVictory.put(awayteamName, awayteammarginofVictory);
                }
                
                
                
                
                /*
                 * Calculate total Margin Of Loss for each team
                 */
                int hometeammarginofLoss = awaynoOfgoals - homenoOfgoals;
                int awayteammarginofLoss = homenoOfgoals - awaynoOfgoals;
                
                if (totmarginOfLoss.size() == 0) {
                	totmarginOfLoss.put(hometeamName, hometeammarginofLoss);
                	totmarginOfLoss.put(awayteamName, awayteammarginofLoss);
                }
                else if (totmarginOfLoss.containsKey(hometeamName) && totmarginOfLoss.containsKey(awayteamName)) {
                	totmarginOfLoss.put(hometeamName, totmarginOfLoss.get(hometeamName) + hometeammarginofLoss);
                	totmarginOfLoss.put(awayteamName, totmarginOfLoss.get(awayteamName) + awayteammarginofLoss);              		
                	}
                else {
                	totmarginOfLoss.put(hometeamName, hometeammarginofLoss);
                	totmarginOfLoss.put(awayteamName, awayteammarginofLoss);
                }
                            
                /*
                 * Calculate Maximum Margin Of Victory for each team
                 */
                if (maxmarginOfVictory.size() == 0) {
                	maxmarginOfVictory.put(hometeamName, hometeammarginofVictory);
                	maxmarginOfVictory.put(awayteamName, awayteammarginofVictory);
                }
                else if (maxmarginOfVictory.containsKey(hometeamName) && maxmarginOfVictory.containsKey(awayteamName)) {
                	maxmarginOfVictory.put(hometeamName, Math.max(maxmarginOfVictory.get(hometeamName), hometeammarginofVictory));
                	maxmarginOfVictory.put(awayteamName, Math.max(maxmarginOfVictory.get(awayteamName), awayteammarginofVictory));              		
                	}
                else {
                	maxmarginOfVictory.put(hometeamName, hometeammarginofVictory);
                	maxmarginOfVictory.put(awayteamName, awayteammarginofVictory);
                }
                
                /*
                 * Calculate Maximum Margin Of Loss for each team
                 */
                if (maxmarginOfLoss.size() == 0) {
                	maxmarginOfLoss.put(hometeamName, hometeammarginofLoss);
                	maxmarginOfLoss.put(awayteamName, awayteammarginofLoss);
                }
                else if (maxmarginOfLoss.containsKey(hometeamName) && maxmarginOfLoss.containsKey(awayteamName)) {
                	maxmarginOfLoss.put(hometeamName, Math.max(maxmarginOfLoss.get(hometeamName), hometeammarginofLoss));
                	maxmarginOfLoss.put(awayteamName, Math.max(maxmarginOfLoss.get(awayteamName), hometeammarginofLoss));              		
                	}
                else {
                	maxmarginOfLoss.put(hometeamName, hometeammarginofLoss);
                	maxmarginOfLoss.put(awayteamName, hometeammarginofLoss);
                }
                
            }            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return dataArray;

    }
        
}