package by.rozov.htp.android.androidversions;

import android.content.Context;
import android.support.annotation.IntegerRes;
import android.support.annotation.StringRes;

/**
 * Created by krozov on 12.08.14.
 */
public class AndroidVersion {
    private final String mName;
    private final int mCode;
    private final String mReleaseNumber;

    public AndroidVersion(String name, int code, String releaseNumber) {
        mName = name;
        mCode = code;
        mReleaseNumber = releaseNumber;
    }

    public AndroidVersion(Context context, @StringRes int nameResId,
                          @IntegerRes int codeResId, @StringRes int releaseResId) {
        this(context.getString(nameResId),
                context.getResources().getInteger(codeResId),
                context.getString(releaseResId));
    }

    public String getName() {
        return mName;
    }

    public int getCode() {
        return mCode;
    }

    public String getReleaseNumber() {
        return mReleaseNumber;
    }
}
