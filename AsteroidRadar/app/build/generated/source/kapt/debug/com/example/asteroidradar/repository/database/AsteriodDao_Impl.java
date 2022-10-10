package com.example.asteroidradar.repository.database;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AsteriodDao_Impl implements AsteriodDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Asteroid> __insertionAdapterOfAsteroid;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAsteroids;

  public AsteriodDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAsteroid = new EntityInsertionAdapter<Asteroid>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `asteroid_table` (`id`,`name`,`closeApproachDate`,`absoluteMagnitude`,`estimatedDiameter`,`relativeVelocity`,`distanceFromEarth`,`is_potentially_hazardous_asteroid`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Asteroid value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getCloseApproachDate() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCloseApproachDate());
        }
        stmt.bindDouble(4, value.getAbsoluteMagnitude());
        stmt.bindDouble(5, value.getEstimatedDiameter());
        stmt.bindDouble(6, value.getRelativeVelocity());
        stmt.bindDouble(7, value.getDistanceFromEarth());
        final int _tmp = value.is_potentially_hazardous_asteroid() ? 1 : 0;
        stmt.bindLong(8, _tmp);
      }
    };
    this.__preparedStmtOfDeleteAsteroids = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM asteroid_table WHERE closeApproachDate < ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertAllAsteroids(final ArrayList<Asteroid> asteroids,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAsteroid.insert(asteroids);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteAsteroids(final long startDate,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAsteroids.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, startDate);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteAsteroids.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public Object getTodayAsteroids(final String today,
      final Continuation<? super List<Asteroid>> continuation) {
    final String _sql = "select * from asteroid_table where closeApproachDate = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (today == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, today);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Asteroid>>() {
      @Override
      public List<Asteroid> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCloseApproachDate = CursorUtil.getColumnIndexOrThrow(_cursor, "closeApproachDate");
          final int _cursorIndexOfAbsoluteMagnitude = CursorUtil.getColumnIndexOrThrow(_cursor, "absoluteMagnitude");
          final int _cursorIndexOfEstimatedDiameter = CursorUtil.getColumnIndexOrThrow(_cursor, "estimatedDiameter");
          final int _cursorIndexOfRelativeVelocity = CursorUtil.getColumnIndexOrThrow(_cursor, "relativeVelocity");
          final int _cursorIndexOfDistanceFromEarth = CursorUtil.getColumnIndexOrThrow(_cursor, "distanceFromEarth");
          final int _cursorIndexOfIsPotentiallyHazardousAsteroid = CursorUtil.getColumnIndexOrThrow(_cursor, "is_potentially_hazardous_asteroid");
          final List<Asteroid> _result = new ArrayList<Asteroid>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Asteroid _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpCloseApproachDate;
            if (_cursor.isNull(_cursorIndexOfCloseApproachDate)) {
              _tmpCloseApproachDate = null;
            } else {
              _tmpCloseApproachDate = _cursor.getString(_cursorIndexOfCloseApproachDate);
            }
            final double _tmpAbsoluteMagnitude;
            _tmpAbsoluteMagnitude = _cursor.getDouble(_cursorIndexOfAbsoluteMagnitude);
            final double _tmpEstimatedDiameter;
            _tmpEstimatedDiameter = _cursor.getDouble(_cursorIndexOfEstimatedDiameter);
            final double _tmpRelativeVelocity;
            _tmpRelativeVelocity = _cursor.getDouble(_cursorIndexOfRelativeVelocity);
            final double _tmpDistanceFromEarth;
            _tmpDistanceFromEarth = _cursor.getDouble(_cursorIndexOfDistanceFromEarth);
            final boolean _tmpIs_potentially_hazardous_asteroid;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPotentiallyHazardousAsteroid);
            _tmpIs_potentially_hazardous_asteroid = _tmp != 0;
            _item = new Asteroid(_tmpId,_tmpName,_tmpCloseApproachDate,_tmpAbsoluteMagnitude,_tmpEstimatedDiameter,_tmpRelativeVelocity,_tmpDistanceFromEarth,_tmpIs_potentially_hazardous_asteroid);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  @Override
  public Object getAllAsteroids(final Continuation<? super List<Asteroid>> continuation) {
    final String _sql = "select * from asteroid_table ORDER BY closeApproachDate";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Asteroid>>() {
      @Override
      public List<Asteroid> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCloseApproachDate = CursorUtil.getColumnIndexOrThrow(_cursor, "closeApproachDate");
          final int _cursorIndexOfAbsoluteMagnitude = CursorUtil.getColumnIndexOrThrow(_cursor, "absoluteMagnitude");
          final int _cursorIndexOfEstimatedDiameter = CursorUtil.getColumnIndexOrThrow(_cursor, "estimatedDiameter");
          final int _cursorIndexOfRelativeVelocity = CursorUtil.getColumnIndexOrThrow(_cursor, "relativeVelocity");
          final int _cursorIndexOfDistanceFromEarth = CursorUtil.getColumnIndexOrThrow(_cursor, "distanceFromEarth");
          final int _cursorIndexOfIsPotentiallyHazardousAsteroid = CursorUtil.getColumnIndexOrThrow(_cursor, "is_potentially_hazardous_asteroid");
          final List<Asteroid> _result = new ArrayList<Asteroid>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Asteroid _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpCloseApproachDate;
            if (_cursor.isNull(_cursorIndexOfCloseApproachDate)) {
              _tmpCloseApproachDate = null;
            } else {
              _tmpCloseApproachDate = _cursor.getString(_cursorIndexOfCloseApproachDate);
            }
            final double _tmpAbsoluteMagnitude;
            _tmpAbsoluteMagnitude = _cursor.getDouble(_cursorIndexOfAbsoluteMagnitude);
            final double _tmpEstimatedDiameter;
            _tmpEstimatedDiameter = _cursor.getDouble(_cursorIndexOfEstimatedDiameter);
            final double _tmpRelativeVelocity;
            _tmpRelativeVelocity = _cursor.getDouble(_cursorIndexOfRelativeVelocity);
            final double _tmpDistanceFromEarth;
            _tmpDistanceFromEarth = _cursor.getDouble(_cursorIndexOfDistanceFromEarth);
            final boolean _tmpIs_potentially_hazardous_asteroid;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPotentiallyHazardousAsteroid);
            _tmpIs_potentially_hazardous_asteroid = _tmp != 0;
            _item = new Asteroid(_tmpId,_tmpName,_tmpCloseApproachDate,_tmpAbsoluteMagnitude,_tmpEstimatedDiameter,_tmpRelativeVelocity,_tmpDistanceFromEarth,_tmpIs_potentially_hazardous_asteroid);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  @Override
  public Object getWeeksAsteroids(final String startDate, final String endDate,
      final Continuation<? super List<Asteroid>> continuation) {
    final String _sql = "SELECT * FROM asteroid_table where closeApproachDate \n"
            + "            BETWEEN ? AND ? \n"
            + "            ORDER BY closeApproachDate";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (startDate == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, startDate);
    }
    _argIndex = 2;
    if (endDate == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, endDate);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Asteroid>>() {
      @Override
      public List<Asteroid> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCloseApproachDate = CursorUtil.getColumnIndexOrThrow(_cursor, "closeApproachDate");
          final int _cursorIndexOfAbsoluteMagnitude = CursorUtil.getColumnIndexOrThrow(_cursor, "absoluteMagnitude");
          final int _cursorIndexOfEstimatedDiameter = CursorUtil.getColumnIndexOrThrow(_cursor, "estimatedDiameter");
          final int _cursorIndexOfRelativeVelocity = CursorUtil.getColumnIndexOrThrow(_cursor, "relativeVelocity");
          final int _cursorIndexOfDistanceFromEarth = CursorUtil.getColumnIndexOrThrow(_cursor, "distanceFromEarth");
          final int _cursorIndexOfIsPotentiallyHazardousAsteroid = CursorUtil.getColumnIndexOrThrow(_cursor, "is_potentially_hazardous_asteroid");
          final List<Asteroid> _result = new ArrayList<Asteroid>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Asteroid _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpCloseApproachDate;
            if (_cursor.isNull(_cursorIndexOfCloseApproachDate)) {
              _tmpCloseApproachDate = null;
            } else {
              _tmpCloseApproachDate = _cursor.getString(_cursorIndexOfCloseApproachDate);
            }
            final double _tmpAbsoluteMagnitude;
            _tmpAbsoluteMagnitude = _cursor.getDouble(_cursorIndexOfAbsoluteMagnitude);
            final double _tmpEstimatedDiameter;
            _tmpEstimatedDiameter = _cursor.getDouble(_cursorIndexOfEstimatedDiameter);
            final double _tmpRelativeVelocity;
            _tmpRelativeVelocity = _cursor.getDouble(_cursorIndexOfRelativeVelocity);
            final double _tmpDistanceFromEarth;
            _tmpDistanceFromEarth = _cursor.getDouble(_cursorIndexOfDistanceFromEarth);
            final boolean _tmpIs_potentially_hazardous_asteroid;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPotentiallyHazardousAsteroid);
            _tmpIs_potentially_hazardous_asteroid = _tmp != 0;
            _item = new Asteroid(_tmpId,_tmpName,_tmpCloseApproachDate,_tmpAbsoluteMagnitude,_tmpEstimatedDiameter,_tmpRelativeVelocity,_tmpDistanceFromEarth,_tmpIs_potentially_hazardous_asteroid);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
