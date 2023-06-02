package entity;

public class Number {
    private Long numbersId;
    private Long number;
    private String description;

    public Number(Long numbersId, Long number, String description) {
        this.numbersId = numbersId;
        this.number = number;
        this.description = description;
    }
    public Number() {

    }
    public Long getNumbers_id() {
        return numbersId;
    }

    public void setNumbers_id(Long numbers_id) {
        this.numbersId = numbers_id;
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
                "numbersId=" + numbersId +
                ", number=" + number +
                ", description='" + description + '\'' +
                '}';
    }
}
