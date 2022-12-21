package sk.stuba.fei.uim.vsa.pr1a;

import sk.stuba.fei.uim.vsa.pr1a.entities.*;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CLI {

    private CarParkService cps;
    private Map<String, Runnable> methodMap = new HashMap<String, Runnable>();
    private Map<String, String> helperMap = new HashMap<String, String>();

    public CLI() {
        cps = new CarParkService();
        helperMap.put("help", "Commands list");
        helperMap.put("quit", "Exit CLI");

        methodMap.put("1", this::createCarPark);
        helperMap.put("1", "Create CarPark");
        methodMap.put("2", this::getCarParkById);
        helperMap.put("2", "Get CarPark by id");
        methodMap.put("3", this::getCarParkByName);
        helperMap.put("3", "Get CarPark by name");
        methodMap.put("4", this::getAllCarParks);
        helperMap.put("4", "Get all CarParks");
        methodMap.put("5", this::deleteCarPark);
        helperMap.put("5", "Delete CarPark by id");
        methodMap.put("6", this::createCarParkFloor);
        helperMap.put("6", "Create CarParkFloor");
        methodMap.put("7", this::getCarParkFloor);
        helperMap.put("7", "Get CarParkFloor by id");
        methodMap.put("8", this::getCarParkFloorsById);
        helperMap.put("8", "Get CarParkFloors by CarPark id");
        methodMap.put("9", this::deleteCarParkFloor);
        helperMap.put("9", "Delete CarParkFloor by id");
        methodMap.put("10", this::createParkingSpot);
        helperMap.put("10", "Create ParkingSpot");
        methodMap.put("11", this::getParkingSpotById);
        helperMap.put("11", "Get ParkingSpot by id");
        methodMap.put("12", this::getParkingSpotsOnFloor);
        helperMap.put("12", "Get ParkingSpots on CarParkFloor");
        methodMap.put("13", this::getParkingSpotsInPark);
        helperMap.put("13", "Get ParkingSpots in CarPark");
        methodMap.put("14", this::getAvailableParkingSpots);
        helperMap.put("14", "Get available ParkingSpots by CarPark name");
        methodMap.put("15", this::getOccupiedParkingSpots);
        helperMap.put("15", "Get occupied ParkingSpots by CarPark name");
        methodMap.put("16", this::deleteParkingSpot);
        helperMap.put("16", "Delete ParkingSpot by id");
        methodMap.put("17", this::createCar);
        helperMap.put("17", "Create Car");
        methodMap.put("18", this::getCarById);
        helperMap.put("18", "Get Car by id");
        methodMap.put("19", this::getCarByReg);
        helperMap.put("19", "Get Car by registration number");
        methodMap.put("20", this::getCarsByUser);
        helperMap.put("20", "Get Cars by User id");
        methodMap.put("21", this::deleteCar);
        helperMap.put("21", "Delete Car by User id");
        methodMap.put("22", this::createUser);
        helperMap.put("22", "Create User");
        methodMap.put("23", this::getUserById);
        helperMap.put("23", "Get User by id");
        methodMap.put("24", this::getUserByEmail);
        helperMap.put("24", "Get User by email");
        methodMap.put("25", this::getAllUsers);
        helperMap.put("25", "Get all Users");
        methodMap.put("26", this::deleteUser);
        helperMap.put("26", "Delete User by id");
        methodMap.put("27", this::createReservation);
        helperMap.put("27", "Create Reservation");
        methodMap.put("28", this::endReservation);
        helperMap.put("28", "End Reservation");
        methodMap.put("29", this::getReservationsByParkingSpot);
        helperMap.put("29", "Get Reservations by ParkingSpot id");
        methodMap.put("30", this::getReservationsByUser);
        helperMap.put("30", "Get Reservations by User id");
        methodMap.put("31", this::createDiscountCoupon);
        helperMap.put("31", "Create Discount coupon");
        methodMap.put("32", this::giveCouponToUser);
        helperMap.put("32", "Give Discount coupon to User");
        methodMap.put("33", this::getCouponById);
        helperMap.put("33", "Get Discount coupon by id");
        methodMap.put("34", this::getCouponsByUser);
        helperMap.put("34", "Get Discount coupons by User id");
        methodMap.put("35", this::endReservationWithCoupon);
        helperMap.put("35", "End Reservation with Discount coupon");
        methodMap.put("36", this::deleteCoupon);
        helperMap.put("36", "Delete Discount coupon");

        System.out.println("CLI initiated, run \"help\" for available commands");
    }

    public void start() {
        while (true) {
            String input = KeyboardInput.readString("").trim();
            switch (input) {
                case "q":
                case "exit":
                case "quit":
                    return;
            }
            if(input.equals("help")) {
                for (Map.Entry<String,String> entry : helperMap.entrySet()) {
                    System.out.println("Command = " + entry.getKey() +
                            ", Method = " + entry.getValue());
                }
            } else if (isCommand(input)) {
                try {
                    executeCommand(input);
                } catch (InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Input '" + input + "' was not recognised as a known command!");
                System.out.println("Run \"help\" for available commands");
            }
        }
    }

    private boolean isCommand(String input) {
        return methodMap.containsKey(getCommand(input));
    }

    private String getCommand(String input) {
        String command = "";
        if (!input.contains(" ")) {
            command = input.trim();
        } else {
            command = input.substring(0, input.indexOf(' '));
        }
        return command;
    }

    private void executeCommand(String input) throws InvocationTargetException, IllegalAccessException {
        if (!isCommand(input)) return;
        String command = getCommand(input);
        methodMap.get(command).run();
    }

    public void createCarPark() {
        System.out.println("-------Zadaj údaje pre CarPark-------");
        String name = KeyboardInput.readString("Meno", 2);
        String address = KeyboardInput.readString("Adresa", 2);
        int price = KeyboardInput.readInt("Cena za hodinu", 2);
        if(price < 0) {
            System.out.println("-------Pokus o vytvorenie CarPark-u zlyhal-------");
            return;
        }
        if(cps.createCarPark(name, address, price) != null) {
            System.out.println("-------Úspešne vytvorený CarPark-------");
        } else {
            System.out.println("-------Pokus o vytvorenie CarPark-u zlyhal-------");
        }
    }

    public void getCarParkById() {
        System.out.println("-------Zadaj ID CarPark-u-------");
        int id = KeyboardInput.readInt("ID", 2);
        if(id < 0) {
            System.out.println("-------Pokus o nájdenie CarPark-u zlyhal-------");
            return;
        }
        CarPark cp = (CarPark) cps.getCarPark((long) id);
        if(cp != null) {
            System.out.println(cp);
        } else {
            System.out.println("-------Pokus o nájdenie CarPark-u zlyhal-------");
        }
    }

    public void getCarParkByName() {
        System.out.println("-------Zadaj meno CarPark-u-------");
        String name = KeyboardInput.readString("Meno", 2);
        CarPark cp = (CarPark) cps.getCarPark(name);
        if(cp != null) {
            System.out.println(cp);
        } else {
            System.out.println("-------Pokus o nájdenie CarPark-u zlyhal-------");
        }
    }

    public void getAllCarParks() {
        System.out.println(cps.getCarParks());
    }

    public void deleteCarPark() {
        System.out.println("-------Zadaj ID CarPark-u na zmazanie-------");
        int id = KeyboardInput.readInt("ID", 2);
        if(id < 0) {
            System.out.println("-------Pokus o nájdenie CarPark-u zlyhal-------");
            return;
        }
        if(cps.deleteCarPark((long) id) != null) {
            System.out.println("-------Úspešne zmazaný CarPark-------");
        } else {
            System.out.println("-------Pokus o zmazanie CarPark-u zlyhal-------");
        }
    }

    public void createCarParkFloor() {
        System.out.println("-------Zadaj údaje pre CarParkFloor-------");
        int id = KeyboardInput.readInt("CarParkId", 2);
        if(id < 0) {
            System.out.println("-------Pokus o nájdenie CarParkFlooru-u zlyhal-------");
            return;
        }
        String idn = KeyboardInput.readString("FloorIdentifier", 2);
        if(cps.createCarParkFloor((long) id, idn) != null) {
            System.out.println("-------Úspešne vytvorený CarParkFloor-------");
        } else {
            System.out.println("-------Pokus o vytvorenie CarParkFloor-u zlyhal-------");
        }
    }

    public void getCarParkFloor() {
        int id = KeyboardInput.readInt("CarParkId", 2);
        String idn = KeyboardInput.readString("FloorIdentifier", 2);
        if(id < 0) {
            System.out.println("-------Pokus o nájdenie CarParkFlooru-u zlyhal-------");
            return;
        }
        CarParkFloor cpf = (CarParkFloor) cps.getCarParkFloor((long) id, idn);
        if(cpf != null) {
            System.out.println(cpf);
        } else {
            System.out.println("-------Pokus o nájdenie CarParkFlooru-u zlyhal-------");
        }
    }

    public void getCarParkFloorsById() {
        System.out.println("-------Zadaj údaje na nájdenie CarParkFloorov-------");
        int id = KeyboardInput.readInt("CarParkID", 2);
        if(id < 0) {
            System.out.println("-------Pokus o nájdenie CarParkFloorov zlyhal-------");
            return;
        }
        List<Object> cpfL = cps.getCarParkFloors((long) id);
        if(cpfL != null) {
            System.out.println(cpfL);
        } else {
            System.out.println("-------Pokus o nájdenie CarParkFloorov zlyhal-------");
        }
    }

    public void deleteCarParkFloor() {
        System.out.println("-------Zadaj údaje na zmazanie CarParkFloor-u-------");
        int id = KeyboardInput.readInt("CarParkId", 2);
        String idn = KeyboardInput.readString("FloorIdentifier", 2);
        if(id < 0) {
            System.out.println("-------Pokus o nájdenie CarParkFlooru-u zlyhal-------");
            return;
        }
        if(cps.deleteCarParkFloor((long) id, idn) != null) {
            System.out.println("-------Úspešne zmazaný CarParkFloor-------");
        } else {
            System.out.println("-------Pokus o zmazanie CarParkFloor-u zlyhal-------");
        }
    }

    public void createParkingSpot() {
        System.out.println("-------Zadaj údaje na vytvorenie ParkingSpotu-u-------");
        int id = KeyboardInput.readInt("CarParkId", 2);
        if(id < 0) {
            System.out.println("-------Pokus o vytvorenie ParkingSpotu-u zlyhal-------");
            return;
        }
        String floorIdn = KeyboardInput.readString("FloorIdentifier", 2);
        String spotIdn = KeyboardInput.readString("SpotIdentifier", 2);
        if(cps.createParkingSpot((long) id, floorIdn, spotIdn) != null) {
            System.out.println("-------Úspešne vytvorený ParkingSpot-------");
        } else {
            System.out.println("-------Pokus o vytvorenie ParkingSpot-u zlyhal-------");
        }
    }

    public void getParkingSpotById() {
        System.out.println("-------Zadaj ID ParkingSpot-u-------");
        int id = KeyboardInput.readInt("ID", 2);
        if(id < 0) {
            System.out.println("-------Pokus o nájdenie ParkingSpot-u zlyhal-------");
            return;
        }
        ParkingSpot cp = (ParkingSpot) cps.getParkingSpot((long) id);
        if(cp != null) {
            System.out.println(cp);
        } else {
            System.out.println("-------Pokus o nájdenie ParkingSpot-u zlyhal-------");
        }
    }

    public void getParkingSpotsOnFloor() {
        System.out.println("-------Zadaj údaje pre vyhľadanie ParkingSpotov-------");
        int id = KeyboardInput.readInt("CarParkId", 2);
        if(id < 0) {
            System.out.println("-------Pokus o nájdenie ParkingSpotov zlyhal-------");
            return;
        }
        String idn = KeyboardInput.readString("FloorIdentifier", 2);
        List<Object> psL = cps.getParkingSpots((long) id, idn);
        if(psL != null) {
            System.out.println(psL);
        } else {
            System.out.println("-------Pokus o nájdenie ParkingSpotov zlyhal-------");
        }
    }

    public void getParkingSpotsInPark() {
        System.out.println("-------Zadaj údaje pre vyhľadanie ParkingSpotov-------");
        int id = KeyboardInput.readInt("CarParkId", 2);
        if(id < 0) {
            System.out.println("-------Pokus o nájdenie ParkingSpotov zlyhal-------");
            return;
        }
        Map<String, List<Object>> psM = cps.getParkingSpots((long) id);
        if(psM != null) {
            System.out.println(psM);
        } else {
            System.out.println("-------Pokus o nájdenie ParkingSpotov zlyhal-------");
        }
    }

    public void getAvailableParkingSpots() {
        System.out.println("-------Zadaj údaje pre vyhľadanie voľných ParkingSpotov-------");
        String idn = KeyboardInput.readString("CarParkName", 2);
        Map<String, List<Object>> psM = cps.getAvailableParkingSpots(idn);
        if(psM != null) {
            System.out.println(psM);
        } else {
            System.out.println("-------Pokus o nájdenie voľných ParkingSpotov zlyhal-------");
        }
    }

    public void getOccupiedParkingSpots() {
        System.out.println("-------Zadaj údaje pre vyhľadanie obsadených ParkingSpotov-------");
        String idn = KeyboardInput.readString("CarParkName", 2);
        Map<String, List<Object>> psM = cps.getAvailableParkingSpots(idn);
        if(psM != null) {
            System.out.println(psM);
        } else {
            System.out.println("-------Pokus o nájdenie obsadených ParkingSpotov zlyhal-------");
        }
    }

    public void deleteParkingSpot() {
        System.out.println("-------Zadaj údaje pre zmazanie ParkingSpot-u-------");
        int id = KeyboardInput.readInt("ParkingSpot ID", 2);
        if(id < 0) {
            System.out.println("-------Pokus o zmazanie ParkingSpot-u zlyhal-------");
            return;
        }
        if(cps.deleteParkingSpot((long) id) != null) {
            System.out.println("-------Úspešne zmazaný ParkingSpot-------");
        } else {
            System.out.println("-------Pokus o zmazanie ParkingSpot-u zlyhal-------");
        }
    }

    public void createCar() {
        System.out.println("-------Zadaj údaje pre vytvorenie Car-------");
        int id = KeyboardInput.readInt("User ID", 2);
        if(id < 0) {
            System.out.println("-------Pokus o vytvorenie Car zlyhal-------");
            return;
        }
        String brand = KeyboardInput.readString("Brand", 2);
        String model = KeyboardInput.readString("Model", 2);
        String colour = KeyboardInput.readString("Colour", 2);
        String reg = KeyboardInput.readString("VehicleRegistrationPlate", 2);
        if(cps.createCar((long) id, brand, model, colour, reg) != null) {
            System.out.println("-------Úspešne vytvorený Car-------");
        } else {
            System.out.println("-------Pokus o vytvorenie Car zlyhal-------");
        }
    }

    public void getCarById() {
        System.out.println("-------Zadaj údaje pre Car-------");
        int id = KeyboardInput.readInt("ID", 2);
        if(id < 0) {
            System.out.println("-------Pokus o nájdenie Car zlyhal-------");
            return;
        }
        Car c = (Car) cps.getCar((long) id);
        if(c != null) {
            System.out.println(c);
        } else {
            System.out.println("-------Pokus o nájdenie Car zlyhal-------");
        }
    }

    public void getCarByReg() {
        System.out.println("-------Zadaj údaje pre Car-------");
        String reg = KeyboardInput.readString("VehicleRegistrationPlate", 2);
        Car c = (Car) cps.getCar(reg);
        if(c != null) {
            System.out.println(c);
        } else {
            System.out.println("-------Pokus o nájdenie Car zlyhal-------");
        }
    }

    public void getCarsByUser() {
        System.out.println("-------Zadaj údaje pre Cars-------");
        int id = KeyboardInput.readInt("User ID", 2);
        if(id < 0) {
            System.out.println("-------Pokus o nájdenie Cars zlyhal-------");
            return;
        }
        List<Object> cL = cps.getCars((long) id);
        if(cL != null) {
            System.out.println(cL);
        } else {
            System.out.println("-------Pokus o nájdenie Cars zlyhal-------");
        }
    }

    public void deleteCar() {
        System.out.println("-------Zadaj údaje pre Car-------");
        int id = KeyboardInput.readInt("ID", 2);
        if(id < 0) {
            System.out.println("-------Pokus o zmazanie Car zlyhal-------");
            return;
        }
        if(cps.deleteCar((long) id) != null) {
            System.out.println("-------Úspešne zmazaný Car-------");
        } else {
            System.out.println("-------Pokus o zmazanie Car zlyhal-------");
        }
    }

    public void createUser() {
        System.out.println("-------Zadaj údaje pre vytvorenie User-a-------");
        String firstname = KeyboardInput.readString("Firstname", 2);
        String lastname = KeyboardInput.readString("Lastname", 2);
        String email = KeyboardInput.readString("Email", 2);
        if(cps.createUser(firstname, lastname, email) != null) {
            System.out.println("-------Úspešne vytvorený User-------");
        } else {
            System.out.println("-------Pokus o vytvorenie User-a zlyhal-------");
        }
    }

    public void getUserById() {
        System.out.println("-------Zadaj údaje pre User-a-------");
        int id = KeyboardInput.readInt("ID", 2);
        if(id < 0) {
            System.out.println("-------Pokus o nájdenie User-a zlyhal-------");
            return;
        }
        Customer c = (Customer) cps.getUser((long) id);
        if(c != null) {
            System.out.println(c);
        } else {
            System.out.println("-------Pokus o nájdenie User-a zlyhal-------");
        }
    }

    public void getUserByEmail() {
        System.out.println("-------Zadaj údaje pre User-a-------");
        String email = KeyboardInput.readString("Email", 2);
        Customer c = (Customer) cps.getUser(email);
        if(c != null) {
            System.out.println(c);
        } else {
            System.out.println("-------Pokus o nájdenie User-a zlyhal-------");
        }
    }

    public void getAllUsers() {
        System.out.println(cps.getUsers());
    }

    public void deleteUser() {
        System.out.println("-------Zadaj údaje pre User-a-------");
        int id = KeyboardInput.readInt("ID", 2);
        if(id < 0) {
            System.out.println("-------Pokus o zmazanie User-a zlyhal-------");
            return;
        }
        if(cps.deleteUser((long) id) != null) {
            System.out.println("-------Úspešne zmazaný User-------");
        } else {
            System.out.println("-------Pokus o zmazanie User-a zlyhal-------");
        }
    }

    public void createReservation() {
        System.out.println("-------Zadaj údaje pre vytvorenie Reservation-------");
        int spotId = KeyboardInput.readInt("ParkingSpot ID", 2);
        if(spotId < 0) {
            System.out.println("-------Pokus o vytvorenie Reservation zlyhal-------");
            return;
        }
        int carId = KeyboardInput.readInt("Car ID", 2);
        if(carId < 0) {
            System.out.println("-------Pokus o vytvorenie Reservation zlyhal-------");
            return;
        }
        if(cps.createReservation((long) spotId, (long) carId) != null) {
            System.out.println("-------Úspešne vytvorená Reservation-------");
        } else {
            System.out.println("-------Pokus o vytvorenie Reservation zlyhal-------");
        }
    }

    public void endReservation() {
        System.out.println("-------Zadaj údaje pre ukončenie Reservation-------");
        int resId = KeyboardInput.readInt("Reservation ID", 2);
        if(resId < 0) {
            System.out.println("-------Pokus o ukončenie Reservation zlyhal-------");
            return;
        }
        if(cps.endReservation((long) resId) != null) {
            System.out.println("-------Úspešne ukončená Reservation-------");
        } else {
            System.out.println("-------Pokus o ukončenie Reservation zlyhal-------");
        }
    }

    public void getReservationsByParkingSpot() {
        System.out.println("-------Zadaj údaje pre získanie Reservations-------");
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy", Locale.ENGLISH);
        int psId = KeyboardInput.readInt("ParkingSpot ID", 2);
        if(psId < 0) {
            System.out.println("-------Pokus o získanie Reservations zlyhal-------");
            return;
        }
        String dateString = KeyboardInput.readString("Date (dd-m-yyyy)", 2);
        Date date;
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            System.out.println("-------Pokus o získanie Reservations zlyhal (neplatný dátum)-------");
            return;
        }
        List<Object> resL = cps.getReservations((long) psId, date);
        if(resL != null) {
            System.out.println(resL);
        } else {
            System.out.println("-------Pokus o získanie Reservations zlyhal-------");
        }
    }

    public void getReservationsByUser() {
        System.out.println("-------Zadaj údaje pre získanie Reservations-------");
        int uId = KeyboardInput.readInt("User ID", 2);
        if(uId < 0) {
            System.out.println("-------Pokus o získanie Reservations zlyhal-------");
            return;
        }
        List<Object> resL = cps.getMyReservations((long) uId);
        if(resL != null) {
            System.out.println(resL);
        } else {
            System.out.println("-------Pokus o získanie Reservations zlyhal-------");
        }
    }

    public void createDiscountCoupon() {
        System.out.println("-------Zadaj údaje pre vytvorenie Coupon-u-------");
        String name = KeyboardInput.readString("Coupon name", 2);
        int discount = KeyboardInput.readInt("Discount (%)", 2);
        if(discount < 0 || discount > 100) {
            System.out.println("-------Pokus o vytvorenie Coupon-u zlyhal-------");
            return;
        }
        if(cps.createDiscountCoupon(name, discount) != null) {
            System.out.println("-------Úspešne vytvorený Coupon-------");
        } else {
            System.out.println("-------Pokus o vytvorenie Coupon-u zlyhal-------");
        }
    }

     public void giveCouponToUser() {
         System.out.println("-------Zadaj údaje pre darovanie Coupon-u-------");
         int uId = KeyboardInput.readInt("User ID", 2);
         if(uId < 0) {
             System.out.println("-------Pokus o darovanie Coupoun-u zlyhal-------");
             return;
         }
         int cId = KeyboardInput.readInt("Coupon ID", 2);
         if(cId < 0) {
             System.out.println("-------Pokus o darovanie Coupoun-u zlyhal-------");
             return;
         }
         cps.giveCouponToUser((long) cId,(long) uId);
         System.out.println("--------------");
     }

    public void getCouponById() {
        System.out.println("-------Zadaj údaje pre získanie Coupon-u-------");
        int cId = KeyboardInput.readInt("Coupon ID", 2);
        if(cId < 0) {
            System.out.println("-------Pokus o získanie Coupoun-u zlyhal-------");
            return;
        }
        DiscountCoupon dc = (DiscountCoupon) cps.getCoupon((long) cId);
        if(dc != null) {
            System.out.println(dc);
        } else {
            System.out.println("-------Pokus o získanie Coupoun-u zlyhal-------");
        }
    }

    public void getCouponsByUser() {
        System.out.println("-------Zadaj údaje pre získanie Coupoun-ov-------");
        int uId = KeyboardInput.readInt("Coupon ID", 2);
        if(uId < 0) {
            System.out.println("-------Pokus o získanie Coupoun-ov zlyhal-------");
            return;
        }
        List<Object> cL = cps.getCoupons((long) uId);
        if(cL != null) {
            System.out.println(cL);
        } else {
            System.out.println("-------Pokus o získanie Coupoun-ov zlyhal-------");
        }
    }

    public void endReservationWithCoupon() {
        System.out.println("-------Zadaj údaje pre ukončenie Reservation-------");
        int rId = KeyboardInput.readInt("Reservation ID", 2);
        if(rId < 0) {
            System.out.println("-------Pokus o ukončenie Reservation zlyhal-------");
            return;
        }
        int cId = KeyboardInput.readInt("Coupon ID", 2);
        if(cId < 0) {
            System.out.println("-------Pokus o ukončenie Reservation zlyhal-------");
            return;
        }
        if(cps.endReservation((long) rId,(long) cId) != null) {
            System.out.println("-------Úspešne ukončenie Reservation-------");
        } else {
            System.out.println("-------Pokus o ukončenie Reservation zlyhal-------");
        }
    }

    public void deleteCoupon() {
        System.out.println("-------Zadaj údaje pre vymazanie Coupon-u-------");
        int id = KeyboardInput.readInt("ID", 2);
        if(id < 0) {
            System.out.println("-------Pokus o zmazanie Coupon-u zlyhal-------");
            return;
        }
        if(cps.deleteCoupon((long) id) != null) {
            System.out.println("-------Úspešne zmazaný Coupon-------");
        } else {
            System.out.println("-------Pokus o zmazanie Coupon-u zlyhal-------");
        }
    }
}
