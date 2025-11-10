package javaprogramming.demo32bankaccountmanagementsystem;

public class Count {
    private String ID;
    private String name;
    private double balance;
    public Count(String ID, String name, double balance) {
        this.ID = ID;
        this.name = name;
        this.balance = balance;
    }
    public Count() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    @Override
    public String toString() {
        return "Count{" +
                "ID='" + ID + '\'' +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}
