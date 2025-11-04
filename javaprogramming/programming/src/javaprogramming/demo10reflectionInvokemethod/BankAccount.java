package javaprogramming.demo10reflectionInvokemethod;

public class BankAccount {
    public void deposit(Account account, double amount){
        account.setBalance(account.getBalance() + amount);
        System.out.println("存款成功,请继续操作：");
    }
    public void withdraw(Account account, double amount){
        if (account.getBalance() >= amount){
            account.setBalance(account.getBalance() - amount);
            System.out.println("取款成功,请继续操作：");
        } else {
            System.out.println("余额不足,请重新操作：");
        }
    }
    public void checkBalance(Account account){
        System.out.println("账户余额为：" + account.getBalance());
    }
}
