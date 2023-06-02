package entity;

public class User {

    private Long userId;
    private String fio;
    private String pol;

    public User(Long userId, String fio, String pol) {
        this.userId = userId;
        this.fio = fio;
        this.pol = pol;
    }

    public User() {
    }

    public Long getUserId() {
        return userId;
    }

    public String getFio() {
        return fio;
    }

    public String getPol() {
        return pol;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", fio='" + fio + '\'' +
                ", pol='" + pol + '\'' +
                '}';
    }
}
