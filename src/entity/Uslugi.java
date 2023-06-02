package entity;

public class Uslugi {
    private Long uslugi_id;
    private String name_uslugi;
    private Long tarif;

    public Uslugi(Long uslugi_id, String name_uslugi, Long tarif) {
        this.uslugi_id = uslugi_id;
        this.name_uslugi = name_uslugi;
        this.tarif = tarif;
    }

    public Uslugi() {
    }

    public Long getUslugi_id() {
        return uslugi_id;
    }

    public void setUslugi_id(Long uslugi_id) {
        this.uslugi_id = uslugi_id;
    }

    public String getName_uslugi() {
        return name_uslugi;
    }

    public void setName_uslugi(String name_uslugi) {
        this.name_uslugi = name_uslugi;
    }

    public Long getTarif() {
        return tarif;
    }

    public void setTarif(Long tarif) {
        this.tarif = tarif;
    }

    @Override
    public String toString() {
        return "Uslugi{" +
                "uslugi_id=" + uslugi_id +
                ", name_uslugi='" + name_uslugi + '\'' +
                ", tarif=" + tarif +
                '}';
    }
}
