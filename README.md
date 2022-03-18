# Android RouletteTextView
© 2022 Dark Tornado, All rights reserved.

* Text rotates like a slot machine.
* 슬롯머신 비슷하게 위에서 아래로 돌아가는 텍스트.

## Demo / 예시
* Click the image to move.
* 아래 이미지를 누르면 영상으로 이동합니다.

[![Demo video on YouTube](https://img.youtube.com/vi/piK7Mh1GHDY/0.jpg)](https://www.youtube.com/watch?v=piK7Mh1GHDY)
```java
String[] foodList = { "Cake", "Ice Scream", "Green Tea", "Coffee", "Crepe" };
AlertDialog.Builder dialog = new AlertDialog.Builder(this);
dialog.setTitle("Roulette Sample");
LinearLayout layout = new LinearLayout(this);
layout.setOrientation(1);
final RouletteTextView roulette = new RouletteTextView(this);
roulette.setTexts(foodList);
roulette.setTextSize(8);
roulette.setMovingData(10, 10, 90);
roulette.setSize(-1, dip2px(50));
roulette.create();
layout.addView(roulette);
int pad = dip2px(5);
roulette.setPadding(pad, pad, pad, pad);
dialog.setView(layout);
dialog.setNegativeButton("Close", null);
dialog.show();
new Handler().postDelayed(() - > roulette.stop(), 5 * 1000);
.
.
.
public int dip2px(int dips) {
    return (int) Math.ceil(dips * this.getResources().getDisplayMetrics().density);
}
```

## How to Use? / 사용법
* Copy and Paste `RouletteTextView.java` file to your Android App Project appropriately.
* Change the package name appropriately.
* `RouletteTextView.java` 파일을 본인의 앱 프로젝트에 적당히 넣고, 패키지명도 적당히 바꾸시거나 하시면 됩니다.

## Docs..?
* Coming soon

## License / 라이선스
* [MIT License](LICENSE)
