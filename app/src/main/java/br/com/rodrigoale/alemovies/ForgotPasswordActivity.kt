package br.com.rodrigoale.alemovies

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth



class ForgotPasswordActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val imgViewForgotPassword = findViewById<ImageView>(R.id.forgotPassword)
        val auth = FirebaseAuth.getInstance()
        val email: EditText = findViewById(R.id.email)
        val btnChangePassword: Button = findViewById(R.id.changePassword)

        Glide.with(this)
            .load("https://ps.w.org/frontend-reset-password/assets/icon-256x256.png")
            .override(200,200)
            .into(imgViewForgotPassword)

        btnChangePassword.setOnClickListener {

            val emailString: String = email.text.toString()

            auth.fetchSignInMethodsForEmail(emailString)
                .addOnSuccessListener { result ->

                    val signInMethods = result.signInMethods

                    if (signInMethods!!.contains(EmailAuthProvider.EMAIL_PASSWORD_SIGN_IN_METHOD)) {
                        auth.sendPasswordResetEmail(emailString)
                        Toast.makeText(baseContext, "Email enviado para: $emailString", Toast.LENGTH_SHORT)
                            .show()
                        val intentMain = Intent(this, MainActivity::class.java)
                        startActivity(intentMain)
                        finish()

                    } else {
                        Toast.makeText(
                            baseContext,
                            "Esse email não possui cadastro!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .addOnFailureListener {

                    Toast.makeText(
                        baseContext, "Email Inválido", Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }
}
