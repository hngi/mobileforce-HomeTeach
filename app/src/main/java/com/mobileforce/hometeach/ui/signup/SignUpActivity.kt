package com.mobileforce.hometeach.ui.signup

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.ui.signin.LoginActivity
import com.mobileforce.hometeach.utils.AppConstants.USER_TUTOR
import com.mobileforce.hometeach.utils.PreferenceHelper
import com.mobileforce.hometeach.utils.Result
import com.mobileforce.hometeach.utils.snack
import com.mobileforce.hometeach.utils.toast
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.regex.Pattern

/**
 * Created by Peculiar C. Umeh on June 2020.
 */


class SignUpActivity : AppCompatActivity() {

    private lateinit var nameWatcher: TextWatcher
    private lateinit var emailWatcher: TextWatcher
    private lateinit var passwordWatcher: TextWatcher
    private lateinit var phoneNumberWatcher: TextWatcher

    private var nameValid = false
    private var emailValid = false
    private var passwordValid = false
    private var phoneNumberValid = false

    private val prefHelper: PreferenceHelper by inject()
    private val viewModel: SignUpViewModel by viewModel()

    private val pd by lazy {
        ProgressDialog(this).apply {
            setMessage("Registering user...")
            setCancelable(false)
        }
    }

    private fun navigateTOSignIn() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        text_log_in.setOnClickListener {
            navigateTOSignIn()
        }
        // replace with the ids in your XML
        nameWatcher = object : TextWatcher {
            override fun afterTextChanged(input: Editable?) {
                if (input!!.length > 6 || input.length > 40) {
                    nameValid = true
                    textInput_fullname.error = null
                } else {
                    textInput_fullname.isHelperTextEnabled = true
                    textInput_fullname.error = "Name is too short"
                    nameValid = false

                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        }

        emailWatcher = object : TextWatcher {
            override fun afterTextChanged(input: Editable?) {
                if (Patterns.EMAIL_ADDRESS.matcher(input)
                        .matches() && input!!.indexOf("@") < input!!.length
                ) {
                    textInput_email.error = null
                    emailValid = true
                } else {
                    textInput_email.isHelperTextEnabled = true
                    textInput_email.error = "Invalid Email address"
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
                    textInput_password.error = null
                    passwordValid = true
                } else {

                    textInput_password.isHelperTextEnabled = true
                    textInput_password.error =
                        "Hint: Password should not be less than 8 with at least 1 special character"
                    passwordValid = false
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        }
        phoneNumberWatcher = object : TextWatcher {
            override fun afterTextChanged(input: Editable?) {
                if (input!!.length < 14) {
                    phoneNumberValid = true
                    textInputLayout.error = null
                } else {
                    textInputLayout.isHelperTextEnabled = true
                    textInputLayout.error = "Invalid Phone Number"
                    phoneNumberValid = false
                }

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        }

        // replace with the ids in your XML
        full_name.addTextChangedListener(nameWatcher)
        pass_word.addTextChangedListener(passwordWatcher)
        email.addTextChangedListener(emailWatcher)
        phone_number.addTextChangedListener(phoneNumberWatcher)


        btn_sign_up.setOnClickListener {
            if (nameValid && emailValid && passwordValid && phoneNumberValid && checkBox.isChecked) {

                prefHelper.userType?.let { userType ->

                    //build sign up params
                    val userData = Params.SignUp(
                        email = email.text.toString(),
                        password = pass_word.text.toString(),
                        full_name = full_name.text.toString(),
                        phone_number = phone_number.text.toString(),
                        is_tutor = userType == USER_TUTOR
                    )
                    viewModel.signUp(userData)

                } ?: kotlin.run {
                    toast("Please select an account mode from previous screen to continue.")
                }

            } else {
                Toast.makeText(this, "Some fields are empty", Toast.LENGTH_SHORT).show()
            }
        }

        observeSignUp()
    }

    private fun observeSignUp() {

        viewModel.signUp.observe(this, Observer { result ->

            when (result) {
                Result.Loading -> {
                    pd.show()
                }
                is Result.Success -> {
                    pd.hide()

                    signUpLayout
                        .snack(
                            message = "Registration Successful, verify account to login",
                            actionText = "LOGIN",
                            actionCallBack = {
                                navigateTOSignIn()
                            }, length = Snackbar.LENGTH_INDEFINITE
                        )
                }

                is Result.Error -> {
                    pd.hide()
                    signUpLayout.snack(message = result.exception.localizedMessage)
                }
            }

        })
    }

}