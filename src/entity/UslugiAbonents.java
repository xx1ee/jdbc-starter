package entity;

public class UslugiAbonents {
    private Long id;
    private Uslugi uslugi;
    private Abonent abonent;

    public UslugiAbonents(Long id, Uslugi uslugi, Abonent abonent) {
        this.id = id;
        this.uslugi = uslugi;
        this.abonent = abonent;
    }

    public UslugiAbonents() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Uslugi getUslugi() {
        return uslugi;
    }

    public void setUslugi(Uslugi uslugi) {
        this.uslugi = uslugi;
    }

    public Abonent getAbonent() {
        return abonent;
    }

    public void setAbonent(Abonent abonent) {
        this.abonent = abonent;
    }

    @Override
    public String toString() {
        return "UslugiAbonents{" +
                "id=" + id +
                ", uslugi=" + uslugi +
                ", abonent=" + abonent +
                '}';
    }
}
