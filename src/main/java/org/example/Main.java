package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;


public class Main {


    public static void main(String[] args) throws IOException {

        String fileName = "tickets.json";
        ObjectMapper mapper = new ObjectMapper();
        TicketService tickets = mapper.readValue(new File(fileName), new TypeReference<>() {});
        TicketService.getMinFly(tickets);
        double difference = TicketService.getDifference(tickets);
        writeToFile(tickets.getMinTimeFly(),difference);



    }





    public static void writeToFile(Long minTimeFly, double difference) {
        Duration duration = Duration.ofMillis(minTimeFly);
        try {
            File file = new File("result.txt");
            if (file.createNewFile()) {
                System.out.println("Файл создан");
            } else {
                System.out.println("Файл уже существует");
            }
        } catch (IOException e) {
            System.out.println("Ошибка при создании файла");
            e.printStackTrace();
        }
        try {
            FileWriter writer = new FileWriter("result.txt");
            writer.write("Минимальное время полета: " + duration.toHours() + " часов " + duration.toMinutes() % 60 + " минут  \n");
            writer.write("Разница между средней ценой и медианой: " + difference);
            writer.close();
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл");
            e.printStackTrace();
        }
        System.out.println();
    }
}