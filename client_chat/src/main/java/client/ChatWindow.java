package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ChatWindow {
    private JFrame frame;
    private JTextArea chatArea;
    private JTextField inputField;
    private ChatClient client;

    public ChatWindow(String username) {
        try {
            client = new ChatClient(username, "localhost", 8080);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to connect to server.");
            return;
        }

        frame = new JFrame("Chat - " + username);
        chatArea = new JTextArea(10, 30);
        chatArea.setEditable(false);
        inputField = new JTextField(20);
        JButton sendButton = new JButton("Send");

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = inputField.getText();
                if (!message.isEmpty()) {
                    client.sendMessage(message);
                    inputField.setText("");
                }
            }
        });

        JPanel panel = new JPanel();
        panel.add(inputField);
        panel.add(sendButton);

        frame.add(new JScrollPane(chatArea), BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Поток для получения сообщений
        new Thread(() -> {
            try {
                while (true) {
                    String message = client.receiveMessage();
                    if (message == null) break;
                    chatArea.append(message + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
