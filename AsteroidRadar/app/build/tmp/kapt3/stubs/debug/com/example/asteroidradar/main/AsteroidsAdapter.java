package com.example.asteroidradar.main;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0003\u0010\u0011\u0012B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000bH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/example/asteroidradar/main/AsteroidsAdapter;", "Landroidx/recyclerview/widget/ListAdapter;", "Lcom/example/asteroidradar/repository/database/Asteroid;", "Lcom/example/asteroidradar/main/AsteroidsAdapter$AsteroidsViewHolder;", "clickListener", "Lcom/example/asteroidradar/main/AsteroidsAdapter$AsteroidClickListener;", "(Lcom/example/asteroidradar/main/AsteroidsAdapter$AsteroidClickListener;)V", "onBindViewHolder", "", "holder", "position", "", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "AsteroidClickListener", "AsteroidsDiffCallBack", "AsteroidsViewHolder", "app_debug"})
public final class AsteroidsAdapter extends androidx.recyclerview.widget.ListAdapter<com.example.asteroidradar.repository.database.Asteroid, com.example.asteroidradar.main.AsteroidsAdapter.AsteroidsViewHolder> {
    private final com.example.asteroidradar.main.AsteroidsAdapter.AsteroidClickListener clickListener = null;
    
    public AsteroidsAdapter(@org.jetbrains.annotations.NotNull()
    com.example.asteroidradar.main.AsteroidsAdapter.AsteroidClickListener clickListener) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.example.asteroidradar.main.AsteroidsAdapter.AsteroidsViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.example.asteroidradar.main.AsteroidsAdapter.AsteroidsViewHolder holder, int position) {
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/example/asteroidradar/main/AsteroidsAdapter$AsteroidsViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/example/asteroidradar/databinding/AsteroidItemBinding;", "(Lcom/example/asteroidradar/databinding/AsteroidItemBinding;)V", "bind", "", "item", "Lcom/example/asteroidradar/repository/database/Asteroid;", "clickListener", "Lcom/example/asteroidradar/main/AsteroidsAdapter$AsteroidClickListener;", "Companion", "app_debug"})
    public static final class AsteroidsViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private final com.example.asteroidradar.databinding.AsteroidItemBinding binding = null;
        @org.jetbrains.annotations.NotNull()
        public static final com.example.asteroidradar.main.AsteroidsAdapter.AsteroidsViewHolder.Companion Companion = null;
        
        private AsteroidsViewHolder(com.example.asteroidradar.databinding.AsteroidItemBinding binding) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.example.asteroidradar.repository.database.Asteroid item, @org.jetbrains.annotations.NotNull()
        com.example.asteroidradar.main.AsteroidsAdapter.AsteroidClickListener clickListener) {
        }
        
        @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/example/asteroidradar/main/AsteroidsAdapter$AsteroidsViewHolder$Companion;", "", "()V", "from", "Lcom/example/asteroidradar/main/AsteroidsAdapter$AsteroidsViewHolder;", "parent", "Landroid/view/ViewGroup;", "app_debug"})
        public static final class Companion {
            
            private Companion() {
                super();
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.example.asteroidradar.main.AsteroidsAdapter.AsteroidsViewHolder from(@org.jetbrains.annotations.NotNull()
            android.view.ViewGroup parent) {
                return null;
            }
        }
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016J\u0018\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016\u00a8\u0006\t"}, d2 = {"Lcom/example/asteroidradar/main/AsteroidsAdapter$AsteroidsDiffCallBack;", "Landroidx/recyclerview/widget/DiffUtil$ItemCallback;", "Lcom/example/asteroidradar/repository/database/Asteroid;", "()V", "areContentsTheSame", "", "oldItem", "newItem", "areItemsTheSame", "app_debug"})
    public static final class AsteroidsDiffCallBack extends androidx.recyclerview.widget.DiffUtil.ItemCallback<com.example.asteroidradar.repository.database.Asteroid> {
        
        public AsteroidsDiffCallBack() {
            super();
        }
        
        @java.lang.Override()
        public boolean areItemsTheSame(@org.jetbrains.annotations.NotNull()
        com.example.asteroidradar.repository.database.Asteroid oldItem, @org.jetbrains.annotations.NotNull()
        com.example.asteroidradar.repository.database.Asteroid newItem) {
            return false;
        }
        
        @java.lang.Override()
        public boolean areContentsTheSame(@org.jetbrains.annotations.NotNull()
        com.example.asteroidradar.repository.database.Asteroid oldItem, @org.jetbrains.annotations.NotNull()
        com.example.asteroidradar.repository.database.Asteroid newItem) {
            return false;
        }
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B(\u0012!\u0010\u0002\u001a\u001d\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\u0004\u0012\u00020\b0\u0003\u00a2\u0006\u0002\u0010\tJ\u000e\u0010\n\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\u0004R)\u0010\u0002\u001a\u001d\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\u0004\u0012\u00020\b0\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/example/asteroidradar/main/AsteroidsAdapter$AsteroidClickListener;", "", "clickListener", "Lkotlin/Function1;", "Lcom/example/asteroidradar/repository/database/Asteroid;", "Lkotlin/ParameterName;", "name", "asteroid", "", "(Lkotlin/jvm/functions/Function1;)V", "onClick", "app_debug"})
    public static final class AsteroidClickListener {
        private final kotlin.jvm.functions.Function1<com.example.asteroidradar.repository.database.Asteroid, kotlin.Unit> clickListener = null;
        
        public AsteroidClickListener(@org.jetbrains.annotations.NotNull()
        kotlin.jvm.functions.Function1<? super com.example.asteroidradar.repository.database.Asteroid, kotlin.Unit> clickListener) {
            super();
        }
        
        public final void onClick(@org.jetbrains.annotations.NotNull()
        com.example.asteroidradar.repository.database.Asteroid asteroid) {
        }
    }
}