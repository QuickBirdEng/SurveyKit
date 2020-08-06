package com.quickbirdstudios.test

import android.os.Parcel
import com.quickbirdstudios.surveykit.Identifier
import com.quickbirdstudios.surveykit.OrderedTask
import com.quickbirdstudios.surveykit.StepIdentifier
import com.quickbirdstudios.surveykit.backend.result_gatherer.ResultGathererImpl
import com.quickbirdstudios.surveykit.result.QuestionResult
import com.quickbirdstudios.surveykit.result.StepResult
import java.util.Date
import org.junit.Assert
import org.junit.Test

internal class ResultGathererTest {

    @Test
    fun testStoring() {
        val task = OrderedTask(listOf())
        val resultGatherer = ResultGathererImpl(task)

        val stepResult = stepResult()
        resultGatherer.store(stepResult)
        Assert.assertEquals(stepResult, resultGatherer.retrieve(stepResult.id))
    }

    @Test
    fun testOverwriting() {
        val task = OrderedTask(listOf())
        val resultGatherer = ResultGathererImpl(task)

        val stepResult = stepResult(questionResult(data = 1))
        resultGatherer.store(stepResult)
        Assert.assertEquals(stepResult, resultGatherer.retrieve(stepResult.id))
        Assert.assertEquals(
            1,
            (resultGatherer.retrieve(stepResult.id)?.results?.first() as DataInterface).data
        )
        Assert.assertEquals(1, resultGatherer.results.size)

        val differentStepResult = stepResult(questionResult(data = 2))
        resultGatherer.store(differentStepResult)
        Assert.assertEquals(differentStepResult, resultGatherer.retrieve(stepResult.id))
        Assert.assertEquals(1, resultGatherer.results.size)
        Assert.assertEquals(
            2,
            (resultGatherer.retrieve(stepResult.id)?.results?.first() as DataInterface).data
        )
    }

    //region Private Helper

    internal interface DataInterface {
        val data: Int
    }

    private val stepId = StepIdentifier()

    private fun questionResult(data: Int = 1) = object : QuestionResult, DataInterface {
        override val stringIdentifier: String = ""
        override val id: Identifier = stepId
        override val startDate: Date = Date()
        override var endDate: Date = Date()

        override fun writeToParcel(dest: Parcel?, flags: Int) {}

        override fun describeContents(): Int = 42

        override val data = data
    }

    private fun stepResult(questionResult: QuestionResult = questionResult()) = StepResult(
        id = questionResult.id as StepIdentifier,
        startDate = Date(),
        endDate = Date(),
        results = mutableListOf(questionResult)
    )

    //endregion
}
