public class FreeState implements VehicleState {
    @Override
    public void dispatch(Vehicle vehicle) {
        vehicle.setState(new BusyState());
    }

    @Override
    public void returnToStation(Vehicle vehicle) {
        System.out.println("Pojazd jest już wolny, nie można go zwrócić.");
    }

    @Override
    public String getStateName() {
        return "FREE";
    }
}

