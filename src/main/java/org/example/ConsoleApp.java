package org.example;

import org.apache.commons.cli.*;
import org.example.progressions.Methods;
import java.util.List;

public class ConsoleApp {
    public static final String help = "-i <in-file> [-o <out-file>]";
    public Methods methods;

    public ConsoleApp(String[] args) {
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
        List<Integer> list = Filer.output;

        FindingProgression.process(list);
        List<Integer> progression = FindingProgression.integerList;
        System.out.println("Progression is: " + progression);

        if (cmdLine.hasOption("o")) {
            Filer.directory = cmdLine.getOptionValue("o");
            Filer.output = progression;
            methods.WriteFile();
        }
        System.out.close();
    }
}