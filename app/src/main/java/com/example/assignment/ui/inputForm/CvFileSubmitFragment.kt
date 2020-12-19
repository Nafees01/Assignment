package com.example.assignment.ui.inputForm

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.assignment.R
import com.example.assignment.model.requestModel.CvFileModel
import com.example.assignment.ui.base.BaseFragment
import com.example.assignment.utils.AppHelper
import com.example.assignment.utils.BitmapHelper
import kotlinx.android.synthetic.main.cv_fragment.*
import kotlinx.android.synthetic.main.layout_action_bar.*

class CvFileSubmitFragment : BaseFragment() {

    private var cvFile: ByteArray? = null
    private var cvId = 0
    private var requestModel = CvFileModel()
    private lateinit var currentViewModel: InputFormViewModel

    companion object {
        fun newInstance() = CvFileSubmitFragment()
    }

    override fun addViewModel() {
        currentViewModel = ViewModelProvider(this).get(InputFormViewModel::class.java)
        viewModel = currentViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cv_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_action_bar_title.text = "Cv Upload"
        btnSubmit.setOnClickListener {
            validateData()
        }

        cvLayout.setOnClickListener {
            showPhotoPicker(101)
        }


        observer()
    }

    private fun observer() {
        currentViewModel.inputInfoResponse.observe(activity!!, {
           /* if (it != null)*/

        })
    }

    private fun showPhotoPicker(requestCode: Int) {
        val pickPhoto = getFileChooserIntent()
        startActivityForResult(pickPhoto, requestCode)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val docUrl = data?.data
            if (requestCode == 101) {
                cvText.text = getFileNameFromUri(docUrl!!)
                cvFile = BitmapHelper.getInputStrem(docUrl, context)
                requestModel.file = BitmapHelper.getInputStrem(docUrl, context)
                requestModel.id =AppHelper.CvId
                requestModel.tsync_id =AppHelper.CvTsyncId


            }
        }
    }

    private fun getFileChooserIntent(): Intent {
        val mimeTypes = arrayOf("image/*", "application/pdf")

        val intent = Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        intent.addCategory(Intent.CATEGORY_OPENABLE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.type = if (mimeTypes.size == 1) mimeTypes[0] else "*/*"
            if (mimeTypes.isNotEmpty()) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            }
        } else {
            var mimeTypesStr = ""

            for (mimeType in mimeTypes) {
                mimeTypesStr += "$mimeType|"
            }

            intent.type = mimeTypesStr.substring(0, mimeTypesStr.length - 1)
        }

        return intent
    }

    private fun validateData() {
        if(cvFile!=null)
        currentViewModel.requestForSubmittingCv(AppHelper.token,requestModel)
        else{
            communicator?.showToast("File is required!")
        }
    }

    private fun getFileNameFromUri(uri: Uri): String {
        var fileName = ""
        activity?.contentResolver
            ?.query(uri, null, null, null, null)
            ?.let {
                it.moveToFirst()
                fileName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }

        return fileName
    }


}