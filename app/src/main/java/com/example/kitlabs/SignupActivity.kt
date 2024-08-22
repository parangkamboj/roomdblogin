package com.example.kitlabs

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.kitlabs.databinding.ActivitySignupBinding
import com.example.kitlabs.roomdatabase.AppDataBase
import com.example.kitlabs.roomdatabase.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var appDb: AppDataBase
    private fun validation(): Boolean {
        if (binding.etFirstName.text.toString().isEmpty()){
            Toast.makeText(this,"Please provide all input", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.etLastName.text.toString().isEmpty()){
            Toast.makeText(this,"Please provide all input", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.etEmail.text.toString().isEmpty()){
            Toast.makeText(this,"Please provide all input", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.etPassword.text.toString().isEmpty()){
            Toast.makeText(this,"Please provide all input", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)

        appDb = AppDataBase.getDatabase(this)

        binding.apply {
            btnSignup.setOnClickListener{
                if (validation()){
                    lifecycleScope.launch(Dispatchers.IO) {
                        appDb.Dao().insert(User(id = 0, firstName = binding.etFirstName.text.toString(), lastName = binding.etLastName.text.toString(), email = binding.etEmail.text.toString(), password = binding.etPassword.text.toString()))
                    }
                    Toast.makeText(this@SignupActivity,"Signup Successfully", Toast.LENGTH_SHORT).show()
                    binding.apply {
                        etFirstName.text.clear()
                        etLastName.text.clear()
                        etEmail.text.clear()
                        etPassword.text.clear()
                    }
                }
            }
            btnLogin.setOnClickListener {
                val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }

    }
}