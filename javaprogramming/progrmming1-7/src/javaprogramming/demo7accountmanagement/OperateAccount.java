package javaprogramming.demo7accountmanagement;

public class OperateAccount {
    public void deposit(BankAccount account, double amount){
        account.setBalance(account.getBalance() + amount);
    }
    public void withdraw(BankAccount account, double amount){
        if (account.getBalance() >= amount){
            account.setBalance(account.getBalance() - amount);
            System.out.println("取款成功,请继续操作：");
        } else {
            System.out.println("余额不足,请重新操作：");
        }
    }
    public void checkBalance(BankAccount account){
        System.out.println("账户余额为：" + account.getBalance());
    }
}
