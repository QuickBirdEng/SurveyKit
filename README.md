# Triangle Android


# Setup

## 1.Add the repository
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
    implementation(project(":survey"))
}
````

## Parcelize necessary? (check survey)


# Usage

## Example

### Find the Survey in the xml
```kotlin
var surveyView: SurveyView = view.findViewById(R.id.survey_view)
```

### Creating steps
````kotlin
val steps = listOf(
    InstructionStep(
        title = R.string.intro_title,
        text = R.string.intro_text,
        startButtonText = R.string.intro_start
    ),
    QuestionStep(
        title = R.string.about_you_question_title,
        text = R.string.about_you_question_text,
        answerFormat = TextAnswerFormat(
            multipleLines = true,
            maximumLength = 100
        )
    ),
    QuestionStep(
        title = R.string.how_old_title,
        text = R.string.how_old_text,
        answerFormat = IntegerAnswerFormat(defaultValue = 25)
    ),
    QuestionStep(
        title = R.string.how_fat_question_title,
        text = R.string.how_fat_question_text,
        answerFormat = ScaleAnswerFormat(
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
        answerFormat = MultipleChoiceAnswerFormat(
            textChoices = listOf(
                MultipleChoiceAnswerFormat.TextChoice(R.string.physical_disabilities_back_pain),
                MultipleChoiceAnswerFormat.TextChoice(R.string.physical_disabilities_heart_problems),
                MultipleChoiceAnswerFormat.TextChoice(R.string.physical_disabilities_joint_pain),
                MultipleChoiceAnswerFormat.TextChoice(R.string.physical_disabilities_joint_asthma)
            )
        )
    ),
    QuestionStep(
        title = R.string.quit_or_continue_question_title,
        text = R.string.quit_or_continue_question_text,
        answerFormat = SingleChoiceAnswerFormat(
            textChoices = listOf(
                SingleChoiceAnswerFormat.TextChoice(R.string.yes),
                SingleChoiceAnswerFormat.TextChoice(R.string.no)
            )
        )
    ),
    FinishStep(
        title = R.string.finish_question_title,
        text = R.string.finish_question_text,
        finishButtonText = R.string.finish_question_submit
    )
)
````
The settings for each 

### Create a Task
```kotlin
val task = OrderedTask(steps = steps)
```

````kotlin
val task = NavigableOrderedTask(steps = steps)
````
This one allows you to specify navigation rules:
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

### Do something with the results
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
```kotlin
surveyView.start(task, configuration)
```
