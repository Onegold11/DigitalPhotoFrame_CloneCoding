package com.clonecoding.digitalphotoframe

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val READ_PERMISSION: String = android.Manifest.permission.READ_EXTERNAL_STORAGE

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
                    //TODO 사진 선택
                }
                shouldShowRequestPermissionRationale(
                    READ_PERMISSION
                ) -> {
                    this.showPermissionContextPopup()
                }
                else -> {
                    requestPermissions(
                        arrayOf(READ_PERMISSION),
                        100)
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
                    100)
            }
            .setNegativeButton("취소하기") {_, _ -> }
            .create()
            .show()
    }
}