class TruckingCompany:
    NameofCompany = None
    MoneyforMass = 0
    TotalMass = 0
    def __init__(self, NameofCompany, MoneyforMass, TotalMass):
        self.NameofCompany = NameofCompany
        self.MoneyforMass = MoneyforMass
        self.TotalMass = TotalMass
    def setMoneyforMass(self, MoneyforMass):
        self.MoneyforMass = MoneyforMass
    def setTotalMass(self, TotalMass):
        self.TotalMass = TotalMass
    def getRevenue(self):
        return self.MoneyforMass * self.TotalMass

Gruz = str(input("Введите название компании: "))
MoneyforMass = int(input("Введите количество денег за тонну веса: "))
TotalMass = int(input("Введите количество тонн: "))
Gruz = TruckingCompany(Gruz, MoneyforMass, TotalMass)
print("Компания по перевозке грузов: " + Gruz.NameofCompany +" перевезла " + str(Gruz.TotalMass) + " тонн груза.")
print("За одну тонну груза компания берет " +  str(Gruz.MoneyforMass) + " денег.")
print("Всего компания заработала " +  str(Gruz.getRevenue()) + " денег.")