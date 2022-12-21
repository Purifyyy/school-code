package sk.stuba.fei.uim.vsa.pr1.tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.stuba.fei.uim.vsa.pr1a.AbstractCarParkService;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static sk.stuba.fei.uim.vsa.pr1.TestData.*;
import static sk.stuba.fei.uim.vsa.pr1.TestUtils.*;

class CarParkFloorTest {

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
    void FLOOR01_createAndGetCarParkFloor() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Object carPark = carParkService.createCarPark(CarPark.name, CarPark.address, CarPark.price);
        assertNotNull(carPark);
        testShouldHaveId(carPark);
        Long carParkId = getEntityId(carPark);

        Object floor = carParkService.createCarParkFloor(carParkId, CarPark.floor);
        assertNotNull(floor);
        testShouldHaveId(floor);
        Long floorId = getEntityId(floor);
        Object found = carParkService.getCarParkFloor(floorId);
        assertNotNull(found);
        testShouldHaveId(found);
        assertEquals(floorId, getEntityId(found));
    }

    @Test
    void FLOOR02_createAndGetAllCarParks() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Object carPark = carParkService.createCarPark(CarPark.name, CarPark.address, CarPark.price);
        assertNotNull(carPark);
        testShouldHaveId(carPark);
        Long carParkId = getEntityId(carPark);

        Object floor = carParkService.createCarParkFloor(carParkId, CarPark.floor);
        assertNotNull(floor);
        testShouldHaveId(floor);
        Object floor2 = carParkService.createCarParkFloor(carParkId, CarPark.floorAlt);
        assertNotNull(floor2);
        testShouldHaveId(floor2);

        List<Object> list = carParkService.getCarParkFloors(carParkId);
        assertNotNull(list);
        assertEquals(2, list.size());
        assertTrue(list.stream().anyMatch(f -> {
            try {
                return Objects.equals(getEntityId(floor), getEntityId(f));
            } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
        }));
        assertTrue(list.stream().anyMatch(f -> {
            try {
                return Objects.equals(getEntityId(floor2), getEntityId(f));
            } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
        }));
    }

    @Test
    void FLOOR03_createAndDeleteCarParkFloor() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Object carPark = carParkService.createCarPark(CarPark.name, CarPark.address, CarPark.price);
        assertNotNull(carPark);
        testShouldHaveId(carPark);
        Long carParkId = getEntityId(carPark);

        Object floor = carParkService.createCarParkFloor(carParkId, CarPark.floor);
        assertNotNull(floor);
        testShouldHaveId(floor);
        Long floorId = getEntityId(floor);

        Object deleted = carParkService.deleteCarParkFloor(floorId);
        assertNotNull(deleted);
        assertEquals(floorId, getEntityId(deleted));
        try {
            Object notFound = carParkService.getCarParkFloor(floorId);
            assertNull(notFound);
        } catch (Exception ex) {
            assertTrue(true);
        }
    }
}
