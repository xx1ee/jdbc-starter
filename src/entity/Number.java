package entity;

public class Number {
    private Long numbers_id;
    private Long number;
    private String description;

    public Number(Long numbers_id, Long number, String description) {
        this.numbers_id = numbers_id;
        this.number = number;
        this.description = description;
    }
    public Number() {

    }
    public Long getNumbers_id() {
        return numbers_id;
    }

    public void setNumbers_id(Long numbers_id) {
        this.numbers_id = numbers_id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Number{" +
                "numbers_id=" + numbers_id +
                ", number=" + number +
                ", description='" + description + '\'' +
                '}';
    }
}
