package com.girogevoro.stopwatch.model

class TimestampProviderImpl :TimestampProvider{
    override fun getMilliseconds(): Long {
        return System.currentTimeMillis()
    }
}