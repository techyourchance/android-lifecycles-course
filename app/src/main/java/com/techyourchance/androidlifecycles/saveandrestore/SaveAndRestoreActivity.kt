package com.techyourchance.androidlifecycles.saveandrestore

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.techyourchance.androidlifecycles.BackgroundDetector
import com.techyourchance.androidlifecycles.CustomApplication
import com.techyourchance.androidlifecycles.R
import timber.log.Timber

class SaveAndRestoreActivity : AppCompatActivity() {

    private lateinit var backgroundDetector: BackgroundDetector

    private lateinit var checkBox: CheckBox
    private lateinit var txtCheckBoxCaption: TextView

    private lateinit var checkBoxMaterial: MaterialCheckBox
    private lateinit var txtCheckBoxMaterialCaption: TextView

    private lateinit var edtNumber: EditText
    private lateinit var btnAddNumber: Button
    private lateinit var recyclerNumbers: RecyclerView
    private lateinit var numbersAdapter: NumbersAdapter
    private lateinit var txtSum: TextView

    private var sum = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        Timber.i("onCreate()")

        val application = this.applicationContext as Application

        super.onCreate(savedInstanceState)

        backgroundDetector = (application as CustomApplication).backgroundDetector

        setContentView(R.layout.activity_save_and_restore)

        checkBox = findViewById(R.id.checkBox)
        txtCheckBoxCaption = findViewById(R.id.txtCheckBoxCaption)
        checkBoxMaterial = findViewById(R.id.checkBoxMaterial)
        txtCheckBoxMaterialCaption = findViewById(R.id.txtCheckBoxMaterialCaption)
        edtNumber = findViewById(R.id.edtNumber)
        btnAddNumber = findViewById(R.id.btnAddNumber)
        recyclerNumbers = findViewById(R.id.recycler)
        txtSum = findViewById(R.id.txtSum)

        recyclerNumbers.layoutManager = LinearLayoutManager(this)
        numbersAdapter = NumbersAdapter()
        recyclerNumbers.adapter = numbersAdapter

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            updateCheckBoxCaption(isChecked)
        }

        checkBoxMaterial.setOnCheckedChangeListener { _, isChecked ->
            updateCheckBoxMaterialCaption(isChecked)
        }

        btnAddNumber.setOnClickListener {
            val number = try {
                edtNumber.text.toString().toInt()
            } catch (e: NumberFormatException) {
                return@setOnClickListener
            }
            numbersAdapter.addNumber(number)
            sum = numbersAdapter.numbers.sum()
            updateSum()
        }
        
        updateCheckBoxCaption(checkBox.isChecked)
        updateCheckBoxMaterialCaption(checkBoxMaterial.isChecked)
        updateSum()
    }

    override fun onDestroy() {
        Timber.i("onDestroy()")
        super.onDestroy()
    }

    override fun onStart() {
        Timber.i("onStart()")
        super.onStart()
        backgroundDetector.activityStarted()
    }

    override fun onStop() {
        Timber.i("onStop()")
        super.onStop()
        backgroundDetector.activityStopped()
    }

    override fun onResume() {
        Timber.i("onResume()")
        super.onResume()
    }

    override fun onPause() {
        Timber.i("onPause()")
        super.onPause()
    }

    override fun onTopResumedActivityChanged(isTopResumedActivity: Boolean) {
        super.onTopResumedActivityChanged(isTopResumedActivity)
        Timber.i("onTopResumedActivityChanged(); isTopResumed: $isTopResumedActivity")
    }

    private fun updateCheckBoxCaption(isChecked: Boolean) {
        if (isChecked) {
            txtCheckBoxCaption.text = "Checkbox is checked"
        } else {
            txtCheckBoxCaption.text = "Checkbox is unchecked"
        }
    }

    private fun updateCheckBoxMaterialCaption(isChecked: Boolean) {
        if (isChecked) {
            txtCheckBoxMaterialCaption.text = "CheckboxMaterial is checked"
        } else {
            txtCheckBoxMaterialCaption.text = "CheckboxMaterial is unchecked"
        }
    }

    private fun updateSum() {
        txtSum.text = "Total: ${sum.toString()}"
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, SaveAndRestoreActivity::class.java)
            context.startActivity(intent)
        }
    }
}

private class NumbersAdapter: RecyclerView.Adapter<NumberViewHolder>() {
    val numbers = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.number_recycler_item, parent, false)
        return NumberViewHolder(view)
    }

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        holder.numberText.text = numbers[position].toString()
    }

    override fun getItemCount() = numbers.size

    fun bindNumbers(numbers: List<Int>) {
        this.numbers.clear()
        this.numbers.addAll(numbers)
        notifyDataSetChanged()
    }

    fun addNumber(number: Int) {
        numbers.add(number)
        notifyItemInserted(itemCount)
    }
}

private class NumberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val numberText: TextView = itemView.findViewById(R.id.txtNumber)
}