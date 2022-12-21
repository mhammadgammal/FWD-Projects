package com.udacity.project4.locationreminders.reminderslist

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.udacity.project4.FakeDataSource
import com.udacity.project4.R
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
//UI Testing
@MediumTest
class ReminderListFragmentTest {
    private lateinit var fakeDataSource: FakeDataSource
    private lateinit var reminderViewModel: RemindersListViewModel

    @Before
    fun setup() {
        fakeDataSource = FakeDataSource()
        reminderViewModel =
            RemindersListViewModel(ApplicationProvider.getApplicationContext(), fakeDataSource)
        stopKoin()

        val module = module {
            single {
                reminderViewModel
            }
        }

        startKoin { modules(listOf(module)) }
    }

    @Test
    fun displayReminderList() = runBlockingTest {
        val list = listOf(
            ReminderDTO(
                "title",
                "description",
                "location",
                30.1235, 31.3387
            ),
            ReminderDTO(
                "title",
                "description",
                "location",
                30.1236, 31.3388
            ),
            ReminderDTO(
                "title",
                "description",
                "location",
                30.1237, 31.3389
            ),
            ReminderDTO(
                "title",
                "description",
                "location",
                30.1237, 31.3390
            )
        )

        list.forEach { reminder ->
            fakeDataSource.saveReminder(reminder)
        }

        val reminders = (fakeDataSource.getReminders() as? Result.Success)?.data

        val firstReminder = reminders!![0]

        onView(
            Matchers.allOf(
                withText(firstReminder.location),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.reminderCardView),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
            .check(matches(withText(firstReminder.location)))
    }

    @Test
    fun navigateToAddReminder() = runBlockingTest {
        val scenario = launchFragmentInContainer<ReminderListFragment>(Bundle(), R.style.AppTheme)
        val navController = mock(NavController::class.java)

        scenario.onFragment { Navigation.setViewNavController(it.view!!, navController) }
        onView(withId(R.id.addReminderFAB)).perform(click())

        verify(navController).navigate(ReminderListFragmentDirections.toSaveReminder())

    }
    @Test
    fun errorSnackBackShown() = runBlockingTest {
        fakeDataSource.deleteAllReminders()
        onView(withText("No Reminders Found"))
            .check(matches(isDisplayed()))
    }
    private fun childAtPosition(parentMatcher: Matcher<View>, i: Int): Matcher<View> {
        return object: TypeSafeMatcher<View>(){
            override fun describeTo(description: Description) {
                description.appendText("Child at position $i in parent ")
                parentMatcher.describeTo(description)
            }

            override fun matchesSafely(item: View): Boolean {
                val parent = item.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && item == parent.getChildAt(i)
            }
        }
    }
}