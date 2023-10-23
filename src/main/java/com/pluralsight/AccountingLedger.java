package com.pluralsight;

import java.io.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class AccountingLedger {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        String chosenOptions;
        do {
            System.out.println("Welcome to Interstellar Banking how can we help you today?");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment(Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");

            chosenOptions = scanner.next();
            switch (chosenOptions){
                case "d":{
                    deposit();
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

                    System.out.println("What would you like to purchase");
                    description = scanner.next();

                    System.out.print("Enter the vendor:");
                    vendor = scanner.next();

                    System.out.println("Enter the amount of item");
                    amount = Double.parseDouble(scanner.next());
                    amount *= -1;

                    Deposit depositItem = new Deposit(currentDate,currentTime,description,vendor,amount);

                    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv", true))) {

                        bufferedWriter.write(String.format("%s|%s|%s|%s|%.2f", currentDate, currentTime, description, vendor, amount));
                        bufferedWriter.newLine();
                    } catch (IOException e) {
                        throw new IOException(e);
                    }


                    try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/transactions.csv"))) {
                        String nextLine;
                        while ((nextLine = bufferedReader.readLine()) != null) {
                            System.out.println(nextLine);
                        }
                    } catch (IOException e) {
                        throw new IOException(e);
                    }

                }
                case "l":{

                }
                case "x":{
                    System.exit(0);
                }
            }

        }while (!chosenOptions.equals("x"));
    }

    public static void deposit() throws IOException {
        Date date = new Date();

        SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");

        String currentTime  = formatter1.format(date);
        String currentDate = formatter2.format(date);
        String description;
        String vendor;
        double amount;

        System.out.println("To complete a deposit we need more information.");

        System.out.print("Please give us a description of the deposit: ");
        description = scanner.next();

        System.out.print("Enter the vendor:");
        vendor = scanner.next();

        System.out.println("Enter the amount to deposit");
        amount = Double.parseDouble(scanner.next());


        Deposit depositItem = new Deposit(currentDate,currentTime,description,vendor,amount);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/transactions.csv", true))) {

            bufferedWriter.write(String.format("%s|%s|%s|%s|%.2f", currentDate, currentTime, description, vendor, amount));
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new IOException(e);
        }


        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/transactions.csv"))) {
            String nextLine;
            while ((nextLine = bufferedReader.readLine()) != null) {
                System.out.println(nextLine);
            }
        } catch (IOException e) {
            throw new IOException(e);
        }




    }
    public static void makePayment() throws IOException {

    }



}
