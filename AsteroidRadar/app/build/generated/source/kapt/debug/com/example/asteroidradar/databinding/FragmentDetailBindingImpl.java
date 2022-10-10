package com.example.asteroidradar.databinding;
import com.example.asteroidradar.R;
import com.example.asteroidradar.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentDetailBindingImpl extends FragmentDetailBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.help_button, 7);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentDetailBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }
    private FragmentDetailBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[3]
            , (android.widget.ImageView) bindings[1]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[4]
            , (android.widget.ImageView) bindings[7]
            , (android.widget.TextView) bindings[5]
            );
        this.absoluteMagnitude.setTag(null);
        this.activityMainImageOfTheDay.setTag(null);
        this.closeApproachDate.setTag(null);
        this.distanceFromEarth.setTag(null);
        this.estimatedDiameter.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.relativeVelocity.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
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
        double asteroidEstimatedDiameter = 0.0;
        com.example.asteroidradar.repository.database.Asteroid asteroid = mAsteroid;
        double asteroidAbsoluteMagnitude = 0.0;
        java.lang.String asteroidCloseApproachDate = null;
        boolean asteroidIsPotentiallyHazardousAsteroid = false;
        double asteroidRelativeVelocity = 0.0;
        double asteroidDistanceFromEarth = 0.0;

        if ((dirtyFlags & 0x3L) != 0) {



                if (asteroid != null) {
                    // read asteroid.estimatedDiameter
                    asteroidEstimatedDiameter = asteroid.getEstimatedDiameter();
                    // read asteroid.absoluteMagnitude
                    asteroidAbsoluteMagnitude = asteroid.getAbsoluteMagnitude();
                    // read asteroid.closeApproachDate
                    asteroidCloseApproachDate = asteroid.getCloseApproachDate();
                    // read asteroid.is_potentially_hazardous_asteroid
                    asteroidIsPotentiallyHazardousAsteroid = asteroid.is_potentially_hazardous_asteroid();
                    // read asteroid.relativeVelocity
                    asteroidRelativeVelocity = asteroid.getRelativeVelocity();
                    // read asteroid.distanceFromEarth
                    asteroidDistanceFromEarth = asteroid.getDistanceFromEarth();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            com.example.asteroidradar.BindingAdaptersKt.bindTextViewToAstronomicalUnit(this.absoluteMagnitude, asteroidAbsoluteMagnitude);
            com.example.asteroidradar.BindingAdaptersKt.bindDetailsStatusImage(this.activityMainImageOfTheDay, asteroidIsPotentiallyHazardousAsteroid);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.closeApproachDate, asteroidCloseApproachDate);
            com.example.asteroidradar.BindingAdaptersKt.bindTextViewToAstronomicalUnit(this.distanceFromEarth, asteroidDistanceFromEarth);
            com.example.asteroidradar.BindingAdaptersKt.bindTextViewToKmUnit(this.estimatedDiameter, asteroidEstimatedDiameter);
            com.example.asteroidradar.BindingAdaptersKt.bindTextViewToDisplayVelocity(this.relativeVelocity, asteroidRelativeVelocity);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): asteroid
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}