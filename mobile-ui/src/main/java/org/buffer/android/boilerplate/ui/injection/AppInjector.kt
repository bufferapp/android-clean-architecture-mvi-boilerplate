package org.buffer.android.boilerplate.ui.injection

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import org.buffer.android.boilerplate.ui.BufferooApplication

/**
 * Helper class to automatically inject fragments if they implement {@link Injectable}.
 */
class AppInjector {

    companion object {

        fun init(bufferooApp: BufferooApplication) {

            DaggerApplicationComponent
                    .builder()
                    .application(bufferooApp)
                    .build()
                    .inject(bufferooApp)

            bufferooApp.registerActivityLifecycleCallbacks(object :
                    Application.ActivityLifecycleCallbacks {
                        override fun onActivityCreated(activity: Activity,
                                                       savedInstanceState: Bundle?) {
                            handleActivity(activity)
                        }

                        override fun onActivityStarted(activity: Activity) { }

                        override fun onActivityResumed(activity: Activity) { }

                        override fun onActivityPaused(activity: Activity) { }

                        override fun onActivityStopped(activity: Activity) { }

                        override fun onActivitySaveInstanceState(activity: Activity,
                                                                 outState: Bundle?) { }

                        override fun onActivityDestroyed(activity: Activity) { }
                    })
        }

        private fun handleActivity(activity: Activity) {
            if (activity is HasSupportFragmentInjector) {
                AndroidInjection.inject(activity)
            }
            (activity as? FragmentActivity)?.supportFragmentManager?.registerFragmentLifecycleCallbacks(
                    object : FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentCreated(fm: FragmentManager, f: Fragment,
                                                       savedInstanceState: Bundle?) {
                            if (f is Injectable) {
                                AndroidSupportInjection.inject(f)
                            }
                        }
                    }, true)
        }

    }

}