package com.example.asteroidradar.repository.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AsteroidRadarDatabase_Impl extends AsteroidRadarDatabase {
  private volatile AsteriodDao _asteriodDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(5) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `asteroid_table` (`id` INTEGER NOT NULL, `name` TEXT NOT NULL, `closeApproachDate` TEXT NOT NULL, `absoluteMagnitude` REAL NOT NULL, `estimatedDiameter` REAL NOT NULL, `relativeVelocity` REAL NOT NULL, `distanceFromEarth` REAL NOT NULL, `is_potentially_hazardous_asteroid` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'e3c9ace38a611a9e3e966a632f0855ea')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `asteroid_table`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsAsteroidTable = new HashMap<String, TableInfo.Column>(8);
        _columnsAsteroidTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAsteroidTable.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAsteroidTable.put("closeApproachDate", new TableInfo.Column("closeApproachDate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAsteroidTable.put("absoluteMagnitude", new TableInfo.Column("absoluteMagnitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAsteroidTable.put("estimatedDiameter", new TableInfo.Column("estimatedDiameter", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAsteroidTable.put("relativeVelocity", new TableInfo.Column("relativeVelocity", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAsteroidTable.put("distanceFromEarth", new TableInfo.Column("distanceFromEarth", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAsteroidTable.put("is_potentially_hazardous_asteroid", new TableInfo.Column("is_potentially_hazardous_asteroid", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAsteroidTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAsteroidTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAsteroidTable = new TableInfo("asteroid_table", _columnsAsteroidTable, _foreignKeysAsteroidTable, _indicesAsteroidTable);
        final TableInfo _existingAsteroidTable = TableInfo.read(_db, "asteroid_table");
        if (! _infoAsteroidTable.equals(_existingAsteroidTable)) {
          return new RoomOpenHelper.ValidationResult(false, "asteroid_table(com.example.asteroidradar.repository.database.Asteroid).\n"
                  + " Expected:\n" + _infoAsteroidTable + "\n"
                  + " Found:\n" + _existingAsteroidTable);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "e3c9ace38a611a9e3e966a632f0855ea", "ca7458ce022fdf18eeeeb521ce5be49a");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "asteroid_table");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `asteroid_table`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(AsteriodDao.class, AsteriodDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public AsteriodDao getAsteroidDao() {
    if (_asteriodDao != null) {
      return _asteriodDao;
    } else {
      synchronized(this) {
        if(_asteriodDao == null) {
          _asteriodDao = new AsteriodDao_Impl(this);
        }
        return _asteriodDao;
      }
    }
  }
}
