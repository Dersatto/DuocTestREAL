package com.dersad.duoctest.ui

import java.text.DecimalFormat




//da valor signo peso de manera global en la app(SE PUEDE PRESCINDIR)
fun formatClp(value: Number): String {
    val format = DecimalFormat("$#,##0")
    return format.format(value)
}
