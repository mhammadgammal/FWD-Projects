package com.example.asteroidradar.databinding;
import com.example.asteroidradar.R;
import com.example.asteroidradar.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class AsteroidItemBindingImpl extends AsteroidItemBinding implements com.example.asteroidradar.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.guideline2, 4);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback1;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public AsteroidItemBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }
    private AsteroidItemBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.constraintlayout.widget.Guideline) bindings[4]
            , (android.widget.ImageView) bindings[3]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[2]
            );
        this.imageView.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.textView2.setTag(null);
        this.textView3.setTag(null);
        setRootTag(root);
        // listeners
        mCallback1 = new com.example.asteroidradar.generated.callback.OnClickListener(this, 1);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x4L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.asteroid == variableId) {
            setAsteroid((com.example.asteroidradar.repository.database.Asteroid) variable);
        }
        else if (BR.click == variableId) {
            setClick((com.example.asteroidradar.main.AsteroidsAdapter.AsteroidClickListener) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setAsteroid(@Nullable com.example.asteroidradar.repository.database.Asteroid Asteroid) {
        this.mAsteroid = Asteroid;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.asteroid);
        super.requestRebind();
    }
    public void setClick(@Nullable com.example.asteroidradar.main.AsteroidsAdapter.AsteroidClickListener Click) {
        this.mClick = Click;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.click);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        com.example.asteroidradar.repository.database.Asteroid asteroid = mAsteroid;
        java.lang.String asteroidName = null;
        java.lang.String asteroidCloseApproachDate = null;
        boolean asteroidIsPotentiallyHazardousAsteroid = false;
        com.example.asteroidradar.main.AsteroidsAdapter.AsteroidClickListener click = mClick;

        if ((dirtyFlags & 0x5L) != 0) {



                if (asteroid != null) {
                    // read asteroid.name
                    asteroidName = asteroid.getName();
                    // read asteroid.closeApproachDate
                    asteroidCloseApproachDate = asteroid.getCloseApproachDate();
                    // read asteroid.is_potentially_hazardous_asteroid
                    asteroidIsPotentiallyHazardousAsteroid = asteroid.is_potentially_hazardous_asteroid();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x5L) != 0) {
            // api target 1

            com.example.asteroidradar.BindingAdaptersKt.bindAsteroidStatusImage(this.imageView, asteroidIsPotentiallyHazardousAsteroid);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.textView2, asteroidName);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.textView3, asteroidCloseApproachDate);
        }
        if ((dirtyFlags & 0x4L) != 0) {
            // api target 1

            this.mboundView0.setOnClickListener(mCallback1);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        // localize variables for thread safety
        // asteroid
        com.example.asteroidradar.repository.database.Asteroid asteroid = mAsteroid;
        // click
        com.example.asteroidradar.main.AsteroidsAdapter.AsteroidClickListener click = mClick;
        // click != null
        boolean clickJavaLangObjectNull = false;



        clickJavaLangObjectNull = (click) != (null);
        if (clickJavaLangObjectNull) {



            click.onClick(asteroid);
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): asteroid
        flag 1 (0x2L): click
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}