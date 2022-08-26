package com.example.grofi

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.grofi.databinding.FragmentAddSuscriptionBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class AddSuscriptionFragment : Fragment() {

    private lateinit var mBinding: FragmentAddSuscriptionBinding
    private var mActivity: Index? = null
    private var mIsEditMode: Boolean = false
    private var mSuscriptionEntity: SuscriptionEntity? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        mBinding = FragmentAddSuscriptionBinding.inflate(inflater,container,false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mActivity = activity as? Index
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mActivity?.supportActionBar?.title = getString(R.string.suscription_add)

        setHasOptionsMenu(true)

        mBinding.btnCreate.setOnClickListener {
            if (mSuscriptionEntity != null &&
                validateFields(mBinding.tilImaUrl,
                    mBinding.tilCost,
                    mBinding.tilDate,
                    mBinding.tilNumPers,
                    mBinding.tilName)){

                with(mSuscriptionEntity!!){
                    name =mBinding.etName.text.toString().trim()
                    numPerson =  mBinding.etNumPers.text.toString().toLong()
                    date = mBinding.etDate.text.toString().trim()
                    costSuscriprion = mBinding.etCost.text.toString().toLong()
                    costSuscriprionP =(mBinding.etCost.text.toString().toLong())/(mBinding.etNumPers.text.toString().toLong())
                    imgUrl = mBinding.etImaUrl.text.toString().trim()
                }
                doAsync {
                    if (mIsEditMode)SuscriptionApplication.database.suscriptionDao().updateSuscription(mSuscriptionEntity!!)
                    else mSuscriptionEntity!!.id = SuscriptionApplication.database.suscriptionDao().addSuscription(mSuscriptionEntity!!)
                    uiThread {
                        hideKerboard()
                        if (mIsEditMode){
                            mActivity?.updatesuscription(mSuscriptionEntity!!)
                            Snackbar.make(mBinding.root,
                                getString(R.string.edit_suscription_message_update_success),
                                Snackbar.LENGTH_SHORT)
                                .show()
                        }else{
                            mActivity?.addsuscription(mSuscriptionEntity!!)
                            Toast.makeText(mActivity, R.string.edit_suscription_message_save_success,
                                Toast.LENGTH_SHORT).show()
                        }
                        mActivity?.onBackPressed()
                    }
                }

            }
        }
        mBinding.btnCancel.setOnClickListener {
            mActivity?.onBackPressed()
        }

        val id = arguments?.getLong(getString(R.string.arg_id), 0)
        if (id != null && id != 0L){
            mIsEditMode = true
            getSuscription(id)
        } else {
            mIsEditMode = false
            mSuscriptionEntity = SuscriptionEntity(name = "", numPerson = 0, date = "", costSuscriprion = 0, imgUrl = "")
        }
        setupTextFields()
        setupBottoms()

    }

    private fun setupBottoms() {
        if (mIsEditMode){
            mBinding.btnCreate.text = getString(R.string.suscription_edit)
        }else{
            mBinding.btnCreate.text = getString(R.string.suscription_add)
        }
    }

    private fun setupTextFields() {
        with(mBinding){
            etName.addTextChangedListener { validateFields(tilName) }
            etNumPers.addTextChangedListener{validateFields(tilNumPers)}
            etDate.addTextChangedListener{validateFields(tilDate)}
            etCost.addTextChangedListener{validateFields(tilCost)}
            etImaUrl.addTextChangedListener{
                validateFields(mBinding.tilImaUrl)
                loadImage(it.toString().trim()) }
        }
    }

    private fun loadImage(url: String){
        Glide.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(mBinding.imgPhoto)
    }

    private fun getSuscription(id: Long) {
        doAsync {
            mSuscriptionEntity = SuscriptionApplication.database.suscriptionDao().getSuscriptionId(id)
            uiThread {
                if (mSuscriptionEntity != null)setUiSuscription(mSuscriptionEntity!!)
            }
        }
    }
    private fun setUiSuscription(suscriptionEntity: SuscriptionEntity) {
        with(mBinding){
            etName.text = suscriptionEntity.name.editable()
            etNumPers.text = suscriptionEntity.numPerson.editable()
            etDate.text = suscriptionEntity.date.editable()
            etCost.text = suscriptionEntity.costSuscriprion.editable()
            etImaUrl.text = suscriptionEntity.imgUrl.editable()
        }
    }

    private fun String.editable(): Editable = Editable.Factory.getInstance().newEditable(this)
    private fun Long.editable(): Editable = Editable.Factory.getInstance().newEditable(this.toString())

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                mActivity?.onBackPressed()
                true
            }
            R.id.action_save -> {
                val suscription = SuscriptionEntity(name =mBinding.etName.text.toString().trim(),
                    numPerson =  mBinding.etNumPers.text.toString().toLong(),
                    date = mBinding.etDate.text.toString().trim(),
                    costSuscriprion = mBinding.etCost.text.toString().toLong(),
                    costSuscriprionP =(mBinding.etCost.text.toString().toLong())/(mBinding.etNumPers.text.toString().toLong()) )

                doAsync {
                    SuscriptionApplication.database.suscriptionDao().addSuscription(suscription)
                    uiThread {
                        Snackbar.make(mBinding.root,
                            getString(R.string.edit_suscription_message_save_success),
                            Snackbar.LENGTH_SHORT)
                            .show()
                        mActivity?.onBackPressed()
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
        //return super.onOptionsItemSelected(item)
    }

    private fun validateFields(vararg textFiels: TextInputLayout): Boolean{
        var isValid = true

        for(textField in textFiels){
            if (textField.editText?.text.toString().trim().isEmpty()){
                textField.error = getString(R.string.helper_required)

                isValid =  false
            }else{
                textField.error = null
            }

        }
        if (!isValid)Snackbar.make(mBinding.root,
            R.string.edit_suscription_message_valid,
            Snackbar.LENGTH_SHORT).show()

        return isValid
    }

    private fun hideKerboard(){
        val imm = mActivity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if(view != null){
            imm.hideSoftInputFromWindow(requireView().windowToken,0)
        }
    }

    override fun onDestroyView() {
        hideKerboard()
        super.onDestroyView()
    }

    override fun onDestroy() {
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mActivity?.supportActionBar?.title = getString(R.string.app_name)
        mActivity?.hideFab(true)
        setHasOptionsMenu(false)
        super.onDestroy()
    }


}