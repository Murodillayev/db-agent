package uz.pdp.dbagent;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class CalculatorCommand {
    private final CalculatorService calculatorService;
    private Boolean logined = false;

    @ShellMethod("Ikkita sonni qo'shadi")
    int add(int a, int b) {
        return calculatorService.add(a, b);
    }

    @ShellMethod(value = "Ikkita sonni ayraydi", key = "s")
    int substract(int a, int b) {
        return calculatorService.sub(a, b);
    }

    @ShellMethod
    int mul(int a, int b) {
        return calculatorService.mul(a, b);
    }

    @ShellMethod
    @ShellMethodAvailability("islogined")
    int div(int a, int b) {

        return calculatorService.div(a, b);
    }

    @ShellMethod
    String login(@ShellOption(value = "-u", valueProvider = LoginProvider.class) String login, @ShellOption("-p") String password) {
        // check login password
        logined = true;
        return "Successfully logged in";
    }

    Availability islogined() {
        return logined ? Availability.available() : Availability.unavailable("=> First login");
    }


}
