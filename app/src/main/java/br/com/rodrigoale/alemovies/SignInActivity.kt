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


class SignInActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        val auth = FirebaseAuth.getInstance()
        val imgViewSignIn = findViewById<ImageView>(R.id.signIn)
        val btnCreateUser: Button = findViewById(R.id.createUser)

        Glide.with(this)
            .load("https://www.agecefrio.com.br/novo-site/app/icones/associese-azul.png")
            .override(200,200)
            .into(imgViewSignIn)

        btnCreateUser.setOnClickListener {

            val email: EditText = findViewById(R.id.email)
            val emailText = email.text.toString()
            val password: EditText = findViewById(R.id.password)
            val passwordString = password.text.toString()

            auth.createUserWithEmailAndPassword(emailText, passwordString)
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {

                        Toast.makeText(
                            baseContext,
                            "Cadastro realizado com sucesso",
                            Toast.LENGTH_SHORT
                        ).show()

                        val mainIntent = Intent(this, MainActivity::class.java)
                        startActivity(mainIntent)
                        finish()

                    } else {

                        val exception: String = task.exception.toString()

                        when {
                            exception.contains("FirebaseAuthWeakPasswordException") -> {
                                Toast.makeText(
                                    baseContext,
                                    "A senha precisa ter no minimo 6 caracteres",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                            exception.contains("FirebaseAuthInvalidCredentialsException") -> {
                                Toast.makeText(
                                    baseContext, "Email Inválido", Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                            exception.contains("FirebaseAuthUserCollisionException") -> {
                                Toast.makeText(
                                    baseContext, "Email ja cadastrado", Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                            else -> {
                                Toast.makeText(
                                    baseContext, "Falha no cadastro. Por favor tente novamente!", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }
        }
    }
}

