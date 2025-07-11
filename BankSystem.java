import java.io.*;
import java.util.*;
class BankAccount
{
    String Name;
    String accountNumber;
    double balance;
    BankAccount(String Name,String accountNumber,double balance)
    {
        this.Name=Name;
        this.accountNumber=accountNumber;
        this.balance=balance;
    }
    @Override
    public String toString()
    {
        return Name + "," + accountNumber + ","+balance;
    }
    static BankAccount fromString(String line)
    {
        String[] parts=line.split(",");
        return new BankAccount(parts[0],parts[1],Double.parseDouble(parts[2]));
    }
}
public class BankSystem
{
    static final String File_Name="acount.txt";
    static Scanner sc=new Scanner(System.in);
    static ArrayList<BankAccount> accounts=new ArrayList<>();
    public static void main(String[] args)
    {
        loadAccounts();
        while(true)
        {
            System.out.println("\n -----Bank Menagement System------");
            System.out.print("\n1. create Account\n2. View Accounts\n3. Deposit\n4. Withdraw\n5.Exit");
            System.out.print("Enetr choice:");
            int choice=sc.nextInt();
            sc.nextLine();
            switch(choice)
            {
                case 1 -> createAccount();
                case 2 -> viewAccounts();
                case 3 -> deposit();
                case 4 -> withdraw();
                case 5 -> {
                    saveAccounts();
                    System.out.println("Exiting....");
                    return;
                }
                default -> System.out.println("Invalid choice...");
            }
        }
    }
    static void loadAccounts()
    {
        try (BufferedReader br=new BufferedReader(new FileReader(File_Name)))
        {
            String line;
            while((line =br.readLine()) != null)
            {
                accounts.add(BankAccount.fromString(line));
            }
        }
        catch(IOException e)
        {
            System.out.println("No previous data found startig fresh");
        }
    }
    static void saveAccounts()
    {
        try (BufferedWriter bw=new BufferedWriter(new FileWriter(File_Name)))
        {
            for(BankAccount acc : accounts)
            {
                bw.write(acc.toString());
                bw.newLine();
            }
        }
        catch(IOException e)
        {
            System.out.println("Error on savings Account");
        }
    }
    static void createAccount()
    {
        System.out.print("Enter name:");
        String name=sc.nextLine();
        System.out.print("Enter Account Number:");
        String accNo=sc.nextLine();
        System.out.print("Enter initail balance:");
        double bal=sc.nextDouble();
        accounts.add(new BankAccount(name, accNo, bal));
        System.out.println("Account created Succesesfully.....");
    }
    static void viewAccounts()
    {
        if(accounts.isEmpty())
        {
            System.out.println("no accounts found");
            return;
        }
        for(BankAccount acc : accounts)
        {
            System.out.println("Name:"+ acc.Name+"\n"+"accNo :"+ acc.accountNumber+"\n"+ "balance:"+ acc.balance);
        }
    }
    static void deposit()
    {
        System.out.print("Enter account No:");
        String accNo=sc.nextLine();
        BankAccount acc=findAccount(accNo);
        if(acc == null)
        {
            System.out.println("Account Not Found:");
            return;
        }
        System.out.print("Enter Deposit amount:");
        double amount=sc.nextDouble();
        acc.balance += amount;
        System.out.println("Amount deposited Successfully....");

    }
    static void withdraw()
    {
        System.out.print("Enter account No:");
        String accNo=sc.nextLine();
        BankAccount acc=findAccount(accNo);
        if(acc == null)
        {
            System.out.println("Account Not Found:");
        }
        System.out.print("Enter Withdraw Amount:");
        double amount=sc.nextDouble();
        if(amount > acc.balance)
        {
            System.out.println("Insufficient balance.");
        }
        acc.balance -= amount;
        System.out.println("Amount withdraw successfully....");

    }
    static BankAccount findAccount(String accNo)
    {
        for(BankAccount acc : accounts)
        {
            if(acc.accountNumber.equals(accNo))
            {
                return acc;
            }
        }
        return null;
    }
}