package org.example.view;

import org.example.dto.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientWine {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 8888;


    public static void addWine(Integer id, String name, String concentration,Integer year,String countryName) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

            // Gửi yêu cầu thêm wine dùng đến server
            System.out.println("Before reading object from client");
            WineDTO wineDTO = new WineDTO(id,name,concentration,year,countryName);
            Method method = new Method();
            method.setMethod("AddWine");
            method.setData(wineDTO);
            out.writeObject(method);
            System.out.println("After reading object from client");

            // Nhận phản hồi từ server
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Object response = in.readObject();

            if (response instanceof ServerResponse) {
                ServerResponse serverResponse = (ServerResponse) response;
                if (serverResponse.isSuccess()) {
                    System.out.println("wine added successfully!");

                } else {
                    System.out.println("Failed to add wine. Reason: " + serverResponse.getErrorMessage());
                }
            } else {
                System.out.println("Unexpected response from server.");
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static List<WineDTO> getData() {
        List<WineDTO> wineDTOS = new ArrayList<>();
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
            // gửi yêu cầu
            Method method = new Method();
            method.setMethod("GetAllWine");
            out.writeObject(method);
            System.out.println("log: gui success ! ");

            // nhận yêu cầu
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Object response = in.readObject();

            if (response instanceof ServerResponse) {
                ServerResponse serverResponse = (ServerResponse) response;
                if (serverResponse.isSuccess()) {
                    wineDTOS = (List<WineDTO>) serverResponse.getData();
                    System.out.println("get All wine success !");

                } else {
                    System.out.println("Failed to . Reason: " + serverResponse.getErrorMessage());
                }
            } else {
                System.out.println("Unexpected response from server.");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return wineDTOS;
    }

    public static void updateWine(Integer id, String name, String concentration,Integer year,String countryName) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

            // Gửi yêu cầu update wine dùng đến server
            System.out.println("Before reading object from client");
            WineDTO wineDTO = new WineDTO(id,name,concentration,year,countryName);
            Method method = new Method();
            method.setMethod("UpdateWine");
            method.setData(wineDTO);
            out.writeObject(method);
            System.out.println("After reading object from client");

            // Nhận phản hồi từ server
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Object response = in.readObject();

            if (response instanceof ServerResponse) {
                ServerResponse serverResponse = (ServerResponse) response;
                if (serverResponse.isSuccess()) {
                    System.out.println("wine updated successfully!");

                } else {
                    System.out.println("Failed to updated wine. Reason: " + serverResponse.getErrorMessage());
                }
            } else {
                System.out.println("Unexpected response from server.");
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void deleteWine(Integer id) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

            // Gửi yêu cầu update wine dùng đến server
            System.out.println("Before reading object from client");
            Method method = new Method();
            method.setMethod("DeleteWine");
            method.setData(id);
            out.writeObject(method);
            System.out.println("After reading object from client");

            // Nhận phản hồi từ server
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Object response = in.readObject();

            if (response instanceof ServerResponse) {
                ServerResponse serverResponse = (ServerResponse) response;
                if (serverResponse.isSuccess()) {
                    System.out.println("wine delete successfully!");

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

    public static WineDTO search(String name) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

            // Gửi yêu cầu update wine dùng đến server
            System.out.println("Before reading object from client");
            Method method = new Method();
            method.setMethod("SearchWine");
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
                    return (WineDTO) serverResponse.getData();
                } else {
                    System.out.println("Failed to search user. Reason: " + serverResponse.getErrorMessage());
                }
            } else {
                System.out.println("Unexpected response from server.");
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new WineDTO();
    }
}
