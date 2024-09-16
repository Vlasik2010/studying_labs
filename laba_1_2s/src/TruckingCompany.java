public class TruckingCompany {
    String NameofCompany="";
    int MoneyforMass=0 ;
    int TotalMass=0;

    TruckingCompany(String NameofCompany) {
        this.NameofCompany = NameofCompany;
    }
    TruckingCompany(String NameofCompany, int MoneyforMass, int TotalMass) {
        this.NameofCompany = NameofCompany;
        this.MoneyforMass = MoneyforMass;
        this.TotalMass = TotalMass;
    }
//  сетеры
    public void setMoneyforMass(int MoneyforMass) {
        this.MoneyforMass = MoneyforMass;
    }
    public void setTotalMass(int TotalMass) {
        this.TotalMass = TotalMass;
    }
// геттеры
    public int getMoneyforMass() {
        return MoneyforMass;
    }
    public int getTotalMass() {
        return TotalMass;
    }
    public String getNameofCompany() {
        return NameofCompany;
    }
// подсчет прибыли
    public int get_revenue(){
            return MoneyforMass * TotalMass;
    }
}
