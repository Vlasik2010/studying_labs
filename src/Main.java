import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        System.out.println("Добро пожаловать!");
        Customer ActualCustomer;
        boolean GoMenu;
            while (true) {
                GoMenu = false;
                System.out.println("Выберите действие: 1 - Создать нового пользователя, 2 - Войти в существующий аккаунт, 3 - Создать аккаунт от имени администратора, 4 - завершить работу программы.");
                Scanner scanner = new Scanner(System.in);
                int choice;
                try {
                    choice = scanner.nextInt();
                }
                catch (Exception e) {
                    System.out.println("Вы ввели некорректное значение!");
                    continue;
                }
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        Customer.CreateCustomer();
                        break;
                    case 2:
                        while (true) {
                            if (GoMenu) break;
                            ActualCustomer = Customer.authenticateUser();
                            if (ActualCustomer == null) {
                                break;
                            }
                            ActualCustomer.PrintInfo();
                                while (true) {
                                    if (!ActualCustomer.getRoot()) {
                                        System.out.println("Вы можете: 1 - выйти из аккаунта, 2 - добавить тариф, 3 - посмотреть корзину.");
                                        int choiceLogIn = -1;
                                        try {
                                            choiceLogIn = scanner.nextInt();
                                        } catch (Exception e) {
                                            System.out.println("Вы ввели некорректное значение!");
                                        }
                                        scanner.nextLine();
                                        switch (choiceLogIn) {
                                            case 1:
                                                ActualCustomer = null;
                                                GoMenu = true;
                                                break;
                                            case 2:
                                                System.out.println("Вы можете добавить в корзину интересующие вас тарифы.");
                                                Tariffs.PrintInfoShop();
                                                ActualCustomer.AddTariff();
                                                break;
                                            case 3:
                                                ActualCustomer.ViewingCart();
                                                break;
                                            default:
                                                System.out.println("Введите значение от 1 до 3!");

                                        }
                                        if (ActualCustomer == null) {
                                            break;
                                        }
                                    }
                                    else{
                                        System.out.println("Вы можете: 1 - выйти из аккаунта, 2 - Посмотреть суммарную стоимость всех заказов");


                                        int choiceAdmin = -1;
                                        try {
                                            choiceAdmin = scanner.nextInt();
                                        } catch (Exception e) {
                                            System.out.println("Вы ввели некорректное значение!");
                                        }
                                        scanner.nextLine();
                                        switch (choiceAdmin) {
                                            case 1:
                                                ActualCustomer = null;
                                                GoMenu = true;
                                                break;
                                            case 2:
                                                Customer.printInfoAllCart();
                                                 break;
                                            default:
                                                System.out.println("Введите значение от 1 до 2!");
                                        }

                                        if (ActualCustomer == null) {
                                            break;
                                        }
                                    }
                                }


                        }
                        break;
                        case 3:
                            Admin.CreateAdmin();
                            break;
                            case 4:
                                return;



                    default:
                        System.out.println("Вы ввели некорректное значаение!");

                }

            }
        }






//        try(Scanner scanner = new Scanner(System.in)) {
//            System.out.println("Введите название компании: ");
//            String NameOfCompany = scanner.nextLine();
//            System.out.println("Введите количество денег за тонну веса: ");
//            int MoneyforMass = scanner.nextInt();
//            System.out.println("Введите количество тонн: ");
//            int TotalMass = scanner.nextInt();
//            TruckingCompany Gruz = new TruckingCompany(NameOfCompany, MoneyforMass, TotalMass);
//            System.out.printf("Компания по перевозке грузов: \"%s\" перевезла %d тонн груза.\n",Gruz.getNameofCompany(), Gruz.getTotalMass());
//            System.out.printf("За одну тонну груза компания берет %d$.\n",Gruz.getMoneyforMass());
//            System.out.printf("Всего компания заработала %d$.\n",Gruz.get_revenue());
//        }
//        catch(Exception e) {
//            System.out.println(e.fillInStackTrace());
//        }
    }

