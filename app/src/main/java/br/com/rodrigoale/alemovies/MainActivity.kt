package br.com.rodrigoale.alemovies

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val auth = FirebaseAuth.getInstance()
        val imgViewLogin = findViewById<ImageView>(R.id.imgLogin)
        val btnLogin: Button = findViewById(R.id.login)
        val btnForgotPassword: Button = findViewById(R.id.btnForgotPassword)
        val btnCreateUser: Button = findViewById(R.id.btnCreateUser)


        Glide.with(this)
            .load("https://kodi.tv/sites/default/files/styles/medium_crop/public/addon_assets/plugin.video.themoviedb.helper/icon/icon.png?itok=VgfwLzmy")
            .override(200,200)
            .into(imgViewLogin)

        btnLogin.setOnClickListener {

            val email: EditText = findViewById(R.id.email)
            val emailString = email.text.toString()
            val password: EditText = findViewById(R.id.password)
            val passwordString = password.text.toString()

            auth.signInWithEmailAndPassword(emailString, passwordString)
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {

                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {

                        val errorCode: String = task.exception.toString()

                        when {
                            errorCode.contains("FirebaseAuthInvalidUserException") -> {
                                Toast.makeText(
                                    baseContext,
                                    "Esse email não possui cadastro!",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                            errorCode.contains("FirebaseAuthInvalidCredentialsException") -> {
                                Toast.makeText(
                                    baseContext, "Email e/ou Senha Inválida. Tente novamente!", Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                            else -> {
                                Toast.makeText(
                                    baseContext,
                                    "Falha na autenticação. Por favor tente novamente!",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                    }
                }
        }
        btnForgotPassword.setOnClickListener {

            val intentForgotPassword = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intentForgotPassword)

        }

        btnCreateUser.setOnClickListener {

            val intentCreateUser = Intent(this, SignInActivity::class.java)
            startActivity(intentCreateUser)

        }
    }
}