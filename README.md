# VerticalStackAnimationView
자연스럽게 쌓이는 뷰 애니메이션 ...

![Screen_Recording_20231128_141120_StackAnimationView_1](https://github.com/hydok/StackAnimationView/assets/26853549/1223550d-6973-44d5-b41a-cebe43da1999)


## Sample
```code
    <com.hydok.stackanimationview.VerticalStackAnimationView
        android:id="@+id/verticalStackView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```


```code
        verticalStackView.launchAnimation {
            it
                .inView(textView("111"))
                .inView(textView("222"))
                .clearView()
                .inView(textView("333"))
                .inView(textView("444"),topMarginDp = 30)
                .inView(textView("555"))
        }.doOnEnd {
            Toast.makeText(this, "end...", Toast.LENGTH_SHORT).show()
        }
```


## Customize
attribute programmatically
- `setDuration()`
- `launchAnimation()`
- `inView()`
- `clearView()`
- `doOnEnd()`

