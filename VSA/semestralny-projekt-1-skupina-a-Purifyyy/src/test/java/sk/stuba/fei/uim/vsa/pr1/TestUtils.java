package sk.stuba.fei.uim.vsa.pr1;

import org.reflections.Reflections;
import sk.stuba.fei.uim.vsa.pr1a.AbstractCarParkService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.reflections.scanners.Scanners.SubTypes;

public class TestUtils {

    public static void log(String msg) {
        System.out.println("[TEST LOG] " + LocalDate.now() + " : " + msg);
    }

    public static Object getFieldValue(Object obj, String field) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method getter = obj.getClass().getMethod("get" + capitalize(field));
        assertNotNull(getter);
        if (getter.getParameterCount() != 0)
            throw new NoSuchMethodException("Retrieved method 'get" + capitalize(field) + "' does not have 0 arguments so it cannot be safely invoked");
        return getter.invoke(obj);
    }

    public static <T> T getFieldValue(Object obj, String field, Class<T> fieldType) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Method getter = obj.getClass().getMethod("get" + capitalize(field));
        assertNotNull(getter);
        if (getter.getParameterCount() != 0)
            throw new NoSuchMethodException("Retrieved method 'get" + capitalize(field) + "' does not have 0 arguments so it cannot be safely invoked");
        if (getter.getReturnType() != fieldType)
            throw new NoSuchMethodException("Retrieved method 'get" + capitalize(field) + "' does not have provided return type " + fieldType.getName());
        return fieldType.cast(getFieldValue(obj, field));
    }

    public static Object setFieldValue(Object obj, String field, Object newValue) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method setter = obj.getClass().getMethod("set" + capitalize(field), newValue.getClass());
        assertNotNull(setter);
        if (setter.getParameterCount() != 1 || (setter.getParameterCount() >= 1 && setter.getParameterTypes()[0] != newValue.getClass()))
            throw new NoSuchMethodException("Retrieved method 'set" + capitalize(field) + "' does not have 1 argument of type '" + newValue.getClass() + "' so it cannot be safely invoked");
        setter.invoke(obj, newValue);
        return obj;
    }

    public static boolean hasField(Object obj, String field) {
        try {
            Method getter = obj.getClass().getMethod("get" + capitalize(field));
            return getter != null && getter.getParameterCount() == 0;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    public static <T> String[] findFieldByNameAndType(Object obj, String fieldContainingString, Class<T> type) {
        return Arrays.stream(obj.getClass().getMethods()).filter(method ->
                        method.getName().startsWith("get") &&
                                method.getName().toLowerCase().contains(fieldContainingString.toLowerCase()) &&
                                method.getParameterCount() == 0 &&
                                method.getReturnType() == type)
                .map(method -> camelCase(method.getName().substring(3)))
                .collect(Collectors.toList()).toArray(new String[]{});
    }

    public static <T> String[] findFieldByType(Object obj, Class<T> type) {
        return Arrays.stream(obj.getClass().getMethods()).filter(method ->
                        method.getName().startsWith("get") &&
                                method.getReturnType() == type &&
                                method.getParameterCount() == 0)
                .map(method -> camelCase(method.getName().substring(3)))
                .collect(Collectors.toList()).toArray(new String[]{});
    }

    public static <T> boolean isFieldNull(Object obj, String field, Class<T> type) {
        try {
            return Objects.isNull(getFieldValue(obj, field, type));
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static String capitalize(String str) {
        if (str.length() == 0) return "";
        if (str.length() == 1) return str.toUpperCase();
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String camelCase(String str) {
        if (str.length() == 0) return "";
        if (str.length() == 1) return str.toLowerCase();
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    public static Long getEntityId(Object obj) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        String[] fields = findFieldByNameAndType(obj, "id", Long.class);
        if (fields != null && fields.length > 0) {
            if (fields.length > 1)
                log("WARNING: More than one potential id field find on object of class " + obj.getClass().getName());
            return getFieldValue(obj, fields[0], Long.class);
        }
        return null;
    }

    public static void testShouldHaveId(Object obj) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Long id = getEntityId(obj);
        assertNotNull(id);
        assertEquals(Long.class, id.getClass());
        assertTrue(id > 0);
    }

    public static AbstractCarParkService getServiceClass() {
        Reflections reflections = new Reflections("sk.stuba.fei.uim.vsa");
        Set<Class<?>> cps = reflections.get(SubTypes.of(AbstractCarParkService.class).asClass());
        assertEquals(1, cps.size());
        return cps.stream().map(clazz -> {
            AbstractCarParkService carParkService = null;
            try {
                carParkService = (AbstractCarParkService) clazz.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                e.printStackTrace();
            }
            assertNotNull(carParkService);
            System.out.println("car park class: " + carParkService.getClass().getName());
            return carParkService;
        }).findFirst().orElse(null);
    }

    public static Connection getMySQL(String db, String username, String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db, username, password);
    }

    public static void runSQLStatement(Connection con, String sql, boolean silent) {
        try (Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException ex) {
            if (!silent)
                ex.printStackTrace();
        }
    }

    public static List<String> tables = new ArrayList<>();

//    public static void clearDB(Connection mysql) {
//        if (tables.isEmpty()) {
//            try (Statement stmt = mysql.createStatement()) {
//                ResultSet set = stmt.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema = (SELECT DATABASE())");
//                while (set.next()) {
//                    String table = set.getString("table_name");
//                    if (!Objects.equals(table, "SEQUENCE")) {
//                        tables.add(table);
//                    }
//                }
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        }
//        runSQLStatement(mysql, "SET FOREIGN_KEY_CHECKS=0", true);
//        tables.forEach(table -> runSQLStatement(mysql, "TRUNCATE TABLE " + table, false));
//        runSQLStatement(mysql, "SET FOREIGN_KEY_CHECKS=1", true);
//    }
    public static void clearDB(Connection mysql) {
        try {
            Statement stmt = mysql.createStatement();
            runSQLStatement(mysql, "SET FOREIGN_KEY_CHECKS=0", true);
            stmt.executeUpdate("DELETE FROM car_park");
            stmt.executeUpdate("DELETE FROM user");
            stmt.executeUpdate("DELETE FROM car");
            stmt.executeUpdate("DELETE FROM car_park_floor");
//            stmt.executeUpdate("DELETE FROM holiday");
            stmt.executeUpdate("DELETE FROM reservation");
            stmt.executeUpdate("DELETE FROM parking_spot");
            runSQLStatement(mysql, "SET FOREIGN_KEY_CHECKS=1", true);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
