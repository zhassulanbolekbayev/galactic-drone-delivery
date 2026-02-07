package edu.narxoz.galactic.refactored;

import edu.narxoz.galactic.drones.Drone;
import edu.narxoz.galactic.drones.HeavyDrone;
import edu.narxoz.galactic.drones.LightDrone;

public class DroneFactory {

    public Drone createDrone(DroneType type, String id, double maxPayloadKg) {
    if (type == null) {
        throw new IllegalArgumentException("DroneType is null");
    }

    switch (type) {
        case LIGHT:
            return new LightDrone(id, maxPayloadKg);
        case HEAVY:
            return new HeavyDrone(id, maxPayloadKg);
        default:
            throw new IllegalArgumentException("Unknown DroneType: " + type);
    }
}

}
