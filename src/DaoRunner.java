import dao.*;
import dto.UserFilter;
import entity.User;
import entity.Number;
import entity.Uslugi;
import utils.ConnectionManager;

public class DaoRunner {
    public static void main(String[] args) {
        System.out.println(UslugiAbonentsDao.getInstance().findByid(8L));
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
    public static void updateUslugiTest(String name_uslugi, Long tar, Long idd) {
        var uslugi = UslugiDao.getInstance().findByid(idd);
        System.out.println(uslugi.get().toString());
        uslugi.ifPresent(uslugi1 -> {
            uslugi1.setTarif(tar);
            uslugi1.setName_uslugi(name_uslugi);
            UslugiDao.getInstance().update(uslugi1);
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
        var saved = dao.UslugiDao.getInstance().save(uslugi);
        System.out.println(saved.getUslugi_id());
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
}
