package com.pluralsight;

import java.io.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;


public class AccountingLedger {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Transaction> transactionArrayList = new ArrayList<>();
    public static void main(String[] args) throws IOException {
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
                    String debitInfo;


                    Date date = new Date();

                    SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm:ss");
                    SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");

                    String currentTime  = formatter1.format(date);
                    String currentDate = formatter2.format(date);
                    String description;
                    String vendor;
                    double amount;
                    String type = "Payment";

                    System.out.print("What would you like to purchase: ");
                    description = scanner.next();
                    scanner.nextLine();

                    System.out.print("Enter the vendor:");
                    vendor = scanner.next();
                    scanner.nextLine();

                    System.out.print("Enter the amount of item:");
                    amount = Double.parseDouble(scanner.next());
                    scanner.nextLine();

                    amount *= -1;

                    Transaction depositItem = new Transaction(currentDate,currentTime,description,vendor,amount,type);
                    transactionArrayList.add(depositItem);

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
                            for (Transaction transactionItem : transactionArrayList) {
                                System.out.println(transactionItem);
                            }
                        }

                        break;
                        case "d":
                        {
                            System.out.println("Deposits");
                            for (Transaction transactionItem : transactionArrayList) {
                                if (transactionItem.getType().equals("Deposit")) {
                                    System.out.println(transactionItem);
                                }
                            }
                        }
                        break;
                        case "p":{
                            System.out.println("Payments");
                            for (Transaction transactionItem : transactionArrayList) {
                                if (transactionItem.getType().equals("Payment")) {
                                    System.out.println(transactionItem);
                                }
                            }
                        }
                        break;
                        case "r":{
                            int chosenReport;
                            do {
                                System.out.println("The report screen");
                                System.out.println("1) Month To Date");
                                System.out.println("2) Previous Month");
                                System.out.println("3) Year To Date");
                                System.out.println("4) Previous Year");
                                System.out.println("5) Search by Vendor");
                                System.out.println("0) Back");
                                chosenReport = scanner.nextInt();

                                switch (chosenReport){
                                    case 1: {
                                        System.out.println("Month to date");

                                        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
                                        String currentDate = formatter2.format(new Date());


                                        Calendar calendar = Calendar.getInstance();
                                        calendar.setTime(new Date());
                                        calendar.set(Calendar.DAY_OF_MONTH, 1);
                                        String firstDayOfMonth = formatter2.format(calendar.getTime());


                                        System.out.println("Month To Date Transactions:");
                                        for (Transaction transactionItem : transactionArrayList) {
                                            String transactionDate = transactionItem.getDate();

                                            System.out.println(transactionDate);
                                            System.out.println(currentDate);



                                            if (transactionDate.compareTo(firstDayOfMonth) >= 0 && transactionDate.compareTo(currentDate) <= 0) {
                                                System.out.println(transactionItem);
                                            }
                                            break;
                                        }
                                    }
                                    case 2:{
                                        System.out.println("Previous Month");

                                        break;
                                    }
                                    case 3:{
                                        System.out.println("Year to date");
                                        break;
                                    }
                                    case 4:{
                                        System.out.println("Previous Year");
                                        break;
                                    }
                                    case 5:{
                                        System.out.println("Searched by Vendor");
                                        break;
                                    }
                                }

                            }while (chosenReport != 0);

                        }
                        break;

                    }





                    break;
                }
                case "x":{
                    System.exit(0);
                }
            }

        }while (!chosenOptions.equals("x"));
    }

    public static Transaction parseCSVLine(String csvLine) {
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

        SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");

        String currentTime  = formatter1.format(date);
        final String currentDate = formatter2.format(date);
        String description;
        String vendor;
        double amount;



        System.out.println("To complete a deposit we need more information.");

        System.out.print("Please give us a description of the deposit: ");
        description = scanner.next();
        scanner.nextLine();

        System.out.print("Enter the vendor:");
        vendor = scanner.next();
        scanner.nextLine();

        System.out.println("Enter the amount to deposit");
        amount = Double.parseDouble(scanner.next());
        scanner.nextLine();

        String type = "Deposit";


        Transaction depositItem = new Transaction(currentDate, currentTime, description, vendor, amount,type);
//        depositItem.setType("Deposit");

        transactionArrayList.add(depositItem);


//
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv", true))) {

            bufferedWriter.write(String.format("%s|%s|%s|%s|%.2f", currentDate, currentTime, description, vendor, amount));
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new IOException(e);
        }






    }




}
