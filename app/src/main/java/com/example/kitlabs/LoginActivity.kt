package com.example.kitlabs

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.kitlabs.databinding.ActivityLoginBinding
import com.example.kitlabs.databinding.ActivitySignupBinding
import com.example.kitlabs.roomdatabase.AppDataBase
import com.example.kitlabs.roomdatabase.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var appDb: AppDataBase
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        supportActionBar?.hide()

        appDb = AppDataBase.getDatabase(this)

        binding.apply {
            btnLogin.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    val getUser = checkUserCred(etEmail.text.toString(),etPassword.text.toString())
                    if(getUser){
                        val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                        startActivity(intent)
                    }
                }

            }
        }

    }
    suspend fun checkUserCred(email: String, password: String): Boolean {
        val user = appDb.Dao().getUserByEmailAndPassword(email, password)
        return user != null
    }
}