# Survey-kit

This library aims to provide the functionality of the [IOs ORK Survey](http://researchkit.org/docs/docs/Survey/CreatingSurveys.html).


This is an early version and work in progress. Do not hesitate to give feedback, ideas or improvements via an issue.

## Overview
-   [What Survey-Kit does for you](#what-survey-kit-does-for-you)
-   [What Survey-Kit does not (yet) for you](#what-survey-kit-does-not-(yet)-for-you)
-   [Setup](#setup)
    -   [Add the repository](#add-the-repository)
    -   [Add the dependency](#add-the-dependency)
-   [Usage](#usage)
    -   [Add and find the survey in the xml](#add-and-find-the-survey-in-the-xml)
    -   [Create steps](#create-steps)
    -   [Create a Task](#create-a-task)
    -   [Evaluate the results](#evaluate-the-results)
    -   [Configure](#configure)
    -   [Start the survey](#start-the-survey)
-   [Custom steps](#custom-steps)

## What Survey-Kit does for you
Survey-kit aims to allow the developer to:
-    create a series of questions (ordered or a custom navigation)
-    display them in a nice consistent style
-    gather the results and give them back to the developer

## What Survey-Kit does not (yet)
As said before, this is an early version and work in progress. Many things might change: we try
to keep the public API as it is now.
We are going to add other question types ([Goal](http://researchkit.org/docs/docs/Survey/CreatingSurveys.html)).

# Setup
## 1. Add the repository
`build.gradle`
```groovy
allprojects {
    repositories {
        jcenter()
    }
}
```

## 2. Add the dependency
`build.gradle.kts`
````kotlin
dependencies {
    implementation(project(":XXXXX"))
}
````
TODO: replace XXXXX with: group:name:version


# Usage
## Example
A working example can be found [TODO](https://google.com)
### Add and Find the Survey in the xml
````xml
<com.quickbirdstudios.survey_kit.public_api.survey.SurveyView
    android:id="@+id/survey_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
````
```kotlin
var surveyView: SurveyView = view.findViewById(R.id.survey_view)
```

### Create steps
````kotlin
val steps = listOf(
    InstructionStep(
        title = R.string.intro_title,
        text = R.string.intro_text,
        buttonText = R.string.intro_start
    ),
    QuestionStep(
        title = R.string.about_you_question_title,
        text = R.string.about_you_question_text,
        answerFormat = AnswerFormat.TextAnswerFormat(
            multipleLines = true,
            maximumLength = 100
        )
    ),
    QuestionStep(
        title = R.string.how_old_title,
        text = R.string.how_old_text,
        answerFormat = AnswerFormat.IntegerAnswerFormat(defaultValue = 25)
    ),
    QuestionStep(
        title = R.string.how_fat_question_title,
        text = R.string.how_fat_question_text,
        answerFormat = AnswerFormat.ScaleAnswerFormat(
            minimumValue = 1,
            maximumValue = 5,
            minimumValueDescription = R.string.how_fat_min,
            maximumValueDescription = R.string.how_fat_max,
            step = 1f,
            defaultValue = 3
        )
    ),
    QuestionStep(
        title = R.string.physical_disabilities_question_title,
        text = R.string.physical_disabilities_question_text,
        answerFormat = AnswerFormat.MultipleChoiceAnswerFormat(
            textChoices = listOf(
                TextChoice(R.string.physical_disabilities_back_pain),
                TextChoice(R.string.physical_disabilities_heart_problems),
                TextChoice(R.string.physical_disabilities_joint_pain),
                TextChoice(R.string.physical_disabilities_joint_asthma)
            )
        )
    ),
    QuestionStep(
        title = R.string.quit_or_continue_question_title,
        text = R.string.quit_or_continue_question_text,
        answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
            textChoices = listOf(
                TextChoice(R.string.yes),
                TextChoice(R.string.no)
            )
        )
    ),
    CompletionStep(
        title = R.string.finish_question_title,
        text = R.string.finish_question_text,
        buttonText = R.string.finish_question_submit
    )
)
````
At the moment, 3 types of steps are available. The `InstructionStep` and the `CompletionStep` are
for the beginning/instructions and the end of the survey. With the `QuestionStep` a single page
can be configured. Which type of question is asked, is defined by the `AnswerFormat`.

The `AnswerFormat` contains all the possible answerformats available for now.


### Create a Task
```kotlin
val task = OrderedTask(steps = steps)
```
The `OrderedTask` just presents the questions in order, as they are given.

````kotlin
val task = NavigableOrderedTask(steps = steps)
````
The `NavigableOrderedTask` allows you to specify navigation rules:
```kotlin
task.setNavigationRule(
    steps[4].id,
    NavigationRule.DirectStepNavigationRule(
        destinationStepStepIdentifier = steps[6].id
    )
)

task.setNavigationRule(
    steps[6].id,
    NavigationRule.MultipleDirectionStepNavigationRule(
        resultToStepIdentifierMapper = { input ->
            when (input) {
                "Yes" -> steps[7].id
                "No" -> steps[0].id
                else -> null
            }
        }
    )
)
```

### Evaluate the results
```kotlin
surveyView.onSurveyFinish = { taskResult: TaskResult, reason: FinishReason ->
    if (reason == FinishReason.Completed) {
        taskResult.results.forEach { stepResult ->
            Log.e("logTag", "answer ${stepResult.results.firstOrNull()}")
        }
    }
    withContext(Dispatchers.Main) {
        requireActivity().onBackPressed()
    }
}
```

### Configure
```kotlin
val configuration = SurveyTheme(
    themeColorDark = ContextCompat.getColor(requireContext(), R.color.cyan_dark),
    themeColor = ContextCompat.getColor(requireContext(), R.color.cyan_normal),
    textColor = ContextCompat.getColor(requireContext(), R.color.cyan_text)
)
```

### Start the survey
Start the survey.
```kotlin
surveyView.start(task, configuration)
```


# Custom steps
You need a `CustomResult` and a `CustomStep`.
```kotlin
@Parcelize
data class CustomResult(
    val customData: String,
    override val stringIdentifier: String,
    override val id: Identifier,
    override val startDate: Date,
    override var endDate: Date
) : QuestionResult, Parcelable
```

```kotlin
class CustomStep : Step {
    override val isOptional: Boolean = true
    override val id: StepIdentifier = StepIdentifier()
    val tmp = id

    override fun createView(context: Context, stepResult: StepResult?): StepView {
        return object : StepView(context, id, isOptional) {

            override fun setupViews() = Unit

            val root = View.inflate(context, R.layout.example, this)

            override fun createResults(): QuestionResult =
                CustomResult(
                    root.findViewById<EditText>(R.id.input).text.toString(),
                    "stringIdentifier",
                    id,
                    Date(),
                    Date()
                )

            override fun isValidInput(): Boolean = this@CustomStep.isOptional

            override var isOptional: Boolean = this@CustomStep.isOptional
            override val id: StepIdentifier = tmp

            override fun style(surveyTheme: SurveyTheme) {
                // do styling here
            }

            init {
                root.findViewById<Button>(R.id.continue)
                    .setOnClickListener { onNextListener(createResults()) }
                root.findViewById<Button>(R.id.back)
                    .setOnClickListener { onBackListener(createResults()) }
                root.findViewById<Button>(R.id.close)
                    .setOnClickListener { onCloseListener(createResults(), FinishReason.Completed) }
                root.findViewById<Button>(R.id.skip)
                    .setOnClickListener { onSkipListener() }
                root.findViewById<EditText>(R.id.input).setText(
                    (stepResult?.results?.firstOrNull() as? CustomResult)?.customData ?: ""
                )
            }
        }
    }
}
```
Then just add your `CustomStep` to the list of steps you pass to the `Task`.
