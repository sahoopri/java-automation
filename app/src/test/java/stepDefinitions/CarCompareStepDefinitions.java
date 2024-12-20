package stepDefinitions;

import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.conditions.Check;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import actions.ReadInputFile;
import actions.ReadOutputFile;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import userInterfaces.CheckDisplayedCarDetails;
import userInterfaces.CheckValidCarRegistrationNumber;
import userInterfaces.NavigateTo;

import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static userInterfaces.CarCompareHomePage.performFreeCheckUsing;

public class CarCompareStepDefinitions {
    ArrayList<String> registrationNumbersToCheck;
    Map<String, HashMap<String, String>> outputCarDetails;

    @Before
    public void set_the_stage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("^(.*) decides to check the tax details of the cars whose registration numbers present in a file under the path (.*)")
    public void euanDecidesToCheckTheCarTaxDetailsOfRegistrationNumbersPresentInAFile(String actor, String inputFilePath) {
        registrationNumbersToCheck = new ArrayList(ReadInputFile.read(inputFilePath));
        theActorCalled(actor).attemptsTo(NavigateTo.theCarComparePage());
    }

    @Then("^s?he should see the displayed car tax details matches the expected details from the output file under the path (.*)")
    public void heShouldSeeTheCarTaxDetailsMatchesTheExpectedDetailsFromTheOutputFile(String outputFilePath) {
        //read the output file to get the expected details of all the cars
        outputCarDetails = ReadOutputFile.read(outputFilePath);

        //for each registration number present in the input file
        for (String regNumber : registrationNumbersToCheck) {
            theActorInTheSpotlight().attemptsTo(performFreeCheckUsing(regNumber));

            if (outputCarDetails.containsKey(regNumber)) {
                Check.whether(outputCarDetails.containsKey(regNumber)).
                        andIfSo(CheckDisplayedCarDetails.using(outputCarDetails, regNumber));
                theActorInTheSpotlight().attemptsTo(NavigateTo.theCarComparePage());
            }
            else {
                theActorInTheSpotlight().attemptsTo(CheckValidCarRegistrationNumber.isRegistrationNumberInvalid(regNumber));
                theActorInTheSpotlight().attemptsTo(NavigateTo.theCarComparePage());
                continue;
            }
        }
    }
}

