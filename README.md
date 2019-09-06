# SurveyKit

With this library you can create a series of questions, in the style of a questionnaire.
Thematically it is made to give you a feeling of a research survey. Visually we aim to be clean, lean and easily configurable.
We aim to keep the functionality close to [iOS ResearchKit Surveys](http://researchkit.org/docs/docs/Survey/CreatingSurveys.html).

This is an early version and work in progress. Do not hesitate to give feedback, ideas or improvements via an issue.

<img src="assets/instruction_start_cyan.png?raw=true" width="350">
<img src="assets/integer_question_disabled.png?raw=true" width="350">
<img src="assets/multiple_choice_question_multiple_selected.png?raw=true" width="350">

## ∑ Overview
-   [What Survey-Kit does for you](#what-survey-kit-does-for-you)
-   [What Survey-Kit does not (yet) for you](#what-survey-kit-does-not-yet-for-you)
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
-   Simplifies the creation of survey
-   Rich animations and transitions out of the box (custom animations planned)
-   Consistent, lean, simple style, to fit research purposes
-   Navigation can be linear or in the structure of a directed graph (everything is possible)
-   Result gathering and handing back to the developer for further use
-   Complete freedom to creating your own questions
-   Customizable style
-   Structure kept close to the one of iOS ResearchKit
-   Used in production by QBS

## What Survey-Kit does not (yet) for you
As said before, this is an early version and work in progress. We aim to for the level iOS ResearchKit is on and then are going to add new features and improvements.
We are also going to add these question types ([Goal](http://researchkit.org/docs/docs/Survey/CreatingSurveys.html)).

# 🏃 ‍Setup
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


# 🧩 Usage
## Example
A working example can be found [HERE](example/)
### Add and Find the Survey in the XML
Add the SurveyView into your `xml` (it looks best if it fills the screen).
````xml
<com.quickbirdstudios.survey_kit.public_api.survey.SurveyView
    android:id="@+id/survey_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
````
Find the view in the `xml` and save it for further use.
```kotlin
var surveyView: SurveyView = view.findViewById(R.id.survey_view)
```

### Create steps
To create a step, create an instance of one of these 3 classes:
#### `InstructionStep`
```kotlin
InstructionStep(
    title = R.string.intro_title,
    text = R.string.intro_text,
    buttonText = R.string.intro_start
)
```
The `title` is the general title of the Survey you want to conduct. <br/>
The `text` is, in this case, the introduction text which should give an introduction, about what the survey is about.<br/>
The `buttonText` specifies the text of the button, which will start the survey.
All of these properties have to be resource Ids.

#### `CompletionStep`
```kotlin
CompletionStep(
    title = R.string.finish_question_title,
    text = R.string.finish_question_text,
    buttonText = R.string.finish_question_submit
)
```
The `title` is the general title of the Survey you want to conduct, same as for the `InstructionStep`. <br/>
The `text` is here should be something motivational: that the survey has been completed successfully. <br/>
The `buttonText` specifies the text of the button, which will end the survey.
All of these properties have to be resource Ids.

#### `QuestionStep`
```kotlin
QuestionStep(
    title = R.string.about_you_question_title,
    text = R.string.about_you_question_text,
    answerFormat = AnswerFormat.TextAnswerFormat(
        multipleLines = true,
        maximumLength = 100
    )
)
```
The `title` same as for the `InstructionStep` and `CompletionStep`. <br/>
The `text` the actual question you want to ask. Depending on the answer type of this, you should set the next property.<br/>
The `answerFormat` specifies the type of question (the type of answer to the question) you want to ask. Currently there these types supported:
-   `TextAnswerFormat`
-   `IntegerAnswerFormat`
-   `ScaleAnswerFormat`
-   `SingleChoiceAnswerFormat`
-   `MultipleChoiceAnswerFormat`

All that's left is to collect your steps in a list.
```kotlin
val steps = listOf(step1, step2, step3, ...)
```

### Create a Task
Next you need a task. Each survey has exactly one task. Task is used to set the `steps` and how the navigation through them works.<br><br>

#### OrderedTask
```kotlin
val task = OrderedTask(steps = steps)
```
The `OrderedTask` just presents the questions in order, as they are given.

#### NavigableOrderedTask
````kotlin
val task = NavigableOrderedTask(steps = steps)
````
The `NavigableOrderedTask` allows you to specify navigation rules.<br>
There are two types of navigation rules:
```kotlin
task.setNavigationRule(
    steps[4].id,
    NavigationRule.DirectStepNavigationRule(
        destinationStepStepIdentifier = steps[6].id
    )
)
```
With the `DirectStepNavigationRule` you say that after this step, another specified step should follow.<br><br

```kotlin
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
With the `MultipleDirectionStepNavigationRule` you can specify the next step, depending on the answer of the step. With this you can navigate to any step.

### Evaluate the results
Set this callback, for when the survey is done. No matter which `FinishReason` you always get the results gathered up until now. <br/>
The `TaskResult` contains a list of `StepResult`s. The `StepResult` contains a list of `QuestionResult`s.
```kotlin
surveyView.onSurveyFinish = { taskResult: TaskResult, reason: FinishReason ->
    if (reason == FinishReason.Completed) {
        taskResult.results.forEach { stepResult ->
            Log.e("logTag", "answer ${stepResult.results.firstOrNull()}")
        }
    }
}
```

### Configure
These is how you configure the survey. We plan to expand the configuration
```kotlin
val configuration = SurveyTheme(
    themeColorDark = ContextCompat.getColor(requireContext(), R.color.cyan_dark),
    themeColor = ContextCompat.getColor(requireContext(), R.color.cyan_normal),
    textColor = ContextCompat.getColor(requireContext(), R.color.cyan_text)
)
```

### Start the survey
All that's left is to start the survey and enjoy.🎉🎊
```kotlin
surveyView.start(task, configuration)
```


# 📇 Custom steps
You need a `CustomResult` and a `CustomStep`. The `CustomStep` can then just be added as another step to the list of steps. `NavigationRule`s work the same as with other steps.
Here you see a really simple examples of how you can do it.
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

# 🍏vs🤖 iOS ResearchKit comparison
First we aim to implement the functionality of iOS ResearchKit. Then we want to add our own features.

| Steps	                    | iOS ResearchKit        | SurveyKit      |
| :------------------------ | :---:                  | :---:	      |
| Instruction               | ✅                     | ✅             |
| Single selection          | ✅                     | ✅             |
| Multi selection           | ✅                     | ✅             |
| Boolean answer            | ✅                     | 𐄂              |
| Value picker              | ✅                     | 𐄂              |
| Image choice              | ✅                     | 𐄂              |
| Numeric answer            | ✅                     | ✅             |
| Time of day               | ✅                     | 𐄂              |
| Date selection            | ✅                     | 𐄂              |
| Text answer (unlimited)   | ✅                     | ✅             |
| Text answer (limited)     | ✅                     | ✅             |
| Text answer (validated)   | ✅                     | 𐄂              |
| Scale answer              | ✅                     | ✅             |
| Email answer              | ✅                     | 𐄂              |
| Location answer           | ✅                     | 𐄂              |

# 👤 Author
This framework is created with ❤️ by [QuickBird Studios](https://quickbirdstudios.com/).

# ❤️ Contributing
Open an issue if you need help, if you found a bug, or if you want to discuss a feature request.

Open a PR if you want to make changes to SurveyKit.

# 📃 License
SurveyKit is released under an MIT license. See [License](LICENSE) for more information.
