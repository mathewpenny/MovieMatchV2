package com.example.moviematchv2;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LandingPageTest {

    @Rule
    public ActivityScenarioRule<LandingPage> mActivityScenarioRule =
            new ActivityScenarioRule<>(LandingPage.class);

    @Test
    public void landingPageTest() {
        ViewInteraction textView = onView(
                allOf(withText("Login"),
                        withParent(allOf(withId(R.id.loginFrame),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

        ViewInteraction imageButton = onView(
                allOf(withId(R.id.registerButton),
                        withParent(allOf(withId(R.id.registerFrame),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        imageButton.check(matches(isDisplayed()));

        ViewInteraction imageButton2 = onView(
                allOf(withId(R.id.metaSignIn),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        imageButton2.check(matches(isDisplayed()));

        ViewInteraction imageButton3 = onView(
                allOf(withId(R.id.googleSignIn),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        imageButton3.check(matches(isDisplayed()));

        ViewInteraction imageButton4 = onView(
                allOf(withId(R.id.loginButton),
                        withParent(allOf(withId(R.id.loginFrame),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        imageButton4.check(matches(isDisplayed()));

        ViewInteraction imageButton5 = onView(
                allOf(withId(R.id.loginButton),
                        withParent(allOf(withId(R.id.loginFrame),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        imageButton5.check(matches(isDisplayed()));
    }
}
