package org.example;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TicketService {


    static long minTimeFly = 1000000000;
    private List<Flying> tickets;


    public List<Flying> getTickets() {
        return tickets;
    }

    public void setTickets(List<Flying> tickets) {
        this.tickets = tickets;
    }

    public static List<Flying> findTicket(List<Flying> tickets) {
        List<Flying> filteredList = new ArrayList<>();
        tickets.stream().filter(flying -> flying.getOrigin().equals("VVO") && flying.getDestination()
                .equals("TLV")).forEach(filteredList::add);
        return filteredList;
    }

    public static void getMinFly(List<Flying> tickets)  {
        List<Flying> filteredList = findTicket(tickets);
        filteredList.forEach(flying -> {
            String departureDate = flying.getDeparture_date();
            String departureTime = flying.getDeparture_time();
            String arrivalDate = flying.getArrival_date();
            String arrivalTime = flying.getArrival_time();
            SimpleDateFormat format = new SimpleDateFormat("d.MM.yy HH:mm");
            try {
                Date dateDeparture = format.parse(departureDate + " " + departureTime);
                Date dateArrival = format.parse(arrivalDate + " " + arrivalTime);
                if (minTimeFly > (dateArrival.getTime() - dateDeparture.getTime())) {

                    minTimeFly = (dateArrival.getTime() - dateDeparture.getTime());
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });


    }

    public static double getDifference(List<Flying> tickets) {
        List<Flying> filteredList = findTicket(tickets);
        List<Integer> flyingPrice = new ArrayList<>();
        filteredList.forEach(flying -> flyingPrice.add(flying.getPrice()));
        Collections.sort(flyingPrice);
        double median;
        int size = flyingPrice.size();
        if (size % 2 == 0) {
            median = (flyingPrice.get(size / 2 - 1) + (flyingPrice.get(size / 2) ))/ 2.0;
        } else {
            median = (flyingPrice.get(size / 2));

        }

        double price = filteredList.stream().mapToInt(Flying::getPrice).average().orElse(0);
        System.out.println(price-median);
        return price - median;
    }
    public static Map<String,Long> getCompanyWithTime(List<Flying> flyings){
        List<Flying> filteredTicket =  findTicket(flyings);
        Map<String,Long> companyWithTime = new HashMap<>();
        filteredTicket.forEach(flying -> companyWithTime.put(flying.getCarrier(),1000000000L));
        filteredTicket.forEach(flying ->
        {
            String departureDate = flying.getDeparture_date();
            String departureTime = flying.getDeparture_time();
            String arrivalDate = flying.getArrival_date();
            String arrivalTime = flying.getArrival_time();
            SimpleDateFormat format = new SimpleDateFormat("d.MM.yy HH:mm");
            try {
                Date dateDeparture = format.parse(departureDate + " " + departureTime);
                Date dateArrival = format.parse(arrivalDate + " " + arrivalTime);
                if (companyWithTime.containsKey(flying.getCarrier()) && companyWithTime.get(flying.getCarrier()) > (dateArrival.getTime() - dateDeparture.getTime()))   {

                    companyWithTime.put(flying.getCarrier(),(dateArrival.getTime() - dateDeparture.getTime()));
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
        return companyWithTime;
    }
}
