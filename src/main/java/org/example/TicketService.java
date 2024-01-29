package org.example;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TicketService {


    private long minTimeFly = 1000000000;
    private List<Flying> tickets;


    public long getMinTimeFly() {
        return minTimeFly;
    }

    public void setMinTimeFly(long minTimeFly) {
        this.minTimeFly = minTimeFly;
    }

    public List<Flying> getTickets() {
        return tickets;
    }

    public void setTickets(List<Flying> tickets) {
        this.tickets = tickets;
    }

    public static List<Flying> findTicket(TicketService tickets) {
        List<Flying> reqList = new ArrayList<>();
        tickets.getTickets().stream().filter(flying -> flying.getOrigin().equals("VVO") && flying.getDestination().equals("TLV")).forEach(reqList::add);
        return reqList;
    }

    public static void getMinFly(TicketService tickets) throws IOException {

        findTicket(tickets).forEach(flying -> {
            String departureDate = flying.getDeparture_date();
            String departureTime = flying.getDeparture_time();
            String arrivalDate = flying.getArrival_date();
            String arrivalTime = flying.getArrival_time();
            SimpleDateFormat format = new SimpleDateFormat("d.MM.yy HH:mm");

            try {
                Date dateDeparture = format.parse(departureDate + " " + departureTime);
                Date dateArrival = format.parse(arrivalDate + " " + arrivalTime);
                if (tickets.getMinTimeFly() > (dateArrival.getTime() - dateDeparture.getTime())) {

                    tickets.setMinTimeFly(dateArrival.getTime() - dateDeparture.getTime());
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }


        });


    }

    public static double getDifference(TicketService tickets) {
        List<Flying> filteredTicket = findTicket(tickets);
        double median;
        int size = filteredTicket.size();
        if (size % 2 == 0) {
            median = filteredTicket.get(size / 2 - 1).getPrice() + (filteredTicket.get(size / 2).getPrice() / 2.0);
        } else {
            median = (filteredTicket.get(size / 2)).getPrice();
        }
        double price = filteredTicket.stream().mapToInt(Flying::getPrice).average().orElse(0);
        return price - median;
    }


}
