package com.example.warehouse.model;

public class AGV {
    private int x; // Current X-coordinate position
    private int y; // Current Y-coordinate position
    private final int capacity; // Maximum load capacity (weight or number of parcels)
    private double batteryLevel; // Battery level percentage (e.g., 0 to 100)
    private Parcel currentLoad; // Parcel currently being carried
    private Status status; // Current status of the AGV

    // Enum for the AGV's status
    public enum Status {
        IDLE, MOVING, LOADING, UNLOADING, RECHARGING
    }

    // Constructor
    public AGV(int capacity) {
        this.x = 0; // Starting X position, can be customized
        this.y = 0; // Starting Y position, can be customized
        this.capacity = capacity;
        this.batteryLevel = 100.0; // Start with a full battery
        this.status = Status.IDLE; // Initial status as IDLE
    }

    public void moveTo(int targetX, int targetY) {
        // Calculate the distance to the target position
        int distance = Math.abs(targetX - this.x) + Math.abs(targetY - this.y);
        this.status = Status.MOVING;
        // Check if the AGV has enough battery for the calculated distance
        if (hasSufficientBattery(distance)) {
            // Update position
            this.x = targetX;
            this.y = targetY;
    
            // Deduct battery based on the distance traveled
            this.batteryLevel -= calculateBatteryConsumption(distance);
    
            // Update status to reflect movement and then set it to IDLE after moving
            
            this.status = Status.IDLE;
        } else {
            System.out.println("Not enough battery for the move.");
        }
    }
    
    /**
     * Calculates the battery consumption based on the current status and distance.
     *
     * @param distance The distance to be traveled (if applicable).
     * @return The battery consumption for the current action.
     */
    private double calculateBatteryConsumption(int distance) {
        double consumptionRate;

        // Use the current status to determine battery consumption rate
        switch (this.status) {
            case MOVING:
                // Define battery consumption rate per unit distance for moving
                consumptionRate = 1.0; // Adjust this value as needed
                return distance * consumptionRate;

            case LOADING:
                // Define a fixed battery consumption rate for loading (picking up)
                consumptionRate = 5.0; // Adjust this value as needed
                return consumptionRate;

            case UNLOADING:
                // Define a fixed battery consumption rate for unloading
                consumptionRate = 3.0; // Adjust this value as needed
                return consumptionRate;

            case RECHARGING:
                // No battery consumption while recharging
                return 0.0;

            default:
                // No battery consumption in IDLE or unknown statuses
                return 0.0;
        }
    }

    /**
     * Picks up a parcel if within the AGV's capacity.
     * Sets the AGV's status to LOADING, updates the current load, and validates the load capacity.
     *
     * @param parcel The parcel to be picked up.
     */
    public void pickUpParcel(Parcel parcel) {
        this.status = Status.LOADING;
        // Check if the AGV is already carrying a parcel
        if (this.currentLoad != null) {
            System.out.println("AGV is already carrying a parcel. Drop off current parcel before picking up a new one.");
            return;
        }
    
        // Check if the parcel's weight exceeds the AGV's capacity
        if (parcel.getWeight() > this.capacity) {
            System.out.println("Parcel is too heavy for this AGV to carry.");
            return;
        }
    
        // Check if the AGV has enough battery to pick up the parcel
        if (!hasSufficientBattery(0)) { // Assume picking up requires a fixed battery amount
            System.out.println("Not enough battery to pick up the parcel.");
            return;
        }
        // Assign the parcel to the AGV’s current load
        this.currentLoad = parcel;
        // Deduct battery for the loading operation
        this.batteryLevel -= calculateBatteryConsumption(0); // Fixed consumption for loading
        // Set status back to IDLE after successfully picking up the parcel
        this.status = Status.IDLE;
        System.out.println("Parcel successfully picked up.");
    }

    /**
     * Drops off the currently carried parcel.
     * Sets the AGV's status to UNLOADING, and clears the current load.
     */
    public void dropOffParcel() {
        // TODO: Implement logic for dropping off the current load
    }

    /**
     * Simulates recharging the AGV’s battery to full.
     * Sets the AGV’s status to RECHARGING and replenishes the battery level.
     */
    public void recharge() {
        // TODO: Implement logic for recharging the battery
    }

    /**
     * Returns the current status of the AGV.
     * This could be used to determine if the AGV is IDLE, MOVING, LOADING, UNLOADING, or RECHARGING.
     *
     * @return The current status of the AGV.
     */
    public Status getStatus() {
        // TODO: Return the current status of the AGV
        return this.status;
    }

    /**
     * Gets the current battery level of the AGV.
     * This can be used to monitor the need for recharging.
     *
     * @return The current battery level as a percentage.
     */
    public double getBatteryLevel() {
        // TODO: Return the current battery level
        return this.batteryLevel;
    }

    /**
     * Gets the current position of the AGV.
     *
     * @return An array where index 0 is the X-coordinate and index 1 is the Y-coordinate.
     */
    public int[] getPosition() {
        // TODO: Return the current position as [x, y]
        int[] position = {this.x, this.y};
        return position;
    }

    /**
    * Checks if the AGV has enough battery to travel a given distance.
    *
    * @param distance The distance to the target position.
    * @return True if there is enough battery, false otherwise.
    */
    public boolean hasSufficientBattery(int distance) {
        double requiredBattery = calculateBatteryConsumption(distance);
        return this.batteryLevel >= requiredBattery;
    }
}
