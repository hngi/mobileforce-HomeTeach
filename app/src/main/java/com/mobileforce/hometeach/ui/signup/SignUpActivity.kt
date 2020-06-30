package com.mobileforce.hometeach.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.ui.LoginActivity
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {

    private lateinit var nameWatcher: TextWatcher
    private lateinit var emailWatcher: TextWatcher
    private lateinit var passwordWatcher: TextWatcher
    private lateinit var phoneNumberWatcher: TextWatcher

    private var nameValid = false
    private var emailValid = false
    private var passwordValid = false
    private var phoneNumberValid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        text_log_in.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
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
                        "Hint: Password should contain at least 1 Special Character and should not be less than 6"
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
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                Toast.makeText(this, "Some fields are empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

}