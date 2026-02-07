package edu.narxoz.galactic.dispatcher;

import edu.narxoz.galactic.drones.Drone;
import edu.narxoz.galactic.drones.DroneStatus;
import edu.narxoz.galactic.task.DeliveryTask;
import edu.narxoz.galactic.task.TaskState;

import java.lang.reflect.Method;

public class Dispatcher {

    public Result assignTask(DeliveryTask task, Drone drone) {
        if (task == null || drone == null) {
            return fail("task or drone is null");
        }

        if (drone.getStatus() != DroneStatus.IDLE) {
            return fail("drone not IDLE");
        }

        if (task.getCargo() == null) {
            return fail("cargo is null");
        }

        if (task.getCargo().getWeightKg() > drone.getMaxPayloadKg()) {
            return fail("cargo overweight");
        }

        if (task.getState() != TaskState.CREATED) {
            return fail("task not CREATED");
        }

        // SUCCESS
        try {
            invokeTaskSetter(task, "setState", new Class[]{TaskState.class}, new Object[]{TaskState.ASSIGNED});
            invokeTaskSetter(task, "setAssignedDrone", new Class[]{Drone.class}, new Object[]{drone});
            invokeDroneSetter(drone, "setStatus", new Class[]{DroneStatus.class}, new Object[]{DroneStatus.IN_FLIGHT});
        } catch (Exception e) {
            return fail("internal error");
        }

        return ok();
    }

    public Result completeTask(DeliveryTask task) {
        if (task == null) {
            return fail("task is null");
        }

        if (task.getState() != TaskState.ASSIGNED) {
            return fail("task not ASSIGNED");
        }

        if (task.getAssignedDrone() == null) {
            return fail("assigned drone is null");
        }

        Drone drone = task.getAssignedDrone();

        if (drone.getStatus() != DroneStatus.IN_FLIGHT) {
            return fail("drone not IN_FLIGHT");
        }

        // SUCCESS
        try {
            invokeTaskSetter(task, "setState", new Class[]{TaskState.class}, new Object[]{TaskState.DONE});
            invokeDroneSetter(drone, "setStatus", new Class[]{DroneStatus.class}, new Object[]{DroneStatus.IDLE});
        } catch (Exception e) {
            return fail("internal error");
        }

        return ok();
    }

    private Result ok() {
        return new Result(true, "");
    }

    private Result fail(String reason) {
        return new Result(false, (reason == null || reason.isEmpty()) ? "failed" : reason);
    }

    private void invokeTaskSetter(DeliveryTask task, String methodName, Class<?>[] types, Object[] args) throws Exception {
        Method m = DeliveryTask.class.getDeclaredMethod(methodName, types);
        m.setAccessible(true);
        m.invoke(task, args);
    }

    private void invokeDroneSetter(Drone drone, String methodName, Class<?>[] types, Object[] args) throws Exception {
        Method m = Drone.class.getDeclaredMethod(methodName, types);
        m.setAccessible(true);
        m.invoke(drone, args);
    }
}

