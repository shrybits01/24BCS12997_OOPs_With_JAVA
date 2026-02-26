import java.util.ArrayList;

class Account {
    String owner;
    double balance;

    Account(String owner, double balance) {
        this.owner = owner;
        this.balance = balance;
    }

    void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited: " + amount + " | Balance: " + balance);
    }

    void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient balance!");
        } else {
            balance -= amount;
            System.out.println("Withdrawn: " + amount + " | Balance: " + balance);
        }
    }

    void showBalance() {
        System.out.println(owner + "'s Balance: " + balance);
    }
}

class SavingsAccount extends Account {
    double interestRate = 0.04;

    SavingsAccount(String owner, double balance) {
        super(owner, balance);
    }

    void addInterest() {
        double interest = balance * interestRate;
        balance += interest;
        System.out.println("Interest added: " + interest + " | New Balance: " + balance);
    }
}

class CurrentAccount extends Account {
    double overdraftLimit;

    CurrentAccount(String owner, double balance, double overdraftLimit) {
        super(owner, balance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    void withdraw(double amount) {
        if (amount > balance + overdraftLimit) {
            System.out.println("Exceeds overdraft limit!");
        } else {
            balance -= amount;
            System.out.println("Withdrawn: " + amount + " | Balance: " + balance);
        }
    }
}

class Bank {
    String name;
    ArrayList<Account> accounts = new ArrayList<>();

    Bank(String name) {
        this.name = name;
    }

    void addAccount(Account acc) {
        accounts.add(acc);
        System.out.println("Account added for: " + acc.owner);
    }

    void showAllAccounts() {
        System.out.println("\n--- All Accounts in " + name + " ---");
        for (Account acc : accounts) {
            acc.showBalance();
        }
    }
}

// Main
public class BankingSystem {
    public static void main(String[] args) {

        Bank bank = new Bank("My Bank");

        SavingsAccount s = new SavingsAccount("Alice", 5000);
        CurrentAccount c = new CurrentAccount("Bob", 3000, 1000);

        bank.addAccount(s);
        bank.addAccount(c);

        System.out.println("\n-- Alice (Savings) --");
        s.deposit(2000);
        s.withdraw(1000);
        s.addInterest();

        System.out.println("\n-- Bob (Current) --");
        c.deposit(500);
        c.withdraw(4000); // uses overdraft

        bank.showAllAccounts();
    }
}