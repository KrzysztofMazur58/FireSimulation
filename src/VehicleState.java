public interface VehicleState {
    void dispatch(Vehicle vehicle);
    void returnToStation(Vehicle vehicle);
    String getStateName();
}

