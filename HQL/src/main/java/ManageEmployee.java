import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ManageEmployee {
    private static SessionFactory factory;

    public static void main(String[] args) {

        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        ManageEmployee ME = new ManageEmployee();

        /* Add few employee records in database */
        Integer empID1 = ME.addEmployee("Zara", "Ali", 1000);
        Integer empID2 = ME.addEmployee("Daisy", "Das", 5000);
        Integer empID3 = ME.addEmployee("John", "Paul", 10000);
        Integer empID4 = ME.addEmployee("Ziva", "Paul", 4000);

        /* List down all the employees */
        //ME.listEmployees();

        /* Update employee's records */
        //ME.updateEmployee(empID1, 5000);

        /* Delete an employee from the database */
        //ME.deleteEmployee(empID2);

        /* List down new list of the employees */
        //ME.listEmployees();

        List result;
        String hql = "From Employee";
        result = runHqlQuerries(hql);
        printResult(result);
        hql = "From Employee e order by e.salary desc";
        result = runHqlQuerries(hql);
        printResult(result);
        hql = "select avg(salary) from Employee";
        System.out.println(runHqlQuerries(hql));
        printResult(result);
        hql ="select min(e.salary) From Employee e";
        System.out.println(runHqlQuerries(hql));

        hql = "SELECT SUM(E.salary), E.firstName FROM Employee E " +
                "GROUP BY E.firstName";
        Query query;
        System.out.println(runHqlQuerries(hql));


        //Named Qurries
        Session session = factory.openSession();
        Transaction tx = null;

        query  = session.getNamedQuery("findEmployeeByID").setInteger("id",3);
        result = query.list();
        printResult(result);

        Criteria cr =session.createCriteria(Employee.class);
        cr.setProjection(Projections.property("firstName"));
        result = cr.list();
        for (Iterator iterator = result.iterator(); iterator.hasNext(); ) {
            String emp = (String)iterator.next();
            System.out.println(emp);
        }

        /*cr.setProjection(Projections.projectionList(
                Projections.projectionList()
                .add( Projections.property("firstName") )
                .add( Projections.avg("lastName") )
                )
        ).;
        */

        Criteria cr2 =session.createCriteria(Employee.class);
        cr2.add(Restrictions.like("firstName","Z%"));
        cr2.add(Restrictions.gt("salary",new Integer(2000)));
        result = cr2.list();
        printResult(result);

        Criteria cr3 =session.createCriteria(Employee.class);
        LogicalExpression orExp = Restrictions.or(
                Restrictions.like("firstName","Z%")
                ,Restrictions.lt("salary",new Integer(6000)
        ));
        result = cr3.list();
        System.out.println("Cr 3");
        printResult(result);

        System.out.println("Native SQL");
        String sql = "SELECT id,firstName,lastName,salary FROM DB_Employee where salary >6000";
        SQLQuery query1 = session.createSQLQuery(sql);
        //List<Object[]> rows =query1.list();
        /*for(Object[] row :rows){
            Employee emp = new Employee();
        }*/
    }

    private static List runHqlQuerries(String str) {
        Session session = factory.openSession();
        Transaction tx = null;
        Query query;
        List result = null;
        try {
            tx = session.beginTransaction();
            query = session.createQuery(str);
            result = query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    private static void printResult(List result) {
        for (Iterator iterator = result.iterator(); iterator.hasNext(); ) {
            Employee emp = (Employee)iterator.next();
            System.out.println(emp);
        }
    }

    /* Method to CREATE an employee in the database */
    public Integer addEmployee(String fname, String lname, int salary) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer employeeID = null;

        try {
            tx = session.beginTransaction();
            Employee employee = new Employee(fname, lname, salary);
            employeeID = (Integer) session.save(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return employeeID;
    }

    /* Method to  READ all the employees */
    public void listEmployees() {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List employees = session.createQuery("FROM Employee").list();
            for (Iterator iterator = employees.iterator(); iterator.hasNext(); ) {
                Employee employee = (Employee) iterator.next();
                System.out.print("{" + employee.getFirstName());
                System.out.print("," + employee.getLastName());
                System.out.println("," + employee.getSalary() + "}");
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /* Method to UPDATE salary for an employee */
    public void updateEmployee(Integer EmployeeID, int salary) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Employee employee = (Employee) session.get(Employee.class, EmployeeID);
            employee.setSalary(salary);
            session.update(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /* Method to DELETE an employee from the records */
    public void deleteEmployee(Integer EmployeeID) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Employee employee = (Employee) session.get(Employee.class, EmployeeID);
            session.delete(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}