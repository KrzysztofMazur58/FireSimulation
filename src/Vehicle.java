public class Vehicle implements Observer {
    private VehicleState state;
    private int vehicleId;

    public Vehicle(FireStation station) {
        this.state = new FreeState();
        this.vehicleId = station.getNextVehicleId();
    }

    @Override
    public void update(String message) {
        System.out.println("Pojazd " + vehicleId + " otrzymał powiadomienie: " + message);
    }

    public void setState(VehicleState state) {
        this.state = state;
    }

    public VehicleState getState() {
        return state;
    }

    public String getStateName() {
        return state.getStateName();
    }

    public void dispatch() {
        state.dispatch(this);
    }

    public void returnToStation() {
        state.returnToStation(this);
    }

    public int getVehicleId() {
        return vehicleId;
    }
}



