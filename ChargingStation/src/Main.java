
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.logging.*;

public class Main extends Logfile {

    public static void main(String[] args) {
//        setupLogger(systemLogger, "system");
//        setupLogger(chargingStationLogger, "charging_station");
//        setupLogger(energyManagementLogger, "energy_management");
//        setupLogger(energySourcelogger, "energySources");

        SystemFunctionalityStartedlog();

        Scanner scanner = new Scanner(System.in);

        int numStations = -1;
        int numLocations = -1;

        while (numStations < 1) {
            System.out.print("Enter the number of charging stations: ");
            try {
                numStations = scanner.nextInt();
                if (numStations < 1) {
                    throw new IllegalArgumentException("Exception: Must be a positive integer");
                }
            } catch (InputMismatchException e) {
                systemLogger.log(Level.SEVERE, "Exception: Must be an integer", e);
                scanner.next();
            } catch (IllegalArgumentException e) {
                systemLogger.log(Level.INFO, e.getMessage());
            }
        }

        while (numLocations < 1) {
            System.out.print("Enter the number of charging locations at each station: ");
            try {
                numLocations = scanner.nextInt();
                if (numLocations < 1) {
                    throw new IllegalArgumentException("Exception: Must be a positive integer");
                }
            } catch (InputMismatchException e) {
                System.out.println("Exception: Must be an integer");
                scanner.next();
            } catch (IllegalArgumentException e) {
                System.out.println(e);
                //throw e;
            }
        }

        Station[] chargingStations = new Station[numStations];


        for (int i = 0; i < numStations; i++) {
            int weatherChoice = -1;
            int stationNumber = i + 1; // Track the current station number

            while (weatherChoice < 0 || weatherChoice > 2) {
                System.out.print("Enter weather condition for station " + stationNumber + " (0 for SUNNY, 1 for WINDY, 2 for RAINY): ");
                try {
                    weatherChoice = scanner.nextInt();
                    if (weatherChoice < 0 || weatherChoice > 2) {
                        throw new IllegalArgumentException("Invalid weather condition");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Exception: Must be an integer");
                    scanner.next();
                } catch (IllegalArgumentException e) {
                    System.out.println(e);
                    //throw e;
                }//finally{
                //scanner.close();
                //}
            }
            simulateChargingStationFunctionality();
            try {
                if (weatherChoice == 0) {
                    System.out.println("Switching to Solarpanel energy source.");
                    chargingStations[i] = new Station(numLocations, new SolarPanel());
                } else if (weatherChoice == 1) {
                    System.out.println("Switching to WindTurbine energy source.");
                    chargingStations[i] = new Station(numLocations, new WindTurbine());
                } else if (weatherChoice == 2) {
                    System.out.println("Switching to electricity energy source.");
                    chargingStations[i] = new Station(numLocations, new Electricity());
                }

                if (chargingStations[i] != null) {
                    chargingStations[i].displayStationStatus(scanner, weatherChoice);
                }
            } catch (Exception e) {
                System.out.println("Exception occurred during station creation: " + e.getMessage());
                //throw e;
            }
        }

        int numUsers = -1;

        while (numUsers < 1) {
            System.out.print("Enter the number of users: ");
            try {
                numUsers = scanner.nextInt();
                if (numUsers < 1) {
                    throw new IllegalArgumentException("Exception: Must be a positive integer");
                }
            } catch (InputMismatchException e) {
                System.out.println("Exception: Must be an integer");
                scanner.next();
            } catch (IllegalArgumentException e) {
                System.out.println(e);
            }
        }

        User[] users = new User[numUsers];
        //setupLogger(energyManagementLogger, "energy_management.log");

        for (int i = 0; i < numUsers; i++) {
            //simulateEnergyManagementFunctionalityStartlog();
            System.out.print("Enter username for user " + (i + 1) + ": ");
            String username = scanner.next();
            int userTypeChoice = -1;
            User.UserType userType = null;

            while (userTypeChoice != 1 && userTypeChoice != 2) {
                System.out.print("Enter user type (1 for ADMINISTRATOR, 2 for EXTERNAL_USER) for user " + (i + 1) + ": ");
                try {
                    userTypeChoice = scanner.nextInt();
                    if (userTypeChoice == 1) {
                        userType = User.UserType.ADMINISTRATOR;
                    } else if (userTypeChoice == 2) {
                        userType = User.UserType.EXTERNAL_USER;
                    } else {
                        throw new IllegalArgumentException("Invalid choice. Enter 1 for ADMINISTRATOR or 2 for EXTERNAL_USER.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Exception: Must be an integer");
                    scanner.next();
                } catch (IllegalArgumentException e) {
                    System.out.println(e);
                }
            }

            users[i] = new User(username, userType);
        }

        for (User user : users) {
        	
        	if (user.getUserType() == User.UserType.EXTERNAL_USER) {
                boolean foundEmptyLocation = false;

                for (int i = 0; i < chargingStations.length; i++) {
                    for (int j = 0; j < chargingStations[i].getLocations().size(); j++) {
                        if (!chargingStations[i].getLocations().get(j).isOccupied()) {
                            foundEmptyLocation = true;
                            System.out.println("Station " + (i + 1) + ", Location " + (j + 1) + " is available for charging.");
                            energySourcelogger.info("Station " + (i + 1) + ", Location " + (j + 1) + " is available for charging.");
                            energySourceLoggerSimulation("Station " + (i + 1) + ", Location " + (j + 1) + " is available for charging.");
                            System.out.print("Book time slot for user " + user.getUsername() + " at this location? (Y/N): ");
                            String bookChoice = scanner.next().toLowerCase();

                            if (bookChoice.equals("y")) {
                                chargingStations[i].getLocations().get(j).occupy();
                                System.out.println("Charging location " + (j + 1) + " at station " + (i + 1) + " is booked for " + user.getUsername() + ".");
                            } else if (bookChoice.equals("n")) {
                                chargingStations[i].addToPriorityQueue(user);
                                System.out.println(user.getUsername() + " added to the priority queue of station " + (i + 1) + ".");
                            }
                            break;
                        }
                    }
                    if (foundEmptyLocation) {
                        break;
                    }
                }

                if (!foundEmptyLocation) {
                    System.out.println("No empty locations were found for username " + user.getUsername() + ".");

                    int stationChoice;
                    do {
                        System.out.println("Where do you want to book a time slot? (Enter station number from 1 to " + numStations + "): ");
                        while (!scanner.hasNextInt()) {
                            System.out.println("Invalid input. Enter a valid station number.");
                            scanner.next();
                        }
                        stationChoice = scanner.nextInt();
                    } while (stationChoice < 1 || stationChoice > numStations);

                    chargingStations[stationChoice - 1].addToPriorityQueue(user);
                    System.out.println(user.getUsername() + " has been added to the priority queue of station " + stationChoice + ".");
                }
            }
        	
            try {
                if (user.getUserType() == User.UserType.ADMINISTRATOR) {
                	
                    System.out.print("Administrator " + user.getUsername() + ", do you want to clear all locations? (yes or no): ");
                    String clearChoice = scanner.next().toLowerCase();

                    if (clearChoice.equals("yes")) {
                        for (Station station : chargingStations) {
                            station.clearAllLocations();
                            checkLogfile(systemLogger, scanner, user.getUsername());
                        }
                    } else if (clearChoice.equals("no")) {
                    	checkLogfile(systemLogger, scanner, user.getUsername());
                        throw new IllegalArgumentException("Invalid choice. Enter 'yes' or 'no'.");
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        
        int maxChargeLevel = 100;
        int minChargeLevelForWarning = 30;

        ReserveBattery reserveBattery = new ReserveBattery(maxChargeLevel, minChargeLevelForWarning);

        EnergySource solarPanel = new EnergySource("Solar Panel", reserveBattery, 5);
        EnergySource electricity = new EnergySource("Electricity", reserveBattery, 10);
        EnergySource windTurbine = new EnergySource("Wind Turbine", reserveBattery, 3);

        Thread solarThread = new Thread(solarPanel);
        Thread electricityThread = new Thread(electricity);
        Thread windThread = new Thread(windTurbine);

        solarThread.start();
        electricityThread.start();
        windThread.start();



        //cars arriving at the charging stations
        for (Station station : chargingStations) {
            station.CarsArriving();

        }
        SystemFunctionalityEndlog();
    }
    private static void checkLogfile(Logger logger, Scanner scanner, String username) {
        System.out.print("Administrator " + username + ", do you want to check the Logfile? (yes or no): ");
        String checkLogfileChoice = scanner.next().toLowerCase();
        
        scanner.nextLine();

        if (checkLogfileChoice.equals("yes")) {
            Logfile.displayLogfiles();
            System.out.print("Enter the Logfile date to view (format: yyyy-MMM-dd): ");
            String selectedDate = scanner.next();
            logger.info("Administrator " + username + " checked the Logfile for " + selectedDate + ".");
            Logfile.viewLogfile(selectedDate);
        }
    }
}
