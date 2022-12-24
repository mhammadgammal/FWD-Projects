package com.udacity.project4.locationreminders.reminderslist

import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.udacity.project4.FakeDataSource
import com.udacity.project4.R
import com.udacity.project4.locationreminders.data.ReminderDataSource
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsNot.not
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
//UI Testing
@MediumTest
class ReminderListFragmentTest :
    AutoCloseKoinTest() {
    private val TAG = "ReminderListFragmentTes"
    private val dataSource: ReminderDataSource by inject()
    private lateinit var fakeDataSource: FakeDataSource
    private lateinit var reminderViewModel: RemindersListViewModel

    @Before
    fun setup() {
        fakeDataSource = FakeDataSource()
        reminderViewModel = RemindersListViewModel(
            getApplicationContext(), fakeDataSource
        )
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
                "title 1",
                "description 1",
                "location 1",
                30.1235, 31.3387
            ),
            ReminderDTO(
                "title 2",
                "description 2",
                "location 2",
                30.1236, 31.3388
            ),
            ReminderDTO(
                "title 3",
                "description 3",
                "location 3",
                30.1237, 31.3389
            ),
            ReminderDTO(
                "title4",
                "description 4",
                "location 4",
                30.1237, 31.3390
            )
        )

        list.forEach { reminder ->
            fakeDataSource.saveReminder(reminder)
        }

        val reminders = (fakeDataSource.getReminders() as? Result.Success)?.data

        val firstReminder = reminders!![0]

        val scenario = launchFragmentInContainer<ReminderListFragment>(Bundle(), R.style.AppTheme)
        val navController = mock(NavController::class.java)
        scenario.onFragment { Navigation.setViewNavController(it.view!!, navController) }
        onView(withText(firstReminder.title)).check(matches(isDisplayed()))
        onView(withText(firstReminder.description)).check(matches(isDisplayed()))
        onView(withText(firstReminder.location)).check(matches(isDisplayed()))
        onView(withId(R.id.noDataTextView)).check(matches(not(isDisplayed())))
    }



    @Test
    fun navigateToAddReminder() = runBlocking {
        val scenario = launchFragmentInContainer<ReminderListFragment>(Bundle(), R.style.AppTheme)
        val navController = mock(NavController::class.java)

        scenario.onFragment { Navigation.setViewNavController(it.view!!, navController) }
        onView(withId(R.id.addReminderFAB)).perform(click())

        verify(navController).navigate(ReminderListFragmentDirections.toSaveReminder())

    }

    @Test
    fun errorSnackBackShown() = runBlockingTest {
        launchFragmentInContainer<ReminderListFragment>(Bundle(), R.style.AppTheme)
        this@ReminderListFragmentTest.dataSource.deleteAllReminders()
        onView(withText("No Data"))
            .check(matches(isDisplayed()))
    }
}