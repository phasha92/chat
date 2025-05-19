package client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginChat {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        JTextField nameField = new JTextField(20);
        JButton loginButton = new JButton("Enter");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = nameField.getText();
                if (!username.isEmpty()) {
                    frame.dispose(); // Закрываем окно входа
                    new ChatWindow(username); // Открываем окно чата
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a username.");
                }
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("Username:"));
        panel.add(nameField);
        panel.add(loginButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        frame.add(panel);
        frame.setVisible(true);
    }
}
