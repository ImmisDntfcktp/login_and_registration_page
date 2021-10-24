package com.example.android.necoactivities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.android.necoactivities.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityMainBinding
    private lateinit var username: String
    private lateinit var password: String
    private lateinit var avatar: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        username = getString(R.string.empty)
        password = getString(R.string.empty)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        bindingClass.startButtonUp.visibility = View.GONE
        bindingClass.descriptionTextSignUp.visibility = View.GONE
        bindingClass.startButtonIn.text = getString(R.string.logOut)
        bindingClass.nameMain.visibility = View.VISIBLE
        bindingClass.avatarMain.visibility = View.VISIBLE
        if (requestCode == Constance.REQUESTCODEIN && resultCode == RESULT_OK && data != null) {
            val usernameSignIn = data.getStringExtra(Constance.SENDNAME.toString())
            val passwordSignIn = data.getStringExtra(Constance.SENDPASSWORD.toString())
            checkUser(usernameSignIn, getText(Constance.USER1), passwordSignIn, getText(Constance.USER1PASSWORD))
            checkUser(usernameSignIn, getText(Constance.USER2), passwordSignIn, getText(Constance.USER2PASSWORD))
            checkUser(usernameSignIn, getText(Constance.USER3), passwordSignIn, getText(Constance.USER3PASSWORD))
            if (bindingClass.nameMain.text != usernameSignIn) errorPass()

        } else if (requestCode == Constance.REQUESTCODEUP && resultCode == RESULT_OK && data != null) {
            username = data.getStringExtra(Constance.SENDNAME.toString())!!
            password = data.getStringExtra(Constance.SENDPASSWORD.toString())!!
            val checkImage = data.getBooleanExtra(Constance.SENDCHECK.toString(), false)
            avatar = data.getStringExtra(Constance.SENDAVATAR.toString())!!
            bindingClass.nameMain.text = username
            if (checkImage) {
                val formatToUri = Uri.parse(avatar)
                bindingClass.avatarMain.setImageURI(formatToUri)
            } else {
                bindingClass.avatarMain.setImageResource(avatar.toInt())
            }

        } else if (resultCode == RESULT_CANCELED){
            goToFirstPage()
        } else errorPass()
    }

    fun onClickGoAddActivity1SignIn(view: View) {
        if (bindingClass.startButtonIn.text == getString(R.string.startButtonIn)) {
            val intentAddActivity1 = Intent(this, AddActivity1::class.java)
            intentAddActivity1.putExtra(
                Constance.KEYSIGNTITLE.toString(),
                getText(R.string.titleActivityAdd1SignIn)
            )
            startActivityForResult(intentAddActivity1, Constance.REQUESTCODEIN)
        } else {
            goToFirstPage()
        }
    }

    fun onClickGoAddActivity1SignUp(view: View) {
        val intentAddActivity1 = Intent(this, AddActivity1::class.java)
        intentAddActivity1.putExtra(
            Constance.KEYSIGNTITLE.toString(),
            getText(R.string.titleActivityAdd1SignUp)
        )
        startActivityForResult(intentAddActivity1, Constance.REQUESTCODEUP)
    }

    private fun errorPass() {
        bindingClass.nameMain.text = getText(R.string.inputError)
        bindingClass.avatarMain.setImageResource(R.drawable.error)
        bindingClass.startButtonIn.text = getString(R.string.startButtonIn)
        bindingClass.startButtonUp.visibility = View.VISIBLE
        bindingClass.descriptionTextSignUp.visibility = View.VISIBLE
    }

    private fun checkUser(usernameSignIn: String?, username: CharSequence, passwordSignIn: String?, password: CharSequence) {
        if (usernameSignIn == username && passwordSignIn == password) {
            bindingClass.nameMain.text = usernameSignIn
            bindingClass.avatarMain.setImageResource(R.drawable.avatar_def)
        }
    }
    private fun goToFirstPage (){
        bindingClass.nameMain.visibility = View.INVISIBLE
        bindingClass.avatarMain.visibility = View.GONE
        bindingClass.startButtonIn.text = getString(R.string.startButtonIn)
        bindingClass.descriptionTextSignUp.visibility = View.VISIBLE
        bindingClass.startButtonUp.visibility = View.VISIBLE
    }
}