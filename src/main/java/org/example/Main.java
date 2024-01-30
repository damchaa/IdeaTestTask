package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;


import static org.example.TicketService.minTimeFly;


public class Main {


    public static void main(String[] args) throws IOException {

        String fileName = "tickets.json";
        ObjectMapper mapper = new ObjectMapper();
        TicketService ticketsService = mapper.readValue(new File(fileName), new TypeReference<>() {});
        TicketService.getMinFly(ticketsService.getTickets());
        double difference = TicketService.getDifference(ticketsService.getTickets());
        writeToFile(minTimeFly,difference);



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