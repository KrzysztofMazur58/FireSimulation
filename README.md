# Fire Rescue Simulation Project

This project is a simulation of fire rescue operations. It models a scenario where fire stations respond to emergency events, dispatch vehicles, and handle various types of incidents. The system demonstrates multithreading, resource management, and event-driven programming in Java.

---

## Features

- **Event Simulation**:
  - Simulates random emergency events with types like local hazards (`MZ`) or fires (`PZ`).
  - Randomized event coordinates within a predefined geographical area.

- **Fire Station Management**:
  - Fire stations with predefined locations and vehicle fleets.
  - Vehicles can be dispatched to events and returned to their respective stations.

- **Multithreading**:
  - Handles multiple simultaneous events using a thread pool.
  - Ensures efficient processing of events without blocking.

- **Dynamic Resource Allocation**:
  - Allocates vehicles based on the event type and required resources.
  - Finds the closest fire stations and gathers vehicles from multiple stations if necessary.

---

## How It Works

1. **Fire Stations**:
   - Each fire station has a fleet of vehicles in either `FREE` or `BUSY` state.
   - Vehicles transition between states based on dispatches and returns.

2. **Event Generation**:
   - Random events are generated periodically.
   - Each event includes coordinates and a type (`MZ` or `PZ`), determining the required number of vehicles.

3. **Vehicle Dispatch**:
   - Stations are sorted by proximity to the event.
   - Vehicles are dispatched from one or multiple stations to meet event requirements.

4. **Event Processing**:
   - Simulates response time, rescue action duration, and vehicle return.
   - Handles false alarms with a small probability.

---

## Technologies Used

- **Programming Language**: Java
- **Concurrency**: `ExecutorService` for thread pooling
- **Data Structures**: Lists for managing vehicles and fire stations
- **Randomized Simulation**: Random coordinates and event types

---
