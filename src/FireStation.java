import java.util.ArrayList;
import java.util.List;

public class FireStation implements Subject {
    private String name;
    private double latitude;
    private double longitude;
    private List<Vehicle> vehicles;
    private int vehicleCounter;
    private boolean notificationSent = false;

    public FireStation(String name, double latitude, double longitude, int vehicleCount) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.vehicles = new ArrayList<>();
        this.vehicleCounter = 0;
        for (int i = 0; i < vehicleCount; i++) {
            Vehicle vehicle = new Vehicle(this);
            vehicles.add(vehicle);
        }
    }

    @Override
    public void notifyObservers(String message) {

        if (!notificationSent) {

            for (Vehicle vehicle : vehicles) {
                vehicle.update(message);
            }
            notificationSent = true;
        }
    }

    public List<Vehicle> getFreeVehicles(int count) {
        List<Vehicle> freeVehicles = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getStateName().equals("FREE") && freeVehicles.size() < count) {
                freeVehicles.add(vehicle);
            }
        }
        return freeVehicles;
    }

    public int getNextVehicleId() {
        return ++vehicleCounter;
    }

    public void printVehicleStates() {
        System.out.println("Stacja " + name + " - stan pojazdów:");
        for (Vehicle vehicle : vehicles) {
            System.out.println("Pojazd " + vehicle.getVehicleId() + " - Stan: " + vehicle.getStateName());
        }
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}

















