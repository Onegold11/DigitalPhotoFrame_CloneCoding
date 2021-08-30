package com.clonecoding.digitalphotoframe

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val READ_PERMISSION: String = android.Manifest.permission.READ_EXTERNAL_STORAGE

    private val READ_STROAGE_CODE: Int = 100

    /**
     * Add photo button
     */
    private val addPhotoButton: Button by lazy {
        findViewById(R.id.addPhotoButton)
    }

    /**
     * Start photo frame mode button
     */
    private val startPhotoFrameModeButton: Button by lazy {
        findViewById(R.id.startPhotoFrameModeButton)
    }

    /**
     * Image view List
     */
    private val imageViewList: List<ImageView> by lazy {
        mutableListOf<ImageView>().apply {
            add(findViewById(R.id.photoImageView1))
            add(findViewById(R.id.photoImageView2))
            add(findViewById(R.id.photoImageView3))
            add(findViewById(R.id.photoImageView4))
            add(findViewById(R.id.photoImageView5))
            add(findViewById(R.id.photoImageView6))
        }
    }

    /**
     * Image uri list
     */
    private val imageUriList: MutableList<Uri> = mutableListOf()

    /**
     * Image storage content
     */
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) {

        if (it != null) {

            if (imageUriList.size == 6) {
                Toast.makeText(this, "이미 사진이 꽉 찼습니다.", Toast.LENGTH_SHORT).show()
                return@registerForActivityResult
            }

            this.imageUriList.add(it)
            this.imageViewList[imageUriList.size - 1].setImageURI(it)
        } else {
            Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * onCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.initAddPhotoButton()
        this.initStartPhotoFrameModeButton()
    }

    /**
     * Init start photo frame mode button
     */
    private fun initStartPhotoFrameModeButton() {

        this.startPhotoFrameModeButton.setOnClickListener {

            val intent = Intent(this, PhotoFrameActivity::class.java)
            this.imageUriList.forEachIndexed{ index, uri ->
                intent.putExtra("photo$index", uri.toString())
            }
            intent.putExtra("photoListSize", imageUriList.size)

            startActivity(intent)
        }
    }

    /**
     * Init add photo button
     */
    private fun initAddPhotoButton() {

        this.addPhotoButton.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    READ_PERMISSION
                ) == PackageManager.PERMISSION_GRANTED -> {

                    this.navigatePhotos()
                }
                shouldShowRequestPermissionRationale(
                    READ_PERMISSION
                ) -> {
                    this.showPermissionContextPopup()
                }
                else -> {
                    requestPermissions(
                        arrayOf(READ_PERMISSION),
                        READ_STROAGE_CODE)
                }
            }
        }
    }



    /**
     * Show permission dialog
     */
    private fun showPermissionContextPopup() {

        AlertDialog.Builder(this)
            .setTitle("권한이 필요합니다.")
            .setMessage("전자액자에 앱에서 사진을 불러오기 위해 권한이 필요합니다.")
            .setPositiveButton("동의하기") { _, _ ->
                requestPermissions(
                    arrayOf(READ_PERMISSION),
                    READ_STROAGE_CODE)
            }
            .setNegativeButton("취소하기") {_, _ -> }
            .create()
            .show()
    }

    /**
     * Permission result
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            READ_STROAGE_CODE -> {
                if (grantResults.isEmpty() == false && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    this.navigatePhotos()
                } else {
                    Toast.makeText(this, "권한을 거부하셨습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {

            }
        }
    }

    /**
     * Navigate photos
     */
    private fun navigatePhotos() {

        getContent.launch("image/*")
    }
}