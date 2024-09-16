import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(System.in)) {
            System.out.println("Введите название компании: ");
            String NameOfCompany = scanner.nextLine();
            System.out.println("Введите количество денег за тонну веса: ");
            int MoneyforMass = scanner.nextInt();
            System.out.println("Введите количество тонн: ");
            int TotalMass = scanner.nextInt();
            TruckingCompany Gruz = new TruckingCompany(NameOfCompany, MoneyforMass, TotalMass);
            System.out.printf("Компания по перевозке грузов: \"%s\" перевезла %d тонн груза.\n",Gruz.getNameofCompany(), Gruz.getTotalMass());
            System.out.printf("За одну тонну груза компания берет %d$.\n",Gruz.getMoneyforMass());
            System.out.printf("Всего компания заработала %d$.\n",Gruz.get_revenue());
        }
        catch(Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }
}

