package com.example.data.db

import android.content.Context

class AppDatabase_Impl(context: Context) : SQLiteAppDatabase(context) {
    constructor() : this(com.example.data.db.AppDatabaseContextHolder.appContext ?: error("Context not available"))
}

object AppDatabaseContextHolder {
    var appContext: Context? = null
}
