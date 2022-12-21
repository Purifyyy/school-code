package sk.stuba.fei.uim.vsa.pr1a;

import sk.stuba.fei.uim.vsa.pr1a.entities.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class CarParkService extends AbstractCarParkService {

    private void persist(Object object) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public Object createCarPark(String name, String address, Integer pricePerHour) {
        EntityManager em = emf.createEntityManager();
        try {
            CarPark cpf = (CarPark) em.createQuery("SELECT cp FROM CarPark cp where cp.name = :name")
                    .setParameter("name", name).getSingleResult();
        } catch (NoResultException e) {
            CarPark cp = new CarPark();
            cp.setName(name);
            cp.setAddress(address);
            cp.setPricePerHour(pricePerHour);
            persist(cp);
            return cp;
        }
        return null;
    }

    @Override
    public Object getCarPark(Long carParkId) {
        if(carParkId == null) {
            return null;
        }
        EntityManager em = emf.createEntityManager();
        return em.find(CarPark.class, carParkId);
    }

    @Override
    public Object getCarPark(String carParkName) {
        if(carParkName == null) {
            return null;
        }
        EntityManager em = emf.createEntityManager();
        TypedQuery<CarPark> q = em.createNamedQuery("CarPark.findByName", CarPark.class);
        q.setParameter("name", carParkName);
        return q.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public List<Object> getCarParks() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<CarPark> q = em.createNamedQuery("CarPark.findAll", CarPark.class);
        return new ArrayList<>(q.getResultList());
    }

    @Override
    public Object updateCarPark(Object carPark) {
        EntityManager em = emf.createEntityManager();
        if(carPark != null) {
            if(carPark instanceof CarPark) {
                CarPark cp = (CarPark) carPark;
                CarPark cpDB = (CarPark) getCarPark(cp.getId());
                if(cpDB != null) {
                    CarPark cpName = (CarPark) getCarPark(cp.getName());
                    if(cpName == null) {
                        em.getTransaction().begin();
                        cpDB.setName(cp.getName());
                        cpDB.setAddress(cp.getAddress());
                        cpDB.setPricePerHour(cp.getPricePerHour());
                        em.merge(cpDB);
                        em.getTransaction().commit();
                        return cpDB;
                    } else if(cpName.equals(cpDB)) {
                        em.getTransaction().begin();
                        cpDB.setAddress(cp.getAddress());
                        cpDB.setPricePerHour(cp.getPricePerHour());
                        em.merge(cpDB);
                        em.getTransaction().commit();
                        return cpDB;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Object deleteCarPark(Long carParkId) {
        EntityManager em = emf.createEntityManager();
        CarPark cp = em.find(CarPark.class, carParkId);
        if (cp != null) {
            List<CarParkFloor> fList;
            try {
                fList = em.createQuery("SELECT pf FROM CarParkFloor pf WHERE pf.carParkLocation.id = :cpId", CarParkFloor.class)
                        .setParameter("cpId", carParkId).getResultList();
            } catch (NoResultException e) {
                em.getTransaction().begin();
                em.remove(cp);
                em.getTransaction().commit();
                return cp;
            }
            for(CarParkFloor cpf : fList) {
                deleteCarParkFloor(carParkId, cpf.getId().getFloorIdentifier());
            }
            em.getTransaction().begin();
            em.remove(cp);
            em.getTransaction().commit();
        }
        em.close();
        return cp;
    }

    @Override
    public Object createCarParkFloor(Long carParkId, String floorIdentifier) {
        EntityManager em = emf.createEntityManager();
        CarPark cp = em.find(CarPark.class, carParkId);
        if (cp != null) {
            CarParkFloor carParkFloor = (CarParkFloor) getCarParkFloor(carParkId, floorIdentifier);
            if(carParkFloor == null) {
                CarParkFloor cpf = new CarParkFloor(new CarParkFloorID(cp.getId(), floorIdentifier));
                cpf.setCarParkLocation(cp);
                persist(cpf);
                em.close();
                return cpf;
            }
        }
        return null;
    }

    @Override
    public Object getCarParkFloor(Long carParkId, String floorIdentifier) {
        EntityManager em = emf.createEntityManager();
        try {
            CarParkFloor cpf = em.createQuery("SELECT cpf FROM CarParkFloor cpf JOIN cpf.carParkLocation cp " +
                            "WHERE cp.id = :cId AND :fId = cpf.id.floorIdentifier", CarParkFloor.class)
                    .setParameter("cId", carParkId).setParameter("fId", floorIdentifier).getSingleResult();
            return cpf;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Object getCarParkFloor(Long carParkFloorId) {
        return null;
    }

    @Override
    public List<Object> getCarParkFloors(Long carParkId) {
        if(carParkId == null) {
            return new ArrayList<>();
        }
        EntityManager em = emf.createEntityManager();
        try {
            List<CarParkFloor> cpfs = em.createQuery("SELECT cp.floors FROM CarPark cp WHERE cp.id = :cId", CarParkFloor.class)
                    .setParameter("cId", carParkId).getResultList();
            return new ArrayList<>(cpfs);
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Object updateCarParkFloor(Object carParkFloor) {
        // Nemožno vykonať update, používam kompozitný klúč
        return null;
    }

    @Override
    public Object deleteCarParkFloor(Long carParkId, String floorIdentifier) {
        EntityManager em = emf.createEntityManager();
        CarPark cp = em.find(CarPark.class, carParkId);
        if(cp != null) {
            CarParkFloor cpf = (CarParkFloor) getCarParkFloor(carParkId, floorIdentifier);
            if(cpf != null) {
                List<ParkingSpot> psList;
                try {
                    psList = em.createQuery("SELECT ps FROM ParkingSpot ps WHERE ps.floorLocation.carParkLocation.id = :cpId AND ps.floorLocation.id.floorIdentifier = :pfId", ParkingSpot.class)
                            .setParameter("cpId", carParkId).setParameter("pfId", cpf.getId().getFloorIdentifier()).getResultList();
                } catch (NoResultException e) {
                    em.getTransaction().begin();
                    em.remove(cpf);
                    em.getTransaction().commit();
                    return cpf;
                }
                for (ParkingSpot ps : psList) {
                    deleteParkingSpot(ps.getId());
                }
                em.getTransaction().begin();
                if (!em.contains(cpf)) {
                    cpf = em.merge(cpf);
                }
                em.remove(cpf);
                em.getTransaction().commit();
                return cpf;
            }
        }
        return null;
    }

    @Override
    public Object deleteCarParkFloor(Long carParkFloorId) {
        return null;
    }

    @Override
    public Object createParkingSpot(Long carParkId, String floorIdentifier, String spotIdentifier) {
        EntityManager em = emf.createEntityManager();
        CarPark cp = (CarPark) getCarPark(carParkId);
        if(cp == null) {
            return null;
        }
        CarParkFloor cpf = (CarParkFloor) getCarParkFloor(carParkId, floorIdentifier);
        if(cpf != null) {
            try {
                ParkingSpot ps = em.createQuery("SELECT ps FROM ParkingSpot ps WHERE ps.floorLocation.carParkLocation.id = :cpId AND ps.spotIdentifier = :sId", ParkingSpot.class)
                        .setParameter("cpId", carParkId).setParameter("sId", spotIdentifier).getSingleResult();
                return null;
            } catch (NoResultException e) {
                ParkingSpot ps = new ParkingSpot();
                ps.setSpotIdentifier(spotIdentifier);
                ps.setFloorLocation(cpf);
                persist(ps);
                return ps;
            }
        }
        return null;
    }

    @Override
    public Object getParkingSpot(Long parkingSpotId) {
        if(parkingSpotId == null) {
            return null;
        }
        return emf.createEntityManager().find(ParkingSpot.class, parkingSpotId);
    }

    @Override
    public List<Object> getParkingSpots(Long carParkId, String floorIdentifier) {
        if(carParkId == null || floorIdentifier == null) {
            return new ArrayList<>();
        }
        CarParkFloor cpf = (CarParkFloor) getCarParkFloor(carParkId, floorIdentifier);
        if(cpf != null) {
            return new ArrayList<>(cpf.getSpots());
        }
        return new ArrayList<>();
    }

    @Override
    public Map<String, List<Object>> getParkingSpots(Long carParkId) {
        if(carParkId == null) {
            return new HashMap<>();
        }
        CarPark cp = (CarPark) getCarPark(carParkId);
        if(cp != null) {
            Map<String, List<Object>> spots = new HashMap<>();
            for(CarParkFloor cpf : cp.getFloors()) {
                spots.put(cpf.getId().getFloorIdentifier(), new ArrayList<>(cpf.getSpots()));
            }
            return spots;
        }
        return new HashMap<>();
    }

    @Override
    public Map<String, List<Object>> getAvailableParkingSpots(String carParkName) {
        if(carParkName == null) {
            return new HashMap<>();
        }
        EntityManager em = emf.createEntityManager();
        CarPark cp = (CarPark) getCarPark(carParkName);
        if (cp == null) {
            return new HashMap<>();
        }
        Map<String, List<Object>> availableSpots = new HashMap<>();
        List<ParkingSpot> spots;
        try {
            spots = em.createQuery("SELECT ps FROM ParkingSpot ps WHERE ps.floorLocation.carParkLocation.id = :cpId", ParkingSpot.class)
                    .setParameter("cpId", cp.getId()).getResultList();
        } catch (NoResultException e) {
            return new HashMap<>();
        }
        for(ParkingSpot ps : spots) {
            availableSpots.putIfAbsent(ps.getFloorLocation().getId().getFloorIdentifier(), new ArrayList<>(ps.getFloorLocation().getSpots()));
        }
        List<Reservation> reservations;
        try {
            reservations = em.createQuery("SELECT res FROM Reservation res WHERE (res.endedAt = null AND res.parkingSpot.floorLocation.carParkLocation.id = :cpId)", Reservation.class)
                    .setParameter("cpId", cp.getId()).getResultList();
        } catch (NoResultException e) {
            return availableSpots;
        }
        for (Map.Entry<String,List<Object>> entry : availableSpots.entrySet()) {
            List<Object> floorList = entry.getValue();
            for(Reservation r : reservations) {
                floorList.remove(r.getParkingSpot());
            }
            entry.setValue(floorList);
        }
        return availableSpots;
    }

    @Override
    public Map<String, List<Object>> getOccupiedParkingSpots(String carParkName) {
        EntityManager em = emf.createEntityManager();
        CarPark cp = (CarPark) getCarPark(carParkName);
        if (cp == null) {
            return new HashMap<>();
        }
        Map<String, List<Object>> occupiedSpots = new HashMap<>();
        List<Reservation> reservations;
        try {
            reservations = em.createQuery("SELECT res FROM Reservation res WHERE (res.endedAt = null AND res.parkingSpot.floorLocation.carParkLocation.id = :cpId)", Reservation.class)
                    .setParameter("cpId", cp.getId()).getResultList();
        } catch (NoResultException e) {
            return occupiedSpots;
        }
        for (Reservation r : reservations) {
            List<Object> parkingSpotList = occupiedSpots.computeIfAbsent(r.getParkingSpot().getFloorLocation().getId().getFloorIdentifier(), k -> new ArrayList<>());
            parkingSpotList.add(r.getParkingSpot());
        }
        return occupiedSpots;
    }

    @Override
    public Object updateParkingSpot(Object parkingSpot) {
        EntityManager em = emf.createEntityManager();
        if(parkingSpot != null) {
            if(parkingSpot instanceof ParkingSpot) {
                ParkingSpot ps = (ParkingSpot) parkingSpot;
                ParkingSpot psDB = (ParkingSpot) getParkingSpot(ps.getId());
                if(psDB != null) {
                    if(Objects.equals(ps.getSpotIdentifier(), psDB.getSpotIdentifier())) {
                        return psDB;
                    } else {
                        CarPark location = psDB.getFloorLocation().getCarParkLocation();
                        try {
                            ParkingSpot psIdn = em.createQuery("SELECT ps FROM ParkingSpot ps WHERE ps.spotIdentifier = :psId AND ps.floorLocation.carParkLocation.id = :cpId", ParkingSpot.class)
                                    .setParameter("psId", ps.getSpotIdentifier()).setParameter("cpId", location.getId()).getSingleResult();
                        } catch (NoResultException e) {
                            em.getTransaction().begin();
                            psDB.setSpotIdentifier(ps.getSpotIdentifier());
                            em.merge(psDB);
                            em.getTransaction().commit();
                            return psDB;
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Object deleteParkingSpot(Long parkingSpotId) {
        EntityManager em = emf.createEntityManager();
        ParkingSpot ps = em.find(ParkingSpot.class, parkingSpotId);
        if (ps != null) {
            List<Reservation> reservationList;
            try {
                reservationList = em.createQuery("SELECT res FROM Reservation res WHERE res.parkingSpot.id = :psId", Reservation.class)
                        .setParameter("psId", parkingSpotId).getResultList();
            } catch (NoResultException e) {
                em.getTransaction().begin();
                em.remove(ps);
                em.getTransaction().commit();
                return ps;
            }
            for (Reservation r : reservationList) {
                if(r.getEndedAt() == null) {
                    endReservation(r.getId());
                }
                em.getTransaction().begin();
                r.setParkingSpot(null);
                em.getTransaction().commit();
            }
            em.getTransaction().begin();
            em.remove(ps);
            em.getTransaction().commit();
            return ps;
        }
        return null;
    }

    @Override
    public Object createCar(Long userId, String brand, String model, String colour, String vehicleRegistrationPlate) {
        Customer c = (Customer) getUser(userId);
        if(c == null) {
            return null;
        }
        Car car = (Car) getCar(vehicleRegistrationPlate);
        if(car != null) {
            return null;
        }
        car = new Car(vehicleRegistrationPlate, brand, model, colour);
        car.setOwner(c);
        persist(car);
        return car;
    }

    @Override
    public Object getCar(Long carId) {
        if(carId == null) {
            return null;
        }
        EntityManager em = emf.createEntityManager();
        return em.find(Car.class, carId);
    }

    @Override
    public Object getCar(String vehicleRegistrationPlate) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Car> q = em.createNamedQuery("Car.findByRegistrationNumber", Car.class);
        q.setParameter("plate", vehicleRegistrationPlate);
        return q.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public List<Object> getCars(Long userId) {
        Customer c = (Customer) getUser(userId);
        if(c == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(c.getCars());
    }

    @Override
    public Object updateCar(Object car) {
        EntityManager em = emf.createEntityManager();
        if(car != null) {
            if(car instanceof Car) {
                Car c = (Car) car;
                Car cDB = (Car) getCar(c.getId());
                if(cDB != null) {
                    Car cReg = (Car) getCar(c.getRegistrationNumber());
                    if(cReg == null) {
                        if(c.getOwner() != null) {
                            em.getTransaction().begin();
                            em.merge(c);
                            em.getTransaction().commit();
                            return cDB;
                        }
                    } else if(cReg.equals(cDB)) {
                        if(c.getOwner() != null) {
                            em.getTransaction().begin();
                            em.merge(c);
                            em.getTransaction().commit();
                            return cDB;
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Object deleteCar(Long carId) {
        EntityManager em = emf.createEntityManager();
        Car car = (Car) getCar(carId);
        if(car != null) {
            List<Reservation> reservationList;
            Customer c = (Customer) getUser(car.getOwner().getId());
            List<Car> vehicleList = c.getCars();
            try {
                reservationList = em.createQuery("SELECT res FROM Reservation res WHERE res.car.id = :cId", Reservation.class)
                        .setParameter("cId", car.getId()).getResultList();
            } catch (NoResultException e) {
                em.getTransaction().begin();
                vehicleList.remove(car);
                c.setCars(vehicleList);
                em.remove(car);
                em.getTransaction().commit();
                return car;
            }
            for (Reservation r : reservationList) {
                if(r.getEndedAt() == null) {
                    endReservation(r.getId());
                }
                em.getTransaction().begin();
                r.setCar(null);
                em.getTransaction().commit();
            }
            em.getTransaction().begin();
            vehicleList.remove(car);
            c.setCars(vehicleList);
            if (!em.contains(car)) {
                car = em.merge(car);
            }
            em.remove(car);
            em.getTransaction().commit();
            return car;
        }
        return null;
    }

    @Override
    public Object createUser(String firstname, String lastname, String email) {
        Customer c = (Customer) getUser(email);
        if(c != null) {
            return null;
        }
        c = new Customer(firstname, lastname, email);
        persist(c);
        return c;
    }

    @Override
    public Object getUser(Long userId) {
        EntityManager em = emf.createEntityManager();
        return em.find(Customer.class, userId);
    }

    @Override
    public Object getUser(String email) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Customer> q = em.createNamedQuery("Customer.findByEmail", Customer.class);
        q.setParameter("email", email);
        return q.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public List<Object> getUsers() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Customer> q = em.createNamedQuery("Customer.findAll", Customer.class);
        return new ArrayList<>(q.getResultList());
    }

    @Override
    public Object updateUser(Object user) {
        EntityManager em = emf.createEntityManager();
        if(user != null) {
            if(user instanceof Customer) {
                Customer c = (Customer) user;
                Customer cDB = (Customer) getUser(c.getId());
                if(cDB != null) {
                    Customer cEmail = (Customer) getUser(c.getEmail());
                    if(cEmail == null) {
                        em.getTransaction().begin();
                        cDB.setName(c.getName());
                        cDB.setSurname(c.getSurname());
                        cDB.setEmail(c.getEmail());
                        em.merge(cDB);
                        em.getTransaction().commit();
                        return cDB;
                    } else if(cEmail.equals(cDB)) {
                        em.getTransaction().begin();
                        cDB.setName(c.getName());
                        cDB.setSurname(c.getSurname());
                        em.merge(cDB);
                        em.getTransaction().commit();
                        return cDB;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Object deleteUser(Long userId) {
        EntityManager em = emf.createEntityManager();
        Customer c = em.find(Customer.class, userId);
        if (c != null) {
            List<Car> userCars;
            try {
                userCars = em.createQuery("SELECT car FROM Car car WHERE car.owner.id = :uId", Car.class)
                        .setParameter("uId", userId).getResultList();
            } catch (NoResultException e) {
                em.getTransaction().begin();
                em.remove(c);
                em.getTransaction().commit();
                return c;
            }
            for(Car car : userCars) {
                deleteCar(car.getId());
            }
            em.getTransaction().begin();
            em.remove(c);
            em.getTransaction().commit();
            return c;
        }
        return null;
    }

    @Override
    public Object createReservation(Long parkingSpotId, Long cardId) {
        EntityManager em = emf.createEntityManager();
        ParkingSpot ps = (ParkingSpot) getParkingSpot(parkingSpotId);
        Car c = (Car) getCar(cardId);
        if(ps == null || c == null) {
            return null;
        }
        try {
            Reservation r = em.createQuery("SELECT res FROM Reservation res WHERE (res.car.id = :cId AND res.endedAt = null) OR (res.parkingSpot.id = :psId AND res.endedAt = null)", Reservation.class)
                    .setParameter("cId", cardId).setParameter("psId", parkingSpotId).getSingleResult();
            return null;
        } catch (NoResultException e) {
            Reservation r = new Reservation(new Date(), ps, c);
            persist(r);
            return r;
        }
    }

    /**
     * Get a diff between two dates
     * @param date1 the oldest date
     * @param date2 the newest date
     * @param timeUnit the unit in which you want the diff
     * @return the diff value, in the provided unit
     *
     * ZDROJ: https://stackoverflow.com/a/10650881/10768248
     */
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }

    @Override
    public Object endReservation(Long reservationId) {
        EntityManager em = emf.createEntityManager();
        Reservation r = em.find(Reservation.class, reservationId);
        if(r == null) {
            return null;
        }
        if(r.getEndedAt() != null) {
            return null;
        }
        em.getTransaction().begin();
        r.setEndedAt(new Date());
        r.setPrice((double)(getDateDiff(r.getStartedAt(), r.getEndedAt(), TimeUnit.HOURS) + 1) *
                r.getParkingSpot().getFloorLocation().getCarParkLocation().getPricePerHour());
        em.getTransaction().commit();
        return r;
    }

    /**
    * ZDROJ: https://www.baeldung.com/java-check-two-dates-on-same-day
    */
    public boolean isSameDay(Date date1, Date date2) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(date1).equals(fmt.format(date2));
    }

    @Override
    public List<Object> getReservations(Long parkingSpotId, Date date) {
        EntityManager em = emf.createEntityManager();
        ParkingSpot ps = em.find(ParkingSpot.class, parkingSpotId);
        if(ps == null) {
            return new ArrayList<>();
        }
        TypedQuery<Reservation> q = em.createNamedQuery("Reservation.findAllByParkingSpot", Reservation.class);
        q.setParameter("pId", parkingSpotId);
        List<Reservation> reservations = q.getResultList();
        reservations.removeIf(res -> !(isSameDay(res.getStartedAt(), date)));
        return new ArrayList<>(reservations);
    }

    @Override
    public List<Object> getMyReservations(Long userId) {
        EntityManager em = emf.createEntityManager();
        Customer c = (Customer) getUser(userId);
        if(c == null) {
            return new ArrayList<>();
        }
        try {
            List <Reservation> reservations = em.createQuery("SELECT res FROM Reservation res WHERE res.car.owner.id = :uId AND res.endedAt = null", Reservation.class)
                    .setParameter("uId", userId).getResultList();
            return new ArrayList<>(reservations);
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Object updateReservation(Object reservation) {
        EntityManager em = emf.createEntityManager();
        if(reservation != null) {
            if(reservation instanceof Reservation) {
                Reservation res = (Reservation) reservation;
                Reservation resDB = em.find(Reservation.class, res.getId());
                if(resDB != null) {
                    em.getTransaction().begin();
                    em.merge(res);
                    em.getTransaction().commit();
                    return resDB;
                }
            }
        }
        return null;
    }

    @Override
    public Object createDiscountCoupon(String name, Integer discount) {
        DiscountCoupon dc = new DiscountCoupon(name, discount);
        persist(dc);
        return dc;
    }

    @Override
    public void giveCouponToUser(Long couponId, Long userId) {
        EntityManager em = emf.createEntityManager();
        Customer c = (Customer) getUser(userId);
        DiscountCoupon coupon = (DiscountCoupon) getCoupon(couponId);
        if(c == null || coupon == null) {
            return;
        }
        if(coupon.getHolder() == null) {
            em.getTransaction().begin();
            coupon.setHolder(c);
            em.merge(coupon);
            em.getTransaction().commit();
        }
    }

    @Override
    public Object getCoupon(Long couponId) {
        return emf.createEntityManager().find(DiscountCoupon.class, couponId);
    }

    @Override
    public List<Object> getCoupons(Long userId) {
        EntityManager em = emf.createEntityManager();
        Customer c = (Customer) getUser(userId);
        if (c == null) {
            return new ArrayList<>();
        }
        List<DiscountCoupon> couponList;
        try {
            couponList = em.createQuery("SELECT dc FROM DiscountCoupon dc WHERE dc.holder.id = :cId", DiscountCoupon.class)
                    .setParameter("cId", userId).getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
        return new ArrayList<>(couponList);
    }

    @Override
    public Object endReservation(Long reservationId, Long couponId) {
        EntityManager em = emf.createEntityManager();
        Reservation r = em.find(Reservation.class, reservationId);
        DiscountCoupon dc = em.find(DiscountCoupon.class, couponId);
        if(r == null) {
            return null;
        }
        r.getCar().getOwner().getCoupons().size();
        if(dc == null || !(r.getCar().getOwner().getCoupons().contains(dc))) {
            return endReservation(reservationId);
        }
        if(r.getEndedAt() != null) {
            return null;
        }
        em.getTransaction().begin();
        r.setEndedAt(new Date());
        double priceToBe = ((double)(getDateDiff(r.getStartedAt(), r.getEndedAt(), TimeUnit.HOURS) + 1) *
                r.getParkingSpot().getFloorLocation().getCarParkLocation().getPricePerHour());
        double discountAmount = priceToBe * ((double)dc.getPercentageDiscount() / 100);
        r.setPrice(priceToBe-discountAmount);
        dc.setHolder(null);
        em.merge(dc);
        r.setCoupon(dc);
        em.getTransaction().commit();
        return r;
    }

    @Override
    public Object deleteCoupon(Long couponId) {
        EntityManager em = emf.createEntityManager();
        DiscountCoupon dc = (DiscountCoupon) getCoupon(couponId);
        if(dc != null) {
            Customer c = (Customer) getUser(dc.getHolder().getId());
            if(c == null) {
                return dc;
            }
            em.getTransaction().begin();
            dc.setHolder(null);
            em.merge(dc);
            em.getTransaction().commit();
            return dc;
        }
        return null;
    }
}
