# VerticalStackAnimationView
자연스럽게 쌓이는 뷰 애니메이션 ...

## Sample
```code
    <com.hydok.stackanimationview.VerticalStackAnimationView
        android:id="@+id/verticalStack"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```


```code
        view.launchAnimation {
            it
                .inView(textView("111"))
                .inView(textView("222"))
                .clearView()
                .inView(textView("333"))
                .inView(textView("444"),topMarginDp = 30)
                .inView(textView("555"))
        }
```


## Customize
You can change your `StepProgressBar` attribute programmatically
- `setDuration()`
- `launchAnimation()`
- `inView()`
- `clearView()`

