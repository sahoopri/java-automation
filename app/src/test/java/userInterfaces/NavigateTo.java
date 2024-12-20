package userInterfaces;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;

public class NavigateTo {
    public static Performable theCarComparePage() {
        return Task.where("{0} goes to the car tax check page",
                Open.browserOn().the(CarCompareHomePage.class));
    }
}
