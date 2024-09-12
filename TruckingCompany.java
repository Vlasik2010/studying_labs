public class TruckingCompany {
    String NameofCompany;
    int MoneyforMass=0 ;
    int TotalMass=0;

    TruckingCompany(String Name) {
        NameofCompany = Name;
    }
    TruckingCompany(String Name, int MoneyforMass, int TotalMass) {
        NameofCompany = Name;
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

// подсчет прибыли
    public int get_revenue(){
            return MoneyforMass * TotalMass;
    }

}
