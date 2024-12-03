package org.example;

import org.apache.commons.cli.*;

import java.util.List;

import static org.example.Reader.ReadFile;
import static org.example.Writer.WriteFile;

public class ConsoleApp {
    public static final String help = "-i <in-file> [-o <out-file>]";

    public static void main(String[] args) {
        Options cmdLineOptions = new Options();
        cmdLineOptions.addOption("a", "app-type", false, "Type of the app to launch");
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

        String inputFilename = cmdLine.getOptionValue("i");
        int[][] array = ReadFile(inputFilename); //===================================================

        List<Integer> progression = FindingProgression(array);
        System.out.println("Progression is: " + progression);

        if (cmdLine.hasOption("o"))
            WriteFile(cmdLine.getOptionValue("o"), progression);
        System.out.close();
    }
}