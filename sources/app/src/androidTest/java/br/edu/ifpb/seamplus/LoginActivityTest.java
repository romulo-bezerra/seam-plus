package br.edu.ifpb.seamplus;

import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.edu.ifpb.seamplus.activity.HomeActivity;
import br.edu.ifpb.seamplus.activity.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class, false, true);


    @Test
    public void checkInitialStateOfInputFieldsAndButtonTest() {
        onView(withId(R.id.etEmailLogin)).check(matches(isDisplayed()));
        onView(withId(R.id.etSenhaLogin)).check(matches(isDisplayed()));
        onView(withId(R.id.btnEntrar)).check(matches(isDisplayed()));
    }

    @Test
    public void loginSuccessTest() {
        Intents.init();

        onView(withId(R.id.etEmailLogin)).perform(typeText("r@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.etSenhaLogin)).perform(typeText("123"), closeSoftKeyboard());

        Matcher<Intent> matcher = hasComponent(HomeActivity.class.getName());

        onView(withId(R.id.btnEntrar)).perform(click());

        intended(matcher);

        Intents.release();
    }

}
