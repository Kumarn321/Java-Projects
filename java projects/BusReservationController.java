import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BusReservationController {
    private List<User> users = new ArrayList<>();
    private List<Bus> buses = new ArrayList<>();
    private User currentUser = null;

    public BusReservationController() {
        // Initialize some buses
        buses.add(new Bus(101, "Express Line", 40));
        buses.add(new Bus(102, "City Cruiser", 30));
        buses.add(new Bus(103, "Interstate Express", 50));
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        users.add(user);
        return "Registration successful!";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        for (User existingUser : users) {
            if (existingUser.getUsername().equals(user.getUsername()) &&
                existingUser.getPassword().equals(user.getPassword())) {
                currentUser = existingUser;
                return "Login successful!";
            }
        }
        return "Invalid credentials.";
    }

    @GetMapping("/buses")
    public List<Bus> getAvailableBuses() {
        return buses;
    }

    @PostMapping("/book/{busNumber}")
    public String bookBus(@PathVariable int busNumber) {
        if (currentUser == null) {
            return "You need to log in first.";
        }
        for (Bus bus : buses) {
            if (bus.getBusNumber() == busNumber && bus.isAvailable()) {
                bus.bookedSeats++;
                return "Booking successful!";
            }
        }
        return "Bus not available or invalid bus number.";
    }

    @PostMapping("/cancel/{busNumber}")
    public String cancelReservation(@PathVariable int busNumber) {
        if (currentUser == null) {
            return "You need to log in first.";
        }
        for (Bus bus : buses) {
            if (bus.getBusNumber() == busNumber && bus.getBookedSeats() > 0) {
                bus.bookedSeats--;
                return "Reservation cancelled successfully!";
            }
        }
        return "No reservation found for the given bus number.";
    }
}
