package com.example.moviematchv2;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
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
public class RegisterPageTest {

    @Rule
    public ActivityScenarioRule<LandingPage> mActivityScenarioRule =
            new ActivityScenarioRule<>(LandingPage.class);

    @Test
    public void registerPageTest() {
        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.registerButton),
                        childAtPosition(
                                allOf(withId(R.id.registerFrame),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction imageButton = onView(
                allOf(withId(R.id.registerButton),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        imageButton.check(matches(isDisplayed()));

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.registerName),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.drawerlayout.widget.DrawerLayout")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.registerName),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.drawerlayout.widget.DrawerLayout")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("Erica Sinclair"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.registerEmail),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.drawerlayout.widget.DrawerLayout")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("ericas@test.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.registerPhone),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.drawerlayout.widget.DrawerLayout")),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("4388225592"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.registerPassword),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.drawerlayout.widget.DrawerLayout")),
                                        0),
                                4),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("Happyday"), closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.confirmPassword),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.drawerlayout.widget.DrawerLayout")),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("s1Happyday"), closeSoftKeyboard());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.registerPassword), withText("Happyday"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.drawerlayout.widget.DrawerLayout")),
                                        0),
                                4),
                        isDisplayed()));
        appCompatEditText7.perform(replaceText("Happydays1"));

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.registerPassword), withText("Happydays1"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.drawerlayout.widget.DrawerLayout")),
                                        0),
                                4),
                        isDisplayed()));
        appCompatEditText8.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.confirmPassword), withText("s1Happyday"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.drawerlayout.widget.DrawerLayout")),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText9.perform(replaceText("Happyday"));

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.confirmPassword), withText("Happyday"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.drawerlayout.widget.DrawerLayout")),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText10.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.confirmPassword), withText("Happyday"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.drawerlayout.widget.DrawerLayout")),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText11.perform(click());

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.confirmPassword), withText("Happyday"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.drawerlayout.widget.DrawerLayout")),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText12.perform(replaceText("Happydays1"));

        ViewInteraction appCompatEditText13 = onView(
                allOf(withId(R.id.confirmPassword), withText("Happydays1"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.drawerlayout.widget.DrawerLayout")),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText13.perform(closeSoftKeyboard());

        ViewInteraction materialCheckBox = onView(
                allOf(withId(R.id.privacyCheck), withText("By checking this I agree to the Privacy Policy "),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.drawerlayout.widget.DrawerLayout")),
                                        0),
                                6),
                        isDisplayed()));
        materialCheckBox.perform(click());
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
