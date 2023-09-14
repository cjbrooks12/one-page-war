package com.copperleaf.onepagewar

import com.russhwolf.settings.Settings

internal class NamedSettingsWrapper(
    private val name: String,
    private val delegate: Settings
) : Settings {
    private val prefix = "$name/"

    override val keys: Set<String>
        get() = delegate.keys.filter { it.startsWith(prefix) }.toSet()

    override val size: Int
        get() = delegate.keys.filter { it.startsWith(prefix) }.size

    override fun clear() {
        TODO("Not yet implemented")
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return delegate.getBoolean("$prefix$key", defaultValue)
    }

    override fun getBooleanOrNull(key: String): Boolean? {
        return delegate.getBooleanOrNull("$prefix$key")
    }

    override fun getDouble(key: String, defaultValue: Double): Double {
        return delegate.getDouble("$prefix$key", defaultValue)
    }

    override fun getDoubleOrNull(key: String): Double? {
        return delegate.getDoubleOrNull("$prefix$key")
    }

    override fun getFloat(key: String, defaultValue: Float): Float {
        return delegate.getFloat("$prefix$key", defaultValue)
    }

    override fun getFloatOrNull(key: String): Float? {
        return delegate.getFloatOrNull("$prefix$key")
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return delegate.getInt("$prefix$key", defaultValue)
    }

    override fun getIntOrNull(key: String): Int? {
        return delegate.getIntOrNull("$prefix$key")
    }

    override fun getLong(key: String, defaultValue: Long): Long {
        return delegate.getLong("$prefix$key", defaultValue)
    }

    override fun getLongOrNull(key: String): Long? {
        return delegate.getLongOrNull("$prefix$key")
    }

    override fun getString(key: String, defaultValue: String): String {
        return delegate.getString("$prefix$key", defaultValue)
    }

    override fun getStringOrNull(key: String): String? {
        return delegate.getStringOrNull("$prefix$key")
    }

    override fun hasKey(key: String): Boolean {
        return keys.contains("$prefix$key")
    }

    override fun putBoolean(key: String, value: Boolean) {
        delegate.putBoolean("$prefix$key", value)
    }

    override fun putDouble(key: String, value: Double) {
        delegate.putDouble("$prefix$key", value)
    }

    override fun putFloat(key: String, value: Float) {
        delegate.putFloat("$prefix$key", value)
    }

    override fun putInt(key: String, value: Int) {
        delegate.putInt("$prefix$key", value)
    }

    override fun putLong(key: String, value: Long) {
        delegate.putLong("$prefix$key", value)
    }

    override fun putString(key: String, value: String) {
        delegate.putString("$prefix$key", value)
    }

    override fun remove(key: String) {
        delegate.remove("$prefix$key")
    }

}
