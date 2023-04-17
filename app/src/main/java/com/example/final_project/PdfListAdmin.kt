package com.example.final_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.example.final_project.databinding.ActivityPdfListAdminBinding

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PdfListAdmin : AppCompatActivity() {
    private lateinit var binding: ActivityPdfListAdminBinding
    private companion object {
        const val  TAG = "PDF_LIST_ADMIN_TAG"
    }
    private lateinit var pdfArrayList: ArrayList<ModelPdf>
    private lateinit var adapterPdfAdmin: AdapterPdfAdmin
    private var categoryId = ""
    private var category = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfListAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        categoryId = intent.getStringExtra("categoryId")!!
        category = intent.getStringExtra("category")!!
        binding.backBtn.setOnClickListener {
            startActivity(Intent(this,AdminDashboard::class.java))
        }
        binding.subtitleTv.text = category
        loadPdfList()
        binding.searchBook.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                try {
                    adapterPdfAdmin.filter!!.filter(s)
                }
                catch (e: Exception){
                    Log.d(TAG, "onTextChanged: ${e.message}")
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun loadPdfList() {
        pdfArrayList = ArrayList()

        val ref = FirebaseDatabase.getInstance().getReference("Books")
        ref.orderByChild("categoryId").equalTo(categoryId)
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    pdfArrayList.clear()
                    for (ds in snapshot.children){
                        val model = ds.getValue(ModelPdf::class.java)
                        if (model != null) {
                            pdfArrayList.add(model!!)
                            Log.d(TAG, "onDataChange: ${model.title} ${model.categoryId}")
                        }
                    }

                    adapterPdfAdmin = AdapterPdfAdmin(this@PdfListAdmin, pdfArrayList)
                    binding.booksRv.adapter = adapterPdfAdmin
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }

}