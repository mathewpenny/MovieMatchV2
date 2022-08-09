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
public class WelcomePageTest {

    @Rule
    public ActivityScenarioRule<LandingPage> mActivityScenarioRule =
            new ActivityScenarioRule<>(LandingPage.class);

    @Test
    public void welcomePageTest() {
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

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.loginEmail),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.drawerlayout.widget.DrawerLayout")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("metallica_rus@t.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.loginEmail), withText("metallica_rus@t.com"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.drawerlayout.widget.DrawerLayout")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText2.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.loginEmail), withText("metallica_rus@t.com"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.drawerlayout.widget.DrawerLayout")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("metallica_ruless@t.com"));

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.loginEmail), withText("metallica_ruless@t.com"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.drawerlayout.widget.DrawerLayout")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText4.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.loginEmail), withText("metallica_ruless@t.com"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.drawerlayout.widget.DrawerLayout")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText5.perform(click());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.loginEmail), withText("metallica_ruless@t.com"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.drawerlayout.widget.DrawerLayout")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("metallica_rules@t.com"));

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.loginEmail), withText("metallica_rules@t.com"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.drawerlayout.widget.DrawerLayout")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText7.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.loginEmail), withText("metallica_rules@t.com"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.drawerlayout.widget.DrawerLayout")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText8.perform(click());

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.loginEmail), withText("metallica_rules@t.com"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.drawerlayout.widget.DrawerLayout")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText9.perform(replaceText("metallica_rules@test.com"));

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.loginEmail), withText("metallica_rules@test.com"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.drawerlayout.widget.DrawerLayout")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText10.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.loginPassword),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.drawerlayout.widget.DrawerLayout")),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText11.perform(replaceText("Happydays1"), closeSoftKeyboard());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.loginButton),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        5),
                                0),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction drawerLayout = onView(
                allOf(withId(R.id.drawer_view),
                        withParent(allOf(withId(android.R.id.content),
                                withParent(withId(androidx.appcompat.R.id.decor_content_parent)))),
                        isDisplayed()));
        drawerLayout.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.nameText), withText("Eddie the frea"),
                        withParent(withParent(withId(R.id.drawer_view))),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

        ViewInteraction imageButton = onView(
                allOf(withId(R.id.startButton),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        imageButton.check(matches(isDisplayed()));

        ViewInteraction imageButton2 = onView(
                allOf(withId(R.id.accountButton),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        imageButton2.check(matches(isDisplayed()));

        ViewInteraction imageButton3 = onView(
                allOf(withId(R.id.logoutButton),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        imageButton3.check(matches(isDisplayed()));
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
