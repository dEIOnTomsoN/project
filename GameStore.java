import java.io.*;
import java.util.Scanner;

class Game {
    static Scanner input = new Scanner(System.in);

    String gameName;
    double price;
    char forp;
    double disc;

    void insert() {
        try (BufferedWriter store = new BufferedWriter(new FileWriter("GameStore.txt", true))) {
            System.out.println("\nEnter Game Name: ");
            gameName = input.next();

            System.out.println("Enter Price: ");
            price = input.nextDouble();

            System.out.println("Enter 'F' for Free or 'P' for Paid: ");
            forp = Character.toUpperCase(input.next().charAt(0));

            System.out.println("Enter Discount Percentage: ");
            disc= input.nextDouble();

            store.write(gameName + "," + price + "," + forp + "," + disc);
            store.newLine();

            System.out.println("Game inserted successfully!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    void delete(String gdelete) {
        try {
            try (BufferedReader rd = new BufferedReader(new FileReader("GameStore.txt"));
                 BufferedWriter wr = new BufferedWriter(new FileWriter("temp.txt"))) {

                String line;

                while ((line = rd.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (!parts[0].equalsIgnoreCase(gdelete)) {
                        wr.write(line);
                        wr.newLine();
                    }
                }
            }

            File file = new File("GameStore.txt");
            File temp = new File("temp.txt");

            if (file.exists()) {
                file.delete();
            }

            temp.renameTo(new File("GameStore.txt"));

            System.out.println("Game deleted successfully!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    void showData(String view) {
        try (BufferedReader reader = new BufferedReader(new FileReader("GameStore.txt"))) {
            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equalsIgnoreCase(view)) {
                    display(parts);
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Game not found!");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    void show() {
        try (BufferedReader reader = new BufferedReader(new FileReader("GameStore.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                display(parts);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void display(String[] parts) {
        System.out.println("Game Name: " + parts[0]);
        System.out.println("Price: Rs." + parts[1]);
        System.out.println("Free or Paid: " + (parts[2].charAt(0) == 'F' ? "Free" : "Paid"));
        System.out.println("Discount Percentage: " + parts[3] + "%");
        double discountedPrice = Double.parseDouble(parts[1]) - (Double.parseDouble(parts[1]) * Double.parseDouble(parts[3]) / 100);
        System.out.println("Price after Discount: Rs." + discountedPrice);
        System.out.println();
    }

    void update(String gameToUpdate, double newDiscount) {
        try (BufferedReader reader = new BufferedReader(new FileReader("GameStore.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("temp.txt"))) {

            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equalsIgnoreCase(gameToUpdate)) {
                    parts[3] = String.valueOf(newDiscount);
                    line = String.join(",", parts);
                }
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        File file = new File("GameStore.txt");
        File tempFile = new File("temp.txt");

        if (file.exists()) {
            file.delete();
        }

        tempFile.renameTo(new File("GameStore.txt"));

        System.out.println("Discount updated successfully!");
    }
}

public class GameStore extends Game {
    public static void main(String args[]) {
        try {
            Game game = new Game();

            while (true) {
                System.out.println("\nEnter your choice: ");
                System.out.println("1: Insert a new game.");
                System.out.println("2: Delete a game.");
                System.out.println("3: View a game's details.");
                System.out.println("4: View all games.");
                System.out.println("5: Update discount percentage.");
                System.out.println("6: Exit.\n");

                int choice = input.nextInt();

                switch (choice) {
                    case 1:
                        game.insert();
                        break;
                    case 2:
                        System.out.println("Enter the game name to delete: ");
                        String gdelete = input.next();
                        game.delete(gdelete);
                        break;
                    case 3:
                        System.out.println("Enter the game name to view: ");
                        String view = input.next();
                        game.showData(view);
                        break;
                    case 4:
                        game.show();
                        break;
                    case 5:
                        System.out.println("Enter the game name to update discount: ");
                        String update = input.next();
                        System.out.println("Enter the new discount percentage: ");
                        double newDiscount = input.nextDouble();
                        game.update(update, newDiscount);
                        break;
                    case 6:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid Entry!!");
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
