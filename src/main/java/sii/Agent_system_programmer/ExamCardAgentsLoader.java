package sii.Agent_system_programmer;

import jade.core.Agent;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings({ "serial", "unused" })
public class ExamCardAgentsLoader extends Agent {
    @Override
    protected void setup() { 
    	int countOfExamCardAgents = 0;
        try(FileWriter writer = new FileWriter("results.txt", false))
        { 
            writer.append(""); 
            writer.flush();
        }
        catch(IOException ex){
             
            System.out.println(ex.getMessage());
        }  
        
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("cards.txt"), "utf-8"))) {
            String currentLine; 
            if ((currentLine = reader.readLine()) != null) 
            {
                countOfExamCardAgents = Integer.parseInt(currentLine.trim());
            }
        } catch (UnsupportedEncodingException ex)
        {
            Logger.getLogger(ExamCardAgentsLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(ExamCardAgentsLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Programmer creation
        int rand = (new Random()).nextInt()%1000000;
        
        for (int i = 1; i <= countOfExamCardAgents; ++i)
        {
            try
            {
                AgentController ac = getContainerController().createNewAgent("Программист " + (rand+i), "sii.Agent_system_programmer.ExamCardAgent", null);
                ac.start();

            } catch (StaleProxyException ex)
            {
                Logger.getLogger(ExamCardAgentsLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //Manager creation
        try
        {
            AgentController ac = getContainerController().createNewAgent("Менеджер ", "sii.Agent_system_programmer.Manager", null);
            ac.start();
        } catch (StaleProxyException ex)
        {
            Logger.getLogger(ExamCardAgentsLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
