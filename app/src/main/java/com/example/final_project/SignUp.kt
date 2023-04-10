package com.example.final_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.ProgressDialog
import android.content.Intent
import android.util.Patterns
import android.widget.Toast
import com.example.final_project.databinding.ActivitySignUpBinding
import com.example.final_project.databinding.ActivityWelcomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progessDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progessDialog = ProgressDialog(this)
        progessDialog.setTitle("Please Wait")
        progessDialog.setCanceledOnTouchOutside(false)

        binding.backToLogin.setOnClickListener{
            onBackPressed()
        }

        binding.signUp.setOnClickListener {
            validateData()
        }
    }

    private var name = ""
    private var email = ""
    private var password = ""

    private fun validateData() {
        name = binding.Username.text.toString().trim()
        email = binding.Email.text.toString().trim()
        password = binding.Password.text.toString().trim()

        if(name.isEmpty()){
            Toast.makeText(this, "Username can't be blanked",Toast.LENGTH_SHORT).show()
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Invalid Email",Toast.LENGTH_SHORT).show()

        }
        else if (password.isEmpty()){
            Toast.makeText(this, "Password can't be blanked",Toast.LENGTH_SHORT).show()

        }
        else {
            createUserAccount()
        }
    }

    private fun createUserAccount() {
        progessDialog.setMessage("Creating Account...")
        progessDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                updateUserInfo()
            }
            .addOnFailureListener { e->
                progessDialog.dismiss()
                Toast.makeText(this, "${e.message }",Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateUserInfo() {
        progessDialog.setMessage("Saving User Info...")

        val timestamp = System.currentTimeMillis()

        var uid = firebaseAuth.uid

        val hashMap: HashMap<String, Any?> = HashMap()
        hashMap["uid"] = uid
        hashMap["email"] = email
        hashMap["password"] = password
        hashMap["name"] = name
        hashMap["profileImage"] = ""
        hashMap["userType"] = "user"
        hashMap["timestamp"] = timestamp

        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(uid!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                progessDialog.dismiss()
                Toast.makeText(this,"User Created",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, UserDashboard::class.java))
                finish()

            }
            .addOnFailureListener {
                progessDialog.dismiss()
                Toast.makeText(this,"Failed to saving user info",Toast.LENGTH_SHORT).show()
            }


    }
}