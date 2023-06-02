package entity;

public class Abonent {
    private Long abonentId;
    private Number number;
    private Long check;
    private Integer minutes;
    private String switchO;
    private User user;

    public Abonent(Long abonentId, Number number, Long check, Integer minutes, String switchO, User user) {
        this.abonentId = abonentId;
        this.number = number;
        this.check = check;
        this.minutes = minutes;
        this.switchO = switchO;
        this.user = user;
    }

    public Abonent() {
    }

    public Long getAbonentId() {
        return abonentId;
    }

    public void setAbonentId(Long abonentId) {
        this.abonentId = abonentId;
    }

    public Number getNumber() {
        return number;
    }

    public void setNumber(Number number) {
        this.number = number;
    }

    public Long getCheck() {
        return check;
    }

    public void setCheck(Long check) {
        this.check = check;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public String getSwitchO() {
        return switchO;
    }

    public void setSwitchO(String switchO) {
        this.switchO = switchO;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Abonent{" +
                "abonentId=" + abonentId +
                ", number=" + number +
                ", check=" + check +
                ", minutes=" + minutes +
                ", switchO='" + switchO + '\'' +
                ", user=" + user +
                '}';
    }
}
