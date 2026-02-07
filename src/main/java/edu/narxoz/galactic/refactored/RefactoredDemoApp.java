package edu.narxoz.galactic.refactored;

import edu.narxoz.galactic.bodies.Planet;
import edu.narxoz.galactic.cargo.Cargo;
import edu.narxoz.galactic.dispatcher.Dispatcher;
import edu.narxoz.galactic.dispatcher.Result;
import edu.narxoz.galactic.drones.Drone;
import edu.narxoz.galactic.task.DeliveryTask;

public class RefactoredDemoApp {
    public static void main(String[] args) {
        Planet earth = new Planet("Earth", 0, 0, "N2/O2");
        Planet mars  = new Planet("Mars", 100, 0, "CO2");

        Cargo cargo = new Cargo(12.0, "Metal parts");

        DroneFactory factory = new DroneFactory();
        Drone light = factory.createDrone(DroneType.LIGHT, "LD-1", 5.0);
        Drone heavy = factory.createDrone(DroneType.HEAVY, "HD-1", 20.0);

        DeliveryTask task = new DeliveryTask(earth, mars, cargo);
        Dispatcher dispatcher = new Dispatcher();

        Result r1 = dispatcher.assignTask(task, light);
        System.out.println("1) Assign to LightDrone: ok=" + r1.ok() + ", reason=" + r1.reason());

        Result r2 = dispatcher.assignTask(task, heavy);
        System.out.println("2) Assign to HeavyDrone: ok=" + r2.ok() + ", reason=" + r2.reason());

        System.out.println("3) Estimated time: " + task.estimateTime());

        Result r3 = dispatcher.completeTask(task);
        System.out.println("4) Complete: ok=" + r3.ok() + ", reason=" + r3.reason());
        System.out.println("Final drone status: " + heavy.getStatus());
        System.out.println("Final task state: " + task.getState());
    }
}
