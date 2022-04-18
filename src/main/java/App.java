import rest.comminication.Communication;
import rest.conf.MyConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {
        System.out.println("GO REST CLIENT");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);


        Communication communication = context.getBean("communication", Communication.class);
        System.out.println("after get Bean");

        //List<User> allUsers = communication.getAllUsers();
        communication.doAllRequests();
//        System.out.println(allUsers);
    }
}
