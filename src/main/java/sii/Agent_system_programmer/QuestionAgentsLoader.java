package sii.Agent_system_programmer;
 
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import jade.core.Agent;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

@SuppressWarnings("serial")
public class QuestionAgentsLoader extends Agent {
    @Override
    protected void setup() {
        // QuestionAgent creation
        int lineCount = 0;
        int rand = (new Random()).nextInt()%100000;
        System.out.println(" Test " );
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("questions.txt"), "utf-8"))){
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                lineCount++;
                AgentController ac = parseAgent(currentLine, rand+lineCount);
                if (ac != null) {
                    ac.start();
                }
            }
        } catch (IOException ex) {
            System.out.println("Reading error in line " + lineCount);
        } catch (StaleProxyException ex) {
            Logger.getLogger(QuestionAgentsLoader.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Test 2" );
        }
    }
    
    private AgentController parseAgent(String s, int number) throws StaleProxyException {
        String[] splitted = s.split(";");
        String theme = splitted[0];
        String text = splitted[1];               
        int complexity = Integer.parseInt(splitted[2]);
        Object[] args = new Object[] {theme, text, complexity};
        return getContainerController().createNewAgent("Задача"+number, "sii.Agent_system_programmer.QuestionAgent", args);
    }
}
