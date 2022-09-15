package sweet;

public abstract class Sweets {
    private String name;
    private double weight;
    private double price;

    public Sweets() {
        this("no name",0.0,0.0);
    }

    public Sweets(String name, double weight, double price) {
        this.name = name;
        this.price = price;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    private String getInfo() {
        String info = "";
        info += "Сладость=\"" + name + '\"' +
                ", цена=" + price +
                "руб., вес=" + weight +
                "кг." +
                '}';
        return info;
    }

    @Override
    public String toString() {
        return getInfo();
    }
}
