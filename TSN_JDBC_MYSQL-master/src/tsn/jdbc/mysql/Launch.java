package tsn.jdbc.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class Launch {

    public static void main(String[] args) {
        // РАБОТА С БАЗОЙ ДАННЫХ MYSQL ЧЕРЕЗ JDBC
        try {
            // Адрес нашей базы данных "tsn_demo" на локальном компьютере (localhost)
            String url = "jdbc:mysql://localhost:3306/tre?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

            // Создание свойств соединения с базой данных
            Properties authorization = new Properties();
            authorization.put("user", "root"); // Зададим имя пользователя БД
            authorization.put("password", "root"); // Зададим пароль доступа в БД

            // Создание соединения с базой данных
            Connection connection = DriverManager.getConnection(url, authorization);

            // Создание оператора доступа к базе данных
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            // Выполнение запроса к базе данных, получение набора данных
            ResultSet table = statement.executeQuery("SELECT * FROM sotr");
            
            System.out.println("Начальная БД:");
            table.first(); // Выведем имена полей
            for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                System.out.print(table.getMetaData().getColumnName(j) + "\t\t");
            }
            System.out.println();

            table.beforeFirst(); // Выведем записи таблицы
            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t\t");
                }
                System.out.println();
            }

            Scanner sc = new Scanner(System.in);
            System.out.println("Введите параметры нового поля таблицы:");
            System.out.print("FIO - ");
            String scannedFIO = sc.nextLine();
            System.out.print("Phone_number - ");
            String scannedPhone_number = sc.nextLine();
            
            System.out.println("После добавления:");
            statement.execute("INSERT sotr(FIO, Phone_number) VALUES ('" + scannedFIO + "','" + scannedPhone_number + "')");
            table = statement.executeQuery("SELECT * FROM sotr");

            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t\t");
                }
                System.out.println();
            }

            System.out.println("Строку с каким id хотите удалить?");
            System.out.print("id - ");
            int scannedId = sc.nextInt();
            statement.execute("DELETE FROM sotr WHERE Id = " + scannedId);
            
            System.out.println("После удаления:");
            table = statement.executeQuery("SELECT * FROM sotr");
            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }
            sc.nextLine();
            
            System.out.println("На что изменить в первую строку?");
            System.out.print("FIO - ");
            String scannedFIOUp = sc.nextLine();
            System.out.print("Phone_number - ");
            String scannedPhone_numberUp = sc.nextLine();
            statement.executeUpdate("UPDATE sotr SET FIO = '" + scannedFIOUp + "' WHERE id = 1");
            statement.executeUpdate("UPDATE sotr SET Phone_number = '" + scannedPhone_numberUp + "' WHERE id = 1");
            System.out.println("После изменения:");
            table = statement.executeQuery("SELECT * FROM sotr");

            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }
            ///фильтрация  
            Scanner sca = new Scanner(System.in);
            System.out.println("Введите условие(фильтр) - ");
            String scannedFilter = sca.nextLine();
            
            table = statement.executeQuery("SELECT * FROM sotr WHERE " + scannedFilter);
            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }
            
            if (table != null) { table.close(); } // Закрытие набора данных
            if (statement != null) { statement.close(); } // Закрытие базы данных
            if (connection != null) { connection.close(); } // Отключение от базы данных

        } catch (Exception e) {
            System.err.println("Error accessing database!");
            e.printStackTrace();
        }
    }

}
