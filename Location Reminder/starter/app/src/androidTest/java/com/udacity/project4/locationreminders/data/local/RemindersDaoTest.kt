
package com.udacity.project4.locationreminders.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import junit.framework.Assert.assertNull

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import kotlinx.coroutines.ExperimentalCoroutinesApi;
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Test
import java.util.*

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
//Unit test the DAO
@SmallTest
class RemindersDaoTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var reminderDatabase: RemindersDatabase
    @Before
    fun initDb(){
        reminderDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RemindersDatabase::class.java
        )
            .build()
    }

    @Test
    fun getReminders() = runBlockingTest {
        //Given - insert a reminder
        val reminder = ReminderDTO("title", "description", "location", 30.1236, 31.3388)
        reminderDatabase.reminderDao().saveReminder(reminder)
        //When - Get reminders from database
        val reminders = reminderDatabase.reminderDao().getReminders()
        //Then - There is a reminder which inserted
        assertThat(reminders.size, `is`(1))
        assertThat(reminders[0].id, `is`(reminder.id))
        assertThat(reminders[0].title, `is`(reminder.title))
        assertThat(reminders[0].description, `is`(reminder.description))
        assertThat(reminders[0].location, `is`(reminder.location))
        assertThat(reminders[0].latitude, `is`(reminder.latitude))
        assertThat(reminders[0].longitude, `is`(reminder.longitude))
    }

    @Test
    fun insertReminder_GetById() = runBlockingTest {
        //Given - insert a reminder
        val reminder = ReminderDTO("title", "description", "location", 30.1236, 31.3388)
        reminderDatabase.reminderDao().saveReminder(reminder)

        // WHEN - Get the reminder by id from the database.
        val reminderGotById = reminderDatabase.reminderDao().getReminderById(reminder.id)

        // THEN - The loaded data contains the expected values.
        assertThat<ReminderDTO>(reminderGotById as ReminderDTO, notNullValue())
        assertThat(reminderGotById.id, `is`(reminder.id))
        assertThat(reminderGotById.title, `is`(reminder.title))
        assertThat(reminderGotById.description, `is`(reminder.description))
        assertThat(reminderGotById.location, `is`(reminder.location))
        assertThat(reminderGotById.latitude, `is`(reminder.latitude))
        assertThat(reminderGotById.longitude, `is`(reminder.longitude))
    }

    @Test
    fun getReminderByIdNotFound() = runBlockingTest {
        // GIVEN - a random reminder id
        val reminderId = UUID.randomUUID().toString()
        // WHEN - Get the reminder by id from the database.
        val loaded = reminderDatabase.reminderDao().getReminderById(reminderId)
        // THEN - The loaded data should be  null.
        assertNull(loaded)
    }

    @Test
    fun deleteReminders() = runBlockingTest {
       //Given - reminders inserted
        val remindersList = listOf<ReminderDTO>(
            ReminderDTO("title 1", "description 1","location 1", 30.1236, 31.3388),
            ReminderDTO("title 2", "description 2","location 2", 30.1237, 31.3389),
            ReminderDTO("title 3", "description 3","location 3", 30.1238, 31.3390),
            ReminderDTO("title 4", "description 4","location 4", 30.1239, 31.33891)
        )
        remindersList.forEach{reminder ->
            reminderDatabase.reminderDao().saveReminder(reminder)
        }
        // when - reminders is deleted
        reminderDatabase.reminderDao().deleteAllReminders()
        // then - the database is empty
        val reminders = reminderDatabase.reminderDao().getReminders()
        assertThat(reminders.isEmpty(), `is`(true))
    }
    @After
    fun closeDb() = reminderDatabase.close()
}

