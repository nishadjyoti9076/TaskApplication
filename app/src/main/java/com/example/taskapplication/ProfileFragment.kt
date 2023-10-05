package com.example.taskapplication

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.di_3rddaggerwithmvvmapp.utils.Constant
import com.example.taskapplication.databinding.FragmentProfileBinding
import com.example.taskapplication.utils.PostsApplication.Companion.sharedPreferenceClass
import com.google.android.gms.auth.api.signin.GoogleSignIn

class ProfileFragment : Fragment() {

    lateinit var binding : FragmentProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentProfileBinding.inflate(inflater,container,false)

        val acct = GoogleSignIn.getLastSignedInAccount(requireContext())
        if (acct != null) {
            var personName: String = acct.getDisplayName()!!
            val personEmail: String = acct.getEmail()!!
            binding.email.setText(personEmail)

            if (sharedPreferenceClass.getName() !=null){
                binding.firstNameEditText.setText(sharedPreferenceClass.getName())
                binding.dobEditText.setText(sharedPreferenceClass.getAge())
                binding.edMobileno.setText(sharedPreferenceClass.getMobile())
            }else{
                binding.firstNameEditText.setText(personName)
            }
        }

        binding.ivEdit.setOnClickListener {
            binding.firstNameEditText.isEnabled=true
            binding.dobEditText.isEnabled=true
            binding.edMobileno.isEnabled=true
            binding.updateButton.visibility=View.VISIBLE
        }

        binding.updateButton.setOnClickListener {

            if (Validation()) {
                sharedPreferenceClass.setInfo(
                    binding.firstNameEditText.text.toString(),
                    binding.dobEditText.text.toString(),
                    binding.edMobileno.text.toString(),
                )
                binding.firstNameEditText.isEnabled = false
                binding.dobEditText.isEnabled = false
                binding.edMobileno.isEnabled = false
                binding.updateButton.visibility = View.GONE
                Toast.makeText(requireContext(), "Profile Updated", Toast.LENGTH_LONG).show()
            }
        }

        return binding.root
    }

    fun Validation() : Boolean{
        if (binding.firstNameEditText.text.isNullOrEmpty()){
            showToast("Please enter name")
            return false
        }else if (binding.dobEditText.text.isNullOrEmpty()){
            showToast("Please enter your age")
            return false
        }else if (binding.edMobileno.text.isNullOrEmpty()){
            showToast("Please enter valid mobile no")
            return false
        }
        return true
    }

    fun showToast(message : String){
        Toast.makeText(requireContext(),message,Toast.LENGTH_LONG).show()
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}