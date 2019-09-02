package com.quickbirdstudios.triangle

import ProgressModule
import android.app.Application
import com.google.firebase.FirebaseApp
import com.quickbirdstudios.quickboot.Quick
import com.quickbirdstudios.quickboot.architecture.QuickApplication
import com.quickbirdstudios.triangle.challenges.ChallengesModule
import com.quickbirdstudios.triangle.chat.ChatModule
import com.quickbirdstudios.triangle.kodein.contextKodein
import com.quickbirdstudios.triangle.kodein.routerKodein
import com.quickbirdstudios.triangle.lexicon.LexiconModule
import com.quickbirdstudios.triangle.more.MoreModule
import com.quickbirdstudios.triangle.notifications.notificationKodein
import com.quickbirdstudios.triangle.questionnaire.QuestionnaireModule
import com.quickbirdstudios.triangle.repository.RepositoryModule
import com.quickbirdstudios.triangle.service.ServiceModule
import org.kodein.di.Kodein

class App : QuickApplication, Application() {

    override fun dependencies() = Kodein {
        import(contextKodein(this@App))
        import(routerKodein)
        importOnce(notificationKodein)

        importOnce(ServiceModule.bindings)
        importOnce(LexiconModule.bindings)
        importOnce(IntroModule.bindings)
        importOnce(ChallengesModule.bindings)
        importOnce(ProgressModule.bindings)
        importOnce(MoreModule.bindings)
        importOnce(ChatModule.bindings)
        importOnce(QuestionnaireModule.bindings)
    }

    override fun onCreate() {
        super.onCreate()
        Quick.boot(this)
        RepositoryModule.init(this)
    }
}
