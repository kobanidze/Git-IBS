import com.google.gson.*;

import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;


public class Logic implements Preparation {
    private Models.Companies companies;

    public static void po(String jsonString) {
        Gson g = new Gson();
        Models.Companies companies = g.fromJson(jsonString, Models.Companies.class);
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    public void call() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String command = "";
        String info = """
                Добро пожаловать!
                команды:
                c/C - показать команды
                l/L - показать список компаний
                s/S - показать список всех ценных бумаг
                ds/DS - показать список всех просроченных ценных бумаг
                cld/CLD - показать список компаний начиная с ДАТЫ (введите дату)
                di/Di - показать список всех ценных бумаг по валюте
                """;
        System.out.println(info);
        while (!(command.equals("0"))) {
            try {
                command = reader.readLine();
                if (!isInteger(command)) {
                    if (command.equals("c") || command.equals("C")) {
                        System.out.println(info);
                    }
                    if (command.equals("l") || command.equals("L")) {
                        showCompany();
                    }
                    if (command.equals("s") || command.equals("S")) {
                        showAllSecurities();
                    }
                    if (command.equals("ds") || command.equals("DS")) {
                        showDelayedSecurities();
                    }
                    if (command.equals("cld") || command.equals("CLD")) {
                        System.out.println("\nВведите дату: ");

                        //showCompaniesFromDate();
                    }
                    if (command.equals("di") || command.equals("DI")) {
                        System.out.println("\nВведите валюту для поиска ценных бумаг (USD, EU, RUB): ");
                        String currency = null;
                        currency = reader.readLine();
                        showSecurityByCurrency(Models.Currency.valueOf(currency));
                    }
                } else if (isInteger(command)) {
                    int num = Integer.parseInt(command);
                    try {
                        num = Integer.parseInt(command);
                    } catch (Exception e) {
                        System.out.println("OUT OF ARRAY");
                        reader.readLine();
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public void showCompany() {
        for (Models.CompanyInfo company: companies.companies) {
            System.out.println("Имя компании: " + company.name + " | " + "Дата основания: " + company.founded + "|\n");
        }
    }

    @Override
    public void showAllSecurities() {
        for (Models.CompanyInfo company: companies.companies) {
            for (Models.Securities securities: company.securities) {
                System.out.println("|Код: " + securities.code + " | Дата истечения: " + securities.date + " | Название организации-владельца: " + securities.secName + "\n");
            }
        }
    }

    @Override
    public void showDelayedSecurities() {
        Date currentDate = new Date();
        int count = 0;
        for (Models.CompanyInfo company: companies.companies) {
            for (Models.Securities securities: company.securities) {
                if (currentDate.compareTo(securities.date) < 0) {
                    count++;
                    System.out.println("|Код: " + securities.code + " | Дата истечения: " + securities.date + " | Название организации-владельца: " + securities.secName + "\n");
                }
            }
        }
        System.out.println("Общее количество просроченных бумаг: " + count + "\n");
    }

    @Override
    public void showCompaniesFromDate(Date date) {
        for (Models.CompanyInfo company: companies.companies) {
            if (date.compareTo(company.founded) >= 0) {
                System.out.println("Имя компании: " + company.name + " | " + "Дата основания: " + company.founded + "|\n");
            }
        }

    }

    @Override
    public void showSecurityByCurrency(Models.Currency currency) {


        for (Models.CompanyInfo company: companies.companies) {
            for (Models.Securities securities: company.securities) {
                if (Arrays.asList(securities.currencies).contains(currency)) {
                    System.out.println("|Код: " + securities.code + " | Дата истечения: " + securities.date + " | Название организации-владельца: " + securities.secName + "\n");
                }
            }
        }
    }
}
