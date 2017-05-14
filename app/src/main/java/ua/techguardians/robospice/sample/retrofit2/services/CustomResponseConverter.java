package ua.techguardians.robospice.sample.retrofit2.services;

import com.octo.android.robospice.persistence.retrofit2.converter.RetrofitGsonResponseConverter;

import java.io.OutputStream;
import java.util.Date;

import ua.techguardians.robospice.sample.retrofit2.responses.SingleMessageJson;

/**
 * Created on 5/7/16.
 * Author: Anton Mykolaichuk
 */
class CustomResponseConverter extends RetrofitGsonResponseConverter {
    @Override
    public void saveObject(Object object, Class<?> clzz, OutputStream out) throws Exception {
        if (clzz.equals(SingleMessageJson.class)) {
            // Here you could modify any field you want inside a received object before saving
            // it to cache.
            // For example we set a "timestamp" field
            ((SingleMessageJson) object).setTimestamp(new Date().toString());
        }
        super.saveObject(object, clzz, out);
    }
}
