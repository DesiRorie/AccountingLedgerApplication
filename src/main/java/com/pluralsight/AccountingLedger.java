package com.pluralsight;

import java.io.*;

import java.text.SimpleDateFormat;
import java.util.*;


public class AccountingLedger {
    //defined scanner here to make it accessible throughout the application.
    static Scanner scanner = new Scanner(System.in);

    // Used an ArrayList to store the Transactions. Also needed it to load the saved data everytime app starts.
    public static ArrayList<Transaction> transactionArrayList = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        //I want to load the transactions saved from the previous session first everytime it runs.
        loadTransactions();

        String chosenOptions;

        do {
            System.out.println("Welcome to Interstellar Banking how can we help you today?");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment(Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");

            chosenOptions = scanner.next();
            switch (chosenOptions.toLowerCase()){
                case "d":{
                    deposit();
                    break;
                }
                case "p":{
                    //payment screen

                    Date date = new Date();

                    //formatters
                    SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm:ss");
                    SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");

                    //manually inserted the time since user does not need the ability to alter the timestamp of the payment.
                    String currentTime  = formatter1.format(date);
                    String currentDate = formatter2.format(date);
                    String description;
                    String vendor;
                    double amount;

                    //manually assigned the type variable to the Transaction for every payment and deposit to be able to filter later.
                    String type = "Payment";

                    System.out.print("What would you like to purchase: ");
                    description = scanner.next();
                    scanner.nextLine();

                    System.out.print("Enter the vendor:");
                    vendor = scanner.next();
                    scanner.nextLine();
                    vendor = vendor.trim();

                    System.out.print("Enter the amount of item:");
                    amount = Double.parseDouble(scanner.next());
                    scanner.nextLine();

                    amount *= -1;

                    Transaction depositItem = new Transaction(currentDate,currentTime,description,vendor,amount,type);

                    //appends the depositItem to my ArrayList
                    transactionArrayList.add(depositItem);

                    //Writing into the csv file every payment and deposit will append.
                    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv", true))) {

                        bufferedWriter.write(String.format("%s|%s|%s|%s|%.2f", currentDate, currentTime, description, vendor, amount));
                        bufferedWriter.newLine();
                    } catch (IOException e) {
                        throw new IOException(e);
                    }


//
                    break;

                }
                case "l":{

                    String chosenLedgerOption;

                    //Ledger Screen and option
                    System.out.println("Ledger Screen");
                    System.out.println("A) Display all entries");
                    System.out.println("D) Display all deposit entries");
                    System.out.println("P) Display all payment entries");
                    System.out.println("R) Report Screen ");

                    chosenLedgerOption = scanner.next();
                    scanner.nextLine();

                    switch (chosenLedgerOption.toLowerCase()){
                        case "a":{
                            System.out.println("All entries");

                            Collections.sort(transactionArrayList, new Comparator<Transaction>() {
                                @Override
                                public int compare(Transaction t1, Transaction t2) {

                                    return t2.getTime().compareTo(t1.getTime());
                                }
                            });

//                            Collections.sort(transactionArrayList);
                            for (Transaction transactionItem : transactionArrayList) {

                                System.out.println(transactionItem);
                            }
                        }

                        break;
                        case "d":
                        {
                            System.out.println("Deposits");
                            Collections.sort(transactionArrayList, new Comparator<Transaction>() {
                                @Override
                                public int compare(Transaction t1, Transaction t2) {

                                    return t2.getTime().compareTo(t1.getTime());
                                }
                            });

                            for (Transaction transactionItem : transactionArrayList) {

                                if (transactionItem.getAmount() > 0) {
                                    System.out.println(transactionItem);
                                }
//                                if (transactionItem.getType().equals("Deposit")) {
//                                    System.out.println(transactionItem);
//                                }
                            }
                        }
                        break;
                        case "p":{
                            System.out.println("Payments");
                            Collections.sort(transactionArrayList, new Comparator<Transaction>() {
                                @Override
                                public int compare(Transaction t1, Transaction t2) {

                                    return t2.getTime().compareTo(t1.getTime());
                                }
                            });
                            for (Transaction transactionItem : transactionArrayList) {
                                if (transactionItem.getAmount() < 0) {
                                    System.out.println(transactionItem);
                                }
//
                            }
                        }
                        break;
                        case "r":{
                            //Report Screen
                            boolean showReportScreen = true;
                            String chosenReport;


                            goBack:
                            while (showReportScreen){
                                System.out.println("The report screen");
                                System.out.println("1) Month To Date");
                                System.out.println("2) Previous Month");
                                System.out.println("3) Year To Date");
                                System.out.println("4) Previous Year");
                                System.out.println("5) Search by Vendor");
                                System.out.println("0) Back");
                                System.out.println("H) Home");
                                chosenReport = scanner.next();
                                scanner.nextLine();
                                chosenReport.toLowerCase();


                                switch (chosenReport){
                                    case "0":{
                                        break;
                                    }
                                    case "1": {
                                        showMonthToDate();
                                    }
                                    break;
                                    case "2":{
                                        showPreviousMonth();

                                        break;
                                    }
                                    case "3":{
                                        showYearToDate();
                                        break;
                                    }
                                    case "4":{
                                        showPreviousYear();
                                        break;
                                    }
                                    case "5": {
                                        System.out.println("Enter the vendor to search");
                                        String chosenVendor = scanner.nextLine().trim();

//                                        System.out.println(chosenVendor);

                                        for (Transaction transactionItem : transactionArrayList) {
                                            if (transactionItem.getVendor().equals(chosenVendor.trim())) {
                                                System.out.println(transactionItem);
                                            }
                                        }
                                        break;
                                    }
                                    case "h":{
                                        break goBack;
                                    }
                                }
                            }
                        }
                        break;
                    }
                    break;
                }
                case "x":{
                    //saves transactions for use next time code runs
                    saveTransactions();
                    System.exit(0);
                }
            }
        }while (!chosenOptions.equals("x"));
    }


    //methods
    private static void showPreviousYear() {
        System.out.println("Previous Year");

        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());


        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        String firstDayOfPreviousYear = formatter2.format(calendar.getTime());

        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        String lastDayOfPreviousYear = formatter2.format(calendar.getTime());

        System.out.println("Previous Year Transactions:");
        for (Transaction transactionItem : transactionArrayList) {
            String transactionDate = transactionItem.getDate();

            if (transactionDate.compareTo(firstDayOfPreviousYear) >= 0 && transactionDate.compareTo(lastDayOfPreviousYear) <= 0) {
                System.out.println(transactionItem);
            }
        }
    }
    private static void showMonthToDate() {
        System.out.println("Month to date");

        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = formatter2.format(new Date());


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String firstDayOfMonth = formatter2.format(calendar.getTime());

        //delete this
        System.out.println( "THIS IS THE TEST" + firstDayOfMonth);


        System.out.println("Month To Date Transactions:");
        for (Transaction transactionItem : transactionArrayList) {
            String transactionDate = transactionItem.getDate();





            if (transactionDate.compareTo(firstDayOfMonth) >= 0 && transactionDate.compareTo(currentDate) <= 0) {
                System.out.println(transactionItem);
            }

        }
    }

    private static void showPreviousMonth() {
        System.out.println("Previous Month");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");

        //Creating instance of Calendar to alter to fit the requirement
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());

        //changing the month by subtracting 1
        calendar.add(Calendar.MONTH, -1);
        //setting the day of that month to 1 to compare from the beginning of last month
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        String firstDayOfPreviousMonth = formatter2.format(calendar.getTime());
        System.out.println("First Day:" + firstDayOfPreviousMonth);


        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String lastDayOfPreviousMonth = formatter2.format(calendar.getTime());

        System.out.println("Last Day:" + lastDayOfPreviousMonth);

        System.out.println("Previous Month Transactions:");
        for (Transaction transactionItem : transactionArrayList) {
            String transactionDate = transactionItem.getDate();

            if (transactionDate.compareTo(firstDayOfPreviousMonth) >= 0 && transactionDate.compareTo(lastDayOfPreviousMonth) <= 0) {
                System.out.println(transactionItem);
            }
        }
    }

    private static void showYearToDate() {
        System.out.println("Year to date");

        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = formatter2.format(new Date());

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());

