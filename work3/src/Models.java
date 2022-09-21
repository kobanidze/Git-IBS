import java.util.Currency;
import java.util.Date;

public class Models {
    static class CompanyInfo {
        public int id;
        public String name;
        public String address;
        public String phoneNumber;
        public String inn;
        public String founded;
        public Securities[] securities;
    }

    enum Currency {
        RUB("RUB"),
        USD("USD"),
        EU("EU");

        private final String name;

        Currency(String name) {
            this.name = name;
        }

        public String getTitle() {
            return name;
        }
    };

    static class Securities {
        public String secName;
        public Currency[] currency;
        public String code;
        public String date;
    }

    static class Companies {
        public CompanyInfo[] companies;
    }
}