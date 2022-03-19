package com.darktornado.library;

/*
MIT License

Copyright (c) 2022 Dark Tornado

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.webkit.WebView;
import android.widget.LinearLayout;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RouletteTextView extends WebView {

    private String textColor = "#000000", backgroundColor = "#FFFFFF", gravity = "center";
    private String[] data;
    private int delay, distance, range, size;

    public RouletteTextView(Context context) {
        super(context);
        super.setBackgroundColor(Color.TRANSPARENT);
        getSettings().setJavaScriptEnabled(true);
        setVerticalScrollBarEnabled(false);
    }

    public void setTextColor(int color) {
        textColor = "#" + color2code(color);
    }

    @Override
    public void setBackgroundColor(int color) {
        backgroundColor = "#" + color2code(color);
    }

    public void setTexts(String[] data) {
        List<String> _data = Arrays.asList(data);
        Collections.shuffle(_data);
        this.data = _data.toArray(new String[0]);
    }

    public void setTexts(List<String> data) {
        Collections.shuffle(data);
        this.data = data.toArray(new String[0]);
    }

    public void setTextSize(int size) {
        this.size = size;
    }

    public void setGravity(int gravity) {
        if (gravity == Gravity.LEFT || gravity == Gravity.START) this.gravity = "left";
        else if (gravity == Gravity.CENTER) this.gravity = "center";
        else if (gravity == Gravity.RIGHT || gravity == Gravity.END) this.gravity = "right";
        else throw new RuntimeException("Invalid Gravity: " + gravity);
    }

    public void setMovingData(int delay, int distance, int range) {
        if (range % distance != 0)
            throw new RuntimeException("Range must be a multiple of distance.");
        this.delay = delay;
        this.distance = distance;
        this.range = range;
    }

    public void setSize(int width, int height) {
        setLayoutParams(new LinearLayout.LayoutParams(width, height));
    }

    public void create() {
        StringBuilder arr = new StringBuilder("'" + data[0].replace("'", "\\'") + "'");
        for (int n = 1; n < data.length; n++) {
            arr.append(",'").append(data[n].replace("'", "\\'")).append("'");
        }
        String src = "<html>\n" +
                "<style>\n" +
                "body {\n" +
                "padding: 0px;\n" +
                "margin: 0px;\n" +
                "}\n" +
                "</style>\n" +
                "<body bgcolor=" + backgroundColor + ">\n" +
                "<font color=" + textColor + " size=" + size + "px><p class='target' align=" + gravity + "></p></font>\n" +
                "<script>\n" +
                "var arr = [" + arr + "]\n" +
                "var target = document.querySelector(\".target\");\n" +
                "var i = 1;\n" +
                "target.innerHTML = arr[0]\n" +
                "var delay = " + delay + ", distance = " + distance + ", range = " + range + ";\n" +
                "var n = -range, flag = false;\n" +
                "var id = setInterval(function() {\n" +
                "target.style.transform = \"translateY(\" + n + \"px)\";\n" +
                "n += distance;\n" +
                "if (n >= range) {\n" +
                "target.innerHTML = arr[i]\n" +
                "i++\n" +
                "if (i >= arr.length) i = 0;\n" +
                "n = -range;\n" +
                "}\n" +
                "if (n == 0 && flag) {\n" +
                "clearInterval(id);\n" +
                "target.style.transform = \"translateY(0px)\";\n" +
                "}\n" +
                "}, delay);\n" +
                "\n" +
                "function stop() {\n" +
                "flag = true;\n" +
                "}\n" +
                "</script>\n" +
                "</body>\n" +
                "</html>";
        if (Build.VERSION.SDK_INT > 23) {
            loadDataWithBaseURL(null, src, "text/html; charset=UTF-8", null, null);
        } else {
            loadData(src, "text/html; charset=UTF-8", null);
        }
    }

    public void stop() {
        loadUrl("javascript:stop();");
    }

    private String color2code(int color) {
        return Integer.toHexString(Color.red(color)) + Integer.toHexString(Color.green(color)) + Integer.toHexString(Color.blue(color));
    }

}