//        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        int currentYear = calendar.get(Calendar.YEAR);

        calendar.set(Calendar.YEAR, currentYear);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);


        String firstDayOfYear = formatter2.format(calendar.getTime());

        System.out.println("Year To Date Transactions:");

        for (Transaction transactionItem : transactionArrayList) {
            String transactionDate = transactionItem.getDate();

//            System.out.println(transactionDate);
//            System.out.println(currentDate);

            if (transactionDate.compareTo(firstDayOfYear) >= 0 && transactionDate.compareTo(currentDate) <= 0) {
                System.out.println(transactionItem);
            }
        }
    }

    private static void loadTransactions() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/transactions.csv"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Transaction transaction = readCSV(line);
                if (transaction != null) {
                    transactionArrayList.add(transaction);
                    Collections.sort(transactionArrayList, new Comparator<Transaction>() {
                        @Override
                        public int compare(Transaction t1, Transaction t2) {

                            return t2.getTime().compareTo(t1.getTime());
                        }
                    });
                }
            }
        } catch (IOException e) {

        }
    }

    private static void saveTransactions() {
        //I do not want it to append to the current file it overwrites a new file to use for next run but reading from all the Transactions added to the arraylist.
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv",false))) {
            for (Transaction transaction : transactionArrayList) {
                bufferedWriter.write(String.format("%s|%s|%s|%s|%.2f", transaction.getDate(), transaction.getTime(),
                        transaction.getDescription(), transaction.getVendor(), transaction.getAmount()));
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Transaction readCSV(String csvLine) {
        String[] info = csvLine.split("\\|");

        if (info.length != 5) {
            System.out.println("Incomplete transaction: " + csvLine);
            return null;
        }

        String currentDate = info[0];
        String currentTime = info[1];
        String description = info[2];
        String vendor = info[3];
        double amount = Double.parseDouble(info[4]);


        String type = "Deposit";

        Transaction transaction = new Transaction(currentDate, currentTime, description, vendor, amount, type);
        return transaction;
    }

    public static void deposit() throws IOException {
        Date date = new Date();
//same formatting throughout
        SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");

        String currentTime  = formatter1.format(date);
        final String currentDate = formatter2.format(date);

        String description;
        String vendor;
        double amount;

        scanner.nextLine();
        System.out.println("Please give us a description of the deposit: ");
        description = scanner.nextLine();

        System.out.println("Enter the vendor: ");
        vendor = scanner.nextLine();
        vendor = vendor.trim();

        System.out.println("Enter the amount to deposit: ");
        amount = scanner.nextDouble();

        //this time its deposit
        String type = "Deposit";


        Transaction depositItem = new Transaction(currentDate, currentTime, description, vendor, amount,type);


        transactionArrayList.add(depositItem);


//still appending to the current state of the file. will be overwritten.
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv", true))) {

            bufferedWriter.write(String.format("%s|%s|%s|%s|%.2f", currentDate, currentTime, description, vendor, amount));
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}
