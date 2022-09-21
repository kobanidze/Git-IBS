import java.util.Date;

public interface Preparation {
    void showCompany();
    void showAllSecurities();
    void showDelayedSecurities();
    void showCompaniesFromDate(String date);
    void showSecurityByCurrency(Models.Currency currency);
}
