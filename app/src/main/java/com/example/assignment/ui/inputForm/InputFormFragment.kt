package com.example.assignment.ui.inputForm

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Toast

import com.example.assignment.R
import com.example.assignment.model.requestModel.InputInfoRequestModel
import com.example.assignment.ui.base.BaseFragment
import com.example.assignment.utils.AppHelper

import kotlinx.android.synthetic.main.input_form_fragment.*
import kotlinx.android.synthetic.main.layout_action_bar.*

import java.util.*

class InputFormFragment : BaseFragment() {

    private var cvId = 0
    private lateinit var currentViewModel: InputFormViewModel

    companion object {
        fun newInstance() = InputFormFragment()
    }

    override fun addViewModel() {
        currentViewModel = ViewModelProvider(this).get(InputFormViewModel::class.java)
        viewModel = currentViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.input_form_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_action_bar_title.text = getString(R.string.input_form)
        btnSubmit.setOnClickListener {
            validateData()
        }

        observer()
    }

    private fun observer() {
        currentViewModel.inputInfoResponse.observe(activity!!, {
            if (it != null)
                getCvid(it)
        })
    }

    private fun getCvid(it: InputInfoRequestModel) {
        cvId = it.cv_file.id
        AppHelper.CvId = cvId
        communicator?.setContentFragment(CvFileSubmitFragment(), false)

    }


    private fun validateData() {
        if (etName.text.isNullOrEmpty() || etName.text.toString().length > 256) {
            Toast.makeText(activity, "Valid Name is required!", Toast.LENGTH_SHORT).show()
        } else if (etEmail.text.isNullOrEmpty()) {
            Toast.makeText(activity, "valid Email is required!", Toast.LENGTH_SHORT).show()
        } else if (etPhone.text.isNullOrEmpty() || etPhone.text.toString().length > 14) {
            Toast.makeText(activity, "valid phone number is required!", Toast.LENGTH_SHORT).show()
        } else if (etUniversity.text.isNullOrEmpty() || etUniversity.text.toString().length > 256) {
            Toast.makeText(activity, "valid University Name is required!", Toast.LENGTH_SHORT)
                .show()
        } else if (etGradYear.text.isNullOrEmpty() || etGradYear.text.toString()
                .toInt() > 2020 || etGradYear.text.toString().toInt() < 2015
        ) {
            Toast.makeText(activity, "valid Graduation Year is required!", Toast.LENGTH_SHORT)
                .show()
        } else if (etApplying.text.isNullOrEmpty()) {
            Toast.makeText(activity, "valid phone number is required!", Toast.LENGTH_SHORT).show()
        } else if (etExpectedSalary.text.isNullOrEmpty() || etExpectedSalary.text.toString()
                .toDouble() > 60000 || etExpectedSalary.text.toString().toDouble() < 15000
        ) {
            Toast.makeText(
                activity,
                "Expected salary should be between 60000 and 15000",
                Toast.LENGTH_SHORT
            ).show()
        } else if (etGit.text.isNullOrEmpty() || !URLUtil.isValidUrl(etGit.text.toString())) {
            Toast.makeText(activity, "valid Project Url is required!", Toast.LENGTH_SHORT).show()
        } else {
            val inputInfoRequestModel = InputInfoRequestModel()
            inputInfoRequestModel.tsync_id = UUID.randomUUID().toString()
            inputInfoRequestModel.name = etName.text.toString()
            inputInfoRequestModel.email = etEmail.text.toString()
            inputInfoRequestModel.phone = etPhone.text.toString()
            inputInfoRequestModel.full_address = etAddress.text.toString()
            inputInfoRequestModel.name_of_university = etUniversity.text.toString()
            inputInfoRequestModel.graduation_year = etGradYear.text.toString()
            inputInfoRequestModel.cgpa = etCgpa.text.toString()
            inputInfoRequestModel.experience_in_months = etExperiance.text.toString()
            inputInfoRequestModel.current_work_place_name = etWorkPlace.text.toString()
            inputInfoRequestModel.applying_in = etApplying.text.toString()
            inputInfoRequestModel.expected_salary = etExpectedSalary.text.toString()
            inputInfoRequestModel.field_buzz_reference = etRef.text.toString()
            inputInfoRequestModel.on_spot_creation_time = System.currentTimeMillis().toString()
            inputInfoRequestModel.on_spot_update_time = System.currentTimeMillis().toString()
            inputInfoRequestModel.cv_file.tsync_id = UUID.randomUUID().toString()
            inputInfoRequestModel.github_project_url = etGit.text.toString()

            AppHelper.CvTsyncId = inputInfoRequestModel.cv_file.tsync_id
            currentViewModel.requestForSubmittingInputInfo(AppHelper.token, inputInfoRequestModel)
        }

    }


}