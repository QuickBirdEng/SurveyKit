import java.io.File

/*
################################################################################################
GLOBALS
################################################################################################
*/
val hookStartIdentifier = "#QBS-HOOK-START"
val hookEndIdentifier = "#QBS-HOOK-END"
val existingGitHookFolder = File(".git${File.separator}hooks")
val existingGitHooks by lazy {
    existingGitHookFolder
        .listFiles()
        ?.filterNot { it.isSample() }
        ?: throw IllegalStateException("Missing ${existingGitHookFolder.absolutePath}")
}
val projectSpecificHookFolder = File("git-hooks")
val projectSpecificGitHooks = projectSpecificHookFolder.listFiles()
/*
################################################################################################
HELPER FUNCTIONS
################################################################################################
*/
fun copyGitHook(folder: File, gitHook: File) {
    val target = File(folder, gitHook.name)
    val wrappedHook = gitHook.readText().wrapInHookIdentifiers()
    target.writeText(wrappedHook)
    target.setExecutable(true)
}

fun getExistingGitHook(fileName: String) =
    existingGitHooks.firstOrNull { file -> file.name == fileName }

fun File.isSample() = name.endsWith(".sample")
fun File.insertHookIfNeeded(text: String) {
    val fileContent = readText()
    /*
    hook is up to date
     */
    if (fileContent.contains(text)) return
    val hook = text.wrapInHookIdentifiers()
    /*
    hook already exists, update it
     */
    if (fileContent.containsQbHook()) {
        val startIndex = fileContent.indexOf(hookStartIdentifier)
        val endIndex = fileContent.indexOf(hookEndIdentifier) + hookEndIdentifier.length
        val updatedContent = fileContent.replaceRange(startIndex, endIndex, hook)
        this.writeText(updatedContent)
    } else {
        /*
        hook isn't installed, append to the current file
         */
        this.appendText("\n\n$hook")
    }
}

fun String.containsQbHook() = contains(hookStartIdentifier) && contains(hookEndIdentifier)
fun String.wrapInHookIdentifiers() = "$hookStartIdentifier\n$this\n$hookEndIdentifier"
/*
################################################################################################
TASK
################################################################################################
*/
task("installGitHooks") {
    try {
        projectSpecificGitHooks.forEach { gitHook ->

            val existingHook = getExistingGitHook(gitHook.name)
            if (existingHook != null) {
                existingHook.insertHookIfNeeded(gitHook.readText())
            } else {
                copyGitHook(existingGitHookFolder, gitHook)
            }
        }
    } catch (e: Throwable) {
        print("Something went wrong while installing the git hooks: $e.message")
    }
}
