import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Station {
    private List<Location> locations;
    private SourceofEnergy sourceOfEnergy;
    private Queue<User> priorityQueue;
    private Map<User, Integer> timeslots;

    public Station(int numLocations, SourceofEnergy sourceOfEnergy) {
        this.sourceOfEnergy = sourceOfEnergy;
        this.locations = new ArrayList<>(numLocations);
        for (int i = 0; i < numLocations; i++) {
            locations.add(new Location());
        }
        this.priorityQueue = new LinkedList<>();
        this.timeslots = new HashMap<>();
    }

    public Map<User, Integer> getTimeslots() {
        return this.timeslots;
    }

    public boolean chargeCar() throws ChargingException {
        boolean charged = false;
        for (Location location : locations) {
            if (!location.isOccupied()) {
                location.occupy();
                double energyProvided = sourceOfEnergy.provideEnergy();
                System.out.println("Charging the car with " + energyProvided + " kWh of energy");
                location.release();
                charged = true;
                break;
            }
        }
        if (!charged) {
            throw new ChargingException("All places occupied, unable to charge");
        }
        return charged;
    }

    public void displayStationStatus(Scanner scanner, int stationNumber) throws ChargingException {
        for (int j = 0; j < locations.size(); j++) {
            int status = -1;
            while (status < 0 || status > 1) {
                System.out.print("Enter status for charging location " + (j + 1) + " at this station (1 for Occupied, 0 for Empty): ");
                try {
                    status = scanner.nextInt();
                    if (status < 0 || status > 1) {
                        System.out.println("Invalid input. Please enter 0 for Empty or 1 for Occupied.");
                        scanner.nextLine();
                    } else {
                        if (status == 1) {
                            locations.get(j).occupy();
                        }
                        String displayStatus = (status == 1) ? "Occupied" : "Empty";
                        System.out.println("Charging location " + (j + 1) + " at this station: " + displayStatus);
                    }
                } catch (InputMismatchException e) {
                    throw new ChargingException("Error while taking input for charging location status", e);
                } catch (Exception e) {
                    throw new ChargingException("Unknown error occurred while taking input for charging location status", e.getCause());
                }
            }
            scanner.nextLine();
        }
    }



    public void bookTimeslot(User user, int timeslot) {
        if (!timeslots.containsKey(user)) {
            timeslots.put(user, timeslot);
            System.out.println(user.getUsername() + " has booked timeslot " + timeslot);
        } else {
            System.out.println("Timeslot already booked by " + user.getUsername());
        }
    }

    public void addToPriorityQueue(User user) {
        priorityQueue.add(user);
        System.out.println(user.getUsername() + " has been added to the priority queue");
    }
    
    public void CarsArriving() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        
        executorService.scheduleAtFixedRate(() -> {
            if (!priorityQueue.isEmpty()) {
                User user = priorityQueue.peek();
                int startTime = timeslots.get(user);
                
                int currentTime = (int) TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis());
                int waitingTime = currentTime - startTime;
                
                if (waitingTime > 15) {
                    System.out.println("Car for user " + user.getUsername() + " has waited for " + waitingTime + " minutes and exceeded 15 minutes. It's leaving the queue.");
                    priorityQueue.poll();
                } else {
                    System.out.println("Car for user " + user.getUsername() + " has been waiting for " + waitingTime + " minutes.");
                }
            }
        }, 0, 1, TimeUnit.MINUTES);

        // Schedule a task to shutdown the executor service after 15 minutes
        executorService.schedule(() -> {
            executorService.shutdown();
            System.out.println("15 minutes countdown finished!");
            // Perform any action after the 15-minute countdown here
        }, 15, TimeUnit.MINUTES);
        
    }
    
    
 void manageAccess(User user) throws ChargingException {
        if (user.getUserType() == User.UserType.ADMINISTRATOR) {
            System.out.println("Administrator " + user.getUsername() + " has unrestricted access.");

        } else if (user.getUserType() == User.UserType.EXTERNAL_USER) {
            if (!priorityQueue.isEmpty() && priorityQueue.peek() == user) {
                System.out.println("User " + user.getUsername() + " has been granted access.");
                chargeCar();
                priorityQueue.poll();
            } else {
                throw new ChargingException("Access denied. User " + user.getUsername() + " is not at the front of the queue.");
            }
        }

    }

    public void clearAllLocations() {
        for (Location location : locations) {
            location.release();
        }
        System.out.println("All locations cleared.");
    }


    public List<Location> getLocations() {
        return this.locations;
    }
}
