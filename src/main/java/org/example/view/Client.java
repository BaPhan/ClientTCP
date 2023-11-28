package org.example.view;

import org.example.dto.CountryDTO;
import org.example.dto.CountryAddDTO;
import org.example.dto.Method;
import org.example.dto.ServerResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 8888;


    public static void addCountry(Integer id, String name, String description) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

            // Gửi yêu cầu thêm wine dùng đến server
            System.out.println("Before reading object from client");
            CountryAddDTO countryAddDTO = new CountryAddDTO(id, name, description);
            Method method = new Method();
            method.setMethod("Add");
            method.setData(countryAddDTO);
            out.writeObject(method);
            System.out.println("After reading object from client");

            // Nhận phản hồi từ server
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Object response = in.readObject();

            if (response instanceof ServerResponse) {
                ServerResponse serverResponse = (ServerResponse) response;
                if (serverResponse.isSuccess()) {
                    System.out.println("Country added successfully!");

                } else {
                    System.out.println("Failed to add user. Reason: " + serverResponse.getErrorMessage());
                }
            } else {
                System.out.println("Unexpected response from server.");
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static List<CountryDTO> getData() {
        List<CountryDTO> countries = new ArrayList<>();
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
            // gửi yêu cầu
            Method method = new Method();
            method.setMethod("GetAll");
            out.writeObject(method);
            System.out.println("log: gui success ! ");

            // nhận yêu cầu
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Object response = in.readObject();

            if (response instanceof ServerResponse) {
                ServerResponse serverResponse = (ServerResponse) response;
                if (serverResponse.isSuccess()) {
                    countries = (List<CountryDTO>) serverResponse.getData();
                    System.out.println("get All Country success !");

                } else {
                    System.out.println("Failed to . Reason: " + serverResponse.getErrorMessage());
                }
            } else {
                System.out.println("Unexpected response from server.");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return countries;
    }

    public static void updateCountry(Integer id, String name, String description) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

            // Gửi yêu cầu update wine dùng đến server
            System.out.println("Before reading object from client");
            CountryDTO countryDTO = new CountryDTO(id, name, description);
            Method method = new Method();
            method.setMethod("Update");
            method.setData(countryDTO);
            out.writeObject(method);
            System.out.println("After reading object from client");

            // Nhận phản hồi từ server
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Object response = in.readObject();

            if (response instanceof ServerResponse) {
                ServerResponse serverResponse = (ServerResponse) response;
                if (serverResponse.isSuccess()) {
                    System.out.println("Country updated successfully!");

                } else {
                    System.out.println("Failed to updated user. Reason: " + serverResponse.getErrorMessage());
                }
            } else {
                System.out.println("Unexpected response from server.");
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void deleteCountry(Integer id) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

            // Gửi yêu cầu update wine dùng đến server
            System.out.println("Before reading object from client");
            Method method = new Method();
            method.setMethod("Delete");
            method.setData(id);
            out.writeObject(method);
            System.out.println("After reading object from client");

            // Nhận phản hồi từ server
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Object response = in.readObject();

            if (response instanceof ServerResponse) {
                ServerResponse serverResponse = (ServerResponse) response;
                if (serverResponse.isSuccess()) {
                    System.out.println("Country delete successfully!");

                } else {
                    System.out.println("Failed to delete user. Reason: " + serverResponse.getErrorMessage());
                }
            } else {
                System.out.println("Unexpected response from server.");
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static CountryDTO search(String name) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

            // Gửi yêu cầu update wine dùng đến server
            System.out.println("Before reading object from client");
            Method method = new Method();
            method.setMethod("Search");
            method.setData(name);
            out.writeObject(method);
            System.out.println("After reading object from client");

            // Nhận phản hồi từ server
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Object response = in.readObject();

            if (response instanceof ServerResponse) {
                ServerResponse serverResponse = (ServerResponse) response;
                if (serverResponse.isSuccess()) {
                    System.out.println("Search name successfully!");
                    // Hiển thị lên table
                    return (CountryDTO) serverResponse.getData();
                } else {
                    System.out.println("Failed to search user. Reason: " + serverResponse.getErrorMessage());
                }
            } else {
                System.out.println("Unexpected response from server.");
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new CountryDTO();
    }
}
