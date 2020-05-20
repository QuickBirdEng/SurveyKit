# SurveyKit: Create beautiful surveys on Android (inspired by ResearchKit Surveys on iOS)

Do you want to display a questionnaire to get the opinion of your users? A survey for a medical trial? A series of instructions in a manual-like style? <br/>
SurveyKit is an Android library that allows you to create exactly that.

Thematically it is built to provide a feeling of a professional research survey. The library aims to be visually clean, lean and easily configurable.
We aim to keep the functionality close to [iOS ResearchKit Surveys](http://researchkit.org/docs/docs/Survey/CreatingSurveys.html).

This is an early version and work in progress. Do not hesitate to give feedback, ideas or improvements via an issue.

# Examples
###### Flow
<p align="center">
<img src="assets/gifs/survey-kit-demo.gif?raw=true" width="350">
</p>

###### Screenshots

| | | | | | 
| :---: | :---: | :---: | :---: | :---: |
| <img src="assets/top/instruction_qbs.png?raw=true" width="200"> | <img src="assets/top/how_old_are_you_with_hint.png?raw=true" width="200"> | <img src="assets/top/known_allergies_with_2_selected.png?raw=true" width="200"> | <img src="assets/top/single_choice_selection_1_selection.png?raw=true" width="200"> | <img src="assets/top/color-picker.png?raw=true" width="200"> |

## 📚 Overview: Creating Research Surveys
-   [What SurveyKit does for you](#what-surveykit-does-for-you)
-   [What SurveyKit does not (yet) do for you](#what-surveykit-does-not-yet-do-for-you)
-   [Library Setup](#-library-setup)
    -   [Add the repository](#1-add-the-repository)
    -   [Add the dependency](#2-add-the-dependency)
-   [Usage](#-usage)
    -   [Add and find the survey in the xml](#add-and-find-the-survey-in-the-xml)
    -   [Create survey steps](#create-survey-steps)
    -   [Create a Task](#create-a-task)
    -   [Evaluate the results](#evaluate-the-results)
    -   [Configure](#style)
    -   [Start the survey](#start-the-survey)
-   [Custom steps](#-custom-steps)
-   [Comparison to ResearchKit on iOS](#vs-comparison-of-surveykit-on-android-to-researchkit-on-ios)
-   [Author](#-author)
-   [Contributing](#%EF%B8%8F-contributing)
-   [License](#-license)

## What SurveyKit does for you
-   Simplifies the creation of surveys
-   Provides rich animations and transitions out of the box (custom animations planned)
-   Build with a consistent, lean, simple style, to fit research purposes
-   Survey navigation can be linear or based on a decision tree (directed graph)
-   Gathers results and provides them in a convinient manner to the developer for further use
-   Gives you complete freedom on creating your own questions
-   Allows you to customize the style
-   Provides an API and structure that is very similar to [iOS ResearchKit Surveys](http://researchkit.org/docs/docs/Survey/CreatingSurveys.html)
-   Is used in production by Quickbird Studios

## What SurveyKit does not (yet) do for you
As stated before, this is an early version and a work in progress. We aim to extend this library until it matches the functionality of the [iOS ResearchKit Surveys](http://researchkit.org/docs/docs/Survey/CreatingSurveys.html).

# 🏃 Library Setup
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
    implementation(project("com.quickbirdstudios:surveykit:1.1.0"))
}
````
Find the [latest version](https://bintray.com/quickbirdstudios/android/SurveyKit) or [all releases](https://github.com/quickbirdstudios/SurveyKit/releases)


# 💻 Usage
## Example
A working example project can be found [HERE](example/)
### Add and Find the Survey in the XML
Add the SurveyView to your `xml` (it looks best if it fills the screen).
````xml
<com.quickbirdstudios.survey_kit.survey.SurveyView
    android:id="@+id/survey_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
````
Find the view in the `xml` and save it for further use.
```kotlin
var surveyView: SurveyView = view.findViewById(R.id.survey_view)
```

### Create survey steps
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
Next you need a task. Each survey has **exactly one** task. A `Task` is used to define how the user should navigate through your `steps`. <br><br>

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
<br/>
With the `DirectStepNavigationRule` you say that after this step, another specified step should follow.
```kotlin
task.setNavigationRule(
    steps[4].id,
    NavigationRule.DirectStepNavigationRule(
        destinationStepStepIdentifier = steps[6].id
    )
)
```
<br><br/>
With the `MultipleDirectionStepNavigationRule` you can specify the next step, depending on the answer of the step.
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

### Evaluate the results
When the survey is finished, you get a callback. No matter of the `FinishReason`, you always get all results gathered until now. <br/>
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

### Style
These is how you add custom styling to your survey. We'll add even more options in the future.
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

# Cancel Survey dialog

When you cancel the survey, there is an option to change dialog default Strings. Must be imported from resources.

```
val configuration = SurveyTheme(
            themeColorDark = ContextCompat.getColor(this, R.color.cyan_dark),
            themeColor = ContextCompat.getColor(this, R.color.cyan_normal),
            textColor = ContextCompat.getColor(this, R.color.cyan_text),
            abortDialogConfiguration = AbortDialogConfiguration(
                title = R.string.title,
                message = R.string.message,
                neutralMessage = R.string.no,
                negativeMessage = R.string.yes
            )
        )
```


# 📇 Custom steps
At some point, you might wanna define your own custom question steps. 
That could, for example, be a question which prompts the user to pick color values or even sound samples. 
These are not implemented yet but you can easily create them yourself: 

You'll need a `CustomResult` and a `CustomStep`. The Result class tells SurveyKit which data you want to save. 
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
Next you'll need a CustomStep class:
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

# 🍏vs🤖 : Comparison of SurveyKit on Android to ResearchKit on iOS
This is an overview of which features [iOS ResearchKit Surveys](http://researchkit.org/docs/docs/Survey/CreatingSurveys.html) provides and which ones are already supported by SurveyKit on Android.
The goal is to make both libraries match in terms of their functionality.

| Steps	                    | iOS ResearchKit        | Android SurveyKit|
| :------------------------ | :---:                  | :---:	       |
| Instruction               | ✅                     | ✅             |
| Single selection          | ✅                     | ✅             |
| Multi selection           | ✅                     | ✅             |
| Boolean answer            | ✅                     | ✅             |
| Value picker              | ✅                     | ✅             |
| Image choice              | ✅                     | ✅             |
| Numeric answer            | ✅                     | ✅             |
| Time of day               | ✅                     | ✅             |
| Date selection            | ✅                     | ✅             |
| Text answer (unlimited)   | ✅                     | ✅             |
| Text answer (limited)     | ✅                     | ✅             |
| Text answer (validated)   | ✅                     | ✅             |
| Scale answer              | ✅                     | ✅             |
| Email answer              | ✅                     | ✅             |
| Location answer           | ✅                     | x              |

# 👤 Author
This Android library is created with ❤️ by [QuickBird Studios](https://quickbirdstudios.com/).

# ❤️ Contributing
Open an issue if you need help, if you found a bug, or if you want to discuss a feature request.

Open a PR if you want to make changes to SurveyKit.

For the moment, a mandatory requirement for a PR to be accepted is also applying [ktlint](https://ktlint.github.io/) when submitting this PR.

# 📃 License
SurveyKit is released under an MIT license. See [License](LICENSE) for more information.
