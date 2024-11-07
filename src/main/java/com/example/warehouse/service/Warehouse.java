import java.util.ArrayList;
import java.util.List;

public class Warehouse {
    private StorageLocation[][] layout;
    private List<Parcel> inventory;
    private List<AGV> robots;

    public Warehouse(int rows, int cols) {
        layout = new StorageLocation[rows][cols];
        inventory = new ArrayList<>();
        robots = new ArrayList<>();
        // Initialize storage locations
    }

    public void addParcel(Parcel item, int row, int col) {
        layout[row][col].addParcel(item);
        inventory.add(item);
    }

    public void removeParcel(Parcel item) {
        // Remove from inventory and storage location
    }

    public void addAGV(AGV robot) {
        robots.add(robot);
    }

    public void processOrder(AGV order) {
        // Assign robots to fulfill order based on item locations
    }
}