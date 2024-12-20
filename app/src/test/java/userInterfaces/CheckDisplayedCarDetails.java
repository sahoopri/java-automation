package userInterfaces;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.waits.WaitUntil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import questions.CarDetails;
import questions.CarDetailsCheckList;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.hasToString;

public class CheckDisplayedCarDetails {

    public static Performable using(Map<String, HashMap<String, String>> outputCarDetails, String regNumber) {
        WaitUntil.the(CarDetailsCheckList.CAR_REGISTRATION_NUMBER, hasToString(regNumber));

        HashMap<String, String> expectedCarDetails = new HashMap<String, String>(outputCarDetails.get(regNumber));
        Iterator carDetailsIterator = expectedCarDetails.entrySet().iterator();
        while (carDetailsIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry) carDetailsIterator.next();
            String valueToCheck = mapElement.getValue().toString();
            switch (mapElement.getKey().toString()) {
                case "make":
                    theActorInTheSpotlight().should(
                            seeThat("car make", CarDetails.carMake(),
                                    equalToIgnoringCase(valueToCheck)));
                    break;
                case "model":
                    theActorInTheSpotlight().should(
                            seeThat("car model", CarDetails.carModel(),
                                    equalToIgnoringCase(valueToCheck)));
                    break;
                case "year":
                    theActorInTheSpotlight().should(
                            seeThat("car year", CarDetails.carYear(),
                                    equalToIgnoringCase(valueToCheck)));
                    break;
            }
        }
        return null;
    }

}
