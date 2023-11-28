package org.example.view;

import org.example.dto.CountryDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AddCountryFrame extends JFrame {
    private JTable countryTable;
    private DefaultTableModel tableModel;
    private JTextField idTextField;
    private JTextField nameTextField;
    private JTextField descriptionTextField;

    private JTextField searchTextField;
    public AddCountryFrame() {
        // Thiết lập các thuộc tính của JFrame
        setTitle("Add Country Frame");
        setSize(300, 200);
        //setLocationRelativeTo(ClientFrame.this); // Đặt vị trí của frame con

        // Tạo và thiết lập layout cho JFrame
        setLayout(new BorderLayout());

        // Tạo DefaultTableModel cho JTable
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Description");

        // Tạo JTable và đặt model
        countryTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(countryTable);

        // Đặt tableScrollPane vào phía trên của BorderLayout
        add(tableScrollPane, BorderLayout.NORTH);

        // Tạo JPanel chứa các thành phần khác
        JPanel inputPanel = new JPanel(new FlowLayout());
        idTextField = new JTextField(10);
        nameTextField = new JTextField(15);
        descriptionTextField = new JTextField(15);
        searchTextField = new JTextField(15);
        JButton searchButton = new JButton("Search");
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton showButton = new JButton("ShowData");

        inputPanel.add(new JLabel("id: "));
        inputPanel.add(idTextField);

        inputPanel.add(new JLabel("Name: "));
        inputPanel.add(nameTextField);
        inputPanel.add(new JLabel("Description: "));
        inputPanel.add(descriptionTextField);
        inputPanel.add(new JLabel("Search: "));
        inputPanel.add(searchTextField);

        inputPanel.add(searchButton);
        inputPanel.add(addButton);
        inputPanel.add(updateButton);
        inputPanel.add(deleteButton);
        inputPanel.add(showButton);

        // Đặt inputPanel vào phía giữa của BorderLayout
        add(inputPanel, BorderLayout.CENTER);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // searchCountry;
                tableModel.setRowCount(0);
                String name = searchTextField.getText();
                CountryDTO countryDTO =  Client.search(name);
                addCountryToTable(countryDTO.getId(),countryDTO.getName(), countryDTO.getDescription());
            }
        });

        // Add sự kiện cho "showData"
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xóa tất cả các hàng trong bảng
                tableModel.setRowCount(0);
                List<CountryDTO> countries = Client.getData();
                for (CountryDTO countryDTO : countries){
                    Integer id = countryDTO.getId();
                    String name = countryDTO.getName();
                    String description = countryDTO.getDescription();
                    addCountryToTable(id, name, description);
                }
            }
        });

        // Thêm sự kiện cho nút "Add"
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Thực hiện các hành động khi nút "Add" được nhấn
                Integer id = Integer.valueOf(idTextField.getText());
                String name = nameTextField.getText();
                String description = descriptionTextField.getText();
                // xử lý add Country call sang bên server
                Client.addCountry(id,name,description);
                // Xử lý logic để thêm country vào danh sách
            }
        });

        // Thêm sự kiện cho nút "Update"
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Thực hiện các hành động khi nút "Update" được nhấn
                updateSelectedCountry();
            }
        });

        // Thêm sự kiện cho nút "Delete"
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Thực hiện các hành động khi nút "Delete" được nhấn
                deleteSelectedCountry();
            }
        });

        // Thêm sự kiện khi chọn một dòng trong JTable
        countryTable.getSelectionModel().addListSelectionListener(event -> {
            // Lấy dòng được chọn
            int selectedRow = countryTable.getSelectedRow();
            if (selectedRow >= 0) {
                // Lấy dữ liệu từ model và cập nhật thông tin lên các ô tương ứng
                Object id = tableModel.getValueAt(selectedRow, 0);
                Object selectedName = tableModel.getValueAt(selectedRow, 1);
                Object selectedDescription = tableModel.getValueAt(selectedRow, 2);
                nameTextField.setText(selectedName.toString());
                descriptionTextField.setText(selectedDescription.toString());
            }
        });
    }

    // Hàm để thêm country vào JTable
    private void addCountryToTable(Integer id, String name, String description) {
        // Thêm dữ liệu vào model của JTable
        tableModel.addRow(new Object[]{id, name, description});
        // Xóa nội dung của các ô nhập liệu
        nameTextField.setText("");
        descriptionTextField.setText("");
    }

    // Hàm để cập nhật country trong JTable
    private void updateSelectedCountry() {
        // Lấy dòng được chọn
        int selectedRow = countryTable.getSelectedRow();
        if (selectedRow >= 0) {
            // Lấy ID và cập nhật dữ liệu trong model của JTable
            Object id = tableModel.getValueAt(selectedRow, 0);
            String updatedName = nameTextField.getText();
            String updatedDescription = descriptionTextField.getText();
//            tableModel.setValueAt(updatedName, selectedRow, 1);
//            tableModel.setValueAt(updatedDescription, selectedRow, 2);
            Client.updateCountry((Integer) id,updatedName,updatedDescription);
        }
    }

    // Hàm để xóa country khỏi JTable
    private void deleteSelectedCountry() {
        // Lấy dòng được chọn
        int selectedRow = countryTable.getSelectedRow();
        if (selectedRow >= 0) {
            // Xóa dòng khỏi model của JTable
            // Lấy ID và cập nhật dữ liệu trong model của JTable
            Object id = tableModel.getValueAt(selectedRow, 0);
            Client.deleteCountry((Integer) id);
            tableModel.removeRow(selectedRow);
            // Xóa nội dung của các ô nhập liệu
            nameTextField.setText("");
            descriptionTextField.setText("");
        }
    }

}

