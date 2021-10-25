package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var rvNotes: RecyclerView
    lateinit var notesList: ArrayList <String>
    lateinit var edtNote: EditText
    lateinit var dbh : DBhlpr

    var note = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbh = DBhlpr(applicationContext)

        edtNote=findViewById(R.id.edtNote)

        rvNotes=findViewById(R.id.rvNotes)
        notesList = arrayListOf()
        notesList = dbh.retriveAll()
        rvNotes.adapter = NotesAdapter(notesList)
        rvNotes.layoutManager = LinearLayoutManager(this)
        rvNotes.adapter?.notifyDataSetChanged()
        rvNotes.scrollToPosition(notesList.size-1)



        var subBtn=findViewById(R.id.submBtn) as Button
        subBtn.setOnClickListener {
           addNote()
        }
    }

    fun addNote(){
        try{
            note=edtNote.text.toString()
            var status = dbh.saveData(note)
            Toast.makeText(applicationContext, "Note submitted successfully! "+status, Toast.LENGTH_LONG).show()
            notesList.add(note)
            rvNotes.adapter?.notifyDataSetChanged()
            edtNote.text.clear()
            edtNote.clearFocus()
            rvNotes.scrollToPosition(notesList.size-1)

        }catch (e:Exception){
            Toast.makeText(applicationContext, "There is no notes, in DB", Toast.LENGTH_SHORT).show()
        }
    }
}
