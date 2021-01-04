package com.clezk.czutilapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.clezk.czutil.ActivityService
import com.clezk.czutilapp.util.component
import com.clezk.czutilapp.util.getViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var activityService: ActivityService
    private lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.activityService = applicationContext.component.activityService()
        this.activityService.onCreate(this)


        this.disposable = applicationContext.component.applicationState().getModel().subscribe() {model ->
//            Toast.makeText(this, "Main", Toast.LENGTH_SHORT).show()
            this.loading_view.visibility = if (model.loading) View.VISIBLE else View.GONE
            this.waiting_view.visibility = if (model.waiting) View.VISIBLE else View.GONE
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        this.disposable.dispose()
        this.activityService.onDestroy(this)
    }
}
