package br.com.rodrigoale.alemovies

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.room.Room
import br.com.rodrigoale.alemovies.db.AppDb
import br.com.rodrigoale.alemovies.fragment.MoviesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        replaceFragment(MoviesFragment())
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigation.setOnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.menu_filmes -> {
                    val fragmentManager = supportFragmentManager
                    val transaction = fragmentManager.beginTransaction()
                    transaction.replace(R.id.host, MoviesFragment())
                    transaction.commit()
                }
                R.id.menu_favoritos -> {
                    val db = Room.databaseBuilder(applicationContext, AppDb::class.java, "MovieDB")
                        .build()
                    val thread = Thread {
                        val user = FirebaseAuth.getInstance().currentUser?.email
                        db.movieDao().getMovie(user!!).forEach()
                        {
                            println("---------------------------------------------------------------------------")
                            Log.i("Filme", "Id:   ${it.id}")
                            Log.i("Filme", "Titulo:   ${it.title}")
                            Log.i("Filme", "Sinopse: ${it.overview}")
                            Log.i("Filme", "Data De Lançamento: ${it.releaseDate}")
                            Log.i("Filme", "Média De Notas: ${it.rating} estrelas")
                            Log.i("Filme", "Usuário:   ${it.user}")

                        }

                    }
                    thread.start()
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Tela de Favoritos Não Implementada, Será Impresso no Console",
                        Toast.LENGTH_LONG).show()

                }
                R.id.menu_logout -> {
                    val intent = Intent(this, MainActivity::class.java)
                    FirebaseAuth.getInstance().signOut()
                    startActivity(intent)
                    finish()

                }
            }

            true
        }
    }

    private fun AppCompatActivity.replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.host, fragment)
        transaction.commit()
    }

}





