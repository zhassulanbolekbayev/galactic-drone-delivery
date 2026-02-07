package edu.narxoz.galactic;

import edu.narxoz.galactic.bodies.Planet;
import edu.narxoz.galactic.cargo.Cargo;
import edu.narxoz.galactic.dispatcher.Dispatcher;
import edu.narxoz.galactic.dispatcher.Result;
import edu.narxoz.galactic.drones.HeavyDrone;
import edu.narxoz.galactic.drones.LightDrone;
import edu.narxoz.galactic.task.DeliveryTask;

public class DemoApp {
    public static void main(String[] args) {
        Planet earth = new Planet("Earth", 0, 0, "N2/O2");
        Planet mars = new Planet("Mars", 100, 0, "CO2");

        Cargo cargo = new Cargo(12.0, "Metal parts");

        LightDrone light = new LightDrone("LD-1", 5.0);
        HeavyDrone heavy = new HeavyDrone("HD-1", 20.0);

        DeliveryTask task = new DeliveryTask(earth, mars, cargo);
        Dispatcher dispatcher = new Dispatcher();

        System.out.println("1) Fail to assign overweight cargo to LightDrone:");
        Result r1 = dispatcher.assignTask(task, light);
        System.out.println("   ok=" + r1.ok() + ", reason=" + r1.reason());

        System.out.println("\n2) Success with HeavyDrone:");
        Result r2 = dispatcher.assignTask(task, heavy);
        System.out.println("   ok=" + r2.ok() + ", reason=" + r2.reason());

        System.out.println("\n3) Estimated time:");
        System.out.println("   time=" + task.estimateTime() + " min");

        System.out.println("\n4) Completion result + final statuses:");
        Result r3 = dispatcher.completeTask(task);
        System.out.println("   ok=" + r3.ok() + ", reason=" + r3.reason());
        System.out.println("   drone status=" + heavy.getStatus() + ", task state=" + task.getState());
    }
}

