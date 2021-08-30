## Digital Photo Frame
저장된 사진을 사용해 디지털 액자로 보여주는 앱

## 사용한 요소
+ UI
    + Constraint Layout
    + View Animation
+ Android
    + Permission
    + SAF(Storage Access Framework
    
## Constraint Layout
app:layout_constraintDimensionRatio 속성을 사용하면 제약 조건이 정해지지 않은 가로 또는 세로의 길이를 정할 수 있다.

ex) app:layout_constraintDimensionRatio="h, 3:1", 높이를 가로/세로 비율이 3:1이 되게 만든다.

## View Animation
view의 animate 메소드를 사용하면 뷰에 애니메이션을 추가할 수 있다.
```kotlin
photoImageView.alpha = 0f
photoImageView.setImageURI(photoList[next])
photoImageView.animate()
    .alpha(1.0f)
    .setDuration(1000)
    .start()
```
Tip) 이미지 뷰 2개를 겹치게 배치하고, 배경 이미지 뷰는 이전 이미지, 전면 이미지 뷰는 다음 이미지로 애니메이션을 주면 자연스러운 페이드 인 애니메이션을 만들 수 있다.

## Permission
[앱 권한 요청](https://developer.android.com/training/permissions/requesting?hl=ko)

앱 권한 요청 순서
1. 권한이 이미 승인됐는지 검사
2. 승인되지 않은 경우
    1. 안내 문구를 나타내야 하는 경우 출력
    2. 안내 문구를 나타내고 사용자가 승인하면 권한 요청 팝업 창 출력
    3. 안내 문구가 필요없는 경우 권한 요청 팝업 창 출력
3. 권한이 승인 된 경우 동작 실행

```kotlin
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
```

권한 요청 결과 받기
```kotlin
override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
) { ... }
```
