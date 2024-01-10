package task5;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Pattern;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.*;
import java.util.logging.*;

public class Logfile {


	public static final Logger systemLogger = Logger.getLogger("SystemLogger");
    public static final Logger chargingStationLogger = Logger.getLogger("ChargingStationFunctionality");
    public static final Logger energyManagementLogger = Logger.getLogger("EnergyManagementFunctionality");
    public static final Logger energySourcelogger = Logger.getLogger("EnergySourceFunctionality");

    public static final String systemLoggerStr = "SystemLogger";
    public static final String chargingStationLoggerStr = "ChargingStationFunctionality";
    public static final String energyManagementLoggerStr = "EnergyManagementFunctionality";
    public static final String energySourceloggerStr = "EnergySourceFunctionality";
    
    
    public static void SystemFunctionalityStartedlog() {
        systemLogger.info("System of station changed started.");
        
        writeFile("System of station changed started.", systemLoggerStr);

    }
    
    
    public static void SystemFunctionalityEndlog() {
        systemLogger.info("System of station changed ended.");

        writeFile("System of station changed ended.", systemLoggerStr);
    }


	public static void simulateChargingStationFunctionality() {
        chargingStationLogger.info("Charging station functionality simulation started.");
        writeFile("Charging station functionality simulation started.", chargingStationLoggerStr);
    }

    //public static void  simulateEnergyManagementFunctionalityStartlog() {
        //energyManagementLogger.info("Energy management functionality simulation started.");


    //}
    public static void  simulateEnergyManagementFunctionalityEndlog() {
        energyManagementLogger.info("Energy management functionality simulation ended.");
        
        writeFile("Energy management functionality simulation ended.", energyManagementLoggerStr);

    }

    //energySourcelogger.info("system has started with energy");
    public static void  SourceofEnergylogsimulation() {

        energySourcelogger.info("Three energy sources are there.");

        writeFile("Three energy sources are there.", energySourceloggerStr);
    }

    public static void  energyManagementLoggersimulation(String content) {
    	
    	writeFile(content, energyManagementLoggerStr);
    }
    
    public static void  energySourceLoggerSimulation(String content) {
        
    	writeFile(content, energySourceloggerStr);

    }
    
    public static void displayLogfiles() {
        File folder = new File(".");
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".log"));

        System.out.println("Available Logfiles:");
        for (File file : files) {
            System.out.println(file.getName());
        }
    }
    
    public static void viewLogfile(String selectedDate) {
        try {
            File folder = new File(".");
            File[] files = folder.listFiles((dir, name) ->
                    name.matches("^.*-" + Pattern.quote(selectedDate) + "\\.txt$"));

            if (files != null && files.length > 0) {
                System.out.println("Logfiles for " + selectedDate + ":");

                for (File file : files) {
                    System.out.println("File: " + file.getName());
                    displayFileContent(file);
                }
            } else {
                System.out.println("No logfiles found for " + selectedDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void displayFileContent(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println(); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void writeFile(String content, String fileName) {
        
		try {
			
            LocalDateTime currentDateTime = LocalDateTime.now();
            
			DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
			String formattedDate = currentDateTime.format(dateformatter);
			
            File file = new File(fileName + "-" + formattedDate + ".txt");

            if (!file.exists()) {
                file.createNewFile();
            }
            

            // Define a custom date and time format (optional)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Format the current date and time using the specified format
            String formattedDateTime = currentDateTime.format(formatter);
            
            // Open the file for writing (true flag for append mode)
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            	writer.write(formattedDateTime + "    ");
                writer.write(content);
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setupLogger(Logger logger, String logFileName) {
        try {
        	
        	File logFile = new File(logFileName + ".log");
            boolean append = logFile.exists();
            
            
            FileHandler fileHandler = new FileHandler(logFileName + ".log", append);


            // Set the log format
            fileHandler.setFormatter(new SimpleFormatter());

            // Add the handler to the logger
            logger.addHandler(fileHandler);

            // Set the logging level (e.g., INFO, WARNING, SEVERE)
            logger.setLevel(Level.INFO);

        } catch (IOException e) {
            logger.log(Level.INFO, "Error setting up logger.", e);
        }
    }
    
}
