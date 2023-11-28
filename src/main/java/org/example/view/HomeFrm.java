package org.example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.net.Socket;

public class HomeFrm extends JFrame {

    private JTextField portTextField;
    private JButton connectButton;
    private JButton addCountryButton;

    private JButton addWineButton;
    private JTextArea logTextArea;
    private Socket clientSocket;

    private ServerSocket myServer;

    public HomeFrm() {
        setTitle("Client Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Đưa frame vào giữa màn hình

        // Tạo và thiết lập layout cho JFrame
        setLayout(new BorderLayout());

        // Tạo các thành phần chuyển đổi giao diện
        JPanel inputPanel = new JPanel(new FlowLayout());
        portTextField = new JTextField(10);
        connectButton = new JButton("Connect to Server");
        addCountryButton = new JButton("Add Country");
        addWineButton = new JButton("Add Wine");
        inputPanel.add(new JLabel("Port: "));
        inputPanel.add(portTextField);
        inputPanel.add(connectButton);
        inputPanel.add(addCountryButton);
        inputPanel.add(addWineButton);

        // Đặt inputPanel vào phía trên của BorderLayout
        add(inputPanel, BorderLayout.NORTH);

        // Tạo JTextArea để hiển thị log
        logTextArea = new JTextArea();
        logTextArea.setEditable(false); // Không cho phép chỉnh sửa nội dung
        JScrollPane scrollPane = new JScrollPane(logTextArea);

        // Đặt scrollPane vào phía giữa của BorderLayout
        add(scrollPane, BorderLayout.CENTER);

        // Thêm sự kiện cho nút "Connect to Server"
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Thực hiện các hành động khi nút "Connect" được nhấn
                String portText = portTextField.getText();
                // Xử lý logic để kết nối đến server với cổng được nhập
                logTextArea.append("Connected to server on port " + portText + "\n");
                try {
                    clientSocket = new Socket("localhost", Integer.parseInt(portText));


                }catch (Exception ex){
                    logTextArea.append( ex + "\n");
                }
            }
        });

        // Thêm sự kiện cho nút "Add Country"
        addCountryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Tạo đối tượng mới của class AddCountryFrame
                AddCountryFrame addCountryFrame = new AddCountryFrame();
                // Hiển thị frame thêm country
                addCountryFrame.setVisible(true);
            }
        });
        // Thêm sự kiện cho nút "Add wine"
        addWineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Tạo đối tượng mới của class
                AddWineFrame addWineFrame = new AddWineFrame();
                // Hiển thị frame thêm wine
                addWineFrame.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HomeFrm().setVisible(true);
            }
        });
    }
}
