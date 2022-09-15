package logic;

import sweet.Sweets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class BoxLogic implements Opimizator,Preparator {
    SweetsList list = new SweetsList();
    private ArrayList<Sweets> box;
    private boolean isPacked;

    public BoxLogic() {
        this.box = new ArrayList<>();
        this.isPacked = false;
    }

    @Override
    public void getList() {

        System.out.println("Меню \n");
        for (int i = 0; i <list.getSweetList().length ; i++) {
            String prefix = "Индекс "+ i +": ";
            System.out.println(prefix+list.getSweetList()[i]);
        }
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    @Override
    public void prepareBox() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String command;
        String info = """
                Введите номер конфеты что бы добавить ее в коробку.
                команды:
                c/C - показать команды
                l/L - показать список сладостей
                b/B - упаковать коробку
                i/I - показать состав коробки
                d/D - удалить последний добавленный подарок
                di/Di - удалить по индексу
                po/PO - оптимизация по цене(удаление минимальных)
                wo/WO - оптимизация по весу(удаление минимальных)
                """;
        System.out.println(info);
        getList();

        while (!isPacked) {
            try {
                command =reader.readLine();
                if (!isInteger(command)) {
                    if (command.equals("c")||command.equals("C")) {
                        System.out.println(info);
                    }
                    if (command.equals("l")||command.equals("L")) {
                        getList();
                    }
                    if (command.equals("b")||command.equals("B")) {
                        isPacked();
                        getAllInfo();
                    }
                    if (command.equals("i")||command.equals("I")) {
                        getAllInfo();
                    }
                    if (command.equals("d")||command.equals("D")) {
                        deleteLastSweet();
                        getInfo();

                    }
                    if (command.equals("di")||command.equals("DI")) {
                        System.out.println("\nвведите индекс для удаления");
                        int index = 0;

                        try {
                            index= Integer.parseInt(reader.readLine());
                        } catch (IOException e) {
                            System.out.println("промах мимо цифры");
                            e.printStackTrace();
                        }
                        deleteSweetByIndex(index-1);
                        getAllInfo();
                    }
                    if (command.equals("po")||command.equals("PO")) {
                        System.out.println("введите предел цены для подарка");
                        try {
                            double maxPrice = Double.parseDouble(reader.readLine());
                            reducePrice(maxPrice);
                            getAllInfo();
                        } catch (IOException e) {
                            System.out.println("какая-то ошибка, возможно неверная цена");
                            e.printStackTrace();
                        }

                    }
                    if (command.equals("wo")||command.equals("WO")) {
                        System.out.println("введите предел веса для подарка");
                        try {
                            double maxWeight= Double.parseDouble(reader.readLine());
                            reduceWeight(maxWeight);
                            getAllInfo();
                        } catch (IOException e) {
                            System.out.println("какая-то ошибка, возможно неверная цена");
                            e.printStackTrace();
                        }
                    }
                }
                else if (isInteger(command)) {
                    int num = Integer.parseInt(command);
                    if (num >= 0) {
                        try {
                            addSweet(list.getSweetList()[num]);
                        }catch (Exception e) {
                            System.out.println("OUT OF ARRAY");
                            reader.readLine();
                        }
                        getInfo();
                    }
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void isPacked() {
        isPacked = true;
    }

    @Override
    public void addSweet(Sweets sweets) {
        box.add(sweets);
        System.out.println("Сладость: " + sweets.getName()+" добавлена.");
    }

    @Override
    public void deleteSweetByIndex(int index) {

        System.out.println("Удалена сладость " +box.get(index).getName()+" по индексу : "+(index));
        box.remove(index);
    }

    @Override
    public void deleteLastSweet() {

        if(box.size()==0) {
            System.out.println("ты уже все удалил");
        } else {
            System.out.println("Удалена сладость " +box.get(box.size()-1).getName()+" по индексу : "+(box.size()));
            box.remove(box.size()-1);
        }


    }

    @Override
    public double getAllWeight() {
        double totalWeight = 0;
        for (Sweets sweets: box) {
            totalWeight += sweets.getWeight();
        }
        return totalWeight;
    }

    @Override
    public double getAllPrice() {
        double totalPrice = 0;
        for (Sweets sweet: box) {
            totalPrice += sweet.getPrice();
        }
        return totalPrice;
    }

    @Override
    public void getInfo() {
        String allInfo = "Общий вес коробки: "+ String.format("%.2f",getAllWeight()) +" кг. Общая цена коробки: "+ getAllPrice()+" рублей. Количество сладостей в подарке: "+box.size()+"\n";
        System.out.println(allInfo);
    }

    @Override
    public void getAllInfo() {

        if (!(box.size()==0)) {
            int index = 0;

            System.out.println("Содержание коробки:");
            for (Sweets sweet : box) {
                index++;
                String prefix = "Индекс: "+index+", ";
                System.out.println(prefix+sweet.toString());

            }
            System.out.println();
            getInfo();
        }
        else System.out.println("коробка пуста");
    }

    @Override
    public void reduceWeight(double weight) {
        int count = 0;
        double min = Double.MAX_VALUE;
        Sweets minSweet = null;
        while (getAllWeight()>weight) {
            for (Sweets sweets : box
            ) {
                if (sweets.getWeight()<min) {
                    min = sweets.getWeight();

                    minSweet = sweets;
                }
            }
            System.out.println("минимальный вес конфеты в подарке: " + min);
            box.remove(minSweet);
            min = Double.MAX_VALUE;
            if (minSweet != null) {
                System.out.println("Удалена сладость: "+minSweet.getName());
            }
            count++;
        }

        System.out.println("Оптимизация подарка выполнена успешна. Удалено "+count+" сладостей");
    }

    @Override
    public void reducePrice(double price) {
        int count = 0;
        double min = Double.MAX_VALUE;
        Sweets minSweet = null;
        while (getAllPrice()>price) {
            for (Sweets sweets : box
            ) {
                if (sweets.getPrice()<min) {
                    min = sweets.getPrice();

                    minSweet = sweets;
                }
            }
            System.out.println("минимальное цена конфеты  в подарке: " + min);
            box.remove(minSweet);
            min = Double.MAX_VALUE;
            if (minSweet != null) {
                System.out.println("Удалена сладость: "+minSweet.getName());
            }
            count++;
        }
        System.out.println("Оптимизация подарка выполнена успешна. Удалено "+count+" сладостей");

    }
}
