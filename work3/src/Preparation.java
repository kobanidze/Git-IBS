import java.util.Date;

public interface Preparation {
    void showCompany();
    void showAllSecurities();
    void showDelayedSecurities();
    void showCompaniesFromDate(Date date);
    void showSecurityByCurrency(Models.Currency currency);
}
