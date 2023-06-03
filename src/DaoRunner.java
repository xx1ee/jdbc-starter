import dao.*;
import dto.UserFilter;
import entity.*;
import entity.Number;
import utils.ConnectionManager;

import java.util.List;

public class DaoRunner {
    public static void main(String[] args) {

    }

    public static void updateUserTest(String fio, Long id) {
        var user = UserDao.getInstance().findByid(id);
        System.out.println(user.get().toString());
        user.ifPresent(user1 -> {
            user1.setFio(fio);
            UserDao.getInstance().update(user1);
        });
    }
    public static void updateNumberTest(Long numberr, Long idd) {
        var num = NumberDao.getInstance().findByid(idd);
        System.out.println(num.get().toString());
        num.ifPresent(number -> {
            number.setNumber(numberr);
            NumberDao.getInstance().update(number);
        });
    }
    public static void updateUslugiTest(String nameUslugi, Long tar, Long idd) {
        var uslugi = UslugiDao.getInstance().findByid(idd);
        System.out.println(uslugi.get().toString());
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
    public static void fondByIdUslugiAbonents(Long id) {
        System.out.println(UslugiAbonentsDao.getInstance().findByid(id));
    }
}
