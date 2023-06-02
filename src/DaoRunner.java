import dao.NumberDao;
import dao.UserDao;
import dao.UslugiDao;
import dto.UserFilter;
import entity.User;
import entity.Number;
import entity.Uslugi;
import utils.ConnectionManager;

public class DaoRunner {
    public static void main(String[] args) {

    }

    public static void updateUserTest(String fio) {
        var user = UserDao.findByid(7L);
        System.out.println(user.get().toString());
        user.ifPresent(user1 -> {
            user1.setFio(fio);
            UserDao.update(user1);
        });
    }
    public static void updateNumberTest(Long numberr, Long idd) {
        var num = NumberDao.findByid(idd);
        System.out.println(num.get().toString());
        num.ifPresent(number -> {
            number.setNumber(numberr);
            NumberDao.update(number);
        });
    }
    public static void updateUslugiTest(String name_uslugi, Long tar, Long idd) {
        var uslugi = UslugiDao.findByid(idd);
        System.out.println(uslugi.get().toString());
        uslugi.ifPresent(uslugi1 -> {
            uslugi1.setTarif(tar);
            uslugi1.setName_uslugi(name_uslugi);
            UslugiDao.update(uslugi1);
        });
    }

    public static void saveUserTest(String fio, String pol) {
        var UserDao = dao.UserDao.getInstance();
        var user = new User();
        user.setFio(fio);
        user.setPol(pol);
        var saved = dao.UserDao.save(user);
        System.out.println(saved.getUserId());
    }
    public static void saveNumberTest(String description, Long num) {
        var NumberDao = dao.NumberDao.getInstance();
        var number = new Number();
        number.setDescription(description);
        number.setNumber(num);
        var saved = dao.NumberDao.save(number);
        System.out.println(saved.getNumbers_id());
    }
    public static void saveUslugiTest(String name_uslugi, Long tar) {
        var UslugiDao = dao.UslugiDao.getInstance();
        var uslugi = new Uslugi();
        uslugi.setName_uslugi(name_uslugi);
        uslugi.setTarif(tar);
        var saved = dao.UslugiDao.save(uslugi);
        System.out.println(saved.getUslugi_id());
    }
    public static void deleteUserTest(Long id) {
        System.out.println(UserDao.delete(id));
    }
    public static void deleteNumberTest(Long id) {
        System.out.println(NumberDao.delete(id));
    }
    public static void deleteUslugiTest(Long id) {
        System.out.println(UslugiDao.delete(id));
    }
}
