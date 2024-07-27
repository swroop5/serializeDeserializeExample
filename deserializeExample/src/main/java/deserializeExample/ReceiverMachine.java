package deserializeExample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReceiverMachine {

    public static void main(String[] args) {
        try {
            int receiverPort = 7777;
            ServerSocket serverSocket = new ServerSocket(receiverPort);
            
            while (true) {
                System.out.println("Waiting for a connection...");
                Socket socket = serverSocket.accept();

                // Handle the connection in a separate thread
                new ConnectionHandler(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ConnectionHandler extends Thread {
    private Socket socket;

    public ConnectionHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            // Create an ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            // Read and process data from the client
            InputStream is = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String receivedJson;
            while ((receivedJson = reader.readLine()) != null) {
                System.out.println("Data received from sender machine: " + receivedJson);

                // Deserialize the JSON data into a Java object
                Student student = objectMapper.readValue(receivedJson, Student.class);
                System.out.println("Deserialized Student: " + student);
            }

            // Close the socket when the connection is terminated
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


class Student {
    private String name;
    private int age;

    public Student() {}

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
