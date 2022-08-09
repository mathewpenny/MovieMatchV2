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
public class UpdateAccountTest {

    @Rule
    public ActivityScenarioRule<LandingPage> mActivityScenarioRule =
            new ActivityScenarioRule<>(LandingPage.class);

    @Test
    public void updateAccountTest() {
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
        appCompatEditText.perform(replaceText("metallica_rules@test.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.loginPassword),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.drawerlayout.widget.DrawerLayout")),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("Happydays1"), closeSoftKeyboard());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.loginButton),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        5),
                                0),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.accountButton),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                0),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction appCompatImageButton4 = onView(
                allOf(withId(R.id.updateButton),
                        childAtPosition(
                                allOf(withId(R.id.updateFrame),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatImageButton4.perform(click());

        ViewInteraction imageView = onView(
                allOf(withId(R.id.imageView),
                        withParent(withParent(withId(R.id.drawer_view))),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.textView), withText("Account Update"),
                        withParent(withParent(withId(R.id.drawer_view))),
                        isDisplayed()));
        textView.check(matches(withText("Account Update")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.textView), withText("Account Update"),
                        withParent(withParent(withId(R.id.drawer_view))),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

        ViewInteraction imageView2 = onView(
                allOf(withId(R.id.profileImage),
                        withParent(withParent(withId(R.id.drawer_view))),
                        isDisplayed()));
        imageView2.check(matches(isDisplayed()));

        ViewInteraction button = onView(
                allOf(withId(R.id.uploadImg), withText("UPLOAD IMAGE?"),
                        withParent(withParent(withId(R.id.drawer_view))),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction editText = onView(
                allOf(withId(R.id.updateName), withText("Name"),
                        withParent(withParent(withId(R.id.drawer_view))),
                        isDisplayed()));
        editText.check(matches(isDisplayed()));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.updatePhone), withText("Phone"),
                        withParent(withParent(withId(R.id.drawer_view))),
                        isDisplayed()));
        editText2.check(matches(isDisplayed()));

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.updatePassword), withText("Password"),
                        withParent(withParent(withId(R.id.drawer_view))),
                        isDisplayed()));
        editText3.check(matches(isDisplayed()));

        ViewInteraction imageButton = onView(
                allOf(withId(R.id.sendRequestButton),
                        withParent(allOf(withId(R.id.updateFrame),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        imageButton.check(matches(isDisplayed()));

        ViewInteraction imageButton2 = onView(
                allOf(withId(R.id.sendRequestButton),
                        withParent(allOf(withId(R.id.updateFrame),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        imageButton2.check(matches(isDisplayed()));
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
