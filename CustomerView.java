import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;



public class CustomerView {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        BufferedReader bfr = new BufferedReader(new FileReader("Tutors.txt"));
        String line = "";
        boolean found = false;
        do {
            String finalSelection = "";
            System.out.println("Select one of the options below:\n1. View list of tutors\n2. Search for a tutor");
            choice = scanner.nextInt();
            scanner.nextLine();
            int counter = 1;
            if (choice == 1) {
                line = bfr.readLine();
                while(line != null) {
                    System.out.println(counter + ". " + line);
                    counter++;
                    line = bfr.readLine();
                }
                System.out.println("Select from tutors found:");
                finalSelection = scanner.nextLine();
            } else if (choice == 2) {
                String searchAgain = "";
                do {
                    System.out.print("Search: ");
                    String name = scanner.next();
                    scanner.nextLine();
                    line = bfr.readLine();
                    while (line != null) {
                        if (line.contains(name)) {
                            System.out.println(counter + ". " + line);
                            counter++;
                            found = true;
                        }
                        line = bfr.readLine();
                    }
                    if (!found) {
                        System.out.println("User not found! Would you like to search again? (Yes/No)");
                        searchAgain = scanner.nextLine();
                    }
                } while (!found && searchAgain.equalsIgnoreCase("yes"));
                System.out.println("Select from tutors found:");

                //newView.finalName = scanner.nextLine();
                //System.out.println(newView.getFinalName());
                //find a way to get this name to saahil and abhi's part
                // if they create separate classes --> have a constructor with that class that i can feed the found name into
            } else {
                System.out.println("Invalid option! Please try again!");
            }
        } while (choice != 1 && choice != 2);
    }
    /** ask Sam to add the information of all the people who signed up onto two separate files
     * one for the customer and one for the seller
     * ask the customer if they would like to view a list of the stores/sellers
     * OR if they would like to search for their member
     * (clarify that it's okay if the store and the seller are the same thing)
     * list of sellers --> traverse through the seller file (print out everything) --> select the person --> traverse through a list and say user found! what would you like to do?
     * search for seller --> given a name --> traverse through a list and say user found! what would you like to do?
     * list of the buyers --> travers through the customer file (print out everything) --> select the person --> traverse through a list and say user found! what would you like to do?
     *
     */

   }
