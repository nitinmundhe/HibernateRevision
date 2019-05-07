import javax.persistence.*;

@Entity
@Table(name="salesperson_joined")
@PrimaryKeyJoinColumn(name="eid")
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
