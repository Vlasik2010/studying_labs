import java.util.*;

public class Customer {
    private static HashSet<Customer> Customers = new HashSet<>();
    private static HashMap<String,String> customers_key = new HashMap<>();
    private List<String> Tariffs_user = new ArrayList<>();
    private String name;
    private int age;
    private String MobileNumber;
    private String password;
    private boolean rootAdmin = false;
    private int total=0;
    private Customer(String name, int age, String MobileNumber, String password, boolean root) {
        this.name = name;
        this.age = age;
        this.MobileNumber = MobileNumber;
        this.password = String.valueOf(password.hashCode());
        this.rootAdmin = root;
    }

    public boolean getRoot(){
        return rootAdmin;
    }
public static void printInfoAllCart(){
        for (Customer c : Customers) {
            System.out.println("У клиента по имени " + c.name + " заказов на сумму: " + c.total+".");
        }

}

public static void CreateAdmin(int Password){
        Customer Admin = new Customer("Admin",0,"Admin", String.valueOf(Password),true);
        customers_key.put("Admin", String.valueOf(String.valueOf(Password).hashCode()));
        Customers.add(Admin);
}

    public static Customer CreateCustomer(){
        Scanner scanner = new Scanner(System.in);
        String name="Unknown";
        while (true) {
            System.out.print("Введите имя пользователя: ");
            name = scanner.nextLine();
            if (name != null && !name.isBlank()) {
                break;
            }
            System.out.println("Введите корректное имя пользователя");

        }

        int age = -1;
        while (true) {
            System.out.print("Введите ваш возраст: ");
            try {
                scanner = new Scanner(System.in);
                age = scanner.nextInt();
                scanner.nextLine();
            }
            catch (Exception e){
                System.out.println("Вы ввели некорректное значение!");
                continue;
            }
            if (age > 0 && age <= 100) {break;}
        }

        String MobileNumber ="Unknown";
        while (true) {
            System.out.print("Введите ваш номер: ");
            MobileNumber = scanner.nextLine();
            if (MobileNumber != null && !MobileNumber.isBlank()) {
                break;
            }
            System.out.println("Введите корректный номер телефона");

        }


        String Password ="Unknown";
        while (true) {
            System.out.print("Введите пароль: ");
            Password = scanner.nextLine();
            if (Password != null && !Password.isBlank()) {
                break;
            }
            System.out.println("Введите корректный пароль");

        }

        Customer customer = new Customer(name, age, MobileNumber,Password,false);
        if (customers_key.containsKey(customer.MobileNumber)){
            System.out.println("Пользователь с таким номером телефона уже зарегистрирован!");
            return null;
        }
        customers_key.put(customer.MobileNumber, customer.password);
        Customers.add(customer);
        System.out.println("Пользователь зарегистрирован");
        return customer;
    }
    public void ViewingCart(){
        System.out.println("Корзина: ");
        int k=1;
        for (String tariff : Tariffs_user){
            System.out.println("Тариф " + k+": "+ tariff);
            k+=1;
        }
        System.out.println("Стоимость вашего заказа равна: " + total + "$.");
    }

    public void AddTariff(){
        Scanner scanner;
        int choice = -1;
        while (true) {
            System.out.print("Введите число соответвующее тарифу: ");
            try {
                scanner = new Scanner(System.in);
                choice = scanner.nextInt();
                scanner.nextLine();
            }
            catch (Exception e){
                System.out.println("Вы ввели некорректное значение!");
                continue;
            }
            break;
        }
        switch (choice) {
            case 1:
                Tariffs_user.add("Маленбкая коробка");
                total+= Tariffs.SMALL_BOX.getPrice();
                break;
            case 2:
                Tariffs_user.add("Средняя коробка");
                total+= Tariffs.MEDIUM_BOX.getPrice();
                break;
            case 3:
                Tariffs_user.add("Большая коробка");
                total+= Tariffs.LARGE_BOX.getPrice();
                break;
             case 4:
                 Tariffs_user.add("Маленькая коробка (быстрая доставка)");
                 total+= Tariffs.SMALL_BOX_QUICK.getPrice();
                 break;
            case 5:
                Tariffs_user.add("Средняя коробка (быстрая доставка)");
                total+= Tariffs.MEDIUM_BOX_QUICK.getPrice();
                break;
            case 6:
                Tariffs_user.add("Большая коробка (быстрая доставка)");
                total+= Tariffs.LARGE_BOX_QUICK.getPrice();
                break;
            default:
                System.out.println("Введите число от 1 до 6!");
        }


    }
    public void PrintInfo(){

        System.out.printf("Имя клиента: %s\n", name);
        System.out.printf("Возраст клиента: %d\n", age);
        System.out.printf("Номер клиента: %s\n", MobileNumber);
    }
    public static Customer authenticateUser(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите номер телефона: ");
        String mobileNumber = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        System.out.println();
        if (customers_key.containsKey(mobileNumber)){
            if (customers_key.get(mobileNumber).equals(String.valueOf(password.hashCode()))){
                System.out.println("Вы успешно вошли в систему!\n");
                Customer ActualCustomer = FindCustomer(mobileNumber);
                if (ActualCustomer != null){
                    return ActualCustomer;
                }
                else{
                    System.out.println("Не смогли войти в аккунт пользователя!");
                    return null;
                }
            }
            else{
                System.out.println("Вы ввели неверный пароль, повторите попытку!");
            }
        }
        else{
            System.out.println("Такого номера телефона не существует в нашей БД, повторите попытку!");
            System.out.println();
        }
        return null;
    }
    public static Customer FindCustomer(String MobileNumber){
        for (Customer customer : Customers) {
            if (customer.MobileNumber.equals(MobileNumber)){
                return customer;
            }
        }
        System.out.println("Пользователь не был найден");
        return null;
    }



}
