import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

class Game implements Serializable {
    String gameName;
    double price;
    char forp;
    double disc;

    Game(String gameName, double price, char forp, double disc) {
        this.gameName = gameName;
        this.price = price;
        this.forp = forp;
        this.disc = disc;
    }

    void display() {
        
    }
}

public class GameStoreGUI extends JFrame {
    private final ArrayList<Game> games;

    public GameStoreGUI() {
        games = new ArrayList<>();
        setTitle("Game Store");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        createMainMenu();
    }

    private void createMainMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton insertButton = new JButton("Insert Game");
        JButton viewButton = new JButton("View a Game");
        JButton viewAllButton = new JButton("View All Games");
        JButton updateButton = new JButton("Update Discount");
        JButton exitButton = new JButton("Exit");

        insertButton.addActionListener(e -> showInsertWindow());
        viewButton.addActionListener(e -> showViewWindow());
        viewAllButton.addActionListener(e -> showViewAllWindow());
        updateButton.addActionListener(e -> showUpdateWindow());
        exitButton.addActionListener(e -> System.exit(0));

        panel.add(insertButton);
        panel.add(viewButton);
        panel.add(viewAllButton);
        panel.add(updateButton);
        panel.add(exitButton);

        add(panel);
    }

    private void showInsertWindow() {
        JFrame insertFrame = new JFrame("Insert Game");
        insertFrame.setSize(300, 200);
        insertFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2));

        JTextField gameNameField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField forpField = new JTextField();
        JTextField discField = new JTextField();

        panel.add(new JLabel("Game Name:"));
        panel.add(gameNameField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Free or Paid (F/P):"));
        panel.add(forpField);
        panel.add(new JLabel("Discount Percentage:"));
        panel.add(discField);

        JButton insertButton = new JButton("Insert");
        insertButton.addActionListener(e -> {
            try {
                String gameName = gameNameField.getText();
                double price = Double.parseDouble(priceField.getText());
                char forp = forpField.getText().toUpperCase().charAt(0);
                double disc = Double.parseDouble(discField.getText());

                Game newGame = new Game(gameName, price, forp, disc);
                games.add(newGame);

                JOptionPane.showMessageDialog(insertFrame, "Game inserted successfully!");
                insertFrame.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(insertFrame, "Invalid input. Please enter valid values.");
            }
        });

        panel.add(insertButton);

        insertFrame.add(panel);
        insertFrame.setVisible(true);
    }

    private void showViewWindow() {
        JFrame viewFrame = new JFrame("View a Game");
        viewFrame.setSize(300, 100);
        viewFrame.setLocationRelativeTo(null);

        String gameName = JOptionPane.showInputDialog(viewFrame, "Enter the Game Name:");

        if (gameName != null) {
            for (Game game : games) {
                if (game.gameName.equalsIgnoreCase(gameName)) {
                    game.display();
                    viewFrame.dispose();
                    return;
                }
            }

            JOptionPane.showMessageDialog(viewFrame, "Game not found!");
        }

        viewFrame.dispose();
    }

    private void showViewAllWindow() {
        JFrame viewAllFrame = new JFrame("View All Games");
        viewAllFrame.setSize(400, 300);
        viewAllFrame.setLocationRelativeTo(null);

        JTextArea outputArea = new JTextArea();

        for (Game game : games) {
            outputArea.append("Game Name: " + game.gameName + "\n");
            outputArea.append("Price: Rs." + game.price + "\n");
            outputArea.append("Free or Paid: " + (game.forp == 'F' ? "Free" : "Paid") + "\n");
            outputArea.append("Discount Percentage: " + game.disc + "%\n");
            double discountedPrice = game.price - (game.price * game.disc / 100);
            outputArea.append("Price after Discount: Rs." + discountedPrice + "\n\n");
        }

        JScrollPane scrollPane = new JScrollPane(outputArea);
        viewAllFrame.add(scrollPane);

        viewAllFrame.setVisible(true);
    }

    private void showUpdateWindow() {
        JFrame updateFrame = new JFrame("Update Discount");
        updateFrame.setSize(300, 150);
        updateFrame.setLocationRelativeTo(null);

        String gameName = JOptionPane.showInputDialog(updateFrame, "Enter the Game Name to update:");
        if (gameName != null) {
            String newDiscountStr = JOptionPane.showInputDialog(updateFrame, "Enter the new Discount Percentage:");
            if (newDiscountStr != null) {
                try {
                    double newDiscount = Double.parseDouble(newDiscountStr);

                    for (Game game : games) {
                        if (game.gameName.equalsIgnoreCase(gameName)) {
                            game.disc = newDiscount;

                            JOptionPane.showMessageDialog(updateFrame, "Discount updated successfully!");
                            updateFrame.dispose();
                            return;
                        }
                    }

                    JOptionPane.showMessageDialog(updateFrame, "Game not found!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(updateFrame, "Invalid input. Please enter a valid discount.");
                }
            }
        }

        updateFrame.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameStoreGUI().setVisible(true));
    }
}
