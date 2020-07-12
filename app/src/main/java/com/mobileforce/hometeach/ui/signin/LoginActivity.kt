package com.mobileforce.hometeach.ui.signin

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.databinding.ActivityLoginBinding
import com.mobileforce.hometeach.remotesource.Params
import com.mobileforce.hometeach.ui.BottonNavigationActivity
import com.mobileforce.hometeach.ui.ExploreActivity
import com.mobileforce.hometeach.ui.signup.SignUpActivity
import com.mobileforce.hometeach.utils.Result
import com.mobileforce.hometeach.utils.snack
import kotlinx.android.synthetic.main.forgot_password_layout1.view.*
import kotlinx.android.synthetic.main.forgot_password_layout1.view.apply_btn
import kotlinx.android.synthetic.main.recover_email_layout.view.*
import kotlinx.android.synthetic.main.recover_phone_layout.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: SignInViewModel by viewModel()

    private var emailValid = false
    private var passwordValid = false

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

        binding.textRegisterNow.setOnClickListener {
            navigateToSignUp()
        }
        binding.forgotPassword.setOnClickListener {
            forgotPassword()
        }

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.forgot_password_layout1, null)
        val selected = mDialogView.radio_group.checkedRadioButtonId
        when (selected) {
            mDialogView.recover_email_chBox.id -> {
                mDialogView.apply_btn.setBackgroundResource(R.drawable.apply_background)
            }
            mDialogView.recover_phone_chBox.id -> {
                mDialogView.apply_btn.setBackgroundResource(R.drawable.apply_background)
            }
        }

//        if (mDialogView.recover_email_chBox.isChecked) {
//            mDialogView.apply.setBackgroundResource(R.drawable.apply_background)
//        } else if (mDialogView.recover_phone_chBox.isChecked) {
//            mDialogView.apply.setBackgroundResource(R.drawable.apply_background)
//        } else {
//
//        }

        emailWatcher = object : TextWatcher {
            override fun afterTextChanged(input: Editable?) {
                if (Patterns.EMAIL_ADDRESS.matcher(input)
                        .matches() && input!!.indexOf("@") < input!!.length
                ) {
                    binding.textInputEmailSignin.error = null
                    emailValid = true
                } else {
                    binding.textInputEmailSignin.isHelperTextEnabled = true
                    binding.textInputEmailSignin.error = "Invalid Email address"
                    emailValid = false
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

        }

        passwordWatcher = object : TextWatcher {
            override fun afterTextChanged(input: Editable?) {
                val PASSWORD_PATTEN =
                    "^(?=.*?[#?!@\$%^&*-]).{6,}\$"
                if (Pattern.matches(PASSWORD_PATTEN, input)) {
                    binding.textInputPasswordField.error = null
                    passwordValid = true
                } else {

                    binding.textInputPasswordField.isHelperTextEnabled = true
                    binding.textInputPasswordField.error =
                        "Hint: Password should not be less than 6 with at least 1 special character"
                    passwordValid = false
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        }

        binding.textEditEmail.addTextChangedListener(emailWatcher)
        binding.textEditPassword.addTextChangedListener(passwordWatcher)

    }

    private fun triggerSignInProcess() {
        if (emailValid && passwordValid) {
            val user = Params.SignIn(
                email = binding.textEditEmail.text.toString(),
                password = binding.textEditPassword.text.toString()
            )
            viewModel.signIn(user)
        } else {
            Toast.makeText(this, "Some fields are empty", Toast.LENGTH_SHORT).show()
        }

        observeSignIn()
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
                    binding.signInLayout.snack(message = result.exception.localizedMessage)
                }
            }
        })
    }


    private fun navigateToDashBoard() {
        startActivity(Intent(this, BottonNavigationActivity::class.java))
        //finish()
    }

    private fun navigateToSignUp() {
        //startActivity(Intent(this, SignUpActivity::class.java))
        startActivity(Intent(this, ExploreActivity::class.java))
    }

    private fun forgotPassword() {


        val mDialogView = LayoutInflater.from(this).inflate(R.layout.forgot_password_layout1, null)
        //AlertDialogBuilder

        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
        //show dialog
        val mAlertDialog = mBuilder.show()


        mDialogView.apply_btn.setOnClickListener {
            mAlertDialog.dismiss()

            if (mDialogView.recover_email_chBox.isChecked) {
                showEmailDialog()
            } else if (mDialogView.recover_phone_chBox.isChecked) {
                showPhoneDialog()
            } else {

            }


        }

    }

    private fun showEmailDialog() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.recover_email_layout, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
        //show dialog
        val mAlertDialog = mBuilder.show()
        val email = mDialogView.emailtext.text.toString()
    }

    private fun showPhoneDialog() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.recover_phone_layout, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
        //show dialog
        val mAlertDialog = mBuilder.show()
        val phone = mDialogView.phone.text.toString()
    }

}