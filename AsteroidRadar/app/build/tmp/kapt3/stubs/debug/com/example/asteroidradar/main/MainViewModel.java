package com.example.asteroidradar.main;

import java.lang.System;

@androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.O)
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010#\u001a\u00020$H\u0002J\u000e\u0010\u0016\u001a\u00020$2\u0006\u0010%\u001a\u00020\bJ\b\u0010&\u001a\u00020$H\u0002J\b\u0010\'\u001a\u00020$H\u0002J\b\u0010\u001f\u001a\u00020$H\u0002J\b\u0010!\u001a\u00020$H\u0002R\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u000f8F\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\b0\u000f8F\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0011R\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000f8F\u00a2\u0006\u0006\u001a\u0004\b\u0019\u0010\u0011R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001bX\u0082D\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u000f8F\u00a2\u0006\u0006\u001a\u0004\b \u0010\u0011R\u001d\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u000f8F\u00a2\u0006\u0006\u001a\u0004\b\"\u0010\u0011\u00a8\u0006("}, d2 = {"Lcom/example/asteroidradar/main/MainViewModel;", "Landroidx/lifecycle/ViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "_allAsteroids", "Landroidx/lifecycle/MutableLiveData;", "", "Lcom/example/asteroidradar/repository/database/Asteroid;", "_navToDetailFragment", "_pic", "Lcom/example/asteroidradar/repository/database/PictureOfDay;", "_todayAsteroids", "_weekAsteroids", "allAsteroid", "Landroidx/lifecycle/LiveData;", "getAllAsteroid", "()Landroidx/lifecycle/LiveData;", "currentDate", "Ljava/util/Date;", "db", "Lcom/example/asteroidradar/repository/database/AsteroidRadarDatabase;", "navToDetailFragment", "getNavToDetailFragment", "pic", "getPic", "picType", "", "repo", "Lcom/example/asteroidradar/repository/AsteroidRepo;", "tag", "todayAsteroids", "getTodayAsteroids", "weekAsteroid", "getWeekAsteroid", "allAsteroids", "", "asteroid", "refreshAsteroids", "refreshPic", "app_debug"})
public final class MainViewModel extends androidx.lifecycle.ViewModel {
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.example.asteroidradar.repository.database.Asteroid>> _weekAsteroids = null;
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.example.asteroidradar.repository.database.Asteroid>> _allAsteroids = null;
    private final androidx.lifecycle.MutableLiveData<com.example.asteroidradar.repository.database.Asteroid> _navToDetailFragment = null;
    private final com.example.asteroidradar.repository.database.AsteroidRadarDatabase db = null;
    private final com.example.asteroidradar.repository.AsteroidRepo repo = null;
    private final androidx.lifecycle.MutableLiveData<com.example.asteroidradar.repository.database.PictureOfDay> _pic = null;
    private final androidx.lifecycle.MutableLiveData<java.lang.String> picType = null;
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.example.asteroidradar.repository.database.Asteroid>> _todayAsteroids = null;
    private final java.util.Date currentDate = null;
    private final java.lang.String tag = "MainViewModel";
    
    public MainViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.example.asteroidradar.repository.database.Asteroid>> getWeekAsteroid() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.example.asteroidradar.repository.database.Asteroid>> getAllAsteroid() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.example.asteroidradar.repository.database.Asteroid> getNavToDetailFragment() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<com.example.asteroidradar.repository.database.PictureOfDay> getPic() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<java.util.List<com.example.asteroidradar.repository.database.Asteroid>> getTodayAsteroids() {
        return null;
    }
    
    private final void refreshPic() {
    }
    
    private final void refreshAsteroids() {
    }
    
    private final void todayAsteroids() {
    }
    
    private final void weekAsteroid() {
    }
    
    private final void allAsteroids() {
    }
    
    public final void navToDetailFragment(@org.jetbrains.annotations.NotNull()
    com.example.asteroidradar.repository.database.Asteroid asteroid) {
    }
}