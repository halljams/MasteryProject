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
//        ConsoleIO io = new ConsoleIO();
//        View view = new View(io);
//
//        ReservationFileRepository reservationFileRepository = new ReservationFileRepository("./reservations");
//        GuestFileRepository guestFileRepository = new GuestFileRepository("./data/guests.csv");
//        HostFileRepository hostFileRepository = new HostFileRepository("./data/hosts.csv");
//
//        GuestService guestService = new GuestService(guestFileRepository);
//        ReservationService reservationService = new ReservationService(reservationFileRepository, guestFileRepository, hostFileRepository);
//        HostService hostService = new HostService(hostFileRepository);
//        Controller controller = new Controller(guestService, hostService, reservationService, view);

        ApplicationContext context = new ClassPathXmlApplicationContext("dependency-config.xml");
        Controller controller = context.getBean(Controller.class);
        controller.run();
    }
}
