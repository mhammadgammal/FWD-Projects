package com.udacity.project4.locationreminders.reminderslist

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.udacity.project4.locationreminders.MainCoroutineRule
import com.udacity.project4.locationreminders.data.FakeDataSource
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.reminderslist.RemindersListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
@Config(sdk = [Build.VERSION_CODES.P])
class RemindersListViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val list = listOf(
        ReminderDTO("title 1", "description 1", "location 1", 0.0, 0.0),
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
            "title 4",
            "description 4",
            "location 4",
            30.1238, 31.3390
        )
    )
    private val reminder1 = list[0]
    private val reminder2 = list[1]
    private val reminder3 = list[2]

    private lateinit var fakeDataSource: FakeDataSource
    private lateinit var remindersViewModel: RemindersListViewModel

    @After
    fun tearDown(){
        stopKoin()
    }

    @Test
    fun getReminderList(){
        val reminderList = mutableListOf(reminder1, reminder2, reminder3)
        fakeDataSource = FakeDataSource(reminderList)
        remindersViewModel = RemindersListViewModel(ApplicationProvider.getApplicationContext(),fakeDataSource)
        remindersViewModel.loadReminders()

        assertThat(remindersViewModel.remindersList.value, (not(emptyList())))
        assertThat(remindersViewModel.remindersList.value?.size, `is`(reminderList.size))

    }

    @Test
    fun checkLoading(){
        fakeDataSource = FakeDataSource(mutableListOf())
        remindersViewModel = RemindersListViewModel(ApplicationProvider.getApplicationContext(),fakeDataSource)
        mainCoroutineRule.pauseDispatcher()
        remindersViewModel.loadReminders()
        assertThat(remindersViewModel.showLoading.value, `is`(true))

    }

    @Test
    fun returnError(){
        fakeDataSource = FakeDataSource(null)
        remindersViewModel = RemindersListViewModel(ApplicationProvider.getApplicationContext(),fakeDataSource)
        remindersViewModel.loadReminders()
        assertThat(remindersViewModel.showSnackBar.value, `is`("No reminders found"))
    }
}