import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {

        int number = 2;

        List<FireStation> fireStations = new ArrayList<>();
        fireStations.add(new FireStation("JRG-1", 50.05993813373955, 19.94313880500473, number));
        fireStations.add(new FireStation("JRG-2", 50.03336467350585, 19.93580543498749, number));
        fireStations.add(new FireStation("JRG-3", 50.07556384286229, 19.88760084869267, number));
        fireStations.add(new FireStation("JRG-4", 50.03759590118408, 20.00572533876222, number));
        fireStations.add(new FireStation("JRG-5", 50.091683218237705, 19.919975069388315, number));
        fireStations.add(new FireStation("JRG-6", 50.015994643023355, 20.015821414386977, number));
        fireStations.add(new FireStation("JRG-7", 50.09408567221154, 19.977390566957112, number));
        fireStations.add(new FireStation("JRG Szkoły Aspirantów PSP", 50.077178466595974, 20.032890815678485, number));
        fireStations.add(new FireStation("JRG Skawina", 49.96831591820049, 19.799420555226455, number));
        fireStations.add(new FireStation("LSP Lotnisko w Balicach", 50.073067858041775, 19.78597191374253, number));

        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 7; i++) {
            final int eventId = i + 1;
            executor.submit(() -> processNewEvent(fireStations, eventId));

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
    }

    private static EventStrategy generateEventType() {
        Random random = new Random();
        double probability = random.nextDouble();

        if (probability < 0.7) {
            return new LocalThreatStrategy();
        } else {
            return new FireStrategy();
        }
    }

    private static void processNewEvent(List<FireStation> fireStations, int eventId) {
        System.out.printf("\nZgłoszenie #%d: Rozpoczyna przetwarzanie.\n", eventId);

        double[] eventCoordinates = generateRandomCoordinates();
        EventStrategy eventType = generateEventType();
        System.out.printf("Zgłoszenie #%d: Typ zdarzenia: %s na współrzędnych: %.6f, %.6f\n",
                eventId, eventType, eventCoordinates[0], eventCoordinates[1]);

        EventStrategy strategy;
        if (eventType instanceof FireStrategy) {
            strategy = new FireStrategy();
        } else {
            strategy = new LocalThreatStrategy();
        }

        EmergencyEvent event = new EmergencyEvent(eventId, eventCoordinates[0], eventCoordinates[1], strategy);

        int requiredVehicles = event.getRequiredVehicles();

        boolean dispatched = dispatchFromMultipleStations(fireStations, eventCoordinates[0], eventCoordinates[1], requiredVehicles, event);

        if (dispatched) {

            synchronized (fireStations) {
                for (FireStation station : fireStations) {
                    station.printVehicleStates();
                }
            }

            simulateDriveToEvent();

            if (new Random().nextDouble() < 0.05) {
                System.out.printf("\nZgłoszenie #%d: Alarm fałszywy. Pojazdy wracają do jednostki.\n", eventId);

                simulateDriveToEvent();
                event.returnVehicles();
                System.out.printf("Zgłoszenie #%d: Pojazdy wróciły do jednostek.\n", eventId);
            } else {

                long actionDuration = simulateRescueAction();

                System.out.printf("\nZgłoszenie #%d: Akcja ratunkowa zakończona. Czas trwania: %d sekund.\n", eventId, actionDuration);

                simulateDriveToEvent();
                event.returnVehicles();
                System.out.printf("\nZgłoszenie #%d: Pojazdy wróciły do jednostek.\n", eventId);
            }

            synchronized (fireStations) {
                for (FireStation station : fireStations) {
                    station.printVehicleStates();
                }
            }
        } else {
            System.out.printf("Zgłoszenie #%d: Nie udało się wysłać wystarczającej liczby pojazdów.\n", eventId);
        }

        System.out.printf("Zgłoszenie #%d: Zakończono przetwarzanie.\n", eventId);
    }

    private static boolean dispatchFromMultipleStations(List<FireStation> fireStations, double eventLat, double eventLon, int requiredVehicles, EmergencyEvent event) {
        int remainingVehicles = requiredVehicles;

        fireStations.sort((a, b) -> {
            double distanceA = calculateDistance(eventLat, eventLon, a.getLatitude(), a.getLongitude());
            double distanceB = calculateDistance(eventLat, eventLon, b.getLatitude(), b.getLongitude());
            return Double.compare(distanceA, distanceB);
        });

        for (FireStation station : fireStations) {
            synchronized (station) {
                List<Vehicle> freeVehicles = station.getFreeVehicles(remainingVehicles);
                VehicleIterator iterator = new VehicleIterator(freeVehicles);
                int dispatched = 0;

                while (iterator.hasNext() && remainingVehicles > 0) {
                    Vehicle vehicle = iterator.next();
                    event.addVehicle(vehicle);
                    dispatched++;
                    remainingVehicles--;
                }

                System.out.printf("Jednostka %s wysłała %d pojazdy do zdarzenia #%d. Pozostało do wysłania: %d\n",
                        station.getName(), dispatched, event.getEventId(), remainingVehicles);

                if (remainingVehicles > 0) {
                    station.notifyObservers("Brak dostępnych pojazdów w jednostce " + station.getName() + ", wracajcie szybko");
                }

                if (remainingVehicles <= 0) {
                    return true;
                }
            }
        }

        System.out.printf("Nie udało się wysłać wystarczającej liczby pojazdów do zdarzenia #%d (%d pozostało).\n", event.getEventId(), remainingVehicles);
        return false;
    }

    private static double[] generateRandomCoordinates() {
        Random random = new Random();
        double latMin = 49.95855025648944;
        double latMax = 50.154564013341734;
        double lonMin = 19.688292482742394;
        double lonMax = 20.02470275868903;

        double latitude = latMin + (latMax - latMin) * random.nextDouble();
        double longitude = lonMin + (lonMax - lonMin) * random.nextDouble();
        return new double[]{latitude, longitude};
    }

    private static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double deltaLat = lat2 - lat1;
        double deltaLon = lon2 - lon1;
        return Math.sqrt(deltaLat * deltaLat + deltaLon * deltaLon);
    }

    private static void simulateDriveToEvent() {
        try {
            Thread.sleep(new Random().nextInt(3000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static long simulateRescueAction() {
        long startTime = System.currentTimeMillis();
        try {
            Thread.sleep(new Random().nextInt(20000) + 5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        return (endTime - startTime) / 1000;
    }


}




