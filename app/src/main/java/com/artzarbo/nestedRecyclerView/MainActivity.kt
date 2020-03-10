package com.artzarbo.nestedRecyclerView

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showList.setOnClickListener {
            showList.visibility = View.GONE
            supportFragmentManager.beginTransaction()
                .add(R.id.container,ListFragment.newInstance(Bundle()))
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        showList.visibility = View.VISIBLE
    }
}
