package com.example.asteroidradar.repository.api;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0011\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0004J\u0011\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0004\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0007"}, d2 = {"Lcom/example/asteroidradar/repository/api/AsteroidApiInterface;", "", "getAsteroidsFeed", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPicOfTheDay", "Lcom/example/asteroidradar/repository/database/PictureOfDay;", "app_debug"})
public abstract interface AsteroidApiInterface {
    
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.GET(value = "neo/rest/v1/feed?api_key=NbYUVZst0NfpCe3oPfQnOYfZNyUPLerj6vbpFjZZ")
    public abstract java.lang.Object getAsteroidsFeed(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.GET(value = "planetary/apod?api_key=NbYUVZst0NfpCe3oPfQnOYfZNyUPLerj6vbpFjZZ")
    public abstract java.lang.Object getPicOfTheDay(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.asteroidradar.repository.database.PictureOfDay> continuation);
}