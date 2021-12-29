package ticketReservation;

import ticketReservation.dto.TicketDto;
import ticketReservation.entity.*;
import ticketReservation.enumration.Gender;
import ticketReservation.enumration.Role;
import ticketReservation.service.PassengerService;
import ticketReservation.service.TicketApp;
import ticketReservation.util.DateUtil;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static TicketApp ticketApp = new TicketApp();
    static PassengerService passengerService = new PassengerService();

    public static void main(String[] args) {
        mainMenu();
    }

    public static void mainMenu() {
        while (true) {
            System.out.println("******* welcome *******");
            System.out.println("1.manager Menu   2.passenger Menu");
            try {
                String choice = scanner.nextLine().trim();
                if (choice.equals("1"))
                    managerMenu();
                else if (choice.equals("2"))
                    passengerMenu();
                else
                    throw new RuntimeException("❌❌❌❌❌ wrong Input ... try again ❌❌❌❌❌");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void managerMenu() {
        System.out.println("enter userName");
        String user = scanner.nextLine();
        System.out.println("enter passWord");
        String pass = scanner.nextLine();

        if (user.equalsIgnoreCase("admin") && pass.equalsIgnoreCase("admin")) {
            System.out.println("1.add company:  2.add bus  3.report ");
            int choice = Integer.parseInt(scanner.nextLine());
            outer:
            while (true) {
                switch (choice) {
                    case 1:
                        System.out.println("enter company name: ");
                        String companyName = scanner.nextLine();
                        ticketApp.saveCompany(new Company(companyName));
                        System.out.println("more request? y/n");
                        String req = scanner.nextLine();

                        if (req.equals("y"))//todo what can i do for this duplicate?
                            continue outer;
                        else if (req.equals("n"))
                            break;

                    case 2:
                      /*  System.out.println("enter plaque: ");
                        String plaque = scanner.nextLine();
                        System.out.println("which busType? ");
                        String busType = scanner.nextLine();
                        int totalSeat = Integer.parseInt(scanner.nextLine());
                        System.out.println("which company? ");
                        String compName = scanner.nextLine();
                        Company company = ticketApp.findCompanyByName(compName);
                        List<String> seatNumber = Bus.busRow(totalSeat);
                        Bus bus = new Bus(plaque, seatNumber, company, busType, totalSeat);
                        ticketApp.saveBus(bus);

                        System.out.println("more request? y/n");//todo what can i do for this duplicate?
                        String req2 = scanner.nextLine();
                        if (req2.equals("y"))
                            continue outer;
                        else if (req2.equals("n"))
                            break;*///???????????????????????????????????????????????????

                        break;
                    case 3:
                        System.out.println("enter type of bus:");
                        String type = scanner.nextLine();
                        ticketApp.findForAdminReport(type).forEach(System.out::println);

                        System.out.println("more request? y/n");//todo what can i do for this duplicate?
                        String req3 = scanner.nextLine();
                        if (req3.equals("y"))
                            continue outer;
                        else if (req3.equals("n"))
                            break;
                }
            }

        }
    }

    public static void passengerMenu() throws ParseException {
        System.out.println("enter ur userName");
        String user = scanner.nextLine();
        System.out.println("enter ur password");
        String pass = scanner.nextLine();
        Passenger foundPassenger = passengerService.findPassengerByUserAndPass(user, pass);
        if (foundPassenger != null) {
            signIn(foundPassenger);//ورود
        } else {
            registerUser();//ثبتنام
        }
    }

    public static void registerUser() throws ParseException {//todo exemption
        System.out.println("you have to register for getting Service");
        System.out.println("enter userName :");
        String user = scanner.nextLine();
        System.out.println("enter password :");
        String password = scanner.nextLine();
        String pass = null;
        outer:
        while (true) {
            try {
                System.out.println("enter email");
                pass = scanner.nextLine();
                boolean b = passengerService.duplicatePassword(pass);
                if (!b) {
                    pass = password;
                    break outer;
                } else {
                    throw new RuntimeException("this passWord is already exist...");
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
        Passenger newPassenger = new Passenger(user, pass);
        passengerService.savePassenger(newPassenger);
        Passenger passenger = passengerService.findPassengerByUserAndPass(user, password);
        signIn(passenger);
    }

    public static void signIn(Passenger passenger) throws ParseException {
        System.out.println("************* you successfully signIn *************");
        System.out.println("enter your origin:");
        String origin = scanner.nextLine();
        System.out.println("enter your destination:");
        String destination = scanner.nextLine();

        Date date = null;
        LocalTime startTime = null;
        LocalTime endTime = null;
        String busType = null;
        String companyName = null;
        double startPrice = 0;
        double endPrice = 0;

        System.out.println("do you want to filter result by date?  1.yes  2.no,show result");
        String choseDate = scanner.nextLine().trim();
        if (choseDate.equals("1")) {//filter date
            System.out.println("enter date:  format => yyyy-MM-dd");
            String strDate = scanner.nextLine();
            date = DateUtil.convertStringToDate(strDate);
            System.out.println("do you want to filter result by\n1.boundingTime  2.companyName  3.busType  4.boundingPrice 5.none");
            System.out.println("enter like => 1 2 3 ");
            String filter = scanner.nextLine();
            if (filter.contains("1")) {//todo what if enter 123 2  3
                System.out.println("enter start hour like=> 12:00:00");
                startTime = DateUtil.convertStringToTime(scanner.nextLine());
                System.out.println("enter end hour like=> 12:00:00");
                endTime = DateUtil.convertStringToTime(scanner.nextLine());
            }
            if (filter.contains("2")) {
                System.out.println("enter companyName");
                companyName = scanner.nextLine().toLowerCase();
            }
            if (filter.contains("3")) {
                System.out.println("enter busType VIP or NORMAL");
                busType = scanner.nextLine().toUpperCase();
            }
            if (filter.contains("4")) {
                System.out.println("enter start cost");
                startPrice = scanner.nextDouble();
                System.out.println("enter end cost");
                endPrice = scanner.nextDouble();
            }
            PassengerRequest request = PassengerRequest.builder().withOrigin(origin).withDestination(destination)
                    .withDate(date).withBusType(busType).withCompanyName(companyName).withStartPrice(startPrice)
                    .withEndPrice(endPrice).withStartTime(startTime).withEndTime(endTime).build();
            if (filter.contains("5")) {
                showResultWithPagination(origin, destination, date,
                        busType, companyName, startPrice, endPrice, startTime, endTime, passenger);
                // showResultWithPagination(request, passenger);
            }
        } else if (choseDate.equals("2")) {
            showResultWithPagination(origin, destination, date,
                    busType, companyName, startPrice, endPrice, startTime, endTime, passenger);
        } else {
            throw new RuntimeException("input write number");
        }
        showResultWithPagination(origin, destination, date, busType, companyName, startPrice, endPrice, startTime, endTime, passenger);
    }


    public static void showResultWithPagination(String origin, String destination, Date date, String busType,
                                                String companyName, double startPrice, double endPrice,
                                                LocalTime startTime, LocalTime endTime, Passenger passenger) throws ParseException {
        System.out.println("enter Number of results per page:");
        int resultPerPage = Integer.parseInt(scanner.nextLine());
        int result = ticketApp.searchForSize(origin, destination, date, busType, companyName, startPrice, endPrice, startTime, endTime);
        int lastPage;
        int currentPage = 1;
        if (result == 0) {
            System.out.println("no result has found");
        } else {
            if ((result % resultPerPage) == 0)
                lastPage = result / resultPerPage;
            else
                lastPage = result / resultPerPage + 1;
            outer:
            while (true) {
                show(currentPage-1, resultPerPage, origin, destination, date, busType, companyName, startPrice, endPrice, startTime, endTime);
                if (currentPage == 1) {//صفحه اول
                    if (currentPage != lastPage) {
                        System.out.println("1.next page   2.show detail   3.exit");
                        String choice = scanner.nextLine();
                        switch (choice) {
                            case "1":
                                currentPage += resultPerPage;
                                continue outer;
                            case "2":
                                showDetails(passenger);
                            case "3":
                                break outer;
                        }
                    } else {
                        System.out.println("1.show details   2.exit");
                        String s = scanner.nextLine();
                        switch (s) {
                            case "1":
                                showDetails(passenger);
                            case "2":
                                break outer;
                        }
                    }
                }
                if (currentPage == lastPage+1 && currentPage != 1) {//صفحه اخر
                    System.out.println("1.previous page  2.show detail  3.exit");
                    String input = scanner.nextLine();
                    switch (input) {
                        case "1":
                            currentPage -= resultPerPage;
                            continue outer;
                        case "2":
                            showDetails(passenger);
                        case "3":
                            break outer;
                    }
                } else {
                    System.out.println("1.next page  2.previous page  3.show detail  4.exit");
                    String choice = scanner.nextLine();
                    switch (choice) {
                        case "1":
                            currentPage += resultPerPage;
                            continue outer;
                        case "2":
                            currentPage -= resultPerPage;
                            continue outer;
                        case "3":
                            showDetails(passenger);
                            break outer;
                        case "4":
                            break outer;
                    }
                    currentPage += resultPerPage;
                    show(currentPage, resultPerPage, origin, destination, null, null, null, 0, 0, null, null);
                }
            }
        }
    }

    private static void showDetails(Passenger passenger) throws ParseException {
        System.out.println("which ticket?");
        String s1 = scanner.nextLine();
        int ticketNumber = Integer.parseInt(s1);
        Ticket ticket = ticketApp.findTicketById(ticketNumber);
        List<String> seatNumber = ticket.getBus().getSeatNumber();//لیست صندلی های موجود
        List<String> listSeatNumber = new ArrayList<>();
        listSeatNumber.addAll(seatNumber);//کپی لیست صندلی های موجود

        List<String> passengerSeat = new ArrayList<>();
        System.out.println("how many tickets?");
        int countOfTicket = Integer.parseInt(scanner.nextLine());
        int availableSeat = ticket.getBus().getAvailableSeat();

        if (countOfTicket < availableSeat) {
            for (int i = 0; i < countOfTicket; i++) {
                System.out.println(listSeatNumber + "\n***************************");
                System.out.println("this seats are available..enter: ");
                Scanner scanner3 = new Scanner(System.in);
                String s = scanner3.nextLine().trim().toUpperCase();
                passengerSeat.add(s);
                listSeatNumber.remove(s);
                availableSeat--;
            }
            System.out.println("do you want to confirm your ticket? 1.yes 2.no");
            Scanner scanner3 = new Scanner(System.in);
            String choice = scanner3.nextLine();
            switch (choice) {
                case "1":
                    buyTicket(passenger, ticket, passengerSeat, countOfTicket, availableSeat, listSeatNumber);
            }
        } else
            throw new RuntimeException("this number is more than availability");
    }


    public static void show(int start, int pageSize, String origin, String destination, Date date,
                            String busType, String companyName, double startPrice, double endPrice, LocalTime startTime, LocalTime endTime) {
        List<TicketDto> ticketByPagination = ticketApp.search(start, pageSize, origin, destination, date, busType, companyName, startPrice, endPrice, startTime, endTime);
        ticketByPagination.forEach(System.out::println);
    }


    public static void buyTicket(Passenger passenger, Ticket ticket, List<String> passengerSeat, int countOfTicket, int availableSeat, List<String> ListSeatNumber) throws ParseException {
        if (passenger.getNationalCode() == null) {
            System.out.println("please complete your info for buy ticket");
            System.out.println("enter name :");
            String name = scanner.nextLine();
            passenger.setFirstName(name);
            System.out.println("enter family :");
            String family = scanner.nextLine();
            passenger.setLastName(family);
            System.out.println("enter nationalCode");
            String nationalCode = scanner.nextLine();
            List<String> phoneList = new ArrayList<>();
            String email = null;
            boolean flag=true;
            while (flag) {
                try {
                    System.out.println("enter email");
                    String inputEmail = scanner.nextLine();
                    boolean b = passengerService.duplicatePassword(inputEmail);
                    if (!b) {
                        email = inputEmail;
                        flag=false ;
                    } else {
                        throw new RuntimeException("this email is already exist...");
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
            }
            System.out.println("enter mobile:");
            String phoneNumbers = scanner.nextLine();
            System.out.println("enter gender: 1.MALE  2.FEMALE");
            int gender = Integer.parseInt(scanner.nextLine());
            switch (gender) {
                case 1:
                    passenger.setGender(Gender.MALE);
                case 2:
                    passenger.setGender(Gender.FEMALE);
            }
            phoneList.add(phoneNumbers);
            passenger.setNationalCode(nationalCode);
            passenger.setEmail(email);
            passenger.setPhoneNumbers(phoneList);
            passenger.setRole(Role.PASSENGER);
            passengerService.updatePassenger(passenger);
        }
        BookingTicket bookingTicket = new BookingTicket();
        bookingTicket.setPassenger(passenger);
        bookingTicket.setTicket(ticket);
        bookingTicket.setSeatSate(passengerSeat);
        bookingTicket.setTotalCost(countOfTicket * ticket.getCost());
        Date currentDay=new Date();
        bookingTicket.setBuyTicket(currentDay);
        ticketApp.saveBookingTicket(bookingTicket);

        Bus bus = ticket.getBus();
        bus.setAvailableSeat(availableSeat);
        bus.setSeatNumber(ListSeatNumber);
        System.out.println(bus.getId());
        ticketApp.updateBus(ticket.getBus());

        System.out.println("this ticket(s) has successfully bought:");
        ticketApp.findTicketByPassengerId(passenger.getId(),currentDay).forEach(System.out::println);
        System.out.println("do you have any request? 1.yes   2.no");
        String s = scanner.nextLine();
        switch (s) {
            case "1":
                signIn(passenger);
            case "2":
                break;//todo exit app
        }
    }

}
