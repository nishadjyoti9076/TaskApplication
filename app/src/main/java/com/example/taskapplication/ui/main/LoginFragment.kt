package com.example.taskapplication.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.di_3rddaggerwithmvvmapp.utils.Constant
import com.example.taskapplication.R
import com.example.taskapplication.databinding.FragmentLoginBinding
import com.example.taskapplication.utils.CheckConnectivity
import com.example.taskapplication.utils.PostsApplication.Companion.sharedPreferenceClass
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private lateinit var navController : NavController
    lateinit var bindnig : FragmentLoginBinding
    companion object {
        private const val RC_SIGN_IN = 9001
    }
    var isSigned : Boolean=false
    private lateinit var mGoogleSignInClient :GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private lateinit var googleApiClient: GoogleApiClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=Navigation.findNavController(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        if (account !=null){
            isSigned=true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindnig= FragmentLoginBinding.inflate(inflater,container,false)
        bindnig.btnLogin.setOnClickListener {
        }
        auth = FirebaseAuth.getInstance()

        isSigned=sharedPreferenceClass.getIsSigned(Constant.IS_SIGNED)
        if (isSigned==true){
            bindnig.mainLay.visibility=View.GONE
            bindnig.mainLay2.visibility=View.VISIBLE
        }else{
            bindnig.mainLay.visibility=View.VISIBLE
            bindnig.mainLay2.visibility=View.GONE
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
        googleApiClient = GoogleApiClient.Builder(requireContext())
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()
        googleApiClient.connect()

        bindnig.signInButton.setSize(SignInButton.SIZE_STANDARD);
        bindnig.signInButton.setOnClickListener {
            signInWithGoogle()
        }

        bindnig.btnHome.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_homeFragment)
        }

        bindnig.btnProfile.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_profileFragment)
        }
        return bindnig.root
    }

    private fun signInWithGoogle() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data!!)
            if (result!!.isSuccess) {
                sharedPreferenceClass.setProfileData(Constant.PROFILE_DATA,result!!)
                sharedPreferenceClass.setIsSigned(Constant.IS_SIGNED,true)
                val account = result.signInAccount
                val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
                firebaseAuthWithGoogle(account!!,task)
            } else {
                // Handle sign-in failure
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount, task: Task<GoogleSignInAccount>) {
        try {
            if (task.isSuccessful) {
                // Sign-in successful, update UI with the signed-in user's information
                val user = auth.currentUser
                navController.navigate(R.id.action_loginFragment_to_homeFragment)
                Toast.makeText(activity,"Login Successful",Toast.LENGTH_LONG).show()
                // Update UI or navigate to the next screen
            } else {
                // Sign-in failed
            }
        }catch (e : Exception){

        }

    }
}