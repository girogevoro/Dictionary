package com.girogevoro.stopwatch.model

interface TimestampProvider {
    fun getMilliseconds(): Long
}