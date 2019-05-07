import javax.persistence.*;

@Entity
@Table(name="salesperson_table")
@AttributeOverrides({
        @AttributeOverride(name="sal",column=@Column(name="sal")),
        @AttributeOverride(name="name",column=@Column(name="name"))
})
public class SalesPerson extends Employee{
    private int incentive;

    public SalesPerson(){

    }

    public SalesPerson(String name,int sal, int incentive) {
        super(name,sal);
        this.incentive = incentive;
    }

    public int getIncentive() {
        return incentive;
    }

    public void setIncentive(int incentive) {
        this.incentive = incentive;
    }
}
