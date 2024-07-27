package serializationExample;

import java.io.*;
import java.net.Socket;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SenderMachine {

    public static void main(String[] args) {
        try {
            String receiverIpAddress = "192.168.178.44";
            int receiverPort = 7777;

            // Create an ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            // Create a socket connection to the server
            Socket socket = new Socket(receiverIpAddress, receiverPort);

            // Serialize and send data continuously
            while (true) {
                // Serialize a Java object to JSON
                Student student = new Student("John Doe", 25);
                String json = objectMapper.writeValueAsString(student);

                // Send the JSON data to the server
                OutputStream os = socket.getOutputStream();
                PrintWriter out = new PrintWriter(os, true);
                out.println(json);
                
                Thread.sleep(1000); // Simulate continuous data transmission
            }
        } catch (Exception e) {
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
