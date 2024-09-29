import java.util.Random;
public class Admin {
private Admin(){
}
    private static String instance;

public static String CreateAdmin(){
    Random rand = new Random();
    int Password = 1000 + rand.nextInt(9000);
    if(instance == null){
        instance = "Login: Admin, Password: "+ Password;
        System.out.println(instance);
        Customer.CreateAdmin(Password);
    }
    return instance;
}

}
