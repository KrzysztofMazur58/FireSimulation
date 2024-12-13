import java.util.ArrayList;
import java.util.List;

public class EmergencyEvent {
    private int eventId;
    private EventStrategy strategy;
    private double eventLat;
    private double eventLon;
    private List<Vehicle> assignedVehicles;

    public EmergencyEvent(int eventId, double eventLat, double eventLon, EventStrategy strategy) {
        this.eventId = eventId;
        this.eventLat = eventLat;
        this.eventLon = eventLon;
        this.strategy = strategy;
        this.assignedVehicles = new ArrayList<>();
    }

    public void addVehicle(Vehicle vehicle) {
        assignedVehicles.add(vehicle);
        vehicle.dispatch();
    }

    public void returnVehicles() {
        for (Vehicle vehicle : assignedVehicles) {
            vehicle.returnToStation();
        }
        assignedVehicles.clear();
    }

    public int getRequiredVehicles() {
        return strategy.getRequiredVehicles();
    }

    public List<Vehicle> getAssignedVehicles() {
        return assignedVehicles;
    }

    public int getEventId() {
        return eventId;
    }

    public double getEventLat() {
        return eventLat;
    }

    public double getEventLon() {
        return eventLon;
    }
}











