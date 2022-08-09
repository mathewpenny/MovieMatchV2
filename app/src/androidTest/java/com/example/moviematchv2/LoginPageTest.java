package com.example.moviematchv2;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginPageTest {

    @Rule
    public ActivityScenarioRule<LandingPage> mActivityScenarioRule =
            new ActivityScenarioRule<>(LandingPage.class);

    @Test
    public void loginPageTest() {
        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.loginButton),
                        childAtPosition(
                                allOf(withId(R.id.loginFrame),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.loginEmail), withText("Email"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(androidx.drawerlayout.widget.DrawerLayout.class))),
                        isDisplayed()));
        editText.check(matches(withText("Email")));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.loginEmail), withText("Email"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(androidx.drawerlayout.widget.DrawerLayout.class))),
                        isDisplayed()));
        editText2.check(matches(isDisplayed()));

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.loginPassword), withText("Password"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(androidx.drawerlayout.widget.DrawerLayout.class))),
                        isDisplayed()));
        editText3.check(matches(withText("Password")));

        ViewInteraction editText4 = onView(
                allOf(withId(R.id.loginPassword), withText("Password"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(androidx.drawerlayout.widget.DrawerLayout.class))),
                        isDisplayed()));
        editText4.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.forgotPassLink), withText("Forgot Password?"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(androidx.drawerlayout.widget.DrawerLayout.class))),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

        ViewInteraction imageButton = onView(
                allOf(withId(R.id.backButton),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        imageButton.check(matches(isDisplayed()));

        ViewInteraction linearLayout = onView(
                allOf(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class)),
                        isDisplayed()));
        linearLayout.check(matches(isDisplayed()));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
