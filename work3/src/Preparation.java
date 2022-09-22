public interface Preparation {
    void showCompany();
    void showAllSecurities();
    void showExpiredSecurities();
    void showCompaniesFromDate(String date);
    void showSecurityByCurrency(Models.Currency currency);
}
