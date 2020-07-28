package com.mobileforce.hometeach.ui.signin

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.ui.navdrawer.HomeNavigationDrawerActivity
import com.mobileforce.hometeach.databinding.ActivityLoginBinding
import com.mobileforce.hometeach.ui.ExploreActivity
import com.mobileforce.hometeach.utils.ApiError
import com.mobileforce.hometeach.utils.Result
import com.mobileforce.hometeach.utils.snack
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.recover_email_layout.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: SignInViewModel by viewModel()

    private var emailValid = false
    private var passwordValid = false
    private var isTutor = false

    private lateinit var emailWatcher: TextWatcher
    private lateinit var passwordWatcher: TextWatcher

    private val progressDialog by lazy {
        ProgressDialog(this).apply {
            setMessage("Login in progress...")
            setCancelable(false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signIn.setOnClickListener {
            triggerSignInProcess()
        }


        binding.toggleButton.check(R.id.button1)
        binding.toggleButton.button1.setOnClickListener {
            isTutor = false
            binding.toggleButton.uncheck(R.id.button12)
            binding.toggleButton.check(R.id.button1)
        }
        binding.toggleButton.button12.setOnClickListener {
            isTutor = true
            binding.toggleButton.uncheck(R.id.button1)
            binding.toggleButton.check(R.id.button12)
        }



        Log.d("login", isTutor.toString())

        binding.textRegisterNow.setOnClickListener {
            navigateToSignUp()
        }
        binding.forgotPassword.setOnClickListener {
            showEmailDialog()
        }

        emailWatcher = object : TextWatcher {
            override fun afterTextChanged(input: Editable?) {
                emailValid = Patterns.EMAIL_ADDRESS.matcher(input)
                        .matches() && input!!.indexOf("@") < input!!.length
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        }

        passwordWatcher = object : TextWatcher {
            override fun afterTextChanged(input: Editable?) {

                input?.let {
                    if (input.length > 7) {
                        passwordValid = true
                        binding.textInputPasswordField.error = null
                    } else {
                        binding.textInputPasswordField.isHelperTextEnabled = true
                        passwordValid = false
                    }
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        }

        binding.textEditEmail.addTextChangedListener(emailWatcher)
        binding.textEditPassword.addTextChangedListener(passwordWatcher)

        observeSignIn()
    }

    override fun onDestroy() {
        super.onDestroy()
        progressDialog.dismiss()
    }

    private fun triggerSignInProcess() {
        if (emailValid && passwordValid) {
            val user = Params.SignIn(
                email = binding.textEditEmail.text.toString(),
                password = binding.textEditPassword.text.toString(),
                is_tutor = isTutor
            )
            viewModel.signIn(user)
        } else if (!emailValid) {
            binding.textInputEmailSignin.isHelperTextEnabled = true
            binding.textInputEmailSignin.error = "Invalid email address"
        } else if (!passwordValid) {
            binding.textInputPasswordField.isHelperTextEnabled = true
            binding.textInputPasswordField.error = "Password too short"
        }
    }


    private fun observeSignIn() {

        viewModel.signIn.observe(this, Observer { result ->

            when (result) {
                is Result.Loading -> {
                    progressDialog.show()
                }

                is Result.Success -> {
                    progressDialog.hide()
                    binding.signInLayout.snack(message = "Login Successful",
                        actionCallBack = {
                            navigateToDashBoard()
                        })

                    navigateToDashBoard()

                }

                is Result.Error -> {
                    progressDialog.hide()
                    val message = ApiError(result.exception).message
                    binding.signInLayout.snack(message = message, length = Snackbar.LENGTH_LONG)
                }
            }
        })
    }


    private fun navigateToDashBoard() {
        startActivity(Intent(this, HomeNavigationDrawerActivity::class.java))
        //finish()
        finish()
    }


    private fun navigateToSignUp() {
        startActivity(Intent(this, ExploreActivity::class.java))
    }


    private fun showEmailDialog() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.recover_email_layout, null)

        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)

        val mAlertDialog = mBuilder.show()
        val submit = mDialogView.findViewById<AppCompatButton>(R.id.submit)

        submit.setOnClickListener {

            val email = mDialogView.email.text.toString()
            val data = Params.PasswordReset(email)
            viewModel.resetPassword(data)

            if (viewModel.success) {
                mAlertDialog.hide()
                binding.signInLayout.snack(message = "A PASSSWORD RESET LINK HAS BEEN SENT TO YOUR MAIL")

            } else {
                mAlertDialog.hide()
                binding.signInLayout.snack(message = "SORRY, THIS EMAIL IS NOT REGISTERED OR YOU HAVE A POOR INTERNET CONNECTION")

            }
        }
    }

}