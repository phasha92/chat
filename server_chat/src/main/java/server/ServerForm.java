package server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ServerForm {

    private JFrame frame;
    private JButton stopButton;

    public ServerForm(Runnable onStop) {
        // Создаем окно
        frame = new JFrame("Server Control");
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Запрещаем закрытие окна стандартным способом

        // Создаем кнопку "ЗАВЕРШИТЬ"
        stopButton = new JButton("ЗАВЕРШИТЬ");

        // Добавляем обработчик нажатия на кнопку
        stopButton.addActionListener(e -> {
            onStop.run(); // Вызываем переданный callback для завершения работы сервера
            frame.dispose(); // Закрываем окно
        });

        // Размещаем кнопку в центре окна
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.add(stopButton);

        frame.add(panel);
        frame.setVisible(true);

        // Обработчик закрытия окна через системное меню
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                stopButton.doClick(); // Имитируем нажатие кнопки "ЗАВЕРШИТЬ"
            }
        });
    }
}
