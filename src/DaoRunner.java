import dao.*;
import dto.UserFilter;
import entity.*;
import entity.Number;
import exception.AuthorizeException;
import exception.DaoException;
import exception.FunctionException;
import utils.ConnectionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DaoRunner {
    private static final String DoZvonokSql = """
            SELECT * FROM do_zvonok(?);
            """;
    private static final String BlockAbonentsSql = """
            SELECT * FROM block_abonents();
            """;
    private static final String UnlockAbonentsSql = """
            SELECT * FROM unlock_abonents();
            """;
    private static final String PopolnitCheckSql = """
            SELECT * FROM popolnit_check(?, ?);
            """;
    public static void run() {
        int table = -1;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя и пароль");
        String name = scanner.nextLine();
        String pass = scanner.nextLine();
        if ((name.equals("postgres") || name.equals("user2")) && pass.equals("English56")) {
            while (table != 0) {
                if (name.equals("user2")) {
                    System.out.println("Выберите таблицу от 1 до 5 или введите 0 для выхода");
                    System.out.println("Номера");
                    System.out.println("Абоненты");
                    System.out.println("Информация о пользователях");
                    System.out.println("Услуги");
                    System.out.println("Услуги подключенные к абонентам");
                    table = scanner.nextInt();
                    if (table == 1) {
                        System.out.println("Выберите что сделать от 1 до 2");
                        System.out.println("Посмотреть номера");
                        System.out.println("Найти номер по id");
                        int d = scanner.nextInt();
                        if (d == 1) {
                            findAllNumbers();
                        }
                        if (d == 2) {
                            System.out.println("Введите id");
                            findByIdNumber(scanner.nextLong());
                        }
                    }
                    if (table == 2) {
                        System.out.println("Выберите что сделать от 1 до 6");
                        System.out.println("Посмотреть абонентов");
                        System.out.println("Добавить абонента");
                        System.out.println("Найти абонента по id");
                        System.out.println("Изменить номер");
                        System.out.println("Совершить звонок");
                        System.out.println("Пополнить счет");
                        System.out.println("Удалить абонента");
                        int d = scanner.nextInt();
                        if (d == 1) {
                            findAllAbonents();
                        }
                        if (d == 2) {
                            System.out.println("Введите id номера");
                            Long id_n = scanner.nextLong();
                            System.out.println("Введите сумму счета");
                            Long sum = scanner.nextLong();
                            System.out.println("Введите id юзера");
                            Long id_u = scanner.nextLong();
                            saveAbonentTest(id_n, sum, 0, "on", id_u);
                        }
                        if (d == 3) {
                            System.out.println("Введите id ");
                            Long id_n = scanner.nextLong();
                            findByIdAbonent(id_n);
                        }
                        if (d == 4) {
                            System.out.println("Введите номер и id");
                            replaceNumber(scanner.nextLong(), scanner.nextLong());
                        }
                        if (d == 5) {
                            System.out.println("Введите id");
                            doZvonok(scanner.nextLong());
                        }
                        if (d == 6) {
                            System.out.println("Введите сумму и id");
                            AddCheck(scanner.nextLong(), scanner.nextLong());
                        }
                        if (d == 7) {
                            System.out.println("Введите id");
                            deleteAbonentTest(scanner.nextLong());
                        }
                    }
                    if (table == 3) {
                        System.out.println("Посмотреть всю информацию о пользователях");
                        System.out.println("Добавить пользователя");
                        System.out.println("Изменить пользователя");
                        System.out.println("Удалить пользователя");
                        int d = scanner.nextInt();
                        if (d == 1) {
                            findAllUsers();
                        }
                        if (d == 2) {
                            saveUserTest(scanner.nextLine(), scanner.next());
                        }
                        if (d == 3) {
                            updateUserTest(scanner.nextLine(), scanner.nextLong());
                        }
                        if (d == 4) {
                            deleteUserTest(scanner.nextLong());
                        }
                    }
                    if (table == 4) {
                        System.out.println("Введите цифру от 1 до 2");
                        System.out.println("Посмотреть все услуги");
                        System.out.println("Найти услугу по id");
                        int usluga = scanner.nextInt();
                        if (usluga == 1) {
                            findAllUslugi();
                        }
                        if (usluga == 2) {
                            findByIdUslugi(scanner.nextLong());
                        }
                    }
                    if (table == 5) {
                        System.out.println("Посмотреть все");
                        System.out.println("Найти по id");
                        System.out.println("Добавить");
                        System.out.println("Удалить");
                        int ua = scanner.nextInt();
                        if (ua == 1) {
                            findAllUslugiAbonents();
                        }
                        if (ua == 2) {
                            findByIdUslugiAbonents(scanner.nextLong());
                        }
                        if (ua == 3) {
                            System.out.println("Введите id услуги и абонента");
                            saveUslugiAbonents(scanner.nextLong(), scanner.nextLong());
                        }
                        if (ua == 4) {
                            deleteUslugiAbonentsTest(scanner.nextLong());
                        }
                    }
                } else {
                    System.out.println("Выберите таблицу от 1 до 5 или введите 0 для выхода");
                    System.out.println("Номера");
                    System.out.println("Абоненты");
                    System.out.println("Информация о пользователях");
                    System.out.println("Услуги");
                    System.out.println("Услуги подключенные к абонентам");
                    table = scanner.nextInt();
                    if (table == 1) {
                        System.out.println("Выберите что сделать от 1 до 5");
                        System.out.println("Посмотреть номера");
                        System.out.println("Найти номер по id");
                        System.out.println("Изменить номер");
                        System.out.println("Удалить номер");
                        System.out.println("Добавить номер");
                        System.out.println("");
                        int d = scanner.nextInt();
                        if (d == 1) {
                            findAllNumbers();
                        }
                        if (d == 2) {
                            System.out.println("Введите id");
                            findByIdNumber(scanner.nextLong());
                        }
                        if (d == 3) {
                            System.out.println("Введите новый номер и id");
                            updateNumberTest(scanner.nextLong(), scanner.nextLong());
                        }
                        if (d == 4) {
                            System.out.println("Введите id номера");
                            deleteNumberTest(scanner.nextLong());
                        }
                        if (d == 5 ) {
                            System.out.println("Введите описание и новый номер");
                            saveNumberTest(scanner.nextLine(), scanner.nextLong());
                        }
                    }
                    if (table == 2) {
                        System.out.println("Выберите что сделать от 1 до 10");
                        System.out.println("Посмотреть абонентов");
                        System.out.println("Добавить абонента");
                        System.out.println("Найти абонента по id");
                        System.out.println("Изменить номер");
                        System.out.println("Совершить звонок");
                        System.out.println("Пополнить счет");
                        System.out.println("Удалить абонента");
                        System.out.println("Изменить абонента");
                        System.out.println("Отключить абонентов");
                        System.out.println("Подключить абонентов");
                        int d = scanner.nextInt();
                        if (d == 1) {
                            findAllAbonents();
                        }
                        if (d == 2) {
                            System.out.println("Введите id номера");
                            Long id_n = scanner.nextLong();
                            System.out.println("Введите сумму счета");
                            Long sum = scanner.nextLong();
                            System.out.println("Введите id юзера");
                            Long id_u = scanner.nextLong();
                            saveAbonentTest(id_n, sum, 0, "on", id_u);
                        }
                        if (d == 3) {
                            System.out.println("Введите id ");
                            Long id_n = scanner.nextLong();
                            findByIdAbonent(id_n);
                        }
                        if (d == 4) {
                            System.out.println("Введите номер и id");
                            replaceNumber(scanner.nextLong(), scanner.nextLong());
                        }
                        if (d == 5) {
                            System.out.println("Введите id");
                            doZvonok(scanner.nextLong());
                        }
                        if (d == 6) {
                            System.out.println("Введите сумму и id");
                            AddCheck(scanner.nextLong(), scanner.nextLong());
                        }
                        if (d == 7) {
                            System.out.println("Введите id");
                            deleteAbonentTest(scanner.nextLong());
                        }
                        if (d == 8) {
                            System.out.println("Введите abonentId, numberId, check, minutes, switch0, userId");
                            updateAbonent(scanner.nextLong(), scanner.nextLong(), scanner.nextLong(), scanner.nextInt(),
                                    scanner.nextLine(), scanner.nextLong());
                        }
                        if (d == 9) {
                            BlockAbonents();
                        }
                        if (d == 10) {
                            UnlockAbonents();
                        }
                    }
                    if (table == 3) {
                        System.out.println("Введите цифру от 1 до 5");
                        System.out.println("Посмотреть всю информацию о пользователях");
                        System.out.println("Добавить пользователя");
                        System.out.println("Изменить пользователя");
                        System.out.println("Удалить пользователя");
                        System.out.println("Найти пользователя по id");
                        int d = scanner.nextInt();
                        if (d == 1) {
                            findAllUsers();
                        }
                        if (d == 2) {
                            saveUserTest(scanner.nextLine(), scanner.next());
                        }
                        if (d == 3) {
                            updateUserTest(scanner.nextLine(), scanner.nextLong());
                        }
                        if (d == 4) {
                            deleteUserTest(scanner.nextLong());
                        }
                        if (d == 5) {
                            findByIdUser(scanner.nextLong());
                        }
                    }
                    if (table == 4) {
                        System.out.println("Введите цифру от 1 до 5");
                        System.out.println("Посмотреть все услуги");
                        System.out.println("Найти услугу по id");
                        System.out.println("Удалить услугу");
                        System.out.println("Добавить услугу");
                        System.out.println("Изменить услугу");
                        int usluga = scanner.nextInt();
                        if (usluga == 1) {
                            findAllUslugi();
                        }
                        if (usluga == 2) {
                            findByIdUslugi(scanner.nextLong());
                        }
                        if (usluga == 3) {
                            deleteUslugiTest(scanner.nextLong());
                        }
                        if (usluga == 4) {
                            System.out.println("Введите название услуги и тариф");
                            saveUslugiTest(scanner.nextLine(), scanner.nextLong());
                        }
                        if (usluga == 5) {
                            System.out.println("Введите название услуги, тариф и id ");
                            updateUslugiTest(scanner.nextLine(), scanner.nextLong(), scanner.nextLong());
                        }
                    }
                    if (table == 5) {
                        System.out.println("Введите цифру от 1 до 5");
                        System.out.println("Посмотреть все");
                        System.out.println("Найти по id");
                        System.out.println("Добавить");
                        System.out.println("Удалить");
                        System.out.println("Изменить");
                        int ua = scanner.nextInt();
                        if (ua == 1) {
                            findAllUslugiAbonents();
                        }
                        if (ua == 2) {
                            findByIdUslugiAbonents(scanner.nextLong());
                        }
                        if (ua == 3) {
                            System.out.println("Введите id услуги и абонента");
                            saveUslugiAbonents(scanner.nextLong(), scanner.nextLong());
                        }
                        if (ua == 4) {
                            deleteUslugiAbonentsTest(scanner.nextLong());
                        }
                        if (ua == 5) {
                            System.out.println("Введите id, idUslugi, idAbonent");
                            updateUslugiAbonents(scanner.nextLong(), scanner.nextLong(),scanner.nextLong());
                        }
                    }
                }
            }
        } else {
            throw new AuthorizeException("Неверные данные");
        }
    }

    public static void updateUserTest(String fio, Long id) {
        var user = UserDao.getInstance().findByid(id);
        System.out.println(user.orElse(null));
        user.ifPresent(user1 -> {
            user1.setFio(fio);
            UserDao.getInstance().update(user1);
        });
    }
    public static void updateNumberTest(Long numberr, Long idd) {
        var num = NumberDao.getInstance().findByid(idd);
        System.out.println(num.orElse(null));
        num.ifPresent(number -> {
            number.setNumber(numberr);
            NumberDao.getInstance().update(number);
        });
    }
    public static void updateUslugiTest(String nameUslugi, Long tar, Long idd) {
        var uslugi = UslugiDao.getInstance().findByid(idd);
        System.out.println(uslugi.orElse(null));
        uslugi.ifPresent(uslugi1 -> {
            uslugi1.setTarif(tar);
            uslugi1.setName_uslugi(nameUslugi);
            UslugiDao.getInstance().update(uslugi1);
        });
    }
    public static void updateAbonent(Long abonentId, Long numberId, Long check, Integer minutes,
                                     String switchO, Long userId) {
        var abonent = AbonentDao.getInstance().findByid(abonentId);
        System.out.println(abonent.orElse(null));
        abonent.ifPresent(abonent1 -> {
            abonent1.setNumber(NumberDao.getInstance().findByid(numberId).orElse(null));
            abonent1.setCheck(check);
            abonent1.setMinutes(minutes);
            abonent1.setSwitchO(switchO);
            abonent1.setUser(UserDao.getInstance().findByid(userId).orElse(null));
            AbonentDao.getInstance().update(abonent1);
        });
    }
    public static void updateUslugiAbonents(Long id, Long uslugiId, Long abonentId) {
        var uslugiAbonents = UslugiAbonentsDao.getInstance().findByid(id);
        System.out.println(uslugiAbonents.orElse(null));
        uslugiAbonents.ifPresent(uslugiAbonents1 -> {
            uslugiAbonents1.setAbonent(AbonentDao.getInstance().findByid(abonentId).orElse(null));
            uslugiAbonents1.setUslugi(UslugiDao.getInstance().findByid(uslugiId).orElse(null));
        });
    }

    public static void saveUserTest(String fio, String pol) {
        var UserDao = dao.UserDao.getInstance();
        var user = new User();
        user.setFio(fio);
        user.setPol(pol);
        var saved = UserDao.save(user);
        System.out.println(saved.getUserId());
    }
    public static void saveNumberTest(String description, Long num) {
        var NumberDao = dao.NumberDao.getInstance();
        var number = new Number();
        number.setDescription(description);
        number.setNumber(num);
        var saved = NumberDao.save(number);
        System.out.println(saved.getNumbers_id());
    }
    public static void saveUslugiTest(String name_uslugi, Long tar) {
        var UslugiDao = dao.UslugiDao.getInstance();
        var uslugi = new Uslugi();
        uslugi.setName_uslugi(name_uslugi);
        uslugi.setTarif(tar);
        var saved = UslugiDao.save(uslugi);
        System.out.println(saved.getUslugi_id());
    }
    public static void saveAbonentTest(Long numberId, Long check, Integer minutes,
                                       String switchO, Long userId) {
        var AbonentDao = dao.AbonentDao.getInstance();
        var abonent = new Abonent();
        abonent.setNumber(NumberDao.getInstance().findByid(numberId).orElse(null));
        abonent.setCheck(check);
        abonent.setMinutes(minutes);
        abonent.setSwitchO(switchO);
        abonent.setUser(UserDao.getInstance().findByid(userId).orElse(null));
        var saved = AbonentDao.save(abonent);
        System.out.println(saved.getAbonentId());
    }
    public static void saveUslugiAbonents(Long uslugiId, Long abonentId) {
        var UslugiAbonentsDao = dao.UslugiAbonentsDao.getInstance();
        var uslugiAbonents = new UslugiAbonents();
        uslugiAbonents.setUslugi(dao.UslugiDao.getInstance().findByid(uslugiId).orElse(null));
        uslugiAbonents.setAbonent(AbonentDao.getInstance().findByid(abonentId).orElse(null));
        var saved = UslugiAbonentsDao.save(uslugiAbonents);
        System.out.println(saved.getId());
    }
    public static void deleteUserTest(Long id) {
        System.out.println(UserDao.getInstance().delete(id));
    }
    public static void deleteNumberTest(Long id) {
        System.out.println(NumberDao.getInstance().delete(id));
    }
    public static void deleteUslugiTest(Long id) {
        System.out.println(UslugiDao.getInstance().delete(id));
    }
    public static void deleteAbonentTest(Long id) {
        System.out.println(AbonentDao.getInstance().delete(id));
    }
    public static void deleteUslugiAbonentsTest(Long id) {
        System.out.println(UslugiAbonentsDao.getInstance().delete(id));
    };
    public static void findAllAbonents() {
        var abonents = AbonentDao.getInstance().findAll();
        for (Abonent abonent : abonents) {
            System.out.println("abonentId " + abonent.getAbonentId());
            System.out.println(abonent.getNumber());
            System.out.println("check " + abonent.getCheck());
            System.out.println("minutes " + abonent.getMinutes());
            System.out.println("switch? " + abonent.getSwitchO());
            System.out.println(abonent.getUser());
            System.out.println("-----------------------------------");
        }
    }
    public static void findAllNumbers() {
        var numbers = NumberDao.getInstance().findAll();
        for (Number number : numbers) {
            System.out.println("numbersId " + number.getNumbers_id());
            System.out.println("number " + number.getNumber());
            System.out.println("description " + number.getDescription());
            System.out.println("-----------------------------------");
        }
    }
    public static void findAllUsers() {
        var users = UserDao.getInstance().findAll();
        for (User user : users) {
            System.out.println("userId" + user.getUserId());
            System.out.println("fio " + user.getFio());
            System.out.println("pol " + user.getPol());
            System.out.println("-----------------------------------");
        }
    }
    public static void findAllUslugi() {
        var uslugi = UslugiDao.getInstance().findAll();
        for (Uslugi usluga : uslugi) {
            System.out.println("uslugiId " + usluga.getUslugi_id());
            System.out.println("nameUslugi " + usluga.getName_uslugi());
            System.out.println("tarif " + usluga.getTarif());
            System.out.println("-----------------------------------");
        }
    }
    public static void findAllUslugiAbonents() {
        var uslugiAbonents = UslugiAbonentsDao.getInstance().findAll();
        for (UslugiAbonents uslugaAbonents : uslugiAbonents) {
            System.out.println("id " + uslugaAbonents.getId());
            System.out.println(uslugaAbonents.getAbonent());
            System.out.println(uslugaAbonents.getUslugi());
            System.out.println("-----------------------------------");
        }
    }
    public static void findByIdAbonent(Long id) {
        System.out.println(AbonentDao.getInstance().findByid(id));
    }
    public static void findByIdNumber(Long id) {
        System.out.println(NumberDao.getInstance().findByid(id));
    }
    public static void findByIdUser(Long id) {
        System.out.println(UserDao.getInstance().findByid(id));
    }
    public static void findByIdUslugi(Long id) {
        System.out.println(UslugiDao.getInstance().findByid(id));
    }
    public static void findByIdUslugiAbonents(Long id) {
        System.out.println(UslugiAbonentsDao.getInstance().findByid(id));
    }
    public static void doZvonok(Long id) {
        try(var connection = ConnectionManager.get();
            var statement = connection.prepareStatement(DoZvonokSql)) {
            statement.setLong(1, id);
            var res = statement.execute();
            System.out.println(res);
        } catch (SQLException e) {
            throw new FunctionException(e);
        }
    }
    public static void BlockAbonents() {
        try(var connection = ConnectionManager.get();
            var statement = connection.prepareStatement(BlockAbonentsSql)) {
            var res = statement.execute();
            System.out.println(res);
        } catch (SQLException e) {
            throw new FunctionException(e);
        }
    }
    public static void UnlockAbonents() {
        try(var connection = ConnectionManager.get();
            var statement = connection.prepareStatement(UnlockAbonentsSql)) {
            var res = statement.execute();
            System.out.println(res);
        } catch (SQLException e) {
            throw new FunctionException(e);
        }
    }
    public static void AddCheck(Long sum, Long id) {
        try(var connection = ConnectionManager.get();
            var statement = connection.prepareStatement(PopolnitCheckSql)) {
            statement.setLong(1, sum);
            statement.setLong(2, id);
            var res = statement.execute();
            System.out.println(res);
        } catch (SQLException e) {
            throw new FunctionException(e);
        }
    }
    public static void replaceNumber(Long num, Long id_a) {
        AbonentDao.getInstance().replaceNumber(num, id_a);
    }
}
