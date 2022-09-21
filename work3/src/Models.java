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

    enum Currency {RUB, USD, EU};

    static class Securities {
        public String secName;
        public Currency[] currencies;
        public String code;
        public String date;
    }

    static class Companies {
        public CompanyInfo[] companies;
    }
}
