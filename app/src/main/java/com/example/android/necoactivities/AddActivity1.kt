package com.example.android.necoactivities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.android.necoactivities.databinding.ActivityAdd1Binding

class AddActivity1 : AppCompatActivity() {
    lateinit var bindingClass: ActivityAdd1Binding
    private lateinit var intentAvatar: Uri
    private var checkImage = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityAdd1Binding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        bindingClass.addTitle.text = intent.getStringExtra(Constance.KEYSIGNTITLE.toString())
        if (bindingClass.addTitle.text == getText(R.string.titleActivityAdd1SignIn)) {
            bindingClass.send.text = getText(R.string.logIn)
            bindingClass.imageAvatar.visibility = View.GONE
            bindingClass.selectImageButton.visibility = View.GONE
        } else {
            bindingClass.send.text = getText(R.string.checkIn)
        }
    }

    fun onClickGoBack(view: View) {
        setResult(RESULT_CANCELED)
        finish()
    }

    fun onClickSendAnswer(view: View) {
        val intent = Intent()
        intent.putExtra(Constance.SENDNAME.toString(), bindingClass.editTextName.text.toString())
        intent.putExtra(
            Constance.SENDPASSWORD.toString(),
            bindingClass.editTextPassword.text.toString()
        )
        if (bindingClass.addTitle.text == getText(R.string.titleActivityAdd1SignUp)) {
            intent.putExtra(Constance.SENDCHECK.toString(), checkImage)
            if (checkImage) {
                intent.putExtra(Constance.SENDAVATAR.toString(), intentAvatar.toString())
            } else {
                intent.putExtra(Constance.SENDAVATAR.toString(), R.drawable.nyako.toString())
            }

        }
        setResult(RESULT_OK, intent)
        finish()
    }

    fun onClickGoGallery(view: View) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, Constance.REQUESTCODEGALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == Constance.REQUESTCODEGALLERY) {
            bindingClass.imageAvatar.setImageURI(data?.data)
            checkImage = true
            intentAvatar = data?.data!!
        }
    }
}