package ru.nikfirs.mapkit.logo

import YandexMapKit.YMKLogo as NativeLogo

public actual class Logo internal constructor(private val nativeLogo: NativeLogo){

    public fun toNative(): NativeLogo {
        return nativeLogo
    }

    public actual fun setAlignment(alignment: LogoAlignment) {
        nativeLogo.setAlignmentWithAlignment(alignment.toNative())
    }

    public actual fun setPadding(logoPadding: LogoPadding) {
        nativeLogo.setPaddingWithPadding(logoPadding.toNative())
    }

}

public fun NativeLogo.toCommon(): Logo {
    return ru.nikfirs.mapkit.logo.Logo(this)
}