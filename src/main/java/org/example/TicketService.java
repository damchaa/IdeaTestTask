package org.example;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public static void getMinFly(List<Flying> tickets) throws IOException {
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
        double median;
        int size = filteredList.size();
        if (size % 2 == 0) {
            median = filteredList.get(size / 2 - 1).getPrice() + (tickets.get(size / 2).getPrice() / 2.0);
        } else {
            median = (filteredList.get(size / 2)).getPrice();
        }
        double price = filteredList.stream().mapToInt(Flying::getPrice).average().orElse(0);
        return price - median;
    }
}
