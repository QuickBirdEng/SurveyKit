package com.quickbirdstudios.surveykit.backend.presenter.animations

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.quickbirdstudios.surveykit.R
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class ViewAnimator(val context: Context) {

    //region Member

    private val enterFromRightAnimation = AnimationUtils.loadAnimation(
        context, R.anim.enter_from_right
    )!!
    private val enterFromLeftAnimation = AnimationUtils.loadAnimation(
        context, R.anim.enter_from_left
    )!!
    private val exitToRightAnimation = AnimationUtils.loadAnimation(
        context, R.anim.exit_to_right
    )!!
    private val exitToLeftAnimation = AnimationUtils.loadAnimation(
        context, R.anim.exit_to_left
    )!!

    //endregion

    //region SubTypes

    internal data class PageSwipe(val swipeOutView: View?, val swipeInView: View)

    //endregion

    //region Public API

    fun rightToLeft(container: ViewGroup, pageSwipe: PageSwipe) {
        pageSwipe.swipeOutView?.startAnimation(
            leaveToLeftAnimationAnd {
                container.removeView(pageSwipe.swipeOutView)
            }
        )
        pageSwipe.swipeInView.startAnimation(enterFromRightAnimationAnd())
    }

    fun leftToRight(container: ViewGroup, pageSwipe: PageSwipe) {
        pageSwipe.swipeOutView?.startAnimation(
            leaveToRightAnimationAnd {
                container.removeView(pageSwipe.swipeOutView)
            }
        )
        pageSwipe.swipeInView.startAnimation(enterFromLeftAnimationAnd())
    }

    //endregion

    //region Private API

    private fun enterFromRightAnimationAnd(onAnimationFinishAction: () -> Unit = {}) =
        enterFromRightAnimation.apply {
            fillBefore = true
            fillAfter = false
            setAnimationListener(
                CustomAnimationListener(
                    animationEnd = onAnimationFinishAction
                )
            )
        }

    private fun enterFromLeftAnimationAnd(onAnimationFinishAction: () -> Unit = {}) =
        enterFromLeftAnimation.apply {
            fillBefore = true
            fillAfter = false
            setAnimationListener(
                CustomAnimationListener(
                    animationEnd = onAnimationFinishAction
                )
            )
        }

    private fun leaveToRightAnimationAnd(onAnimationFinishAction: () -> Unit = {}) =
        exitToRightAnimation.apply {
            fillBefore = false
            fillAfter = true
            setAnimationListener(
                CustomAnimationListener(
                    animationEnd = onAnimationFinishAction
                )
            )
        }

    private fun leaveToLeftAnimationAnd(onAnimationFinishAction: () -> Unit = {}) =
        exitToLeftAnimation.apply {
            fillBefore = false
            fillAfter = true
            setAnimationListener(
                CustomAnimationListener(
                    animationEnd = onAnimationFinishAction
                )
            )
        }

    //endregion

    //region Private Subclasses

    private class CustomAnimationListener(
        val animationEnd: () -> Unit = {},
        val animationStart: () -> Unit = {}
    ) : Animation.AnimationListener, CoroutineScope {

        override val coroutineContext: CoroutineContext = Dispatchers.Main

        override fun onAnimationRepeat(animation: Animation?) {}

        override fun onAnimationEnd(animation: Animation?) {
            launch { runOnMainWithDelay { animationEnd() } }
        }

        override fun onAnimationStart(animation: Animation?) {
            launch { runOnMainWithDelay { animationStart() } }
        }

        // necessary to prevent a NPE
        private suspend fun runOnMainWithDelay(block: () -> Unit) = withContext(Dispatchers.Main) {
            delay(100L)
            block()
        }
    }

    //endregion
}
