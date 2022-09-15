package logic;

import sweet.Sweets;

public interface Preparator {
    void prepareBox();
    void getList();
    double getAllWeight();
    double getAllPrice();
    void addSweet(Sweets sweets);
    void deleteSweetByIndex(int index);
    void deleteLastSweet();
    void getInfo();
    void getAllInfo();
    void isPacked();
}
