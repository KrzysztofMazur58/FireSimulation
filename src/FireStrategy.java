public class FireStrategy implements EventStrategy {
    @Override
    public int getRequiredVehicles() {
        return 3;
    }

    @Override
    public String toString() {
        return "Pożar";
    }
}

