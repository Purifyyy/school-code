package sk.stuba.fei.uim.vsa.pr1.tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.stuba.fei.uim.vsa.pr1a.AbstractCarParkService;
import sk.stuba.fei.uim.vsa.pr1.TestData;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static sk.stuba.fei.uim.vsa.pr1.TestData.*;
import static sk.stuba.fei.uim.vsa.pr1.TestUtils.*;

class CarTest {

    private static AbstractCarParkService carParkService;
    private static Connection mysql;

    @BeforeAll
    static void setup() throws SQLException, ClassNotFoundException {
        carParkService = getServiceClass();
        mysql = getMySQL(DB, USERNAME, PASSWORD);
    }

    @BeforeEach
    void beforeEach() {
        clearDB(mysql);
    }

    @Test
    void CAR01_shouldCreateCar() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Object car = createNewCar();
        assertNotNull(car);
        testShouldHaveId(car);
        Object user = null;
        if (hasField(car, "user")) {
            user = getFieldValue(car, "user");
        } else if (hasField(car, "owner")) {
            user = getFieldValue(car, "owner");
        }
        if (user != null) {
            Object foundUser = carParkService.getUser(getEntityId(user));
            assertNotNull(foundUser);
        }
    }

    private Object createNewCar() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Object user = carParkService.createUser(TestData.User.firstName, TestData.User.lastName, TestData.User.email);
        assertNotNull(user);
        return carParkService.createCar(getEntityId(user),
                TestData.Car.brand, TestData.Car.model, TestData.Car.colour, TestData.Car.ecv);
    }

    @Test
    void CAR02_shouldGetCarById() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Object car = createNewCar();
        Object found = carParkService.getCar(getEntityId(car));
        assertNotNull(found);
        assertEquals(getEntityId(car), getEntityId(found));
    }

    @Test
    void CAR02_shouldGetCarByECV() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Object car = createNewCar();
        Object found = carParkService.getCar(TestData.Car.ecv);
        assertNotNull(found);
        assertEquals(getEntityId(car), getEntityId(found));
    }

    @Test
    void CAR04_shouldGetCarsByUser() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Object car = createNewCar();
        List<Object> users = carParkService.getUsers();
        assertEquals(1, users.size());
        List<Object> cars = carParkService.getCars(getEntityId(users.get(0)));
        assertNotNull(cars);
        assertEquals(1, cars.size());
        assertEquals(car.getClass(), cars.get(0).getClass());
        assertEquals(getEntityId(car), getEntityId(cars.get(0)));
    }

    @Test
    void CAR06_shouldDeleteCar() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Object car = createNewCar();
        Object deleted = carParkService.deleteCar(getEntityId(car));
        assertNotNull(deleted);
        try {
            Object notFound = carParkService.getCar(TestData.Car.ecv);
            assertNull(notFound);
        } catch (Exception ex) {
            assertTrue(true);
        }
    }

}
