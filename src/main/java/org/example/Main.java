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
    static long minTimeFly = 100000000;
    public static void main(String[] args) throws IOException {

        String fileName = "tickets.json";
        ObjectMapper mapper = new ObjectMapper();
        Tickets tickets = mapper.readValue(new File(fileName), new TypeReference<>(){});
        tickets.findTicket(tickets).forEach(flying -> {
            try {
                getMinFlyings(flying);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
        getDifference(tickets.findTicket(tickets));
        writeToFile(minTimeFly,getDifference(tickets.findTicket(tickets)));


    }

    private static void getMinFlyings(Flying flying) throws ParseException{
        long minTimeFly = 100000000;
        String strData = flying.getDeparture_date();
        String chData = flying.getDeparture_time();
        String strData1 = flying.getArrival_date();
        String chData1 = flying.getArrival_time();
        SimpleDateFormat format = new SimpleDateFormat("d.MM.yy HH:mm");
        Date date = format.parse(strData + " " + chData);
        Date date1 = format.parse( strData1 + " " + chData1);
        if (minTimeFly >  (date1.getTime()-date.getTime())){
            minTimeFly =  date1.getTime()-date.getTime();
        }

    }
    public static double getDifference(List<Flying> tickets){
        double median;
        int size = tickets.size();
        if (size%2 == 0){
            median = tickets.get(size/2-1).getPrice() + (tickets.get(size/2).getPrice()/2.0);
        }
        else {
            median = (tickets.get(size/2)).getPrice();
        }
        double price = tickets.stream().mapToInt(Flying::getPrice).average().orElse(0);
        return  price-median;
    }
    public static void writeToFile(Long minTimeFly,double difference){
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
            writer.write("Минимальное время полета: " + duration.toHours() + " часов " + duration.toMinutes()%60 + " минут  \n");
            writer.write("Разница между средней ценой и медианой: "+ difference);
            writer.close();
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл");
            e.printStackTrace();
        }
        System.out.println();
    }
}