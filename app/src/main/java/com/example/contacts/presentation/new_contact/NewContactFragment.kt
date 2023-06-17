package com.example.contacts.presentation.new_contact

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.contacts.R
import com.example.contacts.databinding.FragmentNewContactBinding
import com.example.contacts.presentation.common.isValidEmail
import com.example.contacts.presentation.common.snack
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewContactFragment : Fragment() {


    private var _binding: FragmentNewContactBinding? = null
    private val binding get() = _binding!!
    private var image: Uri? = null

    private val viewModel: NewContactViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewContactBinding.inflate(inflater, container, false)
        val root: View = binding.root
        bind()
        listeners()
        return root
    }

    private fun bind() {
        binding.apply {

            addPicture.setOnClickListener {
                chooseFromGallery()
            }
            imageShape.setOnClickListener {
                chooseFromGallery()
            }
            btnSave.setOnClickListener {
                viewModel.addContact(
                    etName.editText?.text.toString(),
                    etLastName.editText?.text.toString(),
                    etNumber.editText?.text.toString(),
                    etEmail.editText?.text.toString(),
                    image
                )
            }
            etEmail.editText?.doAfterTextChanged {
                if (!it.toString().isValidEmail()) {
                    binding.etEmail.error = getString(R.string.invalid_email)
                } else {
                    binding.etEmail.error = null
                }
            }
            btnClose.setOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    private fun listeners() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.contactsState.collect {
                it.apply(findNavController(), binding.loadingProgressBar)
            }
        }
    }

    private fun requestStoragePermission() {
        storagePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private val storagePermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            storageOpener.launch(getString(R.string.image_open))
        } else {
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                showPermissionNeedsToBeGrantedDialog()
            }
        }
    }


    private fun showPermissionNeedsToBeGrantedDialog() {
        MaterialAlertDialogBuilder(requireContext()).setTitle(
            getString(R.string.permission_required),
        )
            .setMessage(getString(R.string.permission_req_dialog))
            .setNegativeButton(getString(R.string.deny_from_dialog)) { dialog, _ ->
                dialog.dismiss()
            }.setPositiveButton(getString(R.string.accept_from_dialog)) { d, _ ->
                d.dismiss()
            }.show()
    }


    private val storageOpener = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { resultUri ->
        if (resultUri != null) {
            Glide.with(requireContext())
                .load(resultUri)
                .circleCrop()
                .into(binding.imageShape)
            image = resultUri
        }
    }

    private fun chooseFromGallery() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            storageOpener.launch(getString(R.string.image_open))
        } else if (ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED
        ) {
            binding.root.snack(getString(R.string.permission_message_from_snackbar))
        } else {
            requestStoragePermission()
        }
    }
}