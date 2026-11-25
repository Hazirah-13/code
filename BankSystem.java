import java.util.*;
import java.io.*;

public class BankSystem {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Account> accounts = new ArrayList<>();
    static final String FILE_NAME = "accounts.txt";

    public static void main(String[] args) {

        loadAccounts(); // 🔥 load data at start

        while (true) {
            System.out.println("\n1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) createAccount();
            else if (choice == 2) login();
            else break;
        }
    }

    // 🔹 Create Account
    static void createAccount() {
        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Set PIN: ");
        int pin = sc.nextInt();

        System.out.print("Deposit: ");
        double deposit = sc.nextDouble();
        sc.nextLine();

        int accNo = accounts.size() + 1001;

        Account acc = new Account(accNo, name, pin, deposit);
        accounts.add(acc);

        saveAccounts(); // 🔥 save after creation

        System.out.println("Account Created! AccNo: " + accNo);
    }

    // 🔹 Login
    static void login() {
        System.out.print("Enter AccNo: ");
        int accNo = sc.nextInt();

        System.out.print("Enter PIN: ");
        int pin = sc.nextInt();
        sc.nextLine();

        Account acc = findAccount(accNo);

        if (acc != null && acc.getPin() == pin) {
            accountMenu(acc);
        } else {
            System.out.println("Invalid login!");
        }
    }

    // 🔹 Menu
    static void accountMenu(Account acc) {
        while (true) {
            System.out.println("\n1. Balance\n2. Deposit\n3. Withdraw\n4. Logout");

            int ch = sc.nextInt();

            if (ch == 1) {
                System.out.println("Balance: " + acc.getBalance());
            } 
            else if (ch == 2) {
                System.out.print("Amount: ");
                double amt = sc.nextDouble();
                acc.deposit(amt);
                saveAccounts(); // 🔥 save update
            } 
            else if (ch == 3) {
                System.out.print("Amount: ");
                double amt = sc.nextDouble();

                if (amt > acc.getBalance()) {
                    System.out.println("Insufficient balance!");
                } else {
                    acc.withdraw(amt);
                    saveAccounts(); // 🔥 save update
                }
            } 
            else break;
        }
    }

    // 🔹 Find Account
    static Account findAccount(int accNo) {
        for (Account a : accounts) {
            if (a.getAccNo() == accNo) return a;
        }
        return null;
    }

    // 🔥 SAVE TO FILE
    static void saveAccounts() {
        try {
            FileWriter fw = new FileWriter(FILE_NAME);

            for (Account a : accounts) {
                fw.write(a.toString() + "\n");
            }

            fw.close();
        } catch (Exception e) {
            System.out.println("Error saving file");
        }
    }

    // 🔥 LOAD FROM FILE
    static void loadAccounts() {
        try {
            File file = new File(FILE_NAME);

            if (!file.exists()) return;

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                accounts.add(Account.fromString(line));
            }

            br.close();
        } catch (Exception e) {
            System.out.println("Error loading file");
        }
    }
}