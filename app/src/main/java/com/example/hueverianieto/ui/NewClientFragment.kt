package com.example.hueverianieto.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.components.HNModalDialog
import com.example.hueverianieto.components.titletextinputlayout.TitleTextInputLayoutAdapter
import com.example.hueverianieto.components.titletextinputlayout.TitleTextInputLayoutViewHolder
import com.example.hueverianieto.data.bbdd.ClientData
import com.example.hueverianieto.data.bbdd.UserData
import com.example.hueverianieto.data.components.ModalDialogModel
import com.example.hueverianieto.data.components.TextInputLayoutModel
import com.example.hueverianieto.data.components.TitleTextInputLayoutModel
import com.example.hueverianieto.databinding.FragmentNewClientBinding
import com.example.hueverianieto.utils.ClientUtils
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NewClientFragment : BaseFragment() {

    private lateinit var binding: FragmentNewClientBinding
    private lateinit var alertDialog: HNModalDialog

    override fun injection() {
        // TODO: Sin implementar
    }

    override fun configureUI() {

        this.alertDialog = HNModalDialog(requireContext())

        this.binding.let {
            it.saveButton.isEnabled = true
            it.saveButton.setText("Guardar")
            it.cancelButton.isEnabled = true
            it.cancelButton.setText("Eliminar")
        }

    }

    override fun setObservers() {
        //
    }

    override fun setListeners() {
        this.binding.cancelButton.setOnClickListener { activity?.onBackPressedDispatcher?.onBackPressed() }
        this.binding.saveButton.setOnClickListener {
            // TODO: Checkear si vienen todos los datos
            // TODO: Guardar datos
            val userData = ClientData(
                this.binding.cifTextInputLayout.getText(),
                this.binding.cityTextInputLayout.getText(),
                "TODO: created_by",
                this.binding.companyTextInputLayout.getText(),
                false,
                this.binding.directionTextInputLayout.getText(),
                this.binding.emailTextInputLayout.getText(),
                null,
                false,
                "00000",
                listOf(mapOf("h" to 1), mapOf("h" to 2)),
                this.binding.postalCodeTextInputLayout.getText().toLong(),
                this.binding.provinceTextInputLayout.getText(),
                null,
                null,
                null
            )
            Firebase.firestore
                .collection("client_info")
                .add(ClientUtils.parcelableToMap(userData))
                .addOnSuccessListener {
                    Log.d(NewClientFragment::class.java.simpleName, "DocumentSnapshot successfully written!")
                    setPopUp(
                        "Guardado correcto.",
                        "El cliente ha sido guardado correctamente en la base de datos."
                    ) {
                        alertDialog.cancel()
                        activity?.onBackPressedDispatcher?.onBackPressed()
                    }
                }
                .addOnFailureListener { e ->
                    Log.w(NewClientFragment::class.java.simpleName, "Error writing document", e)
                    setPopUp(
                        "Ha ocurrido un error.",
                        "Ha ocurrido un error en el poceso de creación del cliente en nuestra base de datos. Por favor, inténtelo de nuevo.\nError: ${e.message}"
                    ){
                        alertDialog.cancel()
                    }
                }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AllClientsActivity).configNav("Nuevo cliente")
        this.binding = FragmentNewClientBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    /*private fun initRecyclerView() {
        this.binding.newClientClientFieldsRecyclerView.layoutManager = LinearLayoutManager(context)
        this.binding.newClientClientFieldsRecyclerView.adapter = TitleTextInputLayoutAdapter(fieldList)
        this.binding.newClientClientFieldsRecyclerView.isNestedScrollingEnabled = false

        this.binding.containerBorderCheckTitle.getBodyContainer().layoutManager = LinearLayoutManager(context)
        this.binding.containerBorderCheckTitle.getBodyContainer().adapter = TitleTextInputLayoutAdapter(ClientUtils.createUserToClientFields())
        this.binding.containerBorderCheckTitle.getBodyContainer().isNestedScrollingEnabled = false
    }*/

    private fun setPopUp(title: String, message: String, listener: OnClickListener) {
        // TODO: Close button
        alertDialog.show(
            requireContext(),
            ModalDialogModel(
                title,
                message,
                "De acuerdo",
                null,
                {
                    alertDialog.cancel()
                    activity?.onBackPressedDispatcher?.onBackPressed()
                },
                null,
                true
            )
        )
    }
}