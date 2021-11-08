import learn.data.*;
import learn.domain.GuestService;
import learn.domain.HostService;
import learn.domain.ReservationService;
import learn.model.Guest;
import learn.model.Reservation;
import learn.ui.ConsoleIO;
import learn.ui.Controller;
import learn.ui.View;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class App {
    public static void main(String[] args) {


        ApplicationContext context = new ClassPathXmlApplicationContext("dependency-config.xml");
        Controller controller = context.getBean(Controller.class);
        controller.run();
    }
}
