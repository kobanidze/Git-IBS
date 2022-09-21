import com.google.gson.*;

import org.apache.commons.io.FileUtils;
import java.io.*;
import java.lang.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class Logic implements Preparation {
    private Models.Companies companies;

    public void po(String jsonString) {
        Gson g = new Gson();
        this.companies = g.fromJson(jsonString, Models.Companies.class);
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    public void call() throws IOException {
        String exampleRequest = FileUtils.readFileToString(new File("D:\\Java\\Git-IBS\\work3\\src\\Company.json"), StandardCharsets.UTF_8);
        po(exampleRequest);
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
                    switch (command) {
                        case "c", "C" -> System.out.println(info);
                        case "l", "L" -> showCompany();
                        case "s", "S" -> showAllSecurities();
                        case "ds", "DS" -> showDelayedSecurities();
                        case "cld", "CLD" -> {
                            System.out.println("\nВведите дату: ");
                            String date = "";
                            date = reader.readLine();
                            showCompaniesFromDate(date);
                        }
                        case "di", "DI" -> {
                            System.out.println("\nВведите валюту для поиска ценных бумаг (USD, EU, RUB): ");
                            String currency = "";
                            currency = reader.readLine();
                            showSecurityByCurrency(Models.Currency.valueOf("USD"));
                        }
                        default -> System.out.println("Такой команды нет");
                    }
                } else {
                    System.out.println("Выберете команду из списка!\n");
                    System.out.println(info);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public void showCompany() {
        for (Models.CompanyInfo company: companies.companies) {
            System.out.println("|Имя компании: " + company.name + " | " + "Дата основания: " + company.founded + "|\n");
        }
    }

    @Override
    public void showAllSecurities() {
        for (Models.CompanyInfo company: companies.companies) {
            for (Models.Securities securities: company.securities) {
                System.out.println("|Код: " + securities.code + " | Дата истечения: " + securities.date + " | Название организации-владельца: " + company.name + "|\n");
            }
        }
    }

    @Override
    public void showDelayedSecurities() {
        Date currentDate = new Date();
        int count = 0;
        for (Models.CompanyInfo company: companies.companies) {
            for (Models.Securities securities: company.securities) {
                if (currentDate.compareTo(LocalDateAdapter.parse(securities.date)) > 0) {
                    count++;
                    System.out.println("|Код: " + securities.code + " | Дата истечения: " + securities.date + " | Название организации-владельца: " + company.name + "|\n");
                }
            }
        }
        System.out.println("Общее количество просроченных бумаг: " + count + "\n");
    }

    @Override
    public void showCompaniesFromDate(String date) {
        for (Models.CompanyInfo company: companies.companies) {
            if (LocalDateAdapter.parse(date).compareTo(LocalDateAdapter.parse(company.founded)) <= 0) {
                System.out.println("Имя компании: " + company.name + " | " + "Дата основания: " + company.founded + "|\n");
            }
        }

    }

    @Override
    public void showSecurityByCurrency(Models.Currency currency) {


        for (Models.CompanyInfo company: companies.companies) {
            for (Models.Securities securities: company.securities) {
                if (Arrays.asList(securities.currencies).contains(currency)) {
                    System.out.println("|Код: " + securities.code + " | Дата истечения: " + securities.date + " | Название организации-владельца: " + company.name + "|\n");
                }
            }
        }
    }
}