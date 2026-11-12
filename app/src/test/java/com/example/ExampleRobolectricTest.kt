package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.repository.JobRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("HIB Jobs", appName)
  }

  @Test
  fun `job repository loads initial remote positions and toggles bookmark`() = runBlocking {
    val repository = JobRepository()
    val jobs = repository.jobs.first()
    assertTrue(jobs.isNotEmpty())

    val firstJob = jobs.first()
    repository.toggleBookmark(firstJob.id)
    val updatedJobs = repository.jobs.first()
    assertTrue(updatedJobs.first { it.id == firstJob.id }.isBookmarked)
  }

  @Test
  fun `submit job application stores application record`() = runBlocking {
    val repository = JobRepository()
    val job = repository.jobs.first().first()
    val application = repository.submitApplication(
      job = job,
      name = "Sarah Connor",
      email = "sarah@cyberdyne.io",
      portfolio = "https://github.com/sarah",
      resumeName = "Sarah_Resume.pdf",
      coverNote = "Looking forward to remote opportunities."
    )
    assertNotNull(application.id)
    val apps = repository.applications.first()
    assertTrue(apps.any { it.applicantEmail == "sarah@cyberdyne.io" })
  }
}
