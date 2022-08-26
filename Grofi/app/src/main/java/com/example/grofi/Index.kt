package com.example.grofi

import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.grofi.databinding.ActivityIndexBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class Index : AppCompatActivity(), OnClickListener, IndexAux {

    private lateinit var mbinding: ActivityIndexBinding
    private lateinit var mApdapter: SuscriptionAdapter
    private lateinit var mGridLayout: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding = ActivityIndexBinding.inflate(layoutInflater)
        setContentView(mbinding.root)

        /*mbinding.btnSave.setOnClickListener{
            val suscription = SuscriptionEntity(name = mbinding.etName.text.toString().trim())
            Thread{
                SuscriptionApplication.database.suscriptionDao().addSuscription(suscription)
            }
            mApdapter.add(suscription)
        }*/


        mbinding.fabAdd.setOnClickListener { launchEditFragmen() }
        setupRecyclerView()
    }

    private fun launchEditFragmen(args: Bundle? = null) {
        val fragment = AddSuscriptionFragment()
        if(args != null) fragment.arguments = args

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.containerIndex, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
        hideFab()
    }


    private fun setupRecyclerView() {
        mApdapter = SuscriptionAdapter(mutableListOf(), this)
        mGridLayout = GridLayoutManager(this,1)
        getSuscription()

        mbinding.reclycleView.apply {
            setHasFixedSize(true)
            layoutManager = mGridLayout
            adapter = mApdapter
        }
    }

    private fun getSuscription() {
        doAsync {
            val suscriptions = SuscriptionApplication.database.suscriptionDao().getAllSuscription()
            uiThread {
                mApdapter.setSuscription(suscriptions)
            }
        }
    }

    /*
    * OnClickListener
    * */

    override fun onClick(suscriptonID: Long) {
        val args = Bundle()
        args.putLong(getString(R.string.arg_id), suscriptonID)

        launchEditFragmen(args)
    }

    override fun onFavoriteSuscription(suscriptionEntity: SuscriptionEntity) {

    }

    override fun onDeleteSuscription(suscriptionEntity: SuscriptionEntity) {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.dialog_delete_title)
            .setPositiveButton(R.string.dialog_delete_comfir, DialogInterface.OnClickListener { dialogInterface, i ->
                doAsync {
                    SuscriptionApplication.database.suscriptionDao().deleteSuscription(suscriptionEntity)
                    uiThread {
                        mApdapter.delete(suscriptionEntity)
                        Snackbar.make(mbinding.root,
                            (getString(R.string.delete_suscription)),
                            Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
            })
            .setNegativeButton(R.string.dialog_delete_cancel, null)
            .show()
    }

    /*
    * IndexAux
    * */
    override fun hideFab(isVisible: Boolean) {
        if (isVisible) mbinding.fabAdd.show() else mbinding.fabAdd.hide()
    }

    override fun addsuscription(suscriptionEntity: SuscriptionEntity) {
        mApdapter.add(suscriptionEntity)
    }

    override fun updatesuscription(suscriptionEntity: SuscriptionEntity) {
        mApdapter.update(suscriptionEntity)
    }
}