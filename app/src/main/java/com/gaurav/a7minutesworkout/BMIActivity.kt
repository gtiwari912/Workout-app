package com.gaurav.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_bmiactivity.*
import kotlinx.android.synthetic.main.activity_finish.*
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    val METRIC_UNITS_VIEW="METRIC_UNIT_VIEW"
    val US_UNIT_VIEW="US_UNIT_VIEW"

    var currentVisibleView :String= US_UNIT_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmiactivity)

        setSupportActionBar(toolbar_bmi_activity)

        val actionbar= supportActionBar
        if (actionbar!=null){
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.title="Calculate BMI"
        }
        toolbar_bmi_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        btnCalculateUnits.setOnClickListener {
            if (currentVisibleView.equals(METRIC_UNITS_VIEW)){
                if (validateMetricUnits()){
                    val heightValue:Float=etMetricUnitHeight.text.toString().toFloat()/100
                    val weightValue:Float=etMetricUnitWeight.text.toString().toFloat()

                    val bmi=weightValue/(heightValue*heightValue)
                    displayBMIResult(bmi)
                }else{
                    Toast.makeText(this@BMIActivity,"Please enter valid values.",Toast.LENGTH_SHORT).show()
                }
            }else{
                if (validateUsUnits()){
                    val usUnitHeightValueFeet:String=etUSUnitHeightFeet.text.toString()
                    val usUnitHeightValueInch:String=etUSUnitHeightInch.text.toString()
                    val usUnitWeightValue:Float=etUSUnitWeight.text.toString().toFloat()

                    val heightValue=12*usUnitHeightValueFeet.toFloat()+usUnitHeightValueInch.toFloat()

                    val bmi= 703 * (usUnitWeightValue/(heightValue*heightValue))
                    displayBMIResult(bmi)

                }else{
                    Toast.makeText(this@BMIActivity,"Please enter valid values.",Toast.LENGTH_SHORT).show()
                }
            }
        }
        makeVisibleMetricUnitsView()
        rgUnits.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId==R.id.rbMetricUnits){
                makeVisibleMetricUnitsView()
            }else{
                makeVisibleUsUnitsView()
            }
        }
    }

    private fun makeVisibleUsUnitsView(){
        currentVisibleView=US_UNIT_VIEW
        etMetricUnitWeight.visibility=View.GONE
        etMetricUnitHeight.visibility=View.GONE

        etUSUnitWeight.text.clear()
        etUSUnitHeightInch.text.clear()
        etUSUnitHeightFeet.text.clear()

        etUSUnitWeight.visibility=View.VISIBLE
        llUsUnitsHeight.visibility=View.VISIBLE

        llDisplayBMIResult.visibility=View.INVISIBLE
    }

    private fun makeVisibleMetricUnitsView(){
        currentVisibleView=METRIC_UNITS_VIEW
        etMetricUnitWeight.visibility=View.VISIBLE
        etMetricUnitHeight.visibility=View.VISIBLE

        etMetricUnitHeight.text.clear()
        etMetricUnitWeight.text.clear()

        etUSUnitWeight.visibility=View.GONE
        llUsUnitsHeight.visibility=View.GONE

        llDisplayBMIResult.visibility=View.INVISIBLE
    }

    private fun displayBMIResult(bmi:Float){
        val bmiLabel:String;
        val bmiDescription:String

        if (bmi.compareTo(15f)<=0){
            bmiLabel="Very severely underweight"
            bmiDescription="Opps! You really need ot take care of your better! Eat More!"
        }else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0){
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        llDisplayBMIResult.visibility=View.VISIBLE
//        tvYourBMI.visibility=View.VISIBLE
//        tvBMIValue.visibility=View.VISIBLE
//        tvBMIType.visibility=View.VISIBLE
//        tvBMIDescription.visibility=View.VISIBLE

        val bmiValue=BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()
        tvBMIValue.text=bmiValue
        tvBMIType.text=bmiLabel
        tvBMIDescription.text=bmiDescription
    }

    private fun validateMetricUnits():Boolean{
        var isValid=true
        if (etMetricUnitWeight.text.toString().isEmpty())
            isValid=false
        else if (etMetricUnitHeight.text.toString().isEmpty())
            isValid=false

        return isValid
    }

    private fun validateUsUnits():Boolean{
        var isValid=true
        when {
            etUSUnitWeight.text.toString().isEmpty() -> isValid=false
            etUSUnitHeightFeet.text.toString().isEmpty() -> isValid=false
            etUSUnitHeightInch.text.toString().isEmpty() -> isValid=false
        }

        return isValid
    }
}