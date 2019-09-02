package com.quickbirdstudios.`test-app`.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.quickbirdstudios.survey_kit.public_api.survey.SurveyView
import com.quickbirdstudios.triangle.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext


class
MainActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Job() + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty)

        val survey = findViewById<SurveyView>(R.id.survey_view)
    }

}
