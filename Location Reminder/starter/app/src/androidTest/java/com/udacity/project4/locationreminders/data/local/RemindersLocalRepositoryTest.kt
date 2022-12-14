package com.udacity.project4.locationreminders.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.udacity.project4.FakeReminderDao
import com.udacity.project4.MainCoroutineRule
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
//Medium Test to test the repository
@MediumTest
class RemindersLocalRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    val reminderList = listOf(
        ReminderDTO("title 1", "description 1", "location 1", 30.1236, 31.3388),
        ReminderDTO("title 2", "description 2", "location 2", 30.1237, 31.3389),
        ReminderDTO("title 3", "description 3", "location 3", 30.1238, 31.3390),
        ReminderDTO("title 4", "description 4", "location 4", 30.1239, 31.3391)
    )
    private val reminder1 = reminderList[0]
    private val reminder2 = reminderList[1]
    private val reminder3 = reminderList[2]

    private val newReminder = reminderList[3]

    private lateinit var fakeRemindersDao: FakeReminderDao
    private lateinit var remindersRepo: RemindersLocalRepository

    @Before
    fun setupRepo() {
        fakeRemindersDao = FakeReminderDao()
        remindersRepo = RemindersLocalRepository(
            fakeRemindersDao, Dispatchers.Unconfined
        )
    }

    @Test
    fun saveRemindersToDb() = runBlockingTest {
        var list = mutableListOf<ReminderDTO>()
        list.addAll(fakeRemindersDao.remindersList.values)
        assertThat(list).doesNotContain(newReminder)
        assertThat((remindersRepo.getReminders() as? Result.Success)?.data).doesNotContain(newReminder)

        remindersRepo.saveReminder(newReminder)

        list = mutableListOf()
        list.addAll(fakeRemindersDao.remindersList.values)
        assertThat(list).contains(newReminder)

        val result = remindersRepo.getReminders() as? Result.Success
        assertThat(result?.data).contains(newReminder)

    }

    @Test
    fun getReminderByIdThatDoesNotExist() = runBlockingTest {
        val message = (remindersRepo.getReminder(reminder1.id) as? Result.Error)?.message
        Assert.assertThat<String>(message, CoreMatchers.notNullValue())
        assertThat(message).isEqualTo("Reminder not found!")

    }

    @Test
    fun deleteAllReminders_EmptyListFetchedFromDb() = runBlockingTest {
        //Given - Repo with reminders
        assertThat((remindersRepo.getReminders() as? Result.Success)?.data).isEmpty()

        fakeRemindersDao.remindersList[reminder1.id] = reminder1
        fakeRemindersDao.remindersList[reminder2.id] = reminder2
        fakeRemindersDao.remindersList[reminder3.id] = reminder3

        assertThat((remindersRepo.getReminders() as? Result.Success)?.data).isNotEmpty()
        //When - deleting all reminders
        remindersRepo.deleteAllReminders()
        //then - the db should return empty list
        assertThat((remindersRepo.getReminders() as? Result.Success)?.data).isEmpty()

    }
}