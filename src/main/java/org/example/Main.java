package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.example.TicketService.*;


public class Main {


    public static void main(String[] args) throws IOException {

        String fileName = "tickets.json";
        ObjectMapper mapper = new ObjectMapper();
        TicketService ticketsService = mapper.readValue(new File(fileName), new TypeReference<>() {
        });
        double difference = TicketService.getDifference(ticketsService.getTickets());
        writeToFile(getCompanyWithTime(ticketsService.getTickets()), difference);


    }


    public static void writeToFile(Map<String,Long> countryWithTime, double difference) {

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

            String resultDifference = String.format("Разница между средней ценой и медианой: : %f \n"
                    , difference);
            writer.write(resultDifference);
            for (Map.Entry<String,Long>entry: countryWithTime.entrySet()){
                Duration duration = Duration.ofMillis(entry.getValue());
                String resultTime =  String.format("Минимальное время полета для авиакомпании %s : %d часов  %d минут,\n",
                        entry.getKey(), duration.toHours(),duration.toMinutes() % 60 );
                writer.write(resultTime);
            }


            writer.close();
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл");
            e.printStackTrace();
        }
        System.out.println();
    }
}