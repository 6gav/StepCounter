package com.usfit.stepcounter;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.fitness.FitnessOptions;

public class SensorsClient extends GoogleApi<FitnessOptions> {
    protected SensorsClient(@NonNull Context context, Api<FitnessOptions> api, Looper looper) {
        super(context, api, looper);
    }
}
