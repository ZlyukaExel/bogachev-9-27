package org.example;

import org.apache.commons.cli.*;
import org.example.progressions.Methods;

import java.util.ArrayList;
import java.util.List;

public class ConsoleApp {
    public static final String help = "-i <in-file> [-o <out-file>]";
    public Methods methods;

    public ConsoleApp(String[] args) {
        this.methods = new Filer();
        Options cmdLineOptions = new Options();
        cmdLineOptions.addOption("i", "input-file", true, "Input file directory");
        cmdLineOptions.addOption("o", "output-file", true, "Output file directory");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmdLine = null;
        try {
            cmdLine = parser.parse(cmdLineOptions, args);
        } catch (Exception e) {
            new HelpFormatter().printHelp(help, cmdLineOptions);
            System.exit(1);
        }

        if (!cmdLine.hasOption("i")) {
            new HelpFormatter().printHelp(help, cmdLineOptions);
            System.exit(1);
        }

        Filer.directory = cmdLine.getOptionValue("i");
        methods.ReadFile();
        List<List<String>> list = Filer.content;
        List<Candy> candies = new ArrayList<>();
        for(List<String> strLst : list){
            String name = strLst.getFirst();
            double price = Double.parseDouble(strLst.getLast());
            candies.add(new Candy(name, price));
        }
        double money = Double.parseDouble(Filer.money);

        String result = Candies.Count(candies, money);

        if (cmdLine.hasOption("o")) {
            Filer.directory = cmdLine.getOptionValue("o");
            Filer.output = result;
            methods.WriteFile();
        }
        System.out.close();
    }
}