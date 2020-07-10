package com.mobileforce.hometeach.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.mobileforce.hometeach.R
import com.mobileforce.hometeach.databinding.ActivityLoginBinding
import com.mobileforce.hometeach.ui.signup.SignUpActivity
import kotlinx.android.synthetic.main.forgot_password_layout1.view.*
import kotlinx.android.synthetic.main.forgot_password_layout1.view.apply_btn
import kotlinx.android.synthetic.main.recover_email_layout.view.*
import kotlinx.android.synthetic.main.recover_phone_layout.view.*
import kotlinx.android.synthetic.main.activity_login.*
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signIn.setOnClickListener {
            navigateToDashBoard()
        }
        text_register_now.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
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

    }

    private fun navigateToDashBoard() {
        startActivity(Intent(this, BottonNavigationActivity::class.java))
    }

    private fun navigateToSignUp() {
        startActivity(Intent(this, SignUpActivity::class.java))
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