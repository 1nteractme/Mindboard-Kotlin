package com.interactme.mindboard.data.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "ALTER TABLE ideas ADD COLUMN isFavorite INTEGER NOT NULL DEFAULT 0"
        )
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "ALTER TABLE collections ADD COLUMN color INTEGER NOT NULL DEFAULT 4294967295"
        )
        db.execSQL(
            """
                CREATE TABLE IF NOT EXISTS idea_collections (
                    ideaId INTEGER NOT NULL,
                    collectionId INTEGER NOT NULL,
                    PRIMARY KEY(ideaId, collectionId),
                    FOREIGN KEY(ideaId) REFERENCES ideas(id) ON DELETE CASCADE,
                    FOREIGN KEY(collectionId) REFERENCES collections(id) ON DELETE CASCADE
                )
            """.trimIndent()
        )
        db.execSQL("CREATE INDEX IF NOT EXISTS index_idea_collections_ideaId ON idea_collections(ideaId)")
        db.execSQL("CREATE INDEX IF NOT EXISTS index_idea_collections_collectionId ON idea_collections(collectionId)")
    }
}
