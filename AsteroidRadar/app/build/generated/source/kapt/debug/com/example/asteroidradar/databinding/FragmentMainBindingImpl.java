package com.example.asteroidradar.databinding;
import com.example.asteroidradar.R;
import com.example.asteroidradar.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentMainBindingImpl extends FragmentMainBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.asteroid_recycler, 3);
        sViewsWithIds.put(R.id.status_loading_wheel, 4);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentMainBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }
    private FragmentMainBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.widget.ImageView) bindings[1]
            , (androidx.recyclerview.widget.RecyclerView) bindings[3]
            , (android.widget.ProgressBar) bindings[4]
            , (android.widget.TextView) bindings[2]
            );
        this.activityMainImageOfTheDay.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.textView.setTag(null);
        setRootTag(root);
        // listeners
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
        if (BR.viewModel == variableId) {
            setViewModel((com.example.asteroidradar.main.MainViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable com.example.asteroidradar.main.MainViewModel ViewModel) {
        this.mViewModel = ViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeViewModelPic((androidx.lifecycle.LiveData<com.example.asteroidradar.repository.database.PictureOfDay>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModelPic(androidx.lifecycle.LiveData<com.example.asteroidradar.repository.database.PictureOfDay> ViewModelPic, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
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
        java.lang.String viewModelPicTitle = null;
        com.example.asteroidradar.repository.database.PictureOfDay viewModelPicGetValue = null;
        java.lang.String viewModelPicUrl = null;
        com.example.asteroidradar.main.MainViewModel viewModel = mViewModel;
        androidx.lifecycle.LiveData<com.example.asteroidradar.repository.database.PictureOfDay> viewModelPic = null;

        if ((dirtyFlags & 0x7L) != 0) {



                if (viewModel != null) {
                    // read viewModel.pic
                    viewModelPic = viewModel.getPic();
                }
                updateLiveDataRegistration(0, viewModelPic);


                if (viewModelPic != null) {
                    // read viewModel.pic.getValue()
                    viewModelPicGetValue = viewModelPic.getValue();
                }


                if (viewModelPicGetValue != null) {
                    // read viewModel.pic.getValue().title
                    viewModelPicTitle = viewModelPicGetValue.getTitle();
                    // read viewModel.pic.getValue().url
                    viewModelPicUrl = viewModelPicGetValue.getUrl();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x7L) != 0) {
            // api target 1

            com.example.asteroidradar.BindingAdaptersKt.downloadPic(this.activityMainImageOfTheDay, viewModelPicUrl);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.textView, viewModelPicTitle);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewModel.pic
        flag 1 (0x2L): viewModel
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}