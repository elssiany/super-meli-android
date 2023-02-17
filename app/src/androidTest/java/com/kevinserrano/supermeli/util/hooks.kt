import androidx.test.espresso.AmbiguousViewMatcherException
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withSubstring
import org.hamcrest.Matchers
import org.junit.Assert

fun textDisplayed(text: String?) {
    var isDisplayed = true
    onView(withSubstring(text))
        .withFailureHandler { error, _ ->
            isDisplayed = error is AmbiguousViewMatcherException
        }
        .check(matches(ViewMatchers.isDisplayed()))
    Assert.assertTrue(isDisplayed)
}

fun isViewDisplayed(id: Int) {
    onView(ViewMatchers.withId(id))
        .check(matches(ViewMatchers.isDisplayed()))
}

fun isTextNotDisplayed(text: String) {
    var isDisplayed = true
    onView(ViewMatchers.withText(text))
        .withFailureHandler { error, _ ->
            isDisplayed = error is AmbiguousViewMatcherException
        }
        .check(matches(ViewMatchers.isDisplayed()))
    Assert.assertFalse(isDisplayed)
}

fun clickWithId(id: Int) {
    onView(Matchers.allOf(ViewMatchers.isDisplayed(), ViewMatchers.withId(id)))
        .perform(ViewActions.click())
}

fun setTextOnField(
    id: Int,
    text: String
) {
    onView(ViewMatchers.withId(id))
        .perform(ViewActions.replaceText(text))
}