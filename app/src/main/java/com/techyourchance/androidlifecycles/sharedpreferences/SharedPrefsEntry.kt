package com.techyourchance.androidlifecycles.sharedpreferences

interface SharedPrefsEntry<T> {
    fun set(value: T)
    fun get(): T
}