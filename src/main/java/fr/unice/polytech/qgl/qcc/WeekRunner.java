package fr.unice.polytech.qgl.qcc;

import eu.ace_design.island.runner.Runner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

import static eu.ace_design.island.runner.Runner.run;


public class WeekRunner {

    private int budget;
    private int crew;
    private String[] contract;
    private String week;
    private Long seed;
    private int x,y;
    private String startingAt;

    public WeekRunner(String[] contract, int budget, int crew, String week, Long seed, int x, int y, String startingAt){
        this.contract = contract;
        this.budget = budget;
        this.crew = crew;
        this.week = week;
        this.seed = seed;
        this.x = x;
        this.y = y;
        this.startingAt = startingAt;
    }

    public void run(){

        try {


            String dirName = "D:\\Users\\Kevin\\Documents\\Cours\\Polytech SI3\\Polytech S5\\QGL\\outputs\\" + week;
            File dir = new File(dirName);
            dir.mkdirs();
            File file = new File("D:\\Users\\Kevin\\Documents\\Cours\\Polytech SI3\\Polytech S5\\QGL\\outputs\\" + week + "\\result.txt");

            PrintStream printStream = new PrintStream(file);
            System.setOut(printStream);


            Runner runner = Runner.run(Explorer.class)
                    .exploring(new File("D:\\Users\\Kevin\\Documents\\Cours\\Polytech SI3\\Polytech S5\\QGL\\maps\\"+week+".json"))
                    .withSeed(seed)
                    .startingAt(x, y, startingAt)
                    .backBefore(budget)
                    .withCrew(crew);
            for(int i = 0; i < contract.length;){
                runner.collecting(Integer.parseInt(contract[i++]), contract[i++]);
            }

            runner
                .storingInto("D:\\Users\\Kevin\\Documents\\Cours\\Polytech SI3\\Polytech S5\\QGL\\outputs\\"+week)
                .fire();

            printStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
