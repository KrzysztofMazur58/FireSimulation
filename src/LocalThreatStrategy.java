public class LocalThreatStrategy implements EventStrategy {
    @Override
    public int getRequiredVehicles() {
        return 2;
    }

    @Override
    public String toString() {
        return "Miejscowe zagrożenie";
    }
}

