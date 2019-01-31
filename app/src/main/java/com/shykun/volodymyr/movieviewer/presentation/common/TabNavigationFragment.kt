package com.shykun.volodymyr.movieviewer.presentation.common

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shykun.volodymyr.movieviewer.R
import com.shykun.volodymyr.movieviewer.presentation.AppActivity
import com.shykun.volodymyr.movieviewer.presentation.di.AppComponent
import com.shykun.volodymyr.movieviewer.presentation.di.AppModule
import com.shykun.volodymyr.movieviewer.presentation.di.DaggerAppComponent
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import javax.inject.Inject

abstract class TabNavigationFragment : Fragment(), BackButtonListener {
    @Inject
    lateinit var cicerone: Cicerone<Router>

    @Inject
    lateinit var navigator: FlowNavigator

    abstract val navigationKey: String

    val component: AppComponent? by lazy {
        context?.let {
            DaggerAppComponent.builder()
                    .appModule(AppModule(activity as AppActivity, childFragmentManager, R.id.navigationFragmentContainer))
                    .build()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        component?.inject(this)
        return inflater.inflate(R.layout.fragment_navigation, container, false)
    }

    override fun onResume() {
        super.onResume()
        cicerone.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        cicerone.navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackClicked(): Boolean {
        return if(isAdded) {
            val childFragment = childFragmentManager.findFragmentById(R.id.navigationFragmentContainer)
            childFragment != null && childFragment is BackButtonListener && childFragment.onBackClicked()
        } else false
    }
}