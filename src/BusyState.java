public class BusyState implements VehicleState {
    @Override
    public void dispatch(Vehicle vehicle) {
        System.out.println("Pojazd jest już zajęty, nie można go wysłać ponownie.");
    }

    @Override
    public void returnToStation(Vehicle vehicle) {
        vehicle.setState(new FreeState());
    }

    @Override
    public String getStateName() {
        return "BUSY";
    }
}

