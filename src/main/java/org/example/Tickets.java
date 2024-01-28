package org.example;

import java.util.ArrayList;
import java.util.List;

public class Tickets {
    private List<Flying> tickets;

    public List<Flying> getTickets() {
        return tickets;
    }

    public void setTickets(List<Flying> tickets) {
        this.tickets = tickets;
    }
    public List<Flying> findTicket(Tickets tickets){
        List<Flying> reqList = new ArrayList<>();
        tickets.getTickets().stream().filter(flying -> flying.getOrigin().equals("VVO") && flying.getDestination().equals("TLV")).forEach(reqList::add);
        return reqList;
    }
    @Override
    public String toString() {
        return "Tickets{" +
                "tickets=" + tickets +
                '}';
    }
}
