import java.util.Iterator;
import java.util.List;

public class VehicleIterator implements Iterator<Vehicle> {
    private final List<Vehicle> vehicles;
    private int currentIndex = 0;

    public VehicleIterator(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < vehicles.size();
    }

    @Override
    public Vehicle next() {
        return vehicles.get(currentIndex++);
    }
}

