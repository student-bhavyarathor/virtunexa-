
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Calendar {
    // Map to store events. Each date (LocalDate) may have a list of events.
    private static Map<LocalDate, List<String>> events = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n====== Calendar Program Menu ======");
            System.out.println("1. Display Month Calendar");
            System.out.println("2. Add Event/Reminder");
            System.out.println("3. View Events for a Date");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    displayMonthCalendar();
                    break;
                case "2":
                    addEvent();
                    break;
                case "3":
                    viewEvents();
                    break;
                case "4":
                    exit = true;
                    System.out.println("Exiting the Calendar Program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice! Please enter a number from 1 to 4.");
            }
        }
    }
    
    // Displays the calendar for a specific month and year.
    private static void displayMonthCalendar() {
        try {
            System.out.print("Enter year (e.g., 2025): ");
            int year = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter month (1-12): ");
            int month = Integer.parseInt(scanner.nextLine());
            
            YearMonth yearMonth = YearMonth.of(year, month);
            System.out.println("\nCalendar for " + yearMonth.getMonth() + " " + year);
            System.out.println("Su Mo Tu We Th Fr Sa");
            
            // Determine the day of week for the 1st day of the month
            LocalDate firstOfMonth = yearMonth.atDay(1);
            int value = firstOfMonth.getDayOfWeek().getValue(); 
            // Java DayOfWeek uses Monday = 1 to Sunday = 7. Adjust so that Sunday is 0.
            int startIndex = (value % 7);
            
            // Print leading spaces for the first week
            for (int i = 0; i < startIndex; i++) {
                System.out.print("   ");
            }
            
            // Print each day of the month
            for (int day = 1; day <= yearMonth.lengthOfMonth(); day++) {
                System.out.printf("%2d ", day);
                startIndex++;
                if (startIndex % 7 == 0) {
                    System.out.println();
                }
            }
            System.out.println();
        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
        }
    }
    
    // Prompts the user to add an event for a specific date.
    private static void addEvent() {
        try {
            System.out.print("Enter date for event (yyyy-MM-dd): ");
            String dateInput = scanner.nextLine();
            LocalDate date = LocalDate.parse(dateInput, DateTimeFormatter.ISO_LOCAL_DATE);
            
            System.out.print("Enter event description: ");
            String description = scanner.nextLine();
            
            events.putIfAbsent(date, new ArrayList<>());
            events.get(date).add(description);
            System.out.println("Event added for " + date + "!");
        } catch (Exception e) {
            System.out.println("Error processing input. Please ensure the date is in the correct format (yyyy-MM-dd).");
        }
    }
    
    // Displays all events stored for a specific date.
    private static void viewEvents() {
        try {
            System.out.print("Enter date to view events (yyyy-MM-dd): ");
            String dateInput = scanner.nextLine();
            LocalDate date = LocalDate.parse(dateInput, DateTimeFormatter.ISO_LOCAL_DATE);
            
            List<String> eventList = events.get(date);
            if (eventList == null || eventList.isEmpty()) {
                System.out.println("No events found for " + date + ".");
            } else {
                System.out.println("Events for " + date + ":");
                for (int i = 0; i < eventList.size(); i++) {
                    System.out.println((i+1) + ". " + eventList.get(i));
                }
            }
        } catch (Exception e) {
            System.out.println("Error processing input. Please ensure the date is in the correct format (yyyy-MM-dd).");
        }
    }
}