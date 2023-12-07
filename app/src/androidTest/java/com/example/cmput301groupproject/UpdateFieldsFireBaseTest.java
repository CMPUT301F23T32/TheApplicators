package com.example.cmput301groupproject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


public class UpdateFieldsFireBaseTest {
    private ItemEditActivity itemEditActivity;

    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Before
    public void setup() {
        // Initialize Intents before each test
        Intents.init();

        // Type the username "TestUser" in the EditText
        onView(withId(R.id.usernameEditText))
                .perform(ViewActions.typeText("TestUser"), ViewActions.closeSoftKeyboard());

        // Click the login button
        onView(withId(R.id.loginButton))
                .perform(click());

        // Delay for a short time to allow any asynchronous operations to complete
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*@After
    public void tearDown() {
        // Release Intents after each test
        Intents.release();
    }*/
    @Test
    public void test_UpdateFromSerialNumberImage() {
        // Launch the ItemEditActivity
        onView(withId(R.id.add_item_b)).perform(click());

        ActivityScenario<ItemEditActivity> scenario = ActivityScenario.launch(ItemEditActivity.class);

        // Use onActivity to get a reference to the launched activity
        scenario.onActivity(activity -> {
            itemEditActivity = activity;
        });

        // Perform the action that triggers the accessFirebase method
        itemEditActivity.accessFirebase("4234", false);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("4234")).check(matches(isDisplayed()));

    }

    @Test
    public void test_UpdateFromBarcodeScanner() {
        // launching activity
        onView(withId(R.id.add_item_b)).perform(click());

        ActivityScenario<ItemEditActivity> scenario = ActivityScenario.launch(ItemEditActivity.class);

        // get reference to launched activity
        scenario.onActivity(activity -> {
            itemEditActivity = activity;
        });
        // calling accessFirebase
        itemEditActivity.accessFirebase("123456789104", true);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("123456789104")).check(matches(isDisplayed()));
        onView(withText("This is the demo description from barcode scanning demo")).check(matches(isDisplayed()));
        onView(withText("Demo Comment")).check(matches(isDisplayed()));
        onView(withText("555")).check(matches(isDisplayed()));
        onView(withText("DemoMake")).check(matches(isDisplayed()));
        onView(withText("DemoModel")).check(matches(isDisplayed()));
        onView(withText("2002/01/01")).check(matches(isDisplayed()));
    }

    @Test
    public void test_UpdateWhenNotInDB() {
        onView(withId(R.id.add_item_b)).perform(click());

        ActivityScenario<ItemEditActivity> scenario = ActivityScenario.launch(ItemEditActivity.class);

        // get reference to launched activity
        scenario.onActivity(activity -> {
            itemEditActivity = activity;
        });

        // call accessFirebase
        itemEditActivity.accessFirebase("123123123", true);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



}