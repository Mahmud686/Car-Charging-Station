import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Logfile {

    public static final Logger systemLogger = Logger.getLogger("n");
    public static final Logger chargingStationLogger = Logger.getLogger("ChargingStationFunctionality");
    public static final Logger energyManagementLogger = Logger.getLogger("EnergyManagementFunctionality");


    public static void SystemFunctionalityStartedlog() {
        systemLogger.info("System of station changed started.");


    }
    public static void SystemFunctionalityEndlog() {
        systemLogger.info("System of station changed ended.");


    }


    public static void simulateChargingStationFunctionality() {
        chargingStationLogger.info("Charging station functionality simulation started.");

    }

    public static void  simulateEnergyManagementFunctionalityStartlog() {
        energyManagementLogger.info("Energy management functionality simulation started.");


    }
    public static void  simulateEnergyManagementFunctionalityEndlog() {
        energyManagementLogger.info("Energy management functionality simulation ended.");


    }

    public static void setupLogger(Logger logger, String logFileName) {
        try {
            // Create a log file handler
            FileHandler fileHandler = new FileHandler(logFileName);

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
