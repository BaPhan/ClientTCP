package org.example.view;

import org.example.dto.CountryDTO;
import org.example.dto.WineDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AddWineFrame extends JFrame {
    private JTable wineTable;
    private DefaultTableModel tableModel;
    private JTextField idTextField;
    private JTextField nameTextField;
    private JTextField concentrationField;

    private JTextField yearField;


    private JTextField countryNameField;

    private JTextField searchTextField;

    public AddWineFrame() {
        // Thiết lập các thuộc tính của JFrame
        setTitle("Add Wine Frame");
        setSize(300, 200);
        //setLocationRelativeTo(ClientFrame.this); // Đặt vị trí của frame con

        // Tạo và thiết lập layout cho JFrame
        setLayout(new BorderLayout());

        // Tạo DefaultTableModel cho JTable
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Concentration");
        tableModel.addColumn("year");
        tableModel.addColumn("CountryName");

        // Tạo JTable và đặt model
        wineTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(wineTable);

        // Đặt tableScrollPane vào phía trên của BorderLayout
        add(tableScrollPane, BorderLayout.NORTH);

        // Tạo JPanel chứa các thành phần khác
        JPanel inputPanel = new JPanel(new FlowLayout());
        idTextField = new JTextField(10);
        nameTextField = new JTextField(15);
        concentrationField = new JTextField(15);
        yearField = new JTextField(15);
        countryNameField = new JTextField(15);
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
        inputPanel.add(new JLabel("Concentration: "));
        inputPanel.add(concentrationField);
        inputPanel.add(new JLabel("year: "));
        inputPanel.add(yearField);
        inputPanel.add(new JLabel("CountryName: "));
        inputPanel.add(countryNameField);
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
                WineDTO wineDTO =  ClientWine.search(name);
                addCountryToTable(wineDTO.getId(),wineDTO.getName(), wineDTO.getConcentration(),wineDTO.getYear(),wineDTO.getCountryName());
            }
        });

        // Add sự kiện cho "showData"
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xóa tất cả các hàng trong bảng
                tableModel.setRowCount(0);
                List<WineDTO> wineDTOS = ClientWine.getData();
                for (WineDTO wineDTO : wineDTOS){
                    Integer id = wineDTO.getId();
                    String name = wineDTO.getName();
                    String concentration = wineDTO.getConcentration();
                    Integer year = wineDTO.getYear();
                    String countryName = wineDTO.getCountryName();
                    addCountryToTable(id, name, concentration,year,countryName);
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
                String concentration = concentrationField.getText();
                Integer year = Integer.valueOf(yearField.getText());
                String countryName = countryNameField.getText();
                // xử lý add wine call sang bên server
                ClientWine.addWine(id,name,concentration,year,countryName);
                // Xử lý logic để thêm wine vào danh sách
            }
        });

        // Thêm sự kiện cho nút "Update"
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Thực hiện các hành động khi nút "Update" được nhấn
                updateSelectedWine();
            }
        });

        // Thêm sự kiện cho nút "Delete"
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Thực hiện các hành động khi nút "Delete" được nhấn
                deleteSelectedWine();
            }
        });

        // Thêm sự kiện khi chọn một dòng trong JTable
        wineTable.getSelectionModel().addListSelectionListener(event -> {
            // Lấy dòng được chọn
            int selectedRow = wineTable.getSelectedRow();
            if (selectedRow >= 0) {
                // Lấy dữ liệu từ model và cập nhật thông tin lên các ô tương ứng
                Object id = tableModel.getValueAt(selectedRow, 0);
                Object selectedName = tableModel.getValueAt(selectedRow, 1);
                Object selectedConcentration = tableModel.getValueAt(selectedRow, 2);
                Object selectedYear = tableModel.getValueAt(selectedRow, 3);
                Object selectedCountryName = tableModel.getValueAt(selectedRow, 4);
                nameTextField.setText(selectedName.toString());
                concentrationField.setText(selectedConcentration.toString());
                yearField.setText(selectedYear.toString());
                countryNameField.setText(selectedCountryName.toString());
            }
        });
    }

    // Hàm để thêm country vào JTable
    private void addCountryToTable(Integer id, String name, String concentration,Integer year, String countryName ) {
        // Thêm dữ liệu vào model của JTable
        tableModel.addRow(new Object[]{id, name, concentration,year,countryName});
        // Xóa nội dung của các ô nhập liệu
        nameTextField.setText("");
        concentrationField.setText("");
        yearField.setText("");
        countryNameField.setText("");
    }

    // Hàm để cập nhật country trong JTable
    private void updateSelectedWine() {
        // Lấy dòng được chọn
        int selectedRow = wineTable.getSelectedRow();
        if (selectedRow >= 0) {
            // Lấy ID và cập nhật dữ liệu trong model của JTable
            Object id = tableModel.getValueAt(selectedRow, 0);
            String name = nameTextField.getText();
            String concentration = concentrationField.getText();
            Integer year = Integer.valueOf(yearField.getText());
            String countryName = countryNameField.getText();
//            tableModel.setValueAt(updatedName, selectedRow, 1);
//            tableModel.setValueAt(updatedDescription, selectedRow, 2);
            ClientWine.updateWine((Integer) id,name,concentration,year,countryName);
        }
    }

    // Hàm để xóa country khỏi JTable
    private void deleteSelectedWine() {
        // Lấy dòng được chọn
        int selectedRow = wineTable.getSelectedRow();
        if (selectedRow >= 0) {
            // Xóa dòng khỏi model của JTable
            // Lấy ID và cập nhật dữ liệu trong model của JTable
            Object id = tableModel.getValueAt(selectedRow, 0);
            ClientWine.deleteWine((Integer) id);
            tableModel.removeRow(selectedRow);
            // Xóa nội dung của các ô nhập liệu
            nameTextField.setText("");
            concentrationField.setText("");
            yearField.setText("");
            countryNameField.setText("");
        }
    }
}
