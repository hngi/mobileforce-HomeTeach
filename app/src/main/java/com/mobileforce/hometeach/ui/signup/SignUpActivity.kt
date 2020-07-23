package com.mobileforce.hometeach.ui.signup

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.ui.signin.LoginActivity
import com.mobileforce.hometeach.ui.signup.SignUpViewModel.ErrorField
import com.mobileforce.hometeach.utils.AppConstants.USER_TUTOR
import com.mobileforce.hometeach.utils.PreferenceHelper
import com.mobileforce.hometeach.utils.Result
import com.mobileforce.hometeach.utils.snack
import com.mobileforce.hometeach.utils.toast
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

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

        nameWatcher = object : TextWatcher {
            override fun afterTextChanged(input: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(input: CharSequence?, p1: Int, p2: Int, p3: Int) {
                input?.let {

                    nameValid = try {
                        it.toString().split(" ")
                        true
                    } catch (e: Exception) {
                        false
                    }

                }
            }
        }

        emailWatcher = object : TextWatcher {
            override fun afterTextChanged(input: Editable?) {
                emailValid = Patterns.EMAIL_ADDRESS.matcher(input)
                    .matches() && input!!.indexOf("@") < input!!.length
                if (emailValid) textInput_email.error = null

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        }

        passwordWatcher = object : TextWatcher {
            override fun afterTextChanged(input: Editable?) {
                input?.let {
                    if (input.length < 7) {
                        textInput_password.error = null
                        passwordValid = true
                    } else {
                        textInput_password.isHelperTextEnabled = true
                        textInput_password.error =
                            "Password should not be less than 8"
                        passwordValid = false
                    }
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        }
        phoneNumberWatcher = object : TextWatcher {
            override fun afterTextChanged(input: Editable?) {
                phoneNumberValid = input!!.length > 10 || input.length < 15

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        }

        et_full_name.addTextChangedListener(nameWatcher)
        et_password.addTextChangedListener(passwordWatcher)
        et_email.addTextChangedListener(emailWatcher)
        et_phone_number.addTextChangedListener(phoneNumberWatcher)


        btn_sign_up.setOnClickListener {
            if (nameValid && emailValid && passwordValid && phoneNumberValid && checkBox.isChecked) {
                prefHelper.userType?.let { userType ->
                    //build sign up params
                    val userData = Params.SignUp(
                        email = et_email.text.toString(),
                        password = et_password.text.toString(),
                        full_name = et_full_name.text.toString(),
                        phone_number = et_phone_number.text.toString(),
                        is_tutor = userType == USER_TUTOR
                    )
                    viewModel.signUp(userData)
                } ?: kotlin.run {
                    toast("Please select an account mode from previous screen to continue.")
                }
            } else if (!nameValid) {
                textInput_full_name.isHelperTextEnabled = true
                textInput_full_name.error = "Full name is required"
            } else if (!emailValid) {
                textInput_email.isHelperTextEnabled = true
                textInput_email.error = "Input a valid email"
            } else if (!passwordValid) {
                textInput_password.isHelperTextEnabled = true
                textInput_password.error = "Input a valid password"
            } else if (!phoneNumberValid) {
                textInput_phone_number.isHelperTextEnabled = true
                textInput_phone_number.error = "Input a valid phone number"
            } else if (!checkBox.isChecked) {
                toast("Agree to terms and condition to continue!")
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


        viewModel.credentialsError.observe(this, Observer {

            if (pd.isShowing) pd.dismiss()
            when (it.error) {
                ErrorField.EMAIL -> {
                    textInput_email.error = it.message
                }
                ErrorField.PHONE_NUMBER -> {
                    textInput_phone_number.error = it.message
                }
            }
        })
    }

}